import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getPesanan } from "../services/pesananService";
import CardPesanan from "../components/CardPesanan";

const TEMP_USER_ID = 1;

export default function Riwayat() {
  const navigate = useNavigate();
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    getPesanan()
      .then((all) => {
        const milikSaya = all.filter((p) => p.userId === TEMP_USER_ID);
        setData(milikSaya);
      })
      .catch((err) => setError(err.response?.data?.message || err.message))
      .finally(() => setLoading(false));
  }, []);

  return (
    <div className="min-h-[80vh] bg-[#FAFAFA] px-4 py-12">
      <div className="max-w-2xl mx-auto">
        <h1 className="font-[Baloo_2] font-extrabold text-2xl text-[#0A0A0A] mb-6">
          Riwayat Pesanan
        </h1>

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
            <CardPesanan
              key={p.id}
              pesanan={p}
              onClick={() => navigate(`/admin/pesanan/${p.id}`)}
            />
          ))}
        </div>
      </div>
    </div>
  );
}