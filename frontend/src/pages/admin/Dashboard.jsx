import { useEffect, useState } from "react";
import { getTotalPesanan } from "../../services/pesananService";
import { getMitra } from "../../services/mitraService";
import { getUsers } from "../../services/userService";

export default function Dashboard() {
  const [stats, setStats] = useState({
    totalPesanan: null,
    totalMitra: null,
    totalUser: null,
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function fetchStats() {
      try {
        const [totalPesanan, mitraList, userList] = await Promise.all([
          getTotalPesanan(),
          getMitra(),
          getUsers(),
        ]);

        setStats({
          totalPesanan,
          totalMitra: mitraList.length,
          totalUser: userList.length,
        });
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }

    fetchStats();
  }, []);

  const cards = [
    { title: "Total Pesanan", value: stats.totalPesanan, icon: "📦", color: "bg-blue-500" },
    { title: "Total Mitra", value: stats.totalMitra, icon: "🚛", color: "bg-green-500" },
    { title: "Total Pelanggan", value: stats.totalUser, icon: "👤", color: "bg-purple-500" },
  ];

  return (
    <div>
      <h1 className="font-display font-bold text-2xl text-ink mb-8">
        Dashboard Admin
      </h1>

      {error && (
        <p className="text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2 mb-6">
          {error}
        </p>
      )}

      <div className="grid md:grid-cols-3 gap-6">
        {cards.map((card) => (
          <div key={card.title} className={`${card.color} text-white rounded-2xl p-6 shadow`}>
            <div className="text-4xl mb-3">{card.icon}</div>
            <p className="font-body text-sm text-white/80">{card.title}</p>
            <p className="font-display font-bold text-3xl mt-1">
              {loading ? "..." : card.value}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
}