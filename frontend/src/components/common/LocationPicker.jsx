import { useState } from "react";
import { FiMapPin, FiCheck } from "react-icons/fi";
import { updateLocation } from "../../services/userService";

export default function LocationPicker({ onSaved }) {
  const [status, setStatus] = useState("idle"); // idle | loading | success | error
  const [error, setError] = useState("");
  const [coords, setCoords] = useState(null);

  const handleGetLocation = () => {
    if (!navigator.geolocation) {
      setError("Browser kamu gak support geolocation.");
      setStatus("error");
      return;
    }

    setStatus("loading");
    setError("");

    navigator.geolocation.getCurrentPosition(
      async (position) => {
        const { latitude, longitude } = position.coords;
        setCoords({ latitude, longitude });

        try {
          await updateLocation(latitude, longitude);
          setStatus("success");
          onSaved?.();
        } catch (err) {
          setError(err.message);
          setStatus("error");
        }
      },
      (geoError) => {
        const messages = {
          1: "Akses lokasi ditolak. Izinkan akses lokasi di browser kamu.",
          2: "Lokasi gak bisa dideteksi. Coba lagi.",
          3: "Waktu deteksi lokasi habis. Coba lagi.",
        };
        setError(messages[geoError.code] || "Gagal mendapatkan lokasi.");
        setStatus("error");
      }
    );
  };

  return (
    <div className="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
      <div className="flex items-center gap-3 mb-4">
        <div className="w-11 h-11 rounded-full bg-accent/25 flex items-center justify-center">
          <FiMapPin size={18} className="text-ink" />
        </div>
        <div>
          <h2 className="font-display font-bold text-lg text-ink">Lokasi Kamu</h2>
          <p className="font-body text-sm text-ink/50">
            Wajib diisi supaya kamu bisa bikin pesanan.
          </p>
        </div>
      </div>

      {status === "success" && (
        <div className="flex items-center gap-2 bg-green-50 border border-green-200 rounded-xl px-4 py-3 mb-4">
          <FiCheck size={16} className="text-green-600 shrink-0" />
          <p className="font-body text-sm text-green-700">
            Lokasi berhasil disimpan
            {coords && (
              <span className="text-green-600/70">
                {" "}({coords.latitude.toFixed(5)}, {coords.longitude.toFixed(5)})
              </span>
            )}
          </p>
        </div>
      )}

      {error && (
        <p className="font-body text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2 mb-4">
          {error}
        </p>
      )}

      <button
        onClick={handleGetLocation}
        disabled={status === "loading"}
        className="w-full font-body font-bold text-sm text-ink bg-accent rounded-full py-3 hover:brightness-95 disabled:opacity-60 flex items-center justify-center gap-2"
      >
        <FiMapPin size={16} />
        {status === "loading" ? "Mendeteksi lokasi..." : "Gunakan Lokasi Saya"}
      </button>

      <p className="font-body text-xs text-ink/40 mt-3 text-center">
        Browser bakal minta izin akses lokasi — klik "Izinkan"/"Allow".
      </p>
    </div>
  );
}