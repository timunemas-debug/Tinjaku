import { useEffect, useState } from "react";
import { getUsers, deleteUser } from "../../services/userService";

export default function DataPelanggan() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchUsers = () => {
    setLoading(true);
    getUsers()
      .then(setUsers)
      .catch((err) => setError(err.response?.data?.message || err.message))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleDelete = async (userId) => {
    if (!confirm("Yakin ingin menghapus pelanggan ini?")) return;
    try {
      await deleteUser(userId);
      fetchUsers();
    } catch (err) {
      alert(err.response?.data?.message || err.message);
    }
  };

  if (loading) return <p className="p-4">Loading...</p>;
  if (error) return <p className="p-4 text-red-500">Error: {error}</p>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Data Pelanggan</h1>
      <table className="w-full bg-white rounded-xl shadow overflow-hidden">
        <thead className="bg-gray-100">
          <tr>
            <th className="text-left p-3">ID</th>
            <th className="text-left p-3">Nama</th>
            <th className="text-left p-3">Email</th>
            <th className="text-left p-3">Aksi</th>
          </tr>
        </thead>
        <tbody>
          {users.map((u) => (
            <tr key={u.id} className="border-t">
              <td className="p-3">{u.id}</td>
              <td className="p-3">{u.nama || u.name}</td>
              <td className="p-3">{u.email}</td>
              <td className="p-3">
                <button
                  onClick={() => handleDelete(u.id)}
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