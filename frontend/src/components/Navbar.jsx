import { useState } from "react";
import { Link } from "react-router-dom";
import { FiMenu, FiX } from "react-icons/fi";
import TentangKami from "../pages/TentangKami";

export default function Navbar() {
  const [open, setOpen] = useState(false);

  const links = [
    { to: "/", label: "Home" },
    { to: "/tentang-kami", label: "Tentang Kami" },
    { to: "/layanan", label: "Layanan" },
    { to: "/pesanan", label: "Pesan" },
    { to: "/riwayat", label: "Riwayat" },
    { to: "/profile", label: "Profil" },
  ];

  return (
    <header className="bg-[#FAFAFA] sticky top-0 z-50">
      <div className="max-w-6xl mx-auto px-5 h-20 flex items-center justify-between">
        {/* Logo dengan ikon */}
        <Link to="/" className="flex items-center gap-2">
          <div className="w-8 h-8 rounded-lg bg-[#FFC800] flex items-center justify-center relative">
            <div className="flex gap-1">
              <span className="w-1.5 h-1.5 bg-[#0A0A0A] rounded-full" />
              <span className="w-1.5 h-1.5 bg-[#0A0A0A] rounded-full" />
            </div>
          </div>
          <span className="font-[Baloo_2] font-extrabold text-xl text-[#0A0A0A] tracking-tight">
            TINJAKU
          </span>
        </Link>

        {/* Menu tengah — uppercase, rapat, bold */}
        <nav className="hidden md:flex items-center gap-9">
          {links.map((l) => (
            <Link
              key={l.to}
              to={l.to}
              className="text-[13px] font-bold uppercase tracking-wide text-[#0A0A0A] hover:text-[#FFC800] transition-colors"
            >
              {l.label}
            </Link>
          ))}
        </nav>

        {/* Login + tombol pill */}
        <div className="hidden md:flex items-center gap-6">
          <Link
            to="/login"
            className="text-[13px] font-bold uppercase tracking-wide text-[#0A0A0A] hover:text-[#FFC800] transition-colors"
          >
            Login
          </Link>
          <Link
            to="/pesanan"
            className="bg-[#FFC800] text-[#0A0A0A] text-[13px] font-extrabold uppercase tracking-wide px-6 py-3 rounded-full hover:bg-[#e6b400] transition-colors"
          >
            Get Started
          </Link>
        </div>

        {/* Mobile toggle */}
        <button
          className="md:hidden text-[#0A0A0A] text-2xl"
          onClick={() => setOpen(!open)}
          aria-label="Buka menu"
        >
          {open ? <FiX /> : <FiMenu />}
        </button>
      </div>

      {open && (
        <nav className="md:hidden flex flex-col bg-[#FAFAFA] border-t border-[#0A0A0A]/10 px-5 py-4 gap-4">
          {links.map((l) => (
            <Link
              key={l.to}
              to={l.to}
              onClick={() => setOpen(false)}
              className="text-sm font-bold uppercase text-[#0A0A0A]"
            >
              {l.label}
            </Link>
          ))}
          <Link
            to="/login"
            onClick={() => setOpen(false)}
            className="text-sm font-bold uppercase text-[#0A0A0A]"
          >
            Login
          </Link>
          <Link
            to="/pesanan"
            onClick={() => setOpen(false)}
            className="bg-[#FFC800] text-[#0A0A0A] text-sm font-extrabold uppercase text-center px-6 py-3 rounded-full"
          >
            Get Started
          </Link>
        </nav>
      )}
    </header>
  );
}