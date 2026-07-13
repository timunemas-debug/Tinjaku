import { Outlet } from "react-router-dom";
import Sidebar from "../components/Sidebar";

export default function MitraLayout() {
  return (
    <div className="flex bg-[#F7F5F0] min-h-screen">
      <Sidebar />
      <div className="flex-1 md:ml-56 pb-16 md:pb-0">
        <main className="p-6">
          <Outlet />
        </main>
      </div>
    </div>
  );
}