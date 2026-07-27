import { Outlet } from "react-router-dom";
import Sidebar from "../components/layout/Sidebar";
import Topbar from "../components/layout/Topbar";

const ADMIN_MENU = [
  { label: "Dashboard", to: "/admin/dashboard" },
  { label: "Data Mitra", to: "/admin/data-mitra" },
  { label: "Data Pelanggan", to: "/admin/data-pelanggan" },
  { label: "Data Pesanan", to: "/admin/data-pesanan" },
];

export default function AdminLayout() {
  return (
    <div className="flex min-h-screen bg-gray-50">
      <Sidebar title="Tinjaku Admin" menuItems={ADMIN_MENU} />

      <div className="flex-1 flex flex-col">
        <Topbar pageTitle="Admin Panel" />

        <main className="flex-1 p-6">
          <Outlet />
        </main>
      </div>
    </div>
  );
}