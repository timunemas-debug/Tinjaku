import { Outlet } from "react-router-dom";
import SidebarAdmin from "../components/SidebarAdmin";
import TopbarAdmin from "../components/TopbarAdmin";

export default function AdminLayout() {
  return (
    <div className="flex bg-[#F7F5F0] min-h-screen">
      <SidebarAdmin />
      <div className="flex-1 ml-64">
        <TopbarAdmin />
        <main className="p-6">
          <Outlet />
        </main>
      </div>
    </div>
  );
}