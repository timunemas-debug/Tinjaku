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
    <div className="flex min-h-screen bg-[#FAFAFA]">
      <Sidebar title="Tinjaku" menuItems={USER_MENU} />

      <div className="flex-1 flex flex-col min-w-0">
        <Topbar pageTitle="Akun Saya" />

        <main
          className="flex-1 px-8 py-7 max-lg:px-6 max-md:px-4"
          style={{
            backgroundColor: "#fbfbfc",
            backgroundImage:
              "radial-gradient(#e7e7eb 1.2px, transparent 1.2px)",
            backgroundSize: "28px 28px",
          }}
        >
          <Outlet />
        </main>
      </div>
    </div>
  );
}