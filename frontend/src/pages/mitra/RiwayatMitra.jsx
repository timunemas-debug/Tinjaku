import { useEffect, useState } from "react";
import { FiClock } from "react-icons/fi";
import { getRiwayatPesananMitra } from "../../services/pesananService";
import { getStatusInfo } from "../../utils/statusPesananMap";

function RiwayatMitra() {
  const [riwayat, setRiwayat] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function fetchRiwayat() {
      try {
        const data = await getRiwayatPesananMitra();
        setRiwayat(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }

    fetchRiwayat();
  }, []);

  return (
    <div>
      <h1 className="font-display font-bold text-2xl text-ink mb-6">
        Riwayat Pekerjaan
      </h1>

      {error && (
        <p className="font-body text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2 mb-4">
          {error}
        </p>
      )}

      <div className="bg-white rounded-2xl border border-gray-100 shadow-sm overflow-hidden">
        <table className="w-full">
          <thead className="border-b border-gray-100">
            <tr>
              <th className="p-4 text-left font-body text-sm font-semibold text-ink">Pelanggan</th>
              <th className="p-4 text-left font-body text-sm font-semibold text-ink">Alamat</th>
              <th className="p-4 text-left font-body text-sm font-semibold text-ink">Kota</th>
              <th className="p-4 text-left font-body text-sm font-semibold text-ink">Status</th>
            </tr>
          </thead>

          <tbody>
            {loading && (
              <tr>
                <td colSpan={4} className="p-10 text-center font-body text-sm text-ink/50">
                  Memuat riwayat...
                </td>
              </tr>
            )}

            {!loading && riwayat.length === 0 && (
              <tr>
                <td colSpan={4} className="p-10 text-center">
                  <FiClock size={28} className="mx-auto text-ink/20 mb-3" />
                  <p className="font-body text-sm text-ink/50">Belum ada riwayat pekerjaan.</p>
                </td>
              </tr>
            )}

            {riwayat.map((item) => {
              const statusInfo = getStatusInfo(item.status);
              return (
                <tr key={item.id} className="border-t border-gray-100">
                  <td className="p-4 font-body text-sm text-ink">{item.namaLengkap ?? item.namaPenerima}</td>
                  <td className="p-4 font-body text-sm text-ink">{item.alamatLengkap}</td>
                  <td className="p-4 font-body text-sm text-ink">{item.kota}</td>
                  <td className="p-4">
                    <span className={`px-3 py-1 rounded-full text-xs font-semibold ${statusInfo.color}`}>
                      {statusInfo.label}
                    </span>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default RiwayatMitra;