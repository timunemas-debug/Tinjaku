import { useEffect, useState } from "react";
import { getDashboardMitra } from "../../services/mitraService";
import { useAuth } from "../../hooks/useAuth";

function DashboardMitra() {
  const { user } = useAuth();
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function fetchDashboard() {
      try {
        const data = await getDashboardMitra(user.userId);
        setStats(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }

    fetchDashboard();
  }, [user.userId]);

  const cards = [
    { title: "Pesanan Menunggu", value: stats?.pesananMenunggu, color: "bg-yellow-500", icon: "📦" },
    { title: "Diterima", value: stats?.pesananDiTerima, color: "bg-blue-500", icon: "🚛" },
    { title: "Dikerjakan", value: stats?.pesananDiKerjakan, color: "bg-purple-500", icon: "🔧" },
    { title: "Selesai", value: stats?.pesananSelesai, color: "bg-green-500", icon: "✅" },
  ];

  return (
    <div>
      <h1 className="text-3xl font-bold mb-8">Dashboard Mitra</h1>

      {error && (
        <p className="text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2 mb-6">
          {error}
        </p>
      )}

      <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-6">
        {cards.map((item) => (
          <div key={item.title} className={`${item.color} text-white rounded-xl p-6 shadow`}>
            <div className="text-5xl">{item.icon}</div>
            <h2 className="mt-4 text-lg">{item.title}</h2>
            <h1 className="text-3xl font-bold mt-2">
              {loading ? "..." : item.value ?? 0}
            </h1>
          </div>
        ))}
      </div>
    </div>
  );
}

export default DashboardMitra;