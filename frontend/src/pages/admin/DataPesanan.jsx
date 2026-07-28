import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getPesanan, deletePesanan } from "../../services/pesananService";
import { getStatusInfo } from "../../utils/statusPesananMap";

export default function DataPesanan() {
  const [pesananList, setPesananList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const fetchPesanan = async () => {
    setLoading(true);
    try {
      const data = await getPesanan();
      setPesananList(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPesanan();
  }, []);

  const handleHapus = async (id) => {
    if (!confirm("Yakin mau hapus pesanan ini?")) return;

    try {
      await deletePesanan(id);
      setPesananList((prev) => prev.filter((p) => p.id !== id));
    } catch (err) {
      alert(err.message);
    }
  };

  return (
    <div>
      <h1 className="font-display font-bold text-2xl text-ink mb-6">
        Data Pesanan
      </h1>

      {error && (
        <p className="text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2 mb-4">
          {error}
        </p>
      )}

      <div className="bg-white rounded-2xl border border-gray-200 overflow-hidden">
        <table className="w-full">
          <thead className="bg-ink text-white">
            <tr>
              <th className="p-4 text-left font-body text-sm font-semibold">ID</th>
              <th className="p-4 text-left font-body text-sm font-semibold">Pelanggan</th>
              <th className="p-4 text-left font-body text-sm font-semibold">Mitra</th>
              <th className="p-4 text-left font-body text-sm font-semibold">Kota</th>
              <th className="p-4 text-left font-body text-sm font-semibold">Status</th>
              <th className="p-4 text-left font-body text-sm font-semibold">Aksi</th>
            </tr>
          </thead>

          <tbody>
            {loading && (
              <tr>
                <td colSpan={6} className="p-6 text-center font-body text-sm text-ink/50">
                  Memuat data pesanan...
                </td>
              </tr>
            )}

            {!loading && pesananList.length === 0 && (
              <tr>
                <td colSpan={6} className="p-6 text-center font-body text-sm text-ink/50">
                  Belum ada pesanan.
                </td>
              </tr>
            )}

            {pesananList.map((pesanan) => {
              const statusInfo = getStatusInfo(pesanan.status);

              return (
                <tr key={pesanan.id} className="border-t border-gray-100">
                  <td className="p-4 font-body text-sm text-ink">#{pesanan.id}</td>
                  <td className="p-4 font-body text-sm text-ink">{pesanan.namaLengkap ?? "-"}</td>
                  <td className="p-4 font-body text-sm text-ink">{pesanan.namaMitra ?? "-"}</td>
                  <td className="p-4 font-body text-sm text-ink">{pesanan.kota}</td>
                  <td className="p-4">
                    <span className={`px-3 py-1 rounded-full text-xs font-semibold ${statusInfo.color}`}>
                      {statusInfo.label}
                    </span>
                  </td>
                  <td className="p-4 flex gap-3">
                    <Link
                      to={`/admin/data-pesanan/${pesanan.id}`}
                      className="font-body text-xs text-blue-600 hover:underline"
                    >
                      Detail
                    </Link>
                    <button
                      onClick={() => handleHapus(pesanan.id)}
                      className="font-body text-xs text-red-600 hover:underline"
                    >
                      Hapus
                    </button>
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