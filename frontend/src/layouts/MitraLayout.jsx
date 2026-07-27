import { Outlet } from "react-router-dom";
import Sidebar from "../components/layout/Sidebar";
import Topbar from "../components/layout/Topbar";

const MITRA_MENU = [
  { label: "Dashboard", to: "/mitra/dashboard" },
  { label: "Pesanan Masuk", to: "/mitra/pesanan-masuk" },
  { label: "Riwayat", to: "/mitra/riwayat" },
  { label: "Profile", to: "/mitra/profile" },
];

export default function MitraLayout() {
  return (
    <div className="flex min-h-screen bg-gray-50">
      <Sidebar title="Tinjaku Mitra" menuItems={MITRA_MENU} />

      <div className="flex-1 flex flex-col">
        <Topbar pageTitle="Mitra Panel" />

        <main className="flex-1 p-6">
          <Outlet />
        </main>
      </div>
    </div>
  );
}