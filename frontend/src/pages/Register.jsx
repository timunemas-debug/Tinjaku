import { Link } from "react-router-dom";
import { FaUser, FaEnvelope, FaPhone, FaLock } from "react-icons/fa";

function Register() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-100 to-cyan-100 flex items-center justify-center px-4">
      <div className="bg-white w-full max-w-md rounded-2xl shadow-xl p-8">

        <h1 className="text-3xl font-bold text-center text-blue-600">
        Tinjaku
        </h1>

        <p className="text-center text-gray-500 mt-2">
          Buat akun baru untuk mulai menggunakan layanan
        </p>

        <form className="mt-8 space-y-5">

          <div>
            <label className="font-medium">Nama Lengkap</label>

            <div className="flex items-center border rounded-lg mt-2 px-3">
              <FaUser className="text-gray-400" />

              <input
                type="text"
                placeholder="Masukkan nama lengkap"
                className="w-full p-3 outline-none"
              />
            </div>
          </div>

          <div>
            <label className="font-medium">Email</label>

            <div className="flex items-center border rounded-lg mt-2 px-3">
              <FaEnvelope className="text-gray-400" />

              <input
                type="email"
                placeholder="Masukkan email"
                className="w-full p-3 outline-none"
              />
            </div>
          </div>

          <div>
            <label className="font-medium">Nomor HP</label>

            <div className="flex items-center border rounded-lg mt-2 px-3">
              <FaPhone className="text-gray-400" />

              <input
                type="text"
                placeholder="08xxxxxxxxxx"
                className="w-full p-3 outline-none"
              />
            </div>
          </div>

          {/* Password */}
          <div>
            <label className="font-medium">Password</label>

            <div className="flex items-center border rounded-lg mt-2 px-3">
              <FaLock className="text-gray-400" />

              <input
                type="password"
                placeholder="Masukkan password"
                className="w-full p-3 outline-none"
              />
            </div>
          </div>

          <div>
            <label className="font-medium">Konfirmasi Password</label>

            <div className="flex items-center border rounded-lg mt-2 px-3">
              <FaLock className="text-gray-400" />

              <input
                type="password"
                placeholder="Ulangi password"
                className="w-full p-3 outline-none"
              />
            </div>
          </div>

          <button
            type="submit"
            className="w-full bg-blue-600 hover:bg-blue-700 text-white py-3 rounded-lg font-semibold transition duration-300"
          >
            Daftar
          </button>
        </form>

        <p className="text-center mt-6 text-gray-600">
          Sudah punya akun?
          <Link
            to="/login"
            className="text-blue-600 font-semibold ml-2 hover:underline"
          >
            Login
          </Link>
        </p>
      </div>
    </div>
  );
}

export default Register;