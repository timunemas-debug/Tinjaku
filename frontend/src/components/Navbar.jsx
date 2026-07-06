import { Link } from "react-router-dom";

function Navbar() {
  return (
    <nav className="bg-white shadow-md sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">

        <Link
          to="/"
          className="text-3xl font-bold text-blue-600"
        >
        Tinjaku
        </Link>

        <div className="hidden md:flex items-center gap-8 font-medium">

          <Link
            to="/"
            className="hover:text-blue-600 transition"
          >
            Beranda
          </Link>

          <Link
            to="/pesanan"
            className="hover:text-blue-600 transition"
          >
            Pesan
          </Link>

          <Link
            to="/riwayat"
            className="hover:text-blue-600 transition"
          >
            Riwayat
          </Link>

          <Link
            to="/profile"
            className="hover:text-blue-600 transition"
          >
            Profil
          </Link>

        </div>

        <Link
          to="/login"
          className="bg-blue-600 text-white px-5 py-2 rounded-lg hover:bg-blue-700 transition"
        >
          Login
        </Link>

      </div>
    </nav>
  );
}

export default Navbar;