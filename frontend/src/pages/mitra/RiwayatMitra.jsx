import { useEffect, useState } from "react";
import { getPesananByMitra } from "../../services/mitraService";
import { useAuth } from "../../hooks/useAuth";
import { getStatusInfo } from "../../utils/statusPesananMap";

function RiwayatMitra() {
  const { user } = useAuth();
  const [pesananSelesai, setPesananSelesai] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function fetchRiwayat() {
      try {
        const data = await getPesananByMitra(user.userId);
        setPesananSelesai(data.filter((p) => p.status === "SELESAI"));
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }

    fetchRiwayat();
  }, [user.userId]);

  return (
    <div>
      <h1 className="text-3xl font-bold mb-8">Riwayat Pekerjaan</h1>

      {error && (
        <p className="text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2 mb-4">
          {error}
        </p>
      )}

      <div className="bg-white rounded-xl shadow p-6">
        <table className="w-full">
          <thead className="border-b">
            <tr>
              <th className="py-4 text-left">Pelanggan</th>
              <th className="text-left">Alamat</th>
              <th className="text-left">Kota</th>
              <th className="text-left">Status</th>
            </tr>
          </thead>

          <tbody>
            {loading && (
              <tr>
                <td colSpan={4} className="py-6 text-center text-gray-500">
                  Memuat riwayat...
                </td>
              </tr>
            )}

            {!loading && pesananSelesai.length === 0 && (
              <tr>
                <td colSpan={4} className="py-6 text-center text-gray-500">
                  Belum ada pekerjaan selesai.
                </td>
              </tr>
            )}

            {pesananSelesai.map((item) => {
              const statusInfo = getStatusInfo(item.status);
              return (
                <tr key={item.id} className="border-b">
                  <td className="py-4">{item.namaLengkap ?? item.namaPenerima}</td>
                  <td>{item.alamatLengkap}</td>
                  <td>{item.kota}</td>
                  <td>
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