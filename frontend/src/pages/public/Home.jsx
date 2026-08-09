import Reveal from "../../components/common/Reveal";
import CityIsometric from "../../components/illustrations/CityIsometric";

export default function Home() {
  return (
    <div
      className="min-h-screen bg-[#fbfbfc]"
      style={{
        backgroundImage: "radial-gradient(#e4e4e9 1.5px, transparent 1.5px)",
        backgroundSize: "28px 28px",
      }}
    >
      <section className="max-w-[1440px] mx-auto grid grid-cols-2 items-center gap-10 px-16 pt-16 pb-24 max-md:grid-cols-1 max-md:px-6 max-md:pt-10 max-md:pb-16">
        <div>
          <Reveal direction="up">
            <h1 className="font-display font-extrabold text-[52px] leading-[1.15] text-ink mb-7 max-md:text-4xl">
              Solusi Tepat
              <br />
              untuk Masalah
              <br />
              Septic Tank
            </h1>
          </Reveal>

          <Reveal direction="up" delay={150}>
            <p className="font-body font-medium text-[17px] leading-[1.7] text-ink max-w-[420px]">
              Tinjaku memberikan layanan sedot WC profesional dengan proses
              cepat, harga transparan, dan pelayanan terpercaya untuk rumah,
              kantor, serta bangunan komersial.
            </p>
          </Reveal>
        </div>

        <Reveal direction="left" delay={200}>
          <div className="flex justify-center">
            <CityIsometric />
          </div>
        </Reveal>
      </section>
    </div>
  );
}