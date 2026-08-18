import Reveal from "../../components/common/Reveal";
import { FiArrowUpRight, FiCheck } from "react-icons/fi";

const NILAI_LIST = [
  {
    number: "01",
    title: "Lebih gampang mencari jasa",
    desc: "Nggak perlu menyimpan banyak nomor atau mencari jasa secara manual. Semua kebutuhan bisa dimulai dari Tinjaku.",
  },
  {
    number: "02",
    title: "Mitra di sekitar kamu",
    desc: "Tinjaku membantu mempertemukan pelanggan dengan mitra sedot WC yang melayani area mereka.",
  },
  {
    number: "03",
    title: "Prosesnya lebih jelas",
    desc: "Mulai dari membuat pesanan sampai pekerjaan selesai, status pesanan bisa dipantau dengan lebih mudah.",
  },
];

const CARA_KERJA = [
  "Pilih layanan yang kamu butuhkan",
  "Masukkan alamat dan detail pesanan",
  "Tunggu mitra menerima pesanan",
  "Pantau proses sampai pekerjaan selesai",
];

export default function TentangKami() {
  return (
    <div className="max-w-[1280px] mx-auto px-6 md:px-10 py-16 md:py-24">

    
      <Reveal direction="up">
        <div className="max-w-4xl mb-20">
          <p className="font-body text-xs md:text-sm font-bold tracking-[0.18em] uppercase text-ink/40 mb-5">
            Tentang Tinjaku
          </p>

          <h1 className="font-display font-extrabold text-4xl md:text-6xl lg:text-7xl leading-[1.02] text-ink mb-7">
            Urusan sedot WC
            <br />
            <span className="text-ink/35">
              nggak perlu ribet.
            </span>
          </h1>

          <p className="font-body text-base md:text-lg text-ink/60 leading-relaxed max-w-2xl">
            Tinjaku dibuat untuk membantu orang menemukan jasa sedot WC
            tanpa harus bingung mencari kontak atau menunggu terlalu lama.
            Kami mempertemukan pelanggan dengan mitra yang siap melayani
            kebutuhan mereka.
          </p>
        </div>
      </Reveal>

      
      <Reveal direction="up">
        <div className="grid md:grid-cols-[1fr_1.5fr] gap-8 md:gap-16 mb-24 border-t border-ink/10 pt-8">
          <div>
            <p className="font-body text-sm font-bold text-ink">
              Kenapa Tinjaku dibuat?
            </p>
          </div>

          <div>
            <p className="font-display font-bold text-2xl md:text-3xl text-ink leading-snug mb-5">
              Karena mencari jasa sedot WC seharusnya tidak sesulit
              mencari orang yang bisa mengerjakannya.
            </p>

            <p className="font-body text-sm md:text-base text-ink/55 leading-relaxed max-w-2xl">
              Ketika septic tank penuh atau saluran bermasalah, biasanya
              kita cuma ingin satu hal: masalahnya cepat selesai.
              Tinjaku hadir sebagai tempat untuk mencari layanan,
              membuat pesanan, dan mengikuti prosesnya dalam satu
              aplikasi.
            </p>
          </div>
        </div>
      </Reveal>

      
      <section className="mb-24">
        <Reveal direction="up">
          <div className="mb-10">
            <p className="font-body text-xs font-bold tracking-[0.15em] uppercase text-ink/40 mb-3">
              Yang kami tawarkan
            </p>

            <h2 className="font-display font-extrabold text-3xl md:text-4xl text-ink">
              Dibuat supaya lebih praktis.
            </h2>
          </div>
        </Reveal>

        <div className="border-t border-ink/10">
          {NILAI_LIST.map((item, i) => (
            <Reveal
              key={item.number}
              direction="up"
              delay={i * 80}
            >
              <div className="grid md:grid-cols-[70px_280px_1fr] gap-5 md:gap-10 py-7 border-b border-ink/10 group">
                <span className="font-display font-bold text-sm text-ink/30">
                  {item.number}
                </span>

                <h3 className="font-display font-bold text-lg md:text-xl text-ink">
                  {item.title}
                </h3>

                <p className="font-body text-sm md:text-base text-ink/55 leading-relaxed max-w-xl">
                  {item.desc}
                </p>
              </div>
            </Reveal>
          ))}
        </div>
      </section>

      
      <section className="mb-24">
        <Reveal direction="up">
          <div className="grid md:grid-cols-2 gap-10 md:gap-20">

            <div>
              <p className="font-body text-xs font-bold tracking-[0.15em] uppercase text-ink/40 mb-3">
                Cara kerja
              </p>

              <h2 className="font-display font-extrabold text-3xl md:text-4xl text-ink leading-tight mb-5">
                Dari pesan sampai selesai,
                <br />
                semuanya lebih jelas.
              </h2>

              <p className="font-body text-sm md:text-base text-ink/55 leading-relaxed max-w-md">
                Tinjaku dirancang supaya pelanggan tidak perlu bingung
                dengan proses pemesanan. Cukup tentukan kebutuhan,
                masukkan lokasi, dan tunggu mitra menangani pesanan.
              </p>
            </div>

            <div className="space-y-4">
              {CARA_KERJA.map((item, index) => (
                <div
                  key={item}
                  className="flex items-start gap-4 py-4 border-b border-ink/10"
                >
                  <div className="w-8 h-8 shrink-0 rounded-full bg-accent flex items-center justify-center">
                    <FiCheck size={15} className="text-ink" />
                  </div>

                  <div>
                    <span className="font-body text-xs text-ink/35">
                      LANGKAH {index + 1}
                    </span>

                    <p className="font-body font-semibold text-sm md:text-base text-ink mt-1">
                      {item}
                    </p>
                  </div>
                </div>
              ))}
            </div>

          </div>
        </Reveal>
      </section>

      
      <Reveal direction="up">
        <div className="bg-ink rounded-[28px] p-8 md:p-12 flex flex-col md:flex-row md:items-center md:justify-between gap-8">

          <div className="max-w-xl">
            <p className="font-body text-xs font-bold tracking-[0.15em] uppercase text-white/40 mb-3">
              Untuk pemilik usaha
            </p>

            <h2 className="font-display font-extrabold text-2xl md:text-4xl text-white leading-tight mb-4">
              Punya usaha sedot WC?
            </h2>

            <p className="font-body text-sm md:text-base text-white/60 leading-relaxed">
              Gabung menjadi mitra Tinjaku dan dapatkan kesempatan
              menerima pesanan dari pelanggan di area layananmu.
            </p>
          </div>

          <a
            href="/register"
            className="shrink-0 inline-flex items-center justify-center gap-2 font-body font-bold text-sm text-ink bg-accent px-6 py-3.5 rounded-full hover:brightness-95 transition"
          >
            Daftar Jadi Mitra
            <FiArrowUpRight size={17} />
          </a>

        </div>
      </Reveal>

    </div>
  );
}