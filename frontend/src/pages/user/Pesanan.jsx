import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import {
  FiMapPin,
  FiUser,
  FiFileText,
  FiArrowRight,
  FiCheck,
  FiPlus,
} from "react-icons/fi";

import { createPesanan } from "../../services/pesananService";
import { getAlamat } from "../../services/alamatService";
import { useAuth } from "../../hooks/useAuth";

export default function Pesanan() {
  const { user } = useAuth();
  const navigate = useNavigate();

  const [alamatList, setAlamatList] = useState([]);
  const [loadingAlamat, setLoadingAlamat] = useState(true);

  const [form, setForm] = useState({
    namaPenerima: "",
    alamatId: "",
    keluhan: "",
  });

  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    getAlamat()
      .then(setAlamatList)
      .catch((err) => setError(err.message))
      .finally(() => setLoadingAlamat(false));
  }, []);

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    setLoading(true);
    setError(null);

    try {
      await createPesanan({
        ...form,
        alamatId: Number(form.alamatId),
      });

      navigate("/riwayat");
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-5xl mx-auto">

      
      <div className="mb-8">
        <p className="text-sm text-black/40 mb-2">
          Layanan
        </p>

        <h1 className="font-display font-extrabold text-3xl text-[#111116]">
          Buat Pesanan
        </h1>

        <p className="text-sm text-black/50 mt-2 max-w-xl">
          Isi informasi di bawah ini agar mitra dapat mengetahui
          lokasi dan kebutuhan layanan kamu.
        </p>
      </div>

      
      <div className="grid lg:grid-cols-[1fr_300px] gap-6">

        
        <form
          onSubmit={handleSubmit}
          className="bg-white border border-black/[0.07] rounded-2xl overflow-hidden"
        >

          
          <div className="px-7 py-6 border-b border-black/[0.06]">
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 rounded-xl bg-[#FFF4CC] flex items-center justify-center">
                <FiFileText
                  size={18}
                  className="text-[#111116]"
                />
              </div>

              <div>
                <h2 className="font-display font-bold text-lg text-[#111116]">
                  Detail Pesanan
                </h2>

                <p className="text-xs text-black/40 mt-0.5">
                  Lengkapi data berikut
                </p>
              </div>
            </div>
          </div>

          
          <div className="p-7 max-md:p-5">

            
            {error && (
              <div className="mb-6 bg-[#FFF0F0] border border-[#F3CACA] text-[#C43D3D] rounded-xl px-4 py-3 text-sm">
                {error}
              </div>
            )}

            
            <div className="mb-6">
              <label className="flex items-center gap-2 text-sm font-bold text-[#111116] mb-2">
                <FiUser size={15} />
                Nama Penerima
              </label>

              <input
                name="namaPenerima"
                value={form.namaPenerima}
                onChange={handleChange}
                placeholder="Masukkan nama penerima"
                className="w-full border border-black/[0.12] rounded-xl px-4 py-3.5 text-sm text-[#111116] placeholder:text-black/25 outline-none focus:border-[#FFC800] focus:ring-2 focus:ring-[#FFC800]/20 transition"
                required
              />
            </div>

            
            <div className="mb-6">
              <div className="flex items-center justify-between mb-2">
                <label className="flex items-center gap-2 text-sm font-bold text-[#111116]">
                  <FiMapPin size={15} />
                  Alamat Layanan
                </label>

                <button
                  type="button"
                  onClick={() => navigate("/alamat")}
                  className="text-xs font-semibold text-black/40 hover:text-black flex items-center gap-1 transition"
                >
                  <FiPlus size={13} />
                  Kelola alamat
                </button>
              </div>

              {loadingAlamat ? (
                <div className="border border-black/[0.08] rounded-xl px-4 py-4">
                  <div className="flex items-center gap-3">
                    <div className="w-4 h-4 border-2 border-black/10 border-t-black rounded-full animate-spin" />

                    <span className="text-sm text-black/40">
                      Memuat alamat...
                    </span>
                  </div>
                </div>
              ) : alamatList.length === 0 ? (
                <div className="border border-dashed border-black/15 rounded-xl px-5 py-6 text-center">
                  <FiMapPin
                    size={22}
                    className="mx-auto text-black/20 mb-2"
                  />

                  <p className="text-sm font-semibold text-black/60">
                    Belum ada alamat
                  </p>

                  <p className="text-xs text-black/35 mt-1 mb-4">
                    Tambahkan alamat terlebih dahulu sebelum membuat pesanan.
                  </p>

                  <button
                    type="button"
                    onClick={() => navigate("/alamat")}
                    className="inline-flex items-center gap-2 bg-[#FFC800] text-[#111116] px-4 py-2.5 rounded-xl text-xs font-bold hover:brightness-95 transition"
                  >
                    <FiPlus size={14} />
                    Tambah Alamat
                  </button>
                </div>
              ) : (
                <select
                  name="alamatId"
                  value={form.alamatId}
                  onChange={handleChange}
                  className="w-full border border-black/[0.12] rounded-xl px-4 py-3.5 text-sm text-[#111116] bg-white outline-none focus:border-[#FFC800] focus:ring-2 focus:ring-[#FFC800]/20 transition"
                  required
                >
                  <option value="" disabled>
                    Pilih alamat layanan
                  </option>

                  {alamatList.map((a, i) => (
                    <option
                      key={a.idALamat ?? i}
                      value={a.idALamat ?? i}
                    >
                      {a.label} — {a.jalan}, {a.kecamatan}
                    </option>
                  ))}
                </select>
              )}
            </div>

            
            <div className="mb-7">
              <label className="flex items-center gap-2 text-sm font-bold text-[#111116] mb-2">
                <FiFileText size={15} />
                Keluhan / Kebutuhan
              </label>

              <textarea
                name="keluhan"
                value={form.keluhan}
                onChange={handleChange}
                placeholder="Contoh: WC mampet dan air sulit mengalir..."
                rows={5}
                className="w-full border border-black/[0.12] rounded-xl px-4 py-3.5 text-sm text-[#111116] placeholder:text-black/25 outline-none focus:border-[#FFC800] focus:ring-2 focus:ring-[#FFC800]/20 transition resize-none"
                required
              />

              <p className="text-xs text-black/30 mt-2">
                Jelaskan masalah yang sedang kamu alami agar mitra
                bisa mempersiapkan kebutuhan layanan.
              </p>
            </div>

            
            <button
              type="submit"
              disabled={loading || alamatList.length === 0}
              className="w-full flex items-center justify-center gap-2 bg-[#FFC800] text-[#111116] rounded-xl py-3.5 font-bold text-sm hover:brightness-95 disabled:opacity-50 disabled:cursor-not-allowed transition"
            >
              {loading ? (
                <>
                  <div className="w-4 h-4 border-2 border-black/20 border-t-black rounded-full animate-spin" />
                  Mengirim Pesanan...
                </>
              ) : (
                <>
                  Kirim Pesanan
                  <FiArrowRight size={17} />
                </>
              )}
            </button>
          </div>
        </form>

        
        <aside className="flex flex-col gap-4">

          
          <div className="bg-[#111116] rounded-2xl p-6 text-white">
            <p className="text-xs text-white/40 uppercase tracking-wider font-semibold mb-5">
              Cara kerja
            </p>

            <div className="flex flex-col gap-5">

              <div className="flex gap-3">
                <div className="w-7 h-7 rounded-full bg-[#FFC800] text-[#111116] flex items-center justify-center shrink-0 text-xs font-bold">
                  1
                </div>

                <div>
                  <p className="text-sm font-semibold">
                    Buat pesanan
                  </p>

                  <p className="text-xs text-white/45 mt-1 leading-relaxed">
                    Masukkan alamat dan jelaskan kebutuhanmu.
                  </p>
                </div>
              </div>

              <div className="flex gap-3">
                <div className="w-7 h-7 rounded-full bg-white/10 text-white flex items-center justify-center shrink-0 text-xs font-bold">
                  2
                </div>

                <div>
                  <p className="text-sm font-semibold">
                    Mitra menerima
                  </p>

                  <p className="text-xs text-white/45 mt-1 leading-relaxed">
                    Pesanan akan diteruskan kepada mitra.
                  </p>
                </div>
              </div>

              <div className="flex gap-3">
                <div className="w-7 h-7 rounded-full bg-white/10 text-white flex items-center justify-center shrink-0 text-xs font-bold">
                  3
                </div>

                <div>
                  <p className="text-sm font-semibold">
                    Layanan selesai
                  </p>

                  <p className="text-xs text-white/45 mt-1 leading-relaxed">
                    Pantau proses pesanan melalui riwayat.
                  </p>
                </div>
              </div>

            </div>
          </div>

          
          <div className="bg-white border border-black/[0.07] rounded-2xl p-6">

            <div className="w-9 h-9 rounded-xl bg-[#FFF4CC] flex items-center justify-center mb-4">
              <FiCheck
                size={17}
                className="text-[#111116]"
              />
            </div>

            <h3 className="font-display font-bold text-base text-[#111116]">
              Biar lebih cepat
            </h3>

            <p className="text-xs text-black/45 leading-relaxed mt-2">
              Pastikan alamat dan keluhan yang kamu masukkan
              sudah sesuai agar mitra dapat memproses pesanan
              dengan lebih cepat.
            </p>

          </div>

        </aside>
      </div>
    </div>
  );
}