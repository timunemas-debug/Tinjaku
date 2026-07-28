import { useEffect, useState } from "react";
import { getMitra } from "../../services/mitraService";

export default function DataMitra() {
  const [mitraList, setMitraList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function fetchMitra() {
      try {
        const data = await getMitra();
        setMitraList(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }

    fetchMitra();
  }, []);

  return (
    <div>
      <h1 className="font-display font-bold text-2xl text-ink mb-2">
        Data Mitra
      </h1>

      <p className="font-body text-xs text-amber-600 bg-amber-50 border border-amber-200 rounded-lg px-3 py-2 mb-6 inline-block">
        Aksi Detail/Hapus belum aktif — backend belum mengirim field
        <code className="mx-1 font-mono">mitraId</code> di response daftar mitra.
      </p>

      {error && (
        <p className="text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2 mb-4">
          {error}
        </p>
      )}

      <div className="bg-white rounded-2xl border border-gray-200 overflow-hidden">
        <table className="w-full">
          <thead className="bg-ink text-white">
            <tr>
              <th className="p-4 text-left font-body text-sm font-semibold">Nama Mitra</th>
              <th className="p-4 text-left font-body text-sm font-semibold">Rating</th>
              <th className="p-4 text-left font-body text-sm font-semibold">Total Rating</th>
              <th className="p-4 text-left font-body text-sm font-semibold">Jumlah Alamat</th>
              <th className="p-4 text-left font-body text-sm font-semibold">Aksi</th>
            </tr>
          </thead>

          <tbody>
            {loading && (
              <tr>
                <td colSpan={5} className="p-6 text-center font-body text-sm text-ink/50">
                  Memuat data mitra...
                </td>
              </tr>
            )}

            {!loading && mitraList.length === 0 && (
              <tr>
                <td colSpan={5} className="p-6 text-center font-body text-sm text-ink/50">
                  Belum ada mitra terdaftar.
                </td>
              </tr>
            )}

            {mitraList.map((mitra, index) => (
              <tr key={index} className="border-t border-gray-100">
                <td className="p-4 font-body text-sm text-ink font-medium">{mitra.nama}</td>
                <td className="p-4 font-body text-sm text-ink">
                  {mitra.ratingMitra != null ? mitra.ratingMitra.toFixed(1) : "-"} ⭐
                </td>
                <td className="p-4 font-body text-sm text-ink">{mitra.totalRating ?? 0}</td>
                <td className="p-4 font-body text-sm text-ink">{mitra.alamat?.length ?? 0}</td>
                <td className="p-4">
                  <button
                    disabled
                    title="Butuh mitraId dari backend"
                    className="font-body text-xs text-red-400 cursor-not-allowed"
                  >
                    Hapus
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