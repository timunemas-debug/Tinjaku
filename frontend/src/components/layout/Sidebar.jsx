import { NavLink } from "react-router-dom";
import { FiHome, FiPackage, FiClock, FiMapPin, FiUser } from "react-icons/fi";

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
      <div className="px-6 py-7 flex items-center gap-2.5">
        <span className="w-8 h-8 rounded-[50%_50%_50%_6px] bg-accent flex items-center justify-center">
          <span className="w-4 h-1.5 bg-ink rounded-full" />
        </span>
        <span className="font-display font-extrabold text-lg tracking-wide">
          {title}
        </span>
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