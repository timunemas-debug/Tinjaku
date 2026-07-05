package com.tinjaku.mapper;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.request.RatingRequest;
import com.tinjaku.dto.response.RatingResponse;
import com.tinjaku.model.Rating;

@Component
public class RatingMapper {

    public Rating toEntity(RatingRequest request){
        Rating rating = new Rating();

        rating.setRating(request.getRating());
        rating.setDeskripsi(request.getDeskripsi());

        return rating;
    }
    
    public RatingResponse toResponse(Rating rating){
        return new RatingResponse(rating.getRating(),
                                  rating.getDeskripsi(),
                                  rating.getMitra().getNamaMitra());
    }
}