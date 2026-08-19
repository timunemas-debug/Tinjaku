import { NavLink } from "react-router-dom";
import { FiHome, FiPackage, FiClock, FiMapPin, FiUser } from "react-icons/fi";
import logo from "../../assets/logo-tinjaku.png";

const ICONS = {
  "Dashboard": FiHome,
  "Buat Pesanan": FiPackage,
  "Riwayat": FiClock,
  "Alamat": FiMapPin,
  "Profile": FiUser,
};

export default function Sidebar({ title, menuItems = [] }) {
  return (
    <aside className="w-64 min-h-screen bg-ink text-white flex flex-col shrink-0">
      <div className="px-6 py-7">
        <img
          src={logo}
          alt="Tinjaku"
          className="h-9 w-auto"
          style={{ filter: "brightness(0) invert(1)" }}
        />
      </div>

      <nav className="flex-1 px-4 py-2 flex flex-col gap-1.5">
        {menuItems.map((item) => {
          const Icon = ICONS[item.label] ?? FiPackage;
          return (
            <NavLink
              key={item.to}
              to={item.to}
              className={({ isActive }) =>
                `flex items-center gap-3 px-4 py-3 rounded-2xl font-body text-sm font-semibold transition-colors ${
                  isActive
                    ? "bg-accent text-ink"
                    : "text-white/60 hover:bg-white/10 hover:text-white"
                }`
              }
            >
              <Icon size={18} />
              {item.label}
            </NavLink>
          );
        })}
      </nav>
    </aside>
  );
}