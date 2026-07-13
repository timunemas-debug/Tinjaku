import { FiBell, FiChevronDown } from "react-icons/fi";
import { useState } from "react";

export default function TopbarAdmin({ title = "Dashboard", adminName = "Admin" }) {
  const [open, setOpen] = useState(false);

  return (
    <header className="h-20 bg-white border-b border-[#0A0A0A]/10 flex items-center justify-between px-6 ml-64 sticky top-0 z-40">
      <h1 className="font-[Baloo_2] font-bold text-lg text-[#0A0A0A]">{title}</h1>

      <div className="flex items-center gap-4">
        <button className="text-[#6B7280] hover:text-[#0A0A0A] relative" aria-label="Notifikasi">
          <FiBell size={20} />
          <span className="absolute -top-1 -right-1 w-2 h-2 bg-[#FFC800] rounded-full" />
        </button>

        <div className="relative">
          <button onClick={() => setOpen(!open)} className="flex items-center gap-2 text-sm font-[Baloo_2] font-semibold text-[#0A0A0A]">
            <div className="w-8 h-8 rounded-full bg-[#0A0A0A] text-white flex items-center justify-center text-xs font-bold">
              {adminName.charAt(0).toUpperCase()}
            </div>
            {adminName}
            <FiChevronDown size={14} />
          </button>

          {open && (
            <div className="absolute right-0 mt-2 w-40 bg-white border-2 border-[#0A0A0A]/10 rounded-xl shadow-lg py-1">
              <button className="w-full text-left px-4 py-2 text-sm text-[#0A0A0A] hover:bg-[#FAFAFA]">Pengaturan</button>
              <button className="w-full text-left px-4 py-2 text-sm text-[#D64545] hover:bg-[#FAFAFA]">Keluar</button>
            </div>
          )}
        </div>
      </div>
    </header>
  );
}