import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { FiPlus, FiMapPin, FiClock, FiArrowRight } from "react-icons/fi";
import { getAlamat } from "../../services/alamatService";
import { getPesanan } from "../../services/pesananService";
import { useAuth } from "../../hooks/useAuth";
import { getStatusInfo } from "../../utils/statusPesananMap";

export default function Dashboard() {
  const { user } = useAuth();

  const [alamatCount, setAlamatCount] = useState(null);
  const [pesananList, setPesananList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([getAlamat(), getPesanan()])
      .then(([alamat, pesanan]) => {
        setAlamatCount(alamat.length);
        setPesananList(pesanan);
      })
      .catch(() => {
        setAlamatCount(0);
        setPesananList([]);
      })
      .finally(() => setLoading(false));
  }, []);

  const pesananSelesai = pesananList.filter((p) => p.status === "SELESAI").length;
  const pesananAktif = pesananList.filter(
    (p) => !["SELESAI", "DITOLAK"].includes(p.status)
  );

  const namaSapaan = user?.email?.split("@")[0] ?? "there";

  return (
    <div className="flex flex-col gap-6">
      <div className="bg-ink rounded-3xl p-8 flex items-center justify-between overflow-hidden relative">
        <div className="relative z-10">
          <p className="font-body text-white/60 text-sm mb-1">Selamat datang kembali,</p>
          <h1 className="font-display font-extrabold text-2xl text-white mb-4 capitalize">
            {namaSapaan} 👋
          </h1>
          <Link
            to="/pesanan"
            className="inline-flex items-center gap-2 font-body font-bold text-sm text-ink bg-accent px-5 py-3 rounded-full hover:brightness-95"
          >
            <FiPlus size={16} />
            Buat Pesanan Baru
          </Link>
        </div>

        <div className="absolute right-6 top-1/2 -translate-y-1/2 w-24 h-24 rounded-full bg-accent/10 flex items-center justify-center">
          <div className="w-16 h-16 rounded-full bg-accent/20 flex items-center justify-center">
            <span className="w-8 h-3 bg-accent rounded-full" />
          </div>
        </div>
      </div>

      <div className="grid md:grid-cols-3 gap-4">
        <div className="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
          <div className="w-11 h-11 rounded-full bg-accent/25 flex items-center justify-center mb-4">
            <FiClock size={18} className="text-ink" />
          </div>
          <p className="font-body text-sm text-ink/50 mb-1">Pesanan Aktif</p>
          <p className="font-display font-extrabold text-3xl text-ink">
            {loading ? "..." : pesananAktif.length}
          </p>
        </div>

        <div className="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
          <div className="w-11 h-11 rounded-full bg-green-100 flex items-center justify-center mb-4">
            <span className="text-green-600 text-lg">✓</span>
          </div>
          <p className="font-body text-sm text-ink/50 mb-1">Pesanan Selesai</p>
          <p className="font-display font-extrabold text-3xl text-ink">
            {loading ? "..." : pesananSelesai}
          </p>
        </div>

        <div className="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
          <div className="w-11 h-11 rounded-full bg-blue-100 flex items-center justify-center mb-4">
            <FiMapPin size={18} className="text-blue-600" />
          </div>
          <p className="font-body text-sm text-ink/50 mb-1">Alamat Tersimpan</p>
          <p className="font-display font-extrabold text-3xl text-ink">
            {loading ? "..." : alamatCount}
          </p>
        </div>
      </div>

      <div className="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
        <div className="flex items-center justify-between mb-4">
          <h2 className="font-display font-bold text-lg text-ink">Pesanan Aktif</h2>
          <Link
            to="/riwayat"
            className="flex items-center gap-1 font-body text-sm text-ink/60 hover:text-ink"
          >
            Lihat semua <FiArrowRight size={14} />
          </Link>
        </div>

        {loading && <p className="font-body text-sm text-ink/50">Memuat...</p>}

        {!loading && pesananAktif.length === 0 && (
          <div className="text-center py-10">
            <FiClock size={28} className="mx-auto text-ink/20 mb-3" />
            <p className="font-body text-sm text-ink/50 mb-4">
              Belum ada pesanan aktif saat ini.
            </p>
            <Link
              to="/pesanan"
              className="inline-flex items-center gap-2 font-body font-semibold text-sm text-ink bg-accent/30 px-4 py-2 rounded-full hover:bg-accent/50"
            >
              <FiPlus size={14} />
              Buat pesanan pertama kamu
            </Link>
          </div>
        )}

        <div className="flex flex-col gap-3">
          {pesananAktif.slice(0, 3).map((p) => {
            const statusInfo = getStatusInfo(p.status);
            return (
              <div
                key={p.id}
                className="flex items-center justify-between border border-gray-100 rounded-xl p-4"
              >
                <div>
                  <p className="font-body font-semibold text-sm text-ink">{p.keluhan}</p>
                  <p className="font-body text-xs text-ink/50">{p.kota}</p>
                </div>
                <span className={`px-3 py-1 rounded-full text-xs font-semibold ${statusInfo.color}`}>
                  {statusInfo.label}
                </span>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
}