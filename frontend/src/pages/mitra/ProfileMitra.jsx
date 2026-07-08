import AdminLayout from "../../layouts/AdminLayout";

function ProfileMitra() {
  return (
    <AdminLayout>

      <h1 className="text-3xl font-bold mb-6">
        Profile Mitra
      </h1>

      <div className="bg-white rounded-xl shadow-lg p-8">

        <div className="flex items-center gap-6">

          <img
            src="https://via.placeholder.com/120"
            alt="Foto Profil"
            className="rounded-full w-32 h-32"
          />

          <div>

            <h2 className="text-2xl font-bold">
              Budi Santoso
            </h2>

            <p className="text-gray-500">
              Mitra Tinjaku
            </p>

          </div>

        </div>

        <div className="grid md:grid-cols-2 gap-6 mt-10">

          <div>
            <p className="font-semibold">Email</p>
            <p>budi@gmail.com</p>
          </div>

          <div>
            <p className="font-semibold">Nomor HP</p>
            <p>08123456789</p>
          </div>

          <div>
            <p className="font-semibold">Alamat</p>
            <p>Jl. Sudirman No.20</p>
          </div>

          <div>
            <p className="font-semibold">Area Kerja</p>
            <p>Kota Bekasi</p>
          </div>

          <div>
            <p className="font-semibold">Kendaraan</p>
            <p>Mobil Tangki Sedot WC</p>
          </div>

          <div>
            <p className="font-semibold">Status</p>

            <span className="bg-green-500 text-white px-3 py-1 rounded-full">
              Aktif
            </span>

          </div>

        </div>

        <div className="flex gap-4 mt-10">

          <button className="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700">
            Edit Profile
          </button>

          <button className="bg-red-600 text-white px-6 py-3 rounded-lg hover:bg-red-700">
            Ganti Password
          </button>

        </div>

      </div>

      <div className="grid md:grid-cols-3 gap-6 mt-8">

        <div className="bg-green-500 text-white rounded-xl p-6">
          <h3>Total Pekerjaan</h3>
          <h1 className="text-3xl font-bold">120</h1>
        </div>

        <div className="bg-yellow-500 text-white rounded-xl p-6">
          <h3>Rating</h3>
          <h1 className="text-3xl font-bold">4.9 ⭐</h1>
        </div>

        <div className="bg-blue-500 text-white rounded-xl p-6">
          <h3>Pendapatan</h3>
          <h1 className="text-3xl font-bold">Rp12.500.000</h1>
        </div>

      </div>

    </AdminLayout>
  );
}

export default ProfileMitra;