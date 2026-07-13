import { Link } from "react-router-dom";
import { FiHome, FiBriefcase, FiAlertCircle } from "react-icons/fi";
import Button from "../components/Button";

const layananList = [
  {
    icon: FiHome,
    title: "Sedot WC Rumah",
    desc: "Layanan rutin untuk septic tank rumah tangga, proses cepat tanpa bikin berantakan.",
  },
  {
    icon: FiBriefcase,
    title: "Sedot WC Komersial",
    desc: "Untuk kantor, ruko, restoran, dan bangunan komersial dengan kapasitas lebih besar.",
  },
  {
    icon: FiAlertCircle,
    title: "Darurat 24 Jam",
    desc: "Septic tank meluap mendadak? Tim kami siap datang kapan saja, termasuk malam hari.",
  },
];

export default function Layanan() {
  return (
    <div>
      <section className="bg-[#0A0A0A] text-white">
        <div className="max-w-4xl mx-auto px-5 py-16 text-center">
          <h1 className="font-[Baloo_2] font-extrabold text-4xl mb-4">
            Layanan <span className="text-[#FFC800]">Kami</span>
          </h1>
          <p className="text-white/70 text-lg max-w-xl mx-auto">
            Pilih layanan sesuai kebutuhanmu — semuanya ditangani teknisi
            berpengalaman.
          </p>
        </div>
      </section>

      <section className="max-w-5xl mx-auto px-5 py-16">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {layananList.map(({ icon: Icon, title, desc }) => (
            <div
              key={title}
              className="bg-white border-2 border-[#0A0A0A]/10 rounded-2xl p-6 hover:border-[#FFC800] transition-colors"
            >
              <div className="w-12 h-12 rounded-xl bg-[#FFC800] text-[#0A0A0A] flex items-center justify-center mb-4">
                <Icon size={22} />
              </div>
              <h3 className="font-[Baloo_2] font-bold text-lg text-[#0A0A0A] mb-2">
                {title}
              </h3>
              <p className="text-sm text-[#6B7280]">{desc}</p>
            </div>
          ))}
        </div>

        <div className="text-center mt-12">
          <Link to="/pesanan">
            <Button variant="secondary" size="lg">
              Pesan Layanan Sekarang
            </Button>
          </Link>
        </div>
      </section>
    </div>
  );
}