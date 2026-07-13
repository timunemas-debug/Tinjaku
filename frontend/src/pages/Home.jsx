import { Link } from "react-router-dom";
import { FiClock, FiShield, FiPhoneCall } from "react-icons/fi";
import Button from "../components/Button";

export default function Home() {
  return (
    <>
      {/* Hero */}
      <section className="bg-[#FAFAFA]">
        <div className="max-w-6xl mx-auto px-5 py-20 grid grid-cols-1 md:grid-cols-2 gap-10 items-center">
          <div>
            <h1 className="font-[Baloo_2] font-extrabold text-5xl md:text-6xl text-[#0A0A0A] leading-[1.05] mb-6">
              Solusi Tepat
              <br />
              untuk Masalah
              <br />
              <span className="text-[#FFC800]">Septic Tank</span>
            </h1>
            <p className="text-[#6B7280] text-lg mb-8 max-w-md">
              Tinjaku memberikan layanan sedot WC profesional dengan proses
              cepat, harga transparan, dan pelayanan terpercaya untuk rumah,
              kantor, serta bangunan komersial.
            </p>
            <Link to="/pesanan">
              <Button variant="secondary" size="lg">
                Pesan Sekarang
              </Button>
            </Link>
          </div>

          {/* Visual accent — pengganti ilustrasi isometrik */}
          <div className="relative h-80 hidden md:block">
            <div className="absolute inset-0 bg-[radial-gradient(#0A0A0A_1px,transparent_1px)] [background-size:24px_24px] opacity-10" />
            <div className="absolute top-10 left-10 w-24 h-24 bg-[#0A0A0A] rounded-2xl rotate-6" />
            <div className="absolute top-24 right-16 w-20 h-20 bg-[#FFC800] rounded-2xl -rotate-6" />
            <div className="absolute bottom-10 left-24 w-28 h-28 bg-white border-2 border-[#0A0A0A] rounded-2xl rotate-3" />
          </div>
        </div>
      </section>

      {/* Keunggulan */}
      <section className="max-w-5xl mx-auto px-5 py-16">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {[
            { icon: FiClock, title: "Respon Cepat", desc: "Teknisi datang dalam hitungan jam, bukan hari." },
            { icon: FiShield, title: "Bersih & Higienis", desc: "Peralatan standar, hasil kerja terjamin bersih." },
            { icon: FiPhoneCall, title: "Mudah Dihubungi", desc: "Pesan langsung dari aplikasi, tanpa telepon berkali-kali." },
          ].map(({ icon: Icon, title, desc }) => (
            <div key={title} className="text-center">
              <div className="w-14 h-14 rounded-2xl bg-[#FFC800] text-[#0A0A0A] flex items-center justify-center mx-auto mb-4">
                <Icon size={24} />
              </div>
              <h3 className="font-[Baloo_2] font-bold text-[#0A0A0A] mb-1">{title}</h3>
              <p className="text-sm text-[#6B7280]">{desc}</p>
            </div>
          ))}
        </div>
      </section>
    </>
  );
}