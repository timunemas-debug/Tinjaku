import { useEffect, useState } from "react";
import { getPesananByStatus, terimaPesanan } from "../../services/pesananService";
import { useAuth } from "../../hooks/useAuth";

function PesananMasuk() {
  const { user } = useAuth();
  const [pesanan, setPesanan] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [actionError, setActionError] = useState("");

  useEffect(() => {
    async function fetchPesanan() {
      try {
        const data = await getPesananByStatus("MENUNGGU");
        setPesanan(data);
      } catch (err) {
        // Backend melempar 404 kalau list kosong (ResourceNotFound),
        // jadi kita anggap itu bukan error, cuma "belum ada pesanan".
        if (err.message.includes("tidak ditemukan")) {
          setPesanan([]);
        } else {
          setError(err.message);
        }
      } finally {
        setLoading(false);
      }
    }

    fetchPesanan();
  }, []);

  const ambilPesanan = async (pesananId) => {
    setActionError("");
    try {
      await terimaPesanan(pesananId, user.userId);
      setPesanan((prev) => prev.filter((p) => p.id !== pesananId));
    } catch (err) {
      setActionError(err.message);
    }
  };

  return (
    <div>
      <h1 className="text-3xl font-bold mb-8">Pesanan Masuk</h1>

      {error && (
        <p className="text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2 mb-4">
          {error}
        </p>
      )}
      {actionError && (
        <p className="text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2 mb-4">
          {actionError}
        </p>
      )}

      <div className="bg-white rounded-xl shadow overflow-hidden">
        <table className="w-full">
          <thead className="bg-blue-600 text-white">
            <tr>
              <th className="p-4">Nama</th>
              <th>Alamat</th>
              <th>Kota</th>
              <th>Keluhan</th>
              <th>Aksi</th>
            </tr>
          </thead>

          <tbody>
            {loading && (
              <tr>
                <td colSpan={5} className="p-6 text-center text-gray-500">
                  Memuat pesanan...
                </td>
              </tr>
            )}

            {!loading && pesanan.length === 0 && (
              <tr>
                <td colSpan={5} className="p-6 text-center text-gray-500">
                  Belum ada pesanan menunggu.
                </td>
              </tr>
            )}

            {pesanan.map((item) => (
              <tr key={item.id} className="border-b text-center">
                <td className="p-4">{item.namaLengkap ?? item.namaPenerima}</td>
                <td>{item.alamatLengkap}</td>
                <td>{item.kota}</td>
                <td>{item.keluhan}</td>
                <td>
                  <button
                    onClick={() => ambilPesanan(item.id)}
                    className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
                  >
                    Ambil
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default PesananMasuk;