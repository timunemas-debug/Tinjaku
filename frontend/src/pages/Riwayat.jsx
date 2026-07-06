function Riwayat() {
  const riwayat = [
    {
      id: 1,
      tanggal: "07 Juli 2026",
      alamat: "Jl. Merdeka No.10",
      status: "Selesai",
    },
    {
      id: 2,
      tanggal: "08 Juli 2026",
      alamat: "Jl. Sudirman No.20",
      status: "Diproses",
    },
  ];

  return (
    <div className="min-h-screen bg-gray-100 p-8">

      <div className="max-w-5xl mx-auto">

        <h1 className="text-3xl font-bold text-blue-600 mb-8">
          Riwayat Pesanan
        </h1>

        <div className="space-y-5">

          {riwayat.map((item) => (
            <div
              key={item.id}
              className="bg-white rounded-xl shadow p-6"
            >
              <h2 className="font-bold text-lg">
                Pesanan #{item.id}
              </h2>

              <p className="mt-2">
                <b>Tanggal:</b> {item.tanggal}
              </p>

              <p>
                <b>Alamat:</b> {item.alamat}
              </p>

              <p className="mt-2">
                <b>Status:</b>{" "}
                <span
                  className={
                    item.status === "Selesai"
                      ? "text-green-600 font-semibold"
                      : "text-yellow-600 font-semibold"
                  }
                >
                  {item.status}
                </span>
              </p>

              <button className="mt-5 bg-blue-600 text-white px-5 py-2 rounded-lg hover:bg-blue-700">
                Lihat Detail
              </button>
            </div>
          ))}

        </div>

      </div>

    </div>
  );
}

export default Riwayat;