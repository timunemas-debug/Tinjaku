import AdminLayout from "../../layouts/AdminLayout";

function DashboardMitra() {

  const data = [
    {
      title: "Pesanan Baru",
      value: 8,
      color: "bg-blue-500",
      icon: "📦",
    },
    {
      title: "Sedang Dikerjakan",
      value: 3,
      color: "bg-yellow-500",
      icon: "🚛",
    },
    {
      title: "Selesai",
      value: 52,
      color: "bg-green-500",
      icon: "✅",
    },
    {
      title: "Pendapatan",
      value: "Rp4.500.000",
      color: "bg-red-500",
      icon: "💰",
    },
  ];

  return (
    <AdminLayout>

      <h1 className="text-3xl font-bold mb-8">
        Dashboard Mitra
      </h1>

      <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-6">

        {data.map((item, index) => (

          <div
            key={index}
            className={`${item.color} text-white rounded-xl p-6 shadow`}
          >

            <div className="text-5xl">
              {item.icon}
            </div>

            <h2 className="mt-4 text-lg">
              {item.title}
            </h2>

            <h1 className="text-3xl font-bold mt-2">
              {item.value}
            </h1>

          </div>

        ))}

      </div>

    </AdminLayout>
  );
}

export default DashboardMitra;