import { Outlet } from "react-router-dom";
import Sidebar from "../components/layout/Sidebar";
import Topbar from "../components/layout/Topbar";

const USER_MENU = [
  { label: "Buat Pesanan", to: "/pesanan" },
  { label: "Riwayat", to: "/riwayat" },
  { label: "Alamat", to: "/alamat" },
  { label: "Profile", to: "/profile" },
];

export default function UserLayout() {
  return (
    <div className="flex min-h-screen bg-gray-50">
      <Sidebar title="Tinjaku" menuItems={USER_MENU} />

      <div className="flex-1 flex flex-col">
        <Topbar pageTitle="Akun Saya" />

        <main className="flex-1 p-6">
          <Outlet />
        </main>
      </div>
    </div>
  );
}