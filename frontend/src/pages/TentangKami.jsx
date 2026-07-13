import { FiTarget, FiUsers, FiAward } from "react-icons/fi";

export default function TentangKami() {
  return (
    <div>
      {/* Hero kecil */}
      <section className="bg-[#0A0A0A] text-white">
        <div className="max-w-4xl mx-auto px-5 py-16 text-center">
          <h1 className="font-[Baloo_2] font-extrabold text-4xl mb-4">
            Tentang <span className="text-[#FFC800]">Tinjaku</span>
          </h1>
          <p className="text-white/70 text-lg max-w-xl mx-auto">
            Kami hadir untuk bikin urusan septic tank tidak lagi jadi masalah
            besar — cepat, bersih, dan bisa dipercaya.
          </p>
        </div>
      </section>

      {/* Cerita singkat */}
      <section className="max-w-3xl mx-auto px-5 py-16">
        <h2 className="font-[Baloo_2] font-bold text-2xl text-[#0A0A0A] mb-4">
          Cerita Kami
        </h2>
        <p className="text-[#6B7280] leading-relaxed mb-4">
          Tinjaku berawal dari masalah sederhana yang sering dianggap remeh:
          susahnya cari jasa sedot WC yang datang tepat waktu, kerjanya rapi,
          dan harganya jelas dari awal. Kami membangun platform ini supaya
          siapa pun bisa memanggil teknisi terpercaya hanya dengan beberapa
          klik.
        </p>
        <p className="text-[#6B7280] leading-relaxed">
          Sekarang, Tinjaku menghubungkan pelanggan dengan mitra teknisi di
          berbagai kota, memastikan setiap pesanan ditangani dengan standar
          kebersihan dan keamanan yang konsisten.
        </p>
      </section>

      {/* Nilai-nilai */}
      <section className="bg-[#FAFAFA] py-16">
        <div className="max-w-5xl mx-auto px-5 grid grid-cols-1 md:grid-cols-3 gap-8">
          {[
            { icon: FiTarget, title: "Misi Kami", desc: "Membuat layanan sedot WC mudah diakses, cepat, dan transparan bagi semua orang." },
            { icon: FiUsers, title: "Untuk Semua", desc: "Melayani rumah tangga, kantor, hingga bangunan komersial di berbagai kota." },
            { icon: FiAward, title: "Standar Terjamin", desc: "Setiap mitra teknisi melalui proses verifikasi sebelum bergabung." },
          ].map(({ icon: Icon, title, desc }) => (
            <div key={title} className="bg-white rounded-2xl p-6 border-2 border-[#0A0A0A]/10">
              <div className="w-12 h-12 rounded-xl bg-[#FFC800] text-[#0A0A0A] flex items-center justify-center mb-4">
                <Icon size={22} />
              </div>
              <h3 className="font-[Baloo_2] font-bold text-[#0A0A0A] mb-2">{title}</h3>
              <p className="text-sm text-[#6B7280]">{desc}</p>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}