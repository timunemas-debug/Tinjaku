export default function Profile() {
  return (
    <div className="min-h-[80vh] bg-[#FAFAFA] px-4 py-12">
      <div className="max-w-md mx-auto">
        <h1 className="font-[Baloo_2] font-extrabold text-2xl text-[#0A0A0A] mb-6">
          Profil Saya
        </h1>

        <div className="bg-white border-2 border-[#0A0A0A]/10 rounded-3xl p-8 text-center">
          <div className="w-20 h-20 rounded-full bg-[#FFC800] text-[#0A0A0A] font-[Baloo_2] font-extrabold text-2xl flex items-center justify-center mx-auto mb-4">
            ?
          </div>
          <p className="font-[Baloo_2] font-bold text-[#0A0A0A] mb-1">
            Belum Login
          </p>
          <p className="text-sm text-[#6B7280] mb-6">
            Fitur login sedang dalam pengembangan. Data profil akan muncul di sini
            setelah sistem login siap.
          </p>
        </div>
      </div>
    </div>
  );
}