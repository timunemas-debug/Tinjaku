import { useEffect, useState } from "react";
import { FiInbox } from "react-icons/fi";
import { getPesananByStatus, terimaPesanan } from "../../services/pesananService";

function PesananMasuk() {
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
      await terimaPesanan(pesananId);
      setPesanan((prev) => prev.filter((p) => p.id !== pesananId));
    } catch (err) {
      setActionError(err.message);
    }
  };

  return (
    <div>
      <h1 className="font-display font-bold text-2xl text-ink mb-2">
        Pesanan Masuk
      </h1>
      <p className="font-body text-sm text-ink/50 mb-6">
        Pesanan yang menunggu diambil oleh mitra.
      </p>

      {error && (
        <p className="font-body text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2 mb-4">
          {error}
        </p>
      )}
      {actionError && (
        <p className="font-body text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2 mb-4">
          {actionError}
        </p>
      )}

      <div className="bg-white rounded-2xl border border-gray-100 shadow-sm overflow-hidden">
        <table className="w-full">
          <thead className="bg-ink text-white">
            <tr>
              <th className="p-4 text-left font-body text-sm font-semibold">Nama</th>
              <th className="p-4 text-left font-body text-sm font-semibold">Alamat</th>
              <th className="p-4 text-left font-body text-sm font-semibold">Kota</th>
              <th className="p-4 text-left font-body text-sm font-semibold">Keluhan</th>
              <th className="p-4 text-left font-body text-sm font-semibold">Aksi</th>
            </tr>
          </thead>

          <tbody>
            {loading && (
              <tr>
                <td colSpan={5} className="p-10 text-center font-body text-sm text-ink/50">
                  Memuat pesanan...
                </td>
              </tr>
            )}

            {!loading && pesanan.length === 0 && (
              <tr>
                <td colSpan={5} className="p-10 text-center">
                  <FiInbox size={28} className="mx-auto text-ink/20 mb-3" />
                  <p className="font-body text-sm text-ink/50">Belum ada pesanan menunggu.</p>
                </td>
              </tr>
            )}

            {pesanan.map((item) => (
              <tr key={item.id} className="border-t border-gray-100">
                <td className="p-4 font-body text-sm text-ink">{item.namaLengkap ?? item.namaPenerima}</td>
                <td className="p-4 font-body text-sm text-ink">{item.alamatLengkap}</td>
                <td className="p-4 font-body text-sm text-ink">{item.kota}</td>
                <td className="p-4 font-body text-sm text-ink">{item.keluhan}</td>
                <td className="p-4">
                  <button
                    onClick={() => ambilPesanan(item.id)}
                    className="font-body font-bold text-sm text-ink bg-accent px-4 py-2 rounded-full hover:brightness-95"
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