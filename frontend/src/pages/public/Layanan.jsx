const LAYANAN_LIST = [
  {
    icon: "🚛",
    title: "Sedot WC Rumah",
    desc: "Layanan sedot septic tank untuk rumah tinggal, proses cepat dan bersih tanpa bau.",
  },
  {
    icon: "🏢",
    title: "Sedot WC Kantor",
    desc: "Solusi untuk gedung perkantoran dengan jadwal fleksibel, tidak mengganggu jam kerja.",
  },
  {
    icon: "🏭",
    title: "Sedot WC Pabrik & Gudang",
    desc: "Penanganan skala besar untuk kawasan industri dengan peralatan kapasitas tinggi.",
  },
  {
    icon: "🏨",
    title: "Sedot WC Hotel",
    desc: "Layanan rutin maupun darurat untuk menjaga kenyamanan tamu hotel Anda.",
  },
  {
    icon: "🔧",
    title: "Perawatan Berkala",
    desc: "Paket langganan perawatan septic tank agar masalah tidak terjadi berulang.",
  },
  {
    icon: "⚡",
    title: "Panggilan Darurat",
    desc: "Respons cepat untuk kondisi mendesak, tersedia di area jangkauan kami.",
  },
];

export default function Layanan() {
  return (
    <div className="max-w-[1440px] mx-auto px-16 py-20 max-md:px-6 max-md:py-12">
      <div className="text-center max-w-2xl mx-auto mb-16">
        <h1 className="font-display font-extrabold text-[42px] text-ink mb-4 max-md:text-3xl">
          Layanan Kami
        </h1>
        <p className="font-body text-ink/70 text-base leading-relaxed">
          Tinjaku menyediakan berbagai layanan sedot WC profesional untuk
          kebutuhan rumah, kantor, hingga kawasan industri.
        </p>
      </div>

      <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
        {LAYANAN_LIST.map((item) => (
          <div
            key={item.title}
            className="bg-white border border-gray-200 rounded-2xl p-7 hover:shadow-lg transition-shadow"
          >
            <div className="text-4xl mb-4">{item.icon}</div>
            <h3 className="font-display font-bold text-lg text-ink mb-2">
              {item.title}
            </h3>
            <p className="font-body text-sm text-ink/60 leading-relaxed">
              {item.desc}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
}