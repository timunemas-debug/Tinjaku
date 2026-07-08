import { Link } from "react-router-dom";
import AdminLayout from "../../layouts/AdminLayout";

function Dashboard() {

  const statistik = [
    {
      title: "Total Pelanggan",
      value: 120,
      color: "bg-blue-500",
      icon: "👤",
    },
    {
      title: "Total Mitra",
      value: 25,
      color: "bg-green-500",
      icon: "🚛",
    },
    {
      title: "Total Pesanan",
      value: 350,
      color: "bg-yellow-500",
      icon: "📦",
    },
    {
      title: "Pesanan Hari Ini",
      value: 12,
      color: "bg-red-500",
      icon: "📅",
    },
  ];

  return (

    <AdminLayout>

      {/* isi dashboard */}

    </AdminLayout>

  );

}

export default Dashboard;