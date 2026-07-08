import AdminLayout from "../../layouts/AdminLayout";

function DetailPesanan() {
  return (
    <AdminLayout>
      <h1 className="text-3xl font-bold mb-6">
        Detail Pesanan
      </h1>

      <div className="bg-white rounded-xl shadow-lg p-8">

        <div className="grid grid-cols-2 gap-6">

          <div>
            <p className="font-semibold">Nama</p>
            <p>Andi</p>
          </div>

          <div>
            <p className="font-semibold">Nomor HP</p>
            <p>081234567890</p>
          </div>

          <div>
            <p className="font-semibold">Alamat</p>
            <p>Jl. Merdeka No.10</p>
          </div>

          <div>
            <p className="font-semibold">Tanggal</p>
            <p>08 Juli 2026</p>
          </div>

          <div>
            <p className="font-semibold">Status</p>
            <span className="bg-yellow-500 text-white px-3 py-1 rounded-full">
              Menunggu
            </span>
          </div>

          <div>
            <p className="font-semibold">Catatan</p>
            <p>Sedot WC penuh dan mampet.</p>
          </div>

        </div>

        <div className="flex gap-4 mt-10">

          <button className="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700">
            Terima Pesanan
          </button>

          <button className="bg-green-600 text-white px-6 py-3 rounded-lg hover:bg-green-700">
            Selesai
          </button>

          <button className="bg-red-600 text-white px-6 py-3 rounded-lg hover:bg-red-700">
            Batalkan
          </button>

        </div>

      </div>
    </AdminLayout>
  );
}

export default DetailPesanan;