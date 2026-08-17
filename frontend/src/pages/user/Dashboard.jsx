import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
  FiPlus,
  FiMapPin,
  FiClock,
  FiArrowRight,
  FiCheckCircle,
  FiPackage,
  FiChevronRight,
} from "react-icons/fi";

import { getAlamat } from "../../services/alamatService";
import { getRiwayatPesananUser } from "../../services/pesananService";
import { useAuth } from "../../hooks/useAuth";
import { getStatusInfo } from "../../utils/statusPesananMap";

export default function Dashboard() {
  const { user } = useAuth();

  const [alamatCount, setAlamatCount] = useState(null);
  const [pesananList, setPesananList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!user?.userId) return;

    setLoading(true);

    Promise.all([
      getAlamat(),
      getRiwayatPesananUser(user.userId),
    ])
      .then(([alamat, pesanan]) => {
        setAlamatCount(alamat.length);
        setPesananList(pesanan);
      })
      .catch(() => {
        setAlamatCount(0);
        setPesananList([]);
      })
      .finally(() => setLoading(false));
  }, [user?.userId]);

  const pesananSelesai = pesananList.filter(
    (p) => p.status === "SELESAI"
  ).length;

  const pesananAktif = pesananList.filter(
    (p) => !["SELESAI", "DITOLAK"].includes(p.status)
  );

  const namaSapaan =
    user?.email?.split("@")[0] || "Pengguna";

  return (
    <div className="max-w-6xl mx-auto flex flex-col gap-6">

      
      <section className="bg-[#111116] rounded-[28px] overflow-hidden relative">
        <div className="relative z-10 px-8 py-9 max-md:px-6 max-md:py-7">

          <div className="flex items-start justify-between gap-6">
            <div>
              <p className="text-white/50 text-sm mb-2">
                Selamat datang kembali
              </p>

              <h1 className="font-display font-extrabold text-3xl text-white capitalize max-md:text-2xl">
                {namaSapaan} 👋
              </h1>

              <p className="text-white/60 text-sm mt-3 max-w-md leading-relaxed">
                Mau pesan layanan sedot WC? Atur semuanya dengan mudah
                dari sini.
              </p>
            </div>

            <div className="hidden sm:flex w-14 h-14 rounded-2xl bg-[#FFC800] items-center justify-center shrink-0">
              <FiPackage
                size={25}
                className="text-[#111116]"
              />
            </div>
          </div>

          <div className="mt-7">
            <Link
              to="/pesanan"
              className="inline-flex items-center gap-2 bg-[#FFC800] text-[#111116] px-5 py-3 rounded-xl font-bold text-sm hover:brightness-95 transition"
            >
              <FiPlus size={17} />
              Buat Pesanan
            </Link>
          </div>
        </div>

        
        <div className="absolute -right-20 -bottom-24 w-64 h-64 rounded-full border-[40px] border-[#FFC800]/10" />
        <div className="absolute right-20 -top-20 w-40 h-40 rounded-full bg-white/[0.03]" />
      </section>

      
      <section className="grid grid-cols-3 gap-4 max-md:grid-cols-1">

        
        <div className="bg-white border border-black/[0.07] rounded-2xl p-5 hover:border-black/15 transition">
          <div className="flex items-center justify-between mb-5">
            <div className="w-10 h-10 rounded-xl bg-[#FFF4CC] flex items-center justify-center">
              <FiClock
                size={18}
                className="text-[#111116]"
              />
            </div>

            <span className="text-xs text-black/35">
              Saat ini
            </span>
          </div>

          <p className="text-sm text-black/50 mb-1">
            Pesanan aktif
          </p>

          <p className="font-display font-extrabold text-3xl text-[#111116]">
            {loading ? "—" : pesananAktif.length}
          </p>
        </div>

        
        <div className="bg-white border border-black/[0.07] rounded-2xl p-5 hover:border-black/15 transition">
          <div className="flex items-center justify-between mb-5">
            <div className="w-10 h-10 rounded-xl bg-[#EAF9F0] flex items-center justify-center">
              <FiCheckCircle
                size={18}
                className="text-[#249653]"
              />
            </div>

            <span className="text-xs text-black/35">
              Total
            </span>
          </div>

          <p className="text-sm text-black/50 mb-1">
            Pesanan selesai
          </p>

          <p className="font-display font-extrabold text-3xl text-[#111116]">
            {loading ? "—" : pesananSelesai}
          </p>
        </div>

        
        <div className="bg-white border border-black/[0.07] rounded-2xl p-5 hover:border-black/15 transition">
          <div className="flex items-center justify-between mb-5">
            <div className="w-10 h-10 rounded-xl bg-[#EEF4FF] flex items-center justify-center">
              <FiMapPin
                size={18}
                className="text-[#3975D3]"
              />
            </div>

            <Link
              to="/alamat"
              className="text-xs text-black/40 hover:text-black transition"
            >
              Kelola
            </Link>
          </div>

          <p className="text-sm text-black/50 mb-1">
            Alamat tersimpan
          </p>

          <p className="font-display font-extrabold text-3xl text-[#111116]">
            {loading ? "—" : alamatCount}
          </p>
        </div>
      </section>

      
      <section className="bg-white border border-black/[0.07] rounded-2xl overflow-hidden">

        
        <div className="px-6 py-5 border-b border-black/[0.06] flex items-center justify-between gap-4">
          <div>
            <h2 className="font-display font-bold text-lg text-[#111116]">
              Pesanan Aktif
            </h2>

            <p className="text-xs text-black/40 mt-1">
              Pantau pesanan yang sedang berjalan
            </p>
          </div>

          <Link
            to="/riwayat"
            className="inline-flex items-center gap-1 text-sm text-black/50 hover:text-black transition"
          >
            Riwayat
            <FiArrowRight size={15} />
          </Link>
        </div>

        
        {loading && (
          <div className="px-6 py-12 text-center">
            <div className="w-7 h-7 border-2 border-black/10 border-t-black rounded-full animate-spin mx-auto mb-3" />

            <p className="text-sm text-black/40">
              Memuat pesanan...
            </p>
          </div>
        )}

        
        {!loading && pesananAktif.length === 0 && (
          <div className="px-6 py-14 text-center">

            <div className="w-14 h-14 rounded-2xl bg-[#F5F5F6] flex items-center justify-center mx-auto mb-4">
              <FiPackage
                size={23}
                className="text-black/25"
              />
            </div>

            <h3 className="font-display font-bold text-base text-[#111116]">
              Belum ada pesanan aktif
            </h3>

            <p className="text-sm text-black/40 mt-1 mb-5">
              Pesanan yang kamu buat akan muncul di sini.
            </p>

            <Link
              to="/pesanan"
              className="inline-flex items-center gap-2 bg-[#FFC800] text-[#111116] px-5 py-2.5 rounded-xl text-sm font-bold hover:brightness-95 transition"
            >
              <FiPlus size={15} />
              Buat Pesanan
            </Link>
          </div>
        )}

        
        {!loading && pesananAktif.length > 0 && (
          <div className="divide-y divide-black/[0.06]">

            {pesananAktif.slice(0, 3).map((p) => {
              const statusInfo = getStatusInfo(p.status);

              return (
                <div
                  key={p.id}
                  className="px-6 py-5 flex items-center justify-between gap-5 hover:bg-black/[0.015] transition max-md:items-start"
                >

                  <div className="flex items-start gap-4 min-w-0">

                    <div className="w-10 h-10 rounded-xl bg-[#FFF7D9] flex items-center justify-center shrink-0">
                      <FiPackage
                        size={17}
                        className="text-[#111116]"
                      />
                    </div>

                    <div className="min-w-0">
                      <p className="font-semibold text-sm text-[#111116] truncate">
                        {p.keluhan || "Pesanan layanan"}
                      </p>

                      <div className="flex items-center gap-1 mt-1 text-xs text-black/40">
                        <FiMapPin size={12} />
                        <span className="truncate">
                          {p.kota || "Lokasi belum tersedia"}
                        </span>
                      </div>
                    </div>
                  </div>

                  <div className="flex items-center gap-3 shrink-0">
                    <span
                      className={`px-3 py-1.5 rounded-lg text-xs font-semibold ${statusInfo.color}`}
                    >
                      {statusInfo.label}
                    </span>

                    <FiChevronRight
                      size={16}
                      className="text-black/20"
                    />
                  </div>
                </div>
              );
            })}

          </div>
        )}

        
        {!loading && pesananAktif.length > 3 && (
          <div className="px-6 py-4 border-t border-black/[0.06]">
            <Link
              to="/riwayat"
              className="flex items-center justify-center gap-2 text-sm font-semibold text-black/50 hover:text-black transition"
            >
              Lihat semua pesanan
              <FiArrowRight size={15} />
            </Link>
          </div>
        )}

      </section>

      {/* ================= BANTUAN ================= */}
      <section className="bg-[#FFF8DD] border border-[#FFC800]/20 rounded-2xl px-6 py-5 flex items-center justify-between gap-5 max-md:flex-col max-md:items-start">
        <div>
          <p className="font-display font-bold text-[#111116] text-base">
            Butuh layanan sedot WC?
          </p>

          <p className="text-sm text-black/50 mt-1">
            Buat pesanan dan temukan layanan yang sesuai kebutuhanmu.
          </p>
        </div>

        <Link
          to="/pesanan"
          className="inline-flex items-center gap-2 bg-[#111116] text-white px-5 py-2.5 rounded-xl text-sm font-bold hover:bg-black/90 transition shrink-0"
        >
          Pesan Sekarang
          <FiArrowRight size={15} />
        </Link>
      </section>

    </div>
  );
}