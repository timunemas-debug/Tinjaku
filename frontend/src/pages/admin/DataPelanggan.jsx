import { useEffect, useState } from "react";
import { getUsers } from "../../services/userService";

export default function DataPelanggan() {
  const [userList, setUserList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function fetchUsers() {
      try {
        const data = await getUsers();
        setUserList(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }

    fetchUsers();
  }, []);

  return (
    <div>
      <h1 className="font-display font-bold text-2xl text-ink mb-2">
        Data Pelanggan
      </h1>

      <p className="font-body text-xs text-amber-600 bg-amber-50 border border-amber-200 rounded-lg px-3 py-2 mb-6 inline-block">
        Kolom Email & aksi Detail/Hapus belum bisa ditampilkan — backend
        belum mengirim field <code className="mx-1 font-mono">userId</code> maupun
        <code className="mx-1 font-mono">email</code> di response daftar user.
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
              <th className="p-4 text-left font-body text-sm font-semibold">Nama Lengkap</th>
              <th className="p-4 text-left font-body text-sm font-semibold">Jumlah Alamat</th>
              <th className="p-4 text-left font-body text-sm font-semibold">Aksi</th>
            </tr>
          </thead>

          <tbody>
            {loading && (
              <tr>
                <td colSpan={3} className="p-6 text-center font-body text-sm text-ink/50">
                  Memuat data pelanggan...
                </td>
              </tr>
            )}

            {!loading && userList.length === 0 && (
              <tr>
                <td colSpan={3} className="p-6 text-center font-body text-sm text-ink/50">
                  Belum ada pelanggan terdaftar.
                </td>
              </tr>
            )}

            {userList.map((user, index) => (
              <tr key={index} className="border-t border-gray-100">
                <td className="p-4 font-body text-sm text-ink font-medium">{user.namaLengkap}</td>
                <td className="p-4 font-body text-sm text-ink">{user.alamat?.length ?? 0}</td>
                <td className="p-4">
                  <button
                    disabled
                    title="Butuh userId dari backend"
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