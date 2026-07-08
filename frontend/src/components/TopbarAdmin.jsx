import { FaBell, FaSearch, FaUserCircle } from "react-icons/fa";

function TopbarAdmin() {
  return (
    <div className="bg-white shadow flex items-center justify-between px-8 py-4">

      
      <div>
        <h1 className="text-2xl font-bold text-gray-800">
          Dashboard Admin
        </h1>

        <p className="text-gray-500 text-sm">
          Selamat datang kembali 👋
        </p>
      </div>

      
      <div className="hidden md:flex items-center bg-gray-100 rounded-lg px-3 py-2 w-80">

        <FaSearch className="text-gray-400 mr-2" />

        <input
          type="text"
          placeholder="Cari..."
          className="bg-transparent outline-none w-full"
        />

      </div>

      
      <div className="flex items-center gap-6">

        <button className="relative">

          <FaBell size={22} />

          <span className="absolute -top-2 -right-2 bg-red-500 text-white text-xs rounded-full px-1">
            3
          </span>

        </button>

        <div className="flex items-center gap-2">

          <FaUserCircle size={35} />

          <div className="hidden md:block">

            <p className="font-semibold">
              Admin
            </p>

            <p className="text-sm text-gray-500">
              Administrator
            </p>

          </div>

        </div>

      </div>

    </div>
  );
}

export default TopbarAdmin;