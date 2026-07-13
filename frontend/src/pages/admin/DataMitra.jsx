import { useEffect, useState } from "react";
import { getMitra, deleteMitra } from "../../services/mitraService";

export default function DataMitra() {
  const [mitra, setMitra] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchMitra = () => {
    setLoading(true);
    getMitra()
      .then(setMitra)
      .catch((err) => setError(err.response?.data?.message || err.message))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchMitra();
  }, []);

  const handleDelete = async (mitraId) => {
    if (!confirm("Yakin ingin menghapus mitra ini?")) return;
    try {
      await deleteMitra(mitraId);
      fetchMitra();
    } catch (err) {
      alert(err.response?.data?.message || err.message);
    }
  };

  if (loading) return <p className="p-4">Loading...</p>;
  if (error) return <p className="p-4 text-red-500">Error: {error}</p>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Data Mitra</h1>
      <table className="w-full bg-white rounded-xl shadow overflow-hidden">
        <thead className="bg-gray-100">
          <tr>
            <th className="text-left p-3">ID</th>
            <th className="text-left p-3">Nama</th>
            <th className="text-left p-3">Kota</th>
            <th className="text-left p-3">Aksi</th>
          </tr>
        </thead>
        <tbody>
          {mitra.map((m) => (
            <tr key={m.id} className="border-t">
              <td className="p-3">{m.id}</td>
              <td className="p-3">{m.nama || m.name}</td>
              <td className="p-3">{m.kota}</td>
              <td className="p-3">
                <button
                  onClick={() => handleDelete(m.id)}
                  className="text-red-500 hover:underline"
                >
                  Hapus
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}