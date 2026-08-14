import { FiLogOut } from "react-icons/fi";
import NotificationBell from "./NotificationBell";
import { useAuth } from "../../hooks/useAuth";

export default function Topbar({ pageTitle }) {
  const { user, logout } = useAuth();

  return (
    <header className="h-20 flex items-center justify-between px-8 border-b border-gray-100 bg-white">
      <h1 className="font-display font-bold text-2xl text-ink">
        {pageTitle}
      </h1>

      <div className="flex items-center gap-4">
        <NotificationBell />

      <div className="flex items-center gap-3">
        <span className="w-9 h-9 rounded-full bg-accent text-ink font-display font-bold flex items-center justify-center text-sm">
          {user?.email?.[0]?.toUpperCase() ?? "?"}
        </span>
        <span className="font-body text-sm text-ink/70">{user?.email}</span>
      </div>
      
        <button
          onClick={logout}
          className="flex items-center gap-2 font-body text-sm font-semibold text-ink border border-ink/15 rounded-full px-4 py-2 hover:bg-ink hover:text-white transition-colors"
        >
          <FiLogOut size={15} />
          Logout
        </button>
      </div>
    </header>
  );
}