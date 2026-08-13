import { useEffect, useState } from "react";
import { getRiwayatPesananUser } from "../../services/pesananService";
import CardPesanan from "../../components/pesanan/CardPesanan";
import { useAuth } from "../../hooks/useAuth";

export default function Riwayat() {
  const { user } = useAuth();
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
  getRiwayatPesananUser(user.userId)
    .then((all) => {
      setData(all);
    })
    .catch((err) => setError(err.message))
    .finally(() => setLoading(false));
}, [user]);

  return (
    <div className="min-h-[80vh] bg-[#FAFAFA] px-4 py-12">
      <div className="max-w-2xl mx-auto">
        <h1 className="font-[Baloo_2] font-extrabold text-2xl text-[#0A0A0A] mb-6">
          Riwayat Pesanan
        </h1>

        <p className="text-xs text-amber-600 bg-amber-50 border border-amber-200 rounded-lg px-3 py-2 mb-4">
          Sementara menampilkan semua pesanan — backend belum bisa filter
          per user karena PesananResponse belum punya field userId.
        </p>

        {loading && <p className="text-sm text-[#6B7280]">Memuat...</p>}
        {error && (
          <p className="text-sm text-[#D64545] bg-[#D64545]/10 rounded-lg px-3 py-2">
            {error}
          </p>
        )}
        {!loading && !error && data.length === 0 && (
          <div className="bg-white border-2 border-dashed border-[#0A0A0A]/15 rounded-2xl p-10 text-center">
            <p className="text-[#6B7280] text-sm">Belum ada pesanan.</p>
          </div>
        )}

        <div className="space-y-3">
          {data.map((p) => (
            <CardPesanan key={p.id} pesanan={p} />
          ))}
        </div>
      </div>
    </div>
  );
}