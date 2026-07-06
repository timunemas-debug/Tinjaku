import { Link } from "react-router-dom";
import { FaEnvelope, FaLock } from "react-icons/fa";

function Login() {
  return (
    <div className="min-h-screen bg-gray-100 flex items-center justify-center">
      <div className="bg-white w-full max-w-md p-8 rounded-2xl shadow-xl">

        <h1 className="text-3xl font-bold text-center text-blue-600">
        Tinjaku
        </h1>

        <p className="text-center text-gray-500 mt-2">
          Selamat Datang Kembali
        </p>

        <form className="mt-8">

          <label className="font-medium">Email</label>

          <div className="flex items-center border rounded-lg mt-2 px-3">
            <FaEnvelope className="text-gray-400" />

            <input
              type="email"
              placeholder="Masukkan email"
              className="w-full p-3 outline-none"
            />
          </div>

          <label className="font-medium mt-5 block">
            Password
          </label>

          <div className="flex items-center border rounded-lg mt-2 px-3">
            <FaLock className="text-gray-400" />

            <input
              type="password"
              placeholder="Masukkan password"
              className="w-full p-3 outline-none"
            />
          </div>

          <button
            className="w-full mt-8 bg-blue-600 text-white py-3 rounded-lg hover:bg-blue-700"
          >
            Login
          </button>

        </form>

        <p className="text-center mt-6">
          Belum punya akun?

          <Link
            to="/register"
            className="text-blue-600 ml-2 font-semibold"
          >
            Daftar
          </Link>

        </p>

      </div>
    </div>
  );
}

export default Login;