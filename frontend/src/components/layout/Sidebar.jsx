import { NavLink } from "react-router-dom";

/**
 * Sidebar generic, reusable buat role apapun.
 * Contoh pemakaian:
 *   <Sidebar
 *     title="Admin Panel"
 *     menuItems={[
 *       { label: "Dashboard", to: "/admin/dashboard" },
 *       { label: "Data Mitra", to: "/admin/data-mitra" },
 *     ]}
 *   />
 */
export default function Sidebar({ title, menuItems = [] }) {
  return (
    <aside className="w-64 min-h-screen bg-ink text-white flex flex-col shrink-0">
      <div className="px-6 py-6 border-b border-white/10">
        <span className="font-display font-extrabold text-lg tracking-wide">
          {title}
        </span>
      </div>

      <nav className="flex-1 px-3 py-4 flex flex-col gap-1">
        {menuItems.map((item) => (
          <NavLink
            key={item.to}
            to={item.to}
            className={({ isActive }) =>
              `px-4 py-2.5 rounded-lg font-body text-sm font-medium transition-colors ${
                isActive
                  ? "bg-accent text-ink font-semibold"
                  : "text-white/70 hover:bg-white/10 hover:text-white"
              }`
            }
          >
            {item.label}
          </NavLink>
        ))}
      </nav>
    </aside>
  );
}