import { Outlet } from "react-router-dom";
import Navbar from "../components/layout/Navbar";
import Footer from "../components/layout/Footer";

export default function PublicLayout() {
  return (
    <div
      className="min-h-screen flex flex-col bg-[#fbfbfc]"
      style={{
        backgroundImage: "radial-gradient(#e4e4e9 1.5px, transparent 1.5px)",
        backgroundSize: "28px 28px",
      }}
    >
      <Navbar />

      <main className="flex-1">
        <Outlet />
      </main>

      <Footer />
    </div>
  );
}