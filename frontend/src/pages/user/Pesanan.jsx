import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  FiMapPin,
  FiUser,
  FiFileText,
  FiArrowRight,
  FiCheck,
} from "react-icons/fi";

import MapPicker from "../../components/map/MapPicker";

import { createPesanan } from "../../services/pesananService";
import { createAlamat } from "../../services/alamatService";
import { updateLocation } from "../../services/userService";

import { useAuth } from "../../hooks/useAuth";

const LABEL_OPTIONS = [
  "RUMAH",
  "KANTOR",
  "APARTMENT",
  "HOTEL",
  "GUDANG",
  "PABRIK",
];

const UKURAN_OPTIONS = ["KECIL", "SEDANG", "BESAR"];

export default function Pesanan() {
  const { user } = useAuth();
  const navigate = useNavigate();

  const [location, setLocation] = useState(null);

  const [form, setForm] = useState({
    namaPenerima: "",
    keluhan: "",
    label: "RUMAH",
    ukuranSepticTank: "SEDANG",
  });

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;

    setForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleLocationSelect = async (selectedLocation) => {
    setLocation(selectedLocation);
    setError("");

    try {
      await updateLocation(
        selectedLocation.latitude,
        selectedLocation.longitude
      );
    } catch (err) {
      console.error("Gagal menyimpan lokasi:", err);
    }
  };

  const getKota = (rawAddress) => {
    if (!rawAddress) return null;

    const address = JSON.stringify(rawAddress).toUpperCase();

    if (address.includes("JAKARTA")) return "JAKARTA";
    if (address.includes("BOGOR")) return "BOGOR";
    if (address.includes("DEPOK")) return "DEPOK";
    if (address.includes("BEKASI")) return "BEKASI";
    if (address.includes("TANGERANG")) return "TANGERANG";

    return null;
  };

  const buildAlamatData = () => {
    const raw = location?.raw?.address || {};

    const jalan =
      raw.road ||
      raw.pedestrian ||
      raw.footway ||
      raw.residential ||
      location?.address ||
      "Lokasi yang dipilih di peta";

    const jalanLengkap = raw.house_number
      ? `${jalan} No. ${raw.house_number}`
      : jalan;

    const kelurahan =
      raw.village ||
      raw.suburb ||
      raw.neighbourhood ||
      raw.quarter ||
      "Lokasi terpilih";

    const kecamatan =
      raw.city_district ||
      raw.municipality ||
      raw.county ||
      raw.suburb ||
      "Lokasi terpilih";

    const provinsi =
      raw.state ||
      raw.region ||
      "Banten";

    return {
      label: form.label,
      jalan: jalanLengkap,
      kelurahan,
      kecamatan,
      kota: getKota(raw),
      provinsi,
    };
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    setError("");

    if (!user?.userId) {
      setError("Data user tidak ditemukan. Silakan login kembali.");
      return;
    }

    if (!location?.latitude || !location?.longitude) {
      setError("Silakan pilih lokasi layanan terlebih dahulu.");
      return;
    }

    if (!location?.address) {
      setError("Alamat belum ditemukan. Silakan pilih lokasi lagi.");
      return;
    }

    try {
      setLoading(true);

      const alamatData = buildAlamatData();

      if (!alamatData.kota) {
        throw new Error(
          "Kota dari lokasi belum dapat dikenali. Silakan pilih lokasi yang lebih tepat."
        );
      }

      const alamatBaru = await createAlamat(alamatData);

      const alamatId =
        alamatBaru?.idAlamat ??
        alamatBaru?.alamatId ??
        alamatBaru?.id;

      if (!alamatId) {
        throw new Error("ID alamat tidak ditemukan.");
      }

      const pesananData = {
        namaPenerima: form.namaPenerima,
        alamatId: Number(alamatId),
        keluhan: form.keluhan,
        label: form.label,
        ukuranSepticTank: form.ukuranSepticTank,
      };

      await createPesanan(user.userId, pesananData);

      navigate("/riwayat");
    } catch (err) {
      console.error("Gagal membuat pesanan:", err);

      setError(
        err?.response?.data?.message ||
          err?.response?.data?.error ||
          err?.message ||
          "Gagal membuat pesanan."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-5xl mx-auto">
      <div className="mb-8">
        <p className="text-sm text-black/40 mb-2">
          Layanan
        </p>

        <h1 className="font-display font-extrabold text-3xl text-[#111116]">
          Buat Pesanan
        </h1>

        <p className="text-sm text-black/50 mt-2 max-w-xl">
          Pilih lokasi layanan dan lengkapi kebutuhan pesanan
          kamu.
        </p>
      </div>

      <div className="grid lg:grid-cols-[1fr_300px] gap-6">
        <form
          onSubmit={handleSubmit}
          className="bg-white border border-black/[0.07] rounded-2xl overflow-hidden"
        >
          <div className="px-7 py-6 border-b border-black/[0.06]">
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 rounded-xl bg-[#FFF4CC] flex items-center justify-center">
                <FiFileText size={18} />
              </div>

              <div>
                <h2 className="font-display font-bold text-lg">
                  Detail Pesanan
                </h2>

                <p className="text-xs text-black/40 mt-0.5">
                  Tentukan lokasi dan kebutuhan layanan
                </p>
              </div>
            </div>
          </div>

          <div className="p-7 max-md:p-5">
            {error && (
              <div className="mb-6 bg-[#FFF0F0] border border-[#F3CACA] text-[#C43D3D] rounded-xl px-4 py-3 text-sm">
                {error}
              </div>
            )}

            <div className="mb-6">
              <label className="flex items-center gap-2 text-sm font-bold mb-2">
                <FiUser size={15} />
                Nama Penerima
              </label>

              <input
                name="namaPenerima"
                value={form.namaPenerima}
                onChange={handleChange}
                placeholder="Masukkan nama penerima"
                className="w-full border border-black/[0.12] rounded-xl px-4 py-3.5 text-sm outline-none focus:border-[#FFC800] focus:ring-2 focus:ring-[#FFC800]/20"
                required
              />
            </div>

            <div className="mb-7">
              <div className="flex items-center gap-2 text-sm font-bold mb-2">
                <FiMapPin size={15} />
                Lokasi Layanan
              </div>

              <p className="text-xs text-black/40 mb-4">
                Pilih lokasi di peta atau gunakan lokasi kamu.
                Alamat akan terdeteksi otomatis.
              </p>

              <MapPicker
                onLocationSelect={handleLocationSelect}
              />
            </div>

            {location && (
              <div className="mb-7 bg-[#F5FFF7] border border-[#CDEBD4] rounded-xl px-4 py-3">
                <div className="flex items-center gap-2">
                  <FiCheck
                    size={16}
                    className="text-green-600"
                  />

                  <p className="text-sm font-semibold text-green-700">
                    Lokasi layanan sudah dipilih
                  </p>
                </div>
              </div>
            )}

            <div className="grid grid-cols-2 gap-4 mb-6">
              <div>
                <label className="text-sm font-bold block mb-2">
                  Tipe Lokasi
                </label>

                <select
                  name="label"
                  value={form.label}
                  onChange={handleChange}
                  className="w-full border border-black/[0.12] rounded-xl px-4 py-3.5 text-sm bg-white outline-none focus:border-[#FFC800]"
                  required
                >
                  {LABEL_OPTIONS.map((option) => (
                    <option
                      key={option}
                      value={option}
                    >
                      {option}
                    </option>
                  ))}
                </select>
              </div>

              <div>
                <label className="text-sm font-bold block mb-2">
                  Ukuran Septic Tank
                </label>

                <select
                  name="ukuranSepticTank"
                  value={form.ukuranSepticTank}
                  onChange={handleChange}
                  className="w-full border border-black/[0.12] rounded-xl px-4 py-3.5 text-sm bg-white outline-none focus:border-[#FFC800]"
                  required
                >
                  {UKURAN_OPTIONS.map((option) => (
                    <option
                      key={option}
                      value={option}
                    >
                      {option}
                    </option>
                  ))}
                </select>
              </div>
            </div>

            <div className="mb-7">
              <label className="flex items-center gap-2 text-sm font-bold mb-2">
                <FiFileText size={15} />
                Keluhan / Kebutuhan
              </label>

              <textarea
                name="keluhan"
                value={form.keluhan}
                onChange={handleChange}
                placeholder="Contoh: WC mampet dan air sulit mengalir..."
                rows={5}
                className="w-full border border-black/[0.12] rounded-xl px-4 py-3.5 text-sm outline-none focus:border-[#FFC800] focus:ring-2 focus:ring-[#FFC800]/20 resize-none"
                required
              />

              <p className="text-xs text-black/30 mt-2">
                Jelaskan masalah yang sedang kamu alami.
              </p>
            </div>

            <button
              type="submit"
              disabled={loading || !location}
              className="w-full flex items-center justify-center gap-2 bg-[#FFC800] text-[#111116] rounded-xl py-3.5 font-bold text-sm hover:brightness-95 disabled:opacity-50 disabled:cursor-not-allowed transition"
            >
              {loading ? (
                <>
                  <div className="w-4 h-4 border-2 border-black/20 border-t-black rounded-full animate-spin" />
                  Membuat Pesanan...
                </>
              ) : (
                <>
                  Kirim Pesanan
                  <FiArrowRight size={17} />
                </>
              )}
            </button>
          </div>
        </form>

        <aside className="flex flex-col gap-4">
          <div className="bg-[#111116] rounded-2xl p-6 text-white">
            <p className="text-xs text-white/40 uppercase tracking-wider font-semibold mb-5">
              Cara kerja
            </p>

            <div className="flex flex-col gap-5">
              <div className="flex gap-3">
                <div className="w-7 h-7 rounded-full bg-[#FFC800] text-[#111116] flex items-center justify-center shrink-0 text-xs font-bold">
                  1
                </div>

                <div>
                  <p className="text-sm font-semibold">
                    Pilih lokasi
                  </p>

                  <p className="text-xs text-white/45 mt-1 leading-relaxed">
                    Pilih lokasi layanan melalui peta.
                  </p>
                </div>
              </div>

              <div className="flex gap-3">
                <div className="w-7 h-7 rounded-full bg-white/10 flex items-center justify-center shrink-0 text-xs font-bold">
                  2
                </div>

                <div>
                  <p className="text-sm font-semibold">
                    Lengkapi pesanan
                  </p>

                  <p className="text-xs text-white/45 mt-1 leading-relaxed">
                    Isi nama penerima dan kebutuhan layanan.
                  </p>
                </div>
              </div>

              <div className="flex gap-3">
                <div className="w-7 h-7 rounded-full bg-white/10 flex items-center justify-center shrink-0 text-xs font-bold">
                  3
                </div>

                <div>
                  <p className="text-sm font-semibold">
                    Mitra menerima
                  </p>

                  <p className="text-xs text-white/45 mt-1 leading-relaxed">
                    Pesanan diteruskan kepada mitra.
                  </p>
                </div>
              </div>
            </div>
          </div>

          <div className="bg-white border border-black/[0.07] rounded-2xl p-6">
            <div className="w-9 h-9 rounded-xl bg-[#FFF4CC] flex items-center justify-center mb-4">
              <FiCheck size={17} />
            </div>

            <h3 className="font-display font-bold text-base">
              Alamat otomatis
            </h3>

            <p className="text-xs text-black/45 leading-relaxed mt-2">
              Kamu tidak perlu mengetik alamat. Pilih lokasi
              di peta dan alamat akan terdeteksi otomatis.
            </p>
          </div>
        </aside>
      </div>
    </div>
  );
}