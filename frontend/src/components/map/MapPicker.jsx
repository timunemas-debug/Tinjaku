import { useEffect, useState } from "react";
import {
  MapContainer,
  TileLayer,
  Marker,
  useMapEvents,
  useMap,
} from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";


delete L.Icon.Default.prototype._getIconUrl;

L.Icon.Default.mergeOptions({
  iconRetinaUrl:
    "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/images/marker-icon-2x.png",
  iconUrl:
    "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/images/marker-icon.png",
  shadowUrl:
    "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/images/marker-shadow.png",
});


const DEFAULT_POSITION = [-6.2, 106.816666];



function MapClickHandler({ onSelect }) {
  useMapEvents({
    click(e) {
      const { lat, lng } = e.latlng;

      onSelect(lat, lng);
    },
  });

  return null;
}



function MapMover({ position }) {
  const map = useMap();

  useEffect(() => {
    if (position) {
      map.flyTo(position, 17, {
        duration: 1,
      });
    }
  }, [position, map]);

  return null;
}



export default function MapPicker({ onLocationSelect }) {
  const [position, setPosition] = useState(null);
  const [address, setAddress] = useState("");
  const [loadingLocation, setLoadingLocation] = useState(false);
  const [loadingAddress, setLoadingAddress] = useState(false);
  const [error, setError] = useState("");

  
  
  const getAddressFromCoordinates = async (lat, lng) => {
    setLoadingAddress(true);
    setError("");

    try {
      const response = await fetch(
        `https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${lat}&lon=${lng}&accept-language=id`
      );

      if (!response.ok) {
        throw new Error("Gagal mengambil alamat.");
      }

      const data = await response.json();

      const displayAddress =
        data.display_name || "Alamat tidak ditemukan";

      setAddress(displayAddress);

      
      if (onLocationSelect) {
        onLocationSelect({
          latitude: lat,
          longitude: lng,
          address: displayAddress,
          raw: data,
        });
      }
    } catch (err) {
      console.error("Reverse geocoding error:", err);

      setAddress("");

      setError(
        "Lokasi berhasil dipilih, tetapi alamat tidak dapat ditemukan."
      );

      
      if (onLocationSelect) {
        onLocationSelect({
          latitude: lat,
          longitude: lng,
          address: "",
          raw: null,
        });
      }
    } finally {
      setLoadingAddress(false);
    }
  };


  
  const selectLocation = async (lat, lng) => {
    setPosition([lat, lng]);

    await getAddressFromCoordinates(lat, lng);
  };


  
  const handleUseCurrentLocation = () => {
    if (!navigator.geolocation) {
      setError("Browser kamu tidak mendukung lokasi.");

      return;
    }

    setLoadingLocation(true);
    setError("");

    navigator.geolocation.getCurrentPosition(
      async (location) => {
        const lat = location.coords.latitude;
        const lng = location.coords.longitude;

        await selectLocation(lat, lng);

        setLoadingLocation(false);
      },

      (err) => {
        console.error("Geolocation error:", err);

        let message = "Gagal mendapatkan lokasi.";

        if (err.code === 1) {
          message =
            "Izin lokasi ditolak. Izinkan lokasi untuk Firefox lalu coba lagi.";
        }

        if (err.code === 2) {
          message =
            "Lokasi tidak tersedia. Pastikan perangkat terhubung ke jaringan.";
        }

        if (err.code === 3) {
          message =
            "Pengambilan lokasi terlalu lama. Silakan coba lagi.";
        }

        setError(message);
        setLoadingLocation(false);
      },

      {
        enableHighAccuracy: true,
        timeout: 15000,
        maximumAge: 0,
      }
    );
  };


  return (
    <div className="space-y-4">

      {/* ================================================
          MAP
      ================================================= */}
      <div className="overflow-hidden rounded-2xl border border-black/[0.10]">
        <MapContainer
          center={position || DEFAULT_POSITION}
          zoom={position ? 17 : 13}
          scrollWheelZoom={true}
          className="w-full h-[350px]"
        >

          {/* OpenStreetMap */}
          <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />

          {/* Klik peta */}
          <MapClickHandler onSelect={selectLocation} />

          
          {position && <MapMover position={position} />}

          
          {position && <Marker position={position} />}
        </MapContainer>
      </div>


      
      <button
        type="button"
        onClick={handleUseCurrentLocation}
        disabled={loadingLocation}
        className="
          w-full
          flex
          items-center
          justify-center
          gap-2
          bg-[#FFC800]
          text-[#111116]
          rounded-xl
          py-3.5
          px-4
          text-sm
          font-bold
          hover:brightness-95
          disabled:opacity-50
          disabled:cursor-not-allowed
          transition
        "
      >
        {loadingLocation ? (
          <>
            <div className="w-4 h-4 border-2 border-black/20 border-t-black rounded-full animate-spin" />

            Mencari lokasi...
          </>
        ) : (
          <>
            📍 Gunakan lokasi saya
          </>
        )}
      </button>


      
      {!position && !error && (
        <div className="bg-[#FFF9E6] border border-[#FFC800]/30 rounded-xl px-4 py-3">
          <p className="text-xs text-black/55 leading-relaxed">
            Klik titik pada peta untuk memilih lokasi layanan,
            atau tekan <b>Gunakan lokasi saya</b> untuk mengambil
            lokasi dari perangkat kamu.
          </p>
        </div>
      )}


      
      {error && (
        <div className="bg-[#FFF0F0] border border-[#F3CACA] text-[#C43D3D] rounded-xl px-4 py-3">
          <p className="text-sm">{error}</p>
        </div>
      )}


      
      {position && (
        <div className="bg-white border border-black/[0.08] rounded-xl p-4">

          <div className="flex items-start gap-3">

            <div className="w-9 h-9 rounded-xl bg-[#FFF4CC] flex items-center justify-center shrink-0">
              <span className="text-base">📍</span>
            </div>

            <div className="min-w-0 flex-1">

              <p className="text-xs font-bold text-black/40 uppercase tracking-wide mb-1">
                Lokasi dipilih
              </p>

              {loadingAddress ? (
                <div className="flex items-center gap-2">
                  <div className="w-3.5 h-3.5 border-2 border-black/10 border-t-black rounded-full animate-spin" />

                  <p className="text-sm text-black/40">
                    Mencari alamat...
                  </p>
                </div>
              ) : (
                <p className="text-sm font-semibold text-[#111116] leading-relaxed">
                  {address || "Alamat tidak ditemukan"}
                </p>
              )}

            </div>

          </div>


          {/* Koordinat */}
          <div className="grid grid-cols-2 gap-3 mt-4">

            <div className="bg-black/[0.03] rounded-lg px-3 py-2">
              <p className="text-[10px] uppercase tracking-wide text-black/35 font-bold">
                Latitude
              </p>

              <p className="text-xs font-mono text-black/70 mt-1">
                {position[0].toFixed(6)}
              </p>
            </div>

            <div className="bg-black/[0.03] rounded-lg px-3 py-2">
              <p className="text-[10px] uppercase tracking-wide text-black/35 font-bold">
                Longitude
              </p>

              <p className="text-xs font-mono text-black/70 mt-1">
                {position[1].toFixed(6)}
              </p>
            </div>

          </div>

        </div>
      )}

    </div>
  );
}