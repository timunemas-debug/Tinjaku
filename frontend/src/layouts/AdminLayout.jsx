import SidebarAdmin from "../components/SidebarAdmin";
import TopbarAdmin from "../components/TopbarAdmin";

function AdminLayout({ children }) {
  return (
    <div className="flex bg-gray-100 min-h-screen">

      <SidebarAdmin />

      <div className="flex-1">

        <TopbarAdmin />

        <div className="p-8">
          {children}
        </div>

      </div>

    </div>
  );
}

export default AdminLayout;