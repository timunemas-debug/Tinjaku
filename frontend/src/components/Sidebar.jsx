import { NavLink } from "react-router-dom";
import { FiGrid, FiInbox, FiClock, FiUser } from "react-icons/fi";

const menu = [
  { to: "/mitra/dashboard", label: "Dashboard", icon: FiGrid },
  { to: "/mitra/pesanan", label: "Pesanan Masuk", icon: FiInbox },
  { to: "/mitra/riwayat", label: "Riwayat", icon: FiClock },
  { to: "/mitra/profile", label: "Profil", icon: FiUser },
];

export default function Sidebar() {
  return (
    <>
      <aside className="hidden md:flex w-56 h-screen bg-white border-r border-[#0A0A0A]/10 flex-col fixed left-0 top-0">
        <div className="h-20 flex items-center px-6 border-b border-[#0A0A0A]/10">
          <span className="font-[Baloo_2] font-extrabold text-lg text-[#0A0A0A]">
            TINJ<span className="text-[#FFC800]">A</span>KU
          </span>
        </div>
        <nav className="flex-1 px-3 py-6 space-y-1">
          {menu.map(({ to, label, icon: Icon }) => (
            <NavLink
              key={to}
              to={to}
              className={({ isActive }) =>
                `flex items-center gap-3 px-3 py-2.5 rounded-full text-sm font-[Baloo_2] font-semibold transition-colors ${
                  isActive ? "bg-[#0A0A0A] text-white" : "text-[#6B7280] hover:bg-[#FAFAFA]"
                }`
              }
            >
              <Icon size={18} />
              {label}
            </NavLink>
          ))}
        </nav>
      </aside>

      <nav className="md:hidden fixed bottom-0 left-0 right-0 bg-white border-t border-[#0A0A0A]/10 flex justify-around py-2 z-50">
        {menu.map(({ to, label, icon: Icon }) => (
          <NavLink
            key={to}
            to={to}
            className={({ isActive }) =>
              `flex flex-col items-center gap-1 px-2 py-1 text-[10px] font-[Baloo_2] font-semibold ${
                isActive ? "text-[#FFC800]" : "text-[#6B7280]"
              }`
            }
          >
            <Icon size={20} />
            {label}
          </NavLink>
        ))}
      </nav>
    </>
  );
}