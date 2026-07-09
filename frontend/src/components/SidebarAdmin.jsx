import { Link } from "react-router-dom";

function SidebarAdmin() {
  return (
    <div className="w-64 min-h-screen bg-blue-700 text-white">

      <div className="p-6 border-b border-blue-600">
        <h1 className="text-3xl font-bold">
          Tinjaku
        </h1>

        <p className="text-sm mt-2 text-blue-200">
          Admin Panel
        </p>
      </div>

      <nav className="flex flex-col mt-5">

        <Link
          to="/admin"
          className="px-6 py-4 hover:bg-blue-600 transition"
        >
          Dashboard
        </Link>

        <Link
          to="/admin/pesanan"
          className="px-6 py-4 hover:bg-blue-600 transition"
        >
          Data Pesanan
        </Link>

        <Link
          to="/admin/pelanggan"
          className="px-6 py-4 hover:bg-blue-600 transition"
        >
          Data Pelanggan
        </Link>

        <Link
          to="/admin/mitra"
          className="px-6 py-4 hover:bg-blue-600 transition"
        >
          Data Mitra
        </Link>

        <hr className="my-5 border-blue-500"/>

        <Link
          to="/"
          className="px-6 py-4 hover:bg-red-600 transition"
        >
          Keluar
        </Link>

      </nav>

    </div>
  );
}

export default SidebarAdmin;