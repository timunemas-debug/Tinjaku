import { useAuth } from "../../hooks/useAuth";

/**
 * Topbar generic buat halaman internal (admin/mitra/user dashboard).
 * Contoh pemakaian:
 *   <Topbar pageTitle="Dashboard" />
 */
export default function Topbar({ pageTitle }) {
  const { user, logout } = useAuth();

  return (
    <header className="h-16 flex items-center justify-between px-6 border-b border-gray-200 bg-white">
      <h1 className="font-display font-semibold text-lg text-ink">
        {pageTitle}
      </h1>

      <div className="flex items-center gap-4">
        <span className="font-body text-sm text-ink/70">
          {user?.email}
        </span>

        <button
          onClick={logout}
          className="font-body text-sm font-semibold text-ink border border-ink/20 rounded-full px-4 py-1.5 hover:bg-ink hover:text-white transition-colors"
        >
          Logout
        </button>
      </div>
    </header>
  );
}