import Reveal from "../../components/common/Reveal";

const NILAI_LIST = [
  { title: "Cepat", desc: "Tim kami sigap merespons dan menyelesaikan pekerjaan tepat waktu." },
  { title: "Transparan", desc: "Harga jelas di awal, tanpa biaya tersembunyi." },
  { title: "Terpercaya", desc: "Mitra terverifikasi dengan rating dan ulasan dari pelanggan lain." },
];

export default function TentangKami() {
  return (
    <div className="max-w-[1440px] mx-auto px-16 py-20 max-md:px-6 max-md:py-12">
      <Reveal direction="up">
        <div className="max-w-2xl mb-16">
          <h1 className="font-display font-extrabold text-[42px] text-ink mb-4 max-md:text-3xl">
            Tentang Tinjaku
          </h1>
          <p className="font-body text-ink/70 text-base leading-relaxed">
            Tinjaku hadir untuk menjawab masalah septic tank yang sering
            diabaikan. Kami menghubungkan pelanggan dengan mitra sedot WC
            profesional di sekitar mereka, cepat dan tanpa ribet.
          </p>
        </div>
      </Reveal>

      <div className="grid md:grid-cols-3 gap-6 mb-16">
        {NILAI_LIST.map((item, i) => (
          <Reveal key={item.title} direction="up" delay={i * 100}>
            <div className="bg-white border border-gray-200 rounded-2xl p-7 h-full">
              <div className="w-10 h-10 rounded-full bg-accent flex items-center justify-center mb-4">
                <span className="w-4 h-1.5 bg-ink rounded-full"></span>
              </div>
              <h3 className="font-display font-bold text-lg text-ink mb-2">
                {item.title}
              </h3>
              <p className="font-body text-sm text-ink/60 leading-relaxed">
                {item.desc}
              </p>
            </div>
          </Reveal>
        ))}
      </div>

      <Reveal direction="up">
        <div className="bg-ink rounded-2xl p-10 text-center max-md:p-6">
          <h2 className="font-display font-bold text-2xl text-white mb-3">
            Bergabung Jadi Mitra Tinjaku
          </h2>
          <p className="font-body text-white/70 text-sm mb-6 max-w-lg mx-auto leading-relaxed">
            Punya armada sedot WC? Daftarkan usaha Anda dan dapatkan pelanggan baru setiap hari.
          </p>
          <a href="/register" className="inline-block font-body font-bold text-sm uppercase text-ink bg-accent px-7 py-3.5 rounded-full hover:brightness-95">
            Daftar Sekarang
          </a>
        </div>
      </Reveal>
    </div>
  );
}