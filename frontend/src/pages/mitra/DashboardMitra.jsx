import { useEffect, useState } from "react";
import { FiPackage, FiTruck, FiTool, FiCheckCircle } from "react-icons/fi";
import { getDashboardMitra } from "../../services/mitraService";

function DashboardMitra() {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function fetchDashboard() {
      try {
        const data = await getDashboardMitra();
        setStats(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }

    fetchDashboard();
  }, []);

  const cards = [
    { title: "Pesanan Menunggu", value: stats?.pesananMenunggu, icon: FiPackage, bg: "bg-accent/25", iconColor: "text-ink" },
    { title: "Diterima", value: stats?.pesananDiTerima, icon: FiTruck, bg: "bg-blue-100", iconColor: "text-blue-600" },
    { title: "Dikerjakan", value: stats?.pesananDiKerjakan, icon: FiTool, bg: "bg-purple-100", iconColor: "text-purple-600" },
    { title: "Selesai", value: stats?.pesananSelesai, icon: FiCheckCircle, bg: "bg-green-100", iconColor: "text-green-600" },
  ];

  return (
    <div>
      <h1 className="font-display font-bold text-2xl text-ink mb-8">
        Dashboard Mitra
      </h1>

      {error && (
        <p className="font-body text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2 mb-6">
          {error}
        </p>
      )}

      <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-6">
        {cards.map((item) => {
          const Icon = item.icon;
          return (
            <div key={item.title} className="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
              <div className={`w-11 h-11 rounded-full ${item.bg} flex items-center justify-center mb-4`}>
                <Icon size={18} className={item.iconColor} />
              </div>
              <p className="font-body text-sm text-ink/50 mb-1">{item.title}</p>
              <p className="font-display font-extrabold text-3xl text-ink">
                {loading ? "..." : item.value ?? 0}
              </p>
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default DashboardMitra;