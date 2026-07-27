export default function Footer() {
  return (
    <footer className="bg-[#0A0A0A] text-white/70">
      <div className="max-w-6xl mx-auto px-5 py-12 grid grid-cols-1 md:grid-cols-3 gap-8">
        <div>
          <p className="font-[Baloo_2] font-extrabold text-xl text-white mb-2">
            TINJ<span className="text-[#FFC800]">A</span>KU
          </p>
          <p className="text-sm">
            Layanan sedot WC panggilan. Cepat, bersih, dan tanpa drama.
          </p>
        </div>
        <div>
          <p className="font-[Baloo_2] font-bold text-white text-sm uppercase mb-3">Layanan</p>
          <ul className="space-y-2 text-sm">
            <li>Sedot WC Rumah</li>
            <li>Sedot WC Komersial</li>
            <li>Darurat 24 Jam</li>
          </ul>
        </div>
        <div>
          <p className="font-[Baloo_2] font-bold text-white text-sm uppercase mb-3">Bantuan</p>
          <ul className="space-y-2 text-sm">
            <li>Cara Pesan</li>
            <li>Lacak Pesanan</li>
            <li>Hubungi Kami</li>
          </ul>
        </div>
      </div>
      <div className="border-t border-white/10 py-4 text-center text-xs text-white/40">
        © {new Date().getFullYear()} Tinjaku — Semua hak dilindungi.
      </div>
    </footer>
  );
}