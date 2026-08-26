package com.tinjaku.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tinjaku.model.Mitra;
import com.tinjaku.model.Pesanan;
import com.tinjaku.model.StatusOnOff;
import com.tinjaku.repository.MitraRepository;
import com.tinjaku.repository.RatingRepository;
import com.tinjaku.repository.projection.RatingSummary;

@Service
public class MitraMatchingService {
    
    private final MitraRepository mitraRepository;
    private final RatingRepository ratingRepository;

    public MitraMatchingService(MitraRepository mitraRepository, RatingRepository ratingRepository){
        this.mitraRepository = mitraRepository;
        this.ratingRepository =ratingRepository;
    }

        /*
        Rumus Haversine Formula :
        Δlat = lat2- lat1
        Δlong = long2- long1
        a = sin2 (Δlat/2) + cos(lat1).cos(lat2).sin2 (Δlong/2)
        c = 2atan2(√a, √1-a)
        d = R.c
        Keterangan :
        R = jari-jari bumi sebesar 6371(km)
        Δlat = besaran perubahan latitude
        Δlong = besaran perubahan longitude
        C = kalkulasi perpotongan sumbu
        d = jarak (km)
        1 derajat = 0.0174532925 radian */
        public double calculateDistance(double lat1, double lon1, double lat2, double lon2){
    
            double earthRadius = 6371.0;
    
            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);
    
            double a =
                        Math.sin(dLat / 2) * Math.sin(dLat / 2)
                        + Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2)
                        * Math.sin(dLon / 2);
            
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    
            return earthRadius * c;
        }

        /*
        GET ELIGIBLE MITRA INI MENENTUKAN RATING MITRA HARUS DI ATAS 1.5 DAN JARAKNYA HARUS KURANG DARI 10
        JIKA TIDAK SESUAI DENGAN ITU BERARTI TIDAK TERMASUK MITRA YANG ELIGIBLE
        */
        public List<Mitra> getEligibleMitra(Pesanan pesanan){

        List<Mitra> eligibleMitra = new ArrayList<>();
        List<Mitra> mitras = mitraRepository.findByStatusOnOff(StatusOnOff.ONLINE);

        List<Long> mitraIds = mitras.stream()
                .map(Mitra::getMitraId)
                .toList();

        Map<Long, RatingSummary> ratingMap = mitraIds.isEmpty() ? Map.of() : ratingRepository.getRatingSummaryByMitraIds(mitraIds)
                        .stream()
                        .collect(Collectors.toMap(RatingSummary::getMitraId, r -> r));

        for(Mitra mitra : mitras){

            RatingSummary summary = ratingMap.get(mitra.getMitraId());

            Double rating = summary != null && summary.getAvgRating() != null ? summary.getAvgRating() : 0.0;

            if (rating <= 1.5) {
                continue;
            }
            if (mitra.getLatitude() == null || mitra.getLongitude() == null) {
                continue;
            }

            double distance = calculateDistance(pesanan.getUser().getPickupLat(), pesanan.getUser().getPickupLong(), mitra.getLatitude(), mitra.getLongitude());

            if (distance > 10) {
                continue;
            }

            eligibleMitra.add(mitra);
        }

        return eligibleMitra;
    }
}