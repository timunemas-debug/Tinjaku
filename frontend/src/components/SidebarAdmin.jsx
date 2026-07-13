import { NavLink } from "react-router-dom";
import { FiGrid, FiClipboard, FiUsers, FiTruck } from "react-icons/fi";

const menu = [
  { to: "/admin", label: "Dashboard", icon: FiGrid, end: true },
  { to: "/admin/pesanan", label: "Data Pesanan", icon: FiClipboard },
  { to: "/admin/pelanggan", label: "Data Pelanggan", icon: FiUsers },
  { to: "/admin/mitra", label: "Data Mitra", icon: FiTruck },
];

export default function SidebarAdmin() {
  return (
    <aside className="w-64 h-screen bg-[#0A0A0A] text-white flex flex-col fixed left-0 top-0">
      <div className="h-20 flex items-center px-6 border-b border-white/10">
        <span className="font-[Baloo_2] font-extrabold text-lg">
          TINJ<span className="text-[#FFC800]">A</span>KU
        </span>
        <span className="ml-2 text-[10px] font-bold uppercase tracking-wider bg-white/10 px-2 py-0.5 rounded-full">
          Admin
        </span>
      </div>

      <nav className="flex-1 px-3 py-6 space-y-1">
        {menu.map(({ to, label, icon: Icon, end }) => (
          <NavLink
            key={to}
            to={to}
            end={end}
            className={({ isActive }) =>
              `flex items-center gap-3 px-3 py-2.5 rounded-full text-sm font-[Baloo_2] font-semibold transition-colors ${
                isActive ? "bg-[#FFC800] text-[#0A0A0A]" : "text-white/70 hover:bg-white/10 hover:text-white"
              }`
            }
          >
            <Icon size={18} />
            {label}
          </NavLink>
        ))}
      </nav>

      <div className="px-6 py-4 border-t border-white/10 text-xs text-white/40">v1.0.0</div>
    </aside>
  );
}