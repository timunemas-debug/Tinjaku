import { useEffect, useState } from "react";
import { getTotalPesanan } from "../../services/pesananService";
import { getUsers } from "../../services/userService";
import { getMitra } from "../../services/mitraService";

export default function Dashboard() {
  const [totalPesanan, setTotalPesanan] = useState(0);
  const [totalUser, setTotalUser] = useState(0);
  const [totalMitra, setTotalMitra] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    Promise.all([getTotalPesanan(), getUsers(), getMitra()])
      .then(([pesanan, users, mitra]) => {
        setTotalPesanan(pesanan);
        setTotalUser(users.length);
        setTotalMitra(mitra.length);
      })
      .catch((err) => setError(err.response?.data?.message || err.message))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p className="p-4">Loading...</p>;
  if (error) return <p className="p-4 text-red-500">Error: {error}</p>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-6">Dashboard Admin</h1>
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
        <div className="bg-white rounded-xl shadow p-5">
          <p className="text-gray-500 text-sm">Total Pesanan</p>
          <p className="text-3xl font-bold">{totalPesanan}</p>
        </div>
        <div className="bg-white rounded-xl shadow p-5">
          <p className="text-gray-500 text-sm">Total Pelanggan</p>
          <p className="text-3xl font-bold">{totalUser}</p>
        </div>
        <div className="bg-white rounded-xl shadow p-5">
          <p className="text-gray-500 text-sm">Total Mitra</p>
          <p className="text-3xl font-bold">{totalMitra}</p>
        </div>
      </div>
    </div>
  );
}