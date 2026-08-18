import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
  FiClock,
  FiCheckCircle,
  FiXCircle,
  FiArrowRight,
  FiPackage,
  FiMapPin,
} from "react-icons/fi";

import { getRiwayatPesananUser } from "../../services/pesananService";
import { useAuth } from "../../hooks/useAuth";
import { getStatusInfo } from "../../utils/statusPesananMap";

export default function Riwayat() {
  const { user } = useAuth();

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!user?.userId) return;

    setLoading(true);
    setError(null);

    getRiwayatPesananUser(user.userId)
      .then((result) => {
        setData(result || []);
      })
      .catch((err) => {
        setError(err.message);
      })
      .finally(() => {
        setLoading(false);
      });
  }, [user?.userId]);

  const totalPesanan = data.length;

  const selesai = data.filter(
    (p) => p.status === "SELESAI"
  ).length;

  const ditolak = data.filter(
    (p) => p.status === "DITOLAK"
  ).length;

  const aktif = data.filter(
    (p) => !["SELESAI", "DITOLAK"].includes(p.status)
  ).length;

  return (
    <div className="max-w-6xl mx-auto">

      
      <div className="flex items-end justify-between gap-4 mb-8 max-md:flex-col max-md:items-start">
        <div>
          <p className="text-sm text-black/40 mb-2">
            Aktivitas akun
          </p>

          <h1 className="font-display font-extrabold text-3xl text-[#111116]">
            Riwayat Pesanan
          </h1>

          <p className="text-sm text-black/50 mt-2">
            Pantau semua pesanan yang pernah kamu buat.
          </p>
        </div>

        <Link
          to="/pesanan"
          className="inline-flex items-center gap-2 bg-[#FFC800] text-[#111116] px-5 py-3 rounded-xl text-sm font-bold hover:brightness-95 transition"
        >
          Buat Pesanan
          <FiArrowRight size={16} />
        </Link>
      </div>

      
      <div className="grid grid-cols-3 gap-4 mb-7 max-md:grid-cols-1">

        <SummaryCard
          icon={<FiPackage size={17} />}
          label="Total Pesanan"
          value={loading ? "..." : totalPesanan}
          iconClass="bg-[#FFF4CC] text-[#111116]"
        />

        <SummaryCard
          icon={<FiCheckCircle size={17} />}
          label="Selesai"
          value={loading ? "..." : selesai}
          iconClass="bg-[#EAF8EF] text-[#238447]"
        />

        <SummaryCard
          icon={<FiClock size={17} />}
          label="Sedang Diproses"
          value={loading ? "..." : aktif}
          iconClass="bg-[#EEF4FF] text-[#3769C8]"
        />

      </div>

      
      <div className="bg-white border border-black/[0.07] rounded-2xl overflow-hidden">

        
        <div className="px-6 py-5 border-b border-black/[0.06] flex items-center justify-between">
          <div>
            <h2 className="font-display font-bold text-lg text-[#111116]">
              Pesanan Kamu
            </h2>

            <p className="text-xs text-black/40 mt-1">
              {loading
                ? "Sedang mengambil data..."
                : `${totalPesanan} pesanan tercatat`}
            </p>
          </div>

          {!loading && totalPesanan > 0 && (
            <span className="text-xs font-semibold text-black/40">
              Terbaru
            </span>
          )}
        </div>

        
        {error && (
          <div className="m-6 bg-[#FFF0F0] border border-[#F3CACA] rounded-xl px-4 py-3 text-sm text-[#C43D3D]">
            {error}
          </div>
        )}

        
        {loading && (
          <div className="p-10 text-center">
            <div className="w-7 h-7 border-2 border-black/10 border-t-[#111116] rounded-full animate-spin mx-auto mb-3" />

            <p className="text-sm text-black/40">
              Memuat riwayat pesanan...
            </p>
          </div>
        )}

        
        {!loading && !error && data.length === 0 && (
          <div className="py-16 px-6 text-center">

            <div className="w-14 h-14 rounded-2xl bg-[#F5F5F6] flex items-center justify-center mx-auto mb-4">
              <FiPackage
                size={24}
                className="text-black/25"
              />
            </div>

            <h3 className="font-display font-bold text-lg text-[#111116]">
              Belum ada pesanan
            </h3>

            <p className="text-sm text-black/40 mt-2 max-w-sm mx-auto">
              Kamu belum memiliki riwayat pesanan. Yuk,
              buat pesanan pertamamu sekarang.
            </p>

            <Link
              to="/pesanan"
              className="inline-flex items-center gap-2 mt-5 bg-[#FFC800] text-[#111116] px-5 py-3 rounded-xl text-sm font-bold"
            >
              Buat Pesanan
              <FiArrowRight size={15} />
            </Link>
          </div>
        )}

        
        {!loading && !error && data.length > 0 && (
          <div className="divide-y divide-black/[0.06]">

            {data.map((p) => {
              const statusInfo = getStatusInfo(p.status);

              return (
                <div
                  key={p.id}
                  className="p-6 hover:bg-[#FAFAFA] transition"
                >

                  <div className="flex items-start justify-between gap-5 max-md:flex-col">

                    
                    <div className="flex gap-4 min-w-0">

                      <div className="w-11 h-11 rounded-xl bg-[#F5F5F6] flex items-center justify-center shrink-0">
                        <FiPackage
                          size={18}
                          className="text-[#111116]/60"
                        />
                      </div>

                      <div className="min-w-0">

                        <div className="flex items-center gap-2 flex-wrap mb-1">

                          <h3 className="font-display font-bold text-base text-[#111116]">
                            Pesanan #{p.id}
                          </h3>

                          <span
                            className={`px-2.5 py-1 rounded-full text-[11px] font-bold ${statusInfo.color}`}
                          >
                            {statusInfo.label}
                          </span>

                        </div>

                        <p className="text-sm text-black/60 truncate max-w-lg">
                          {p.keluhan || "Tidak ada keterangan"}
                        </p>

                        <div className="flex items-center gap-1.5 mt-2 text-xs text-black/35">
                          <FiMapPin size={13} />

                          <span>
                            {p.kota || "Alamat layanan"}
                          </span>
                        </div>

                      </div>
                    </div>

                    
                    <div className="text-right shrink-0 max-md:text-left">

                      <p className="text-xs text-black/35 mb-2">
                        Status pesanan
                      </p>

                      <div className="flex items-center gap-2 justify-end max-md:justify-start">

                        {p.status === "SELESAI" && (
                          <FiCheckCircle
                            size={16}
                            className="text-[#238447]"
                          />
                        )}

                        {p.status === "DITOLAK" && (
                          <FiXCircle
                            size={16}
                            className="text-[#C43D3D]"
                          />
                        )}

                        {!["SELESAI", "DITOLAK"].includes(p.status) && (
                          <FiClock
                            size={16}
                            className="text-[#3769C8]"
                          />
                        )}

                        <span className="text-sm font-semibold text-[#111116]">
                          {statusInfo.label}
                        </span>

                      </div>

                    </div>

                  </div>

                </div>
              );
            })}

          </div>
        )}

      </div>

      
      {!loading && !error && data.length > 0 && (
        <div className="mt-5 flex items-center justify-between text-xs text-black/35">
          <span>
            Menampilkan {data.length} pesanan
          </span>

          {ditolak > 0 && (
            <span>
              {ditolak} pesanan ditolak
            </span>
          )}
        </div>
      )}

    </div>
  );
}



function SummaryCard({
  icon,
  label,
  value,
  iconClass,
}) {
  return (
    <div className="bg-white border border-black/[0.07] rounded-2xl p-5">

      <div
        className={`w-10 h-10 rounded-xl flex items-center justify-center mb-4 ${iconClass}`}
      >
        {icon}
      </div>

      <p className="text-xs text-black/40 mb-1">
        {label}
      </p>

      <p className="font-display font-extrabold text-2xl text-[#111116]">
        {value}
      </p>

    </div>
  );
}