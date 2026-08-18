import Reveal from "../../components/common/Reveal";
import { FiArrowUpRight } from "react-icons/fi";

const LAYANAN_LIST = [
  {
    number: "01",
    category: "RUMAH TINGGAL",
    title: "Sedot WC Rumah",
    desc: "Untuk septic tank rumah yang mulai penuh, mampet, atau butuh dikuras secara berkala.",
  },
  {
    number: "02",
    category: "PERKANTORAN",
    title: "Sedot WC Kantor",
    desc: "Layanan untuk kantor dan ruko dengan waktu pengerjaan yang bisa disesuaikan dengan aktivitas kerja.",
  },
  {
    number: "03",
    category: "USAHA & INDUSTRI",
    title: "Sedot WC Skala Besar",
    desc: "Penanganan septic tank berkapasitas besar untuk pabrik, gudang, dan area usaha.",
  },
  {
    number: "04",
    category: "HOTEL & PENGINAPAN",
    title: "Perawatan WC Hotel",
    desc: "Solusi untuk menjaga fasilitas sanitasi tetap nyaman digunakan oleh tamu dan staf.",
  },
  {
    number: "05",
    category: "PERAWATAN",
    title: "Perawatan Berkala",
    desc: "Jadwal penyedotan rutin untuk membantu mencegah septic tank penuh dan masalah saluran.",
  },
  {
    number: "06",
    category: "DARURAT",
    title: "Panggilan Darurat",
    desc: "Butuh bantuan segera? Hubungi Tinjaku dan kami akan membantu sesuai area layanan yang tersedia.",
  },
];

export default function Layanan() {
  return (
    <div className="max-w-[1280px] mx-auto px-6 md:px-10 py-16 md:py-24">

      
      <Reveal direction="up">
        <div className="max-w-3xl mb-14">
          <p className="font-body text-sm font-semibold tracking-[0.18em] text-ink/45 uppercase mb-4">
            Layanan Tinjaku
          </p>

          <h1 className="font-display font-extrabold text-4xl md:text-6xl leading-[1.05] text-ink mb-6">
            Urusan WC,
            <br />
            <span className="text-ink/45">biar kami yang urus.</span>
          </h1>

          <p className="font-body text-base md:text-lg text-ink/60 leading-relaxed max-w-2xl">
            Dari rumah tinggal sampai tempat usaha, Tinjaku membantu
            menangani kebutuhan sedot WC dan perawatan septic tank
            tanpa ribet.
          </p>
        </div>
      </Reveal>

      
      <div className="border-t border-ink/10">
        {LAYANAN_LIST.map((item, i) => (
          <Reveal
            key={item.title}
            direction="up"
            delay={i * 60}
          >
            <div className="group grid md:grid-cols-[80px_180px_1fr_50px] gap-5 md:gap-8 items-start py-7 border-b border-ink/10 hover:bg-white/60 transition-colors px-2 md:px-4">

              
              <span className="font-display font-bold text-sm text-ink/30 pt-1">
                {item.number}
              </span>

              
              <span className="font-body text-[11px] font-bold tracking-[0.12em] text-ink/40 pt-1">
                {item.category}
              </span>

              
              <div>
                <h2 className="font-display font-bold text-xl md:text-2xl text-ink mb-2">
                  {item.title}
                </h2>

                <p className="font-body text-sm md:text-base text-ink/55 leading-relaxed max-w-2xl">
                  {item.desc}
                </p>
              </div>

              
              <div className="hidden md:flex w-10 h-10 rounded-full border border-ink/10 items-center justify-center group-hover:bg-accent group-hover:border-accent transition-all">
                <FiArrowUpRight
                  size={18}
                  className="text-ink group-hover:translate-x-0.5 group-hover:-translate-y-0.5 transition-transform"
                />
              </div>
            </div>
          </Reveal>
        ))}
      </div>

      
      <Reveal direction="up">
        <div className="mt-12 bg-ink rounded-3xl px-7 py-8 md:px-10 md:py-10 flex flex-col md:flex-row md:items-center md:justify-between gap-6">
          <div>
            <p className="font-body text-sm text-white/50 mb-2">
              Tidak yakin pilih yang mana?
            </p>

            <h2 className="font-display font-bold text-2xl md:text-3xl text-white">
              Ceritakan kebutuhanmu.
            </h2>
          </div>

          <a
            href="/pesanan"
            className="inline-flex items-center justify-center gap-2 bg-accent text-ink font-body font-bold text-sm px-6 py-3.5 rounded-full hover:brightness-95 transition"
          >
            Buat Pesanan
            <FiArrowUpRight size={17} />
          </a>
        </div>
      </Reveal>

    </div>
  );
}