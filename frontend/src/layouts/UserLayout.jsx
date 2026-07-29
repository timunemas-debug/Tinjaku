import { Outlet } from "react-router-dom";
import Sidebar from "../components/layout/Sidebar";
import Topbar from "../components/layout/Topbar";

const USER_MENU = [
  { label: "Dashboard", to: "/dashboard" },
  { label: "Buat Pesanan", to: "/pesanan" },
  { label: "Riwayat", to: "/riwayat" },
  { label: "Alamat", to: "/alamat" },
  { label: "Profile", to: "/profile" },
];

export default function UserLayout() {
  return (
    <div className="flex min-h-screen">
      <Sidebar title="Tinjaku" menuItems={USER_MENU} />

      <div className="flex-1 flex flex-col">
        <Topbar pageTitle="Akun Saya" />

        <main
          className="flex-1 p-8"
          style={{
            backgroundColor: "#fbfbfc",
            backgroundImage: "radial-gradient(#e4e4e9 1.5px, transparent 1.5px)",
            backgroundSize: "28px 28px",
          }}
        >
          <Outlet />
        </main>
      </div>
    </div>
  );
}