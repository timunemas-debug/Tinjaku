import { useEffect, useState } from "react";
import {
  FiMapPin,
  FiPlus,
  FiEdit2,
  FiHome,
  FiCheck,
  FiX,
} from "react-icons/fi";

import {
  getAlamat,
  createAlamat,
  updateAlamat,
} from "../../services/alamatService";

import { useAuth } from "../../hooks/useAuth";

export default function Alamat() {
  const { user } = useAuth();

  const [alamatList, setAlamatList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState(null);

  const [showForm, setShowForm] = useState(false);
  const [editingId, setEditingId] = useState(null);

  const [form, setForm] = useState({
    label: "",
    jalan: "",
    kecamatan: "",
    kota: "",
    provinsi: "",
    kodePos: "",
  });

  useEffect(() => {
    loadAlamat();
  }, []);

  const loadAlamat = async () => {
    try {
      setLoading(true);
      setError(null);

      const data = await getAlamat();

      setAlamatList(Array.isArray(data) ? data : []);
    } catch (err) {
      console.error("Gagal mengambil alamat:", err);

      setError(
        err?.response?.data?.message ||
          err?.message ||
          "Gagal mengambil data alamat."
      );

      setAlamatList([]);
    } finally {
      setLoading(false);
    }
  };

  const resetForm = () => {
    setForm({
      label: "",
      jalan: "",
      kecamatan: "",
      kota: "",
      provinsi: "",
      kodePos: "",
    });

    setEditingId(null);
    setShowForm(false);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    setForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const getAlamatId = (alamat) => {
    return alamat.idALamat ?? alamat.alamatId ?? alamat.id ?? null;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!user?.userId) {
      setError("Data user tidak ditemukan. Silakan login kembali.");
      return;
    }

    try {
      setSaving(true);
      setError(null);

      if (editingId) {
        await updateAlamat(editingId, form);
      } else {
        await createAlamat(user.userId, form);
      }

      await loadAlamat();

      resetForm();
    } catch (err) {
      console.error("Gagal menyimpan alamat:", err);

      setError(
        err?.response?.data?.message ||
          err?.message ||
          "Gagal menyimpan alamat."
      );
    } finally {
      setSaving(false);
    }
  };

  const handleEdit = (alamat) => {
    const id = getAlamatId(alamat);

    setForm({
      label: alamat.label || "",
      jalan: alamat.jalan || "",
      kecamatan: alamat.kecamatan || "",
      kota: alamat.kota || "",
      provinsi: alamat.provinsi || "",
      kodePos: alamat.kodePos || "",
    });

    setEditingId(id);
    setShowForm(true);

    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };

  return (
    <div className="max-w-5xl mx-auto">
      <div className="flex items-end justify-between gap-4 mb-8 max-md:flex-col max-md:items-start">
        <div>
          <p className="text-sm text-black/40 mb-2">
            Pengaturan akun
          </p>

          <h1 className="font-display font-extrabold text-3xl text-[#111116]">
            Alamat Saya
          </h1>

          <p className="text-sm text-black/50 mt-2">
            Simpan alamat yang sering digunakan untuk layanan Tinjaku.
          </p>
        </div>

        {!showForm && (
          <button
            type="button"
            onClick={() => {
              setEditingId(null);
              setShowForm(true);
            }}
            className="inline-flex items-center gap-2 bg-[#FFC800] text-[#111116] px-5 py-3 rounded-xl text-sm font-bold hover:brightness-95 transition"
          >
            <FiPlus size={16} />
            Tambah Alamat
          </button>
        )}
      </div>

      {error && (
        <div className="mb-6 bg-[#FFF0F0] border border-[#F3CACA] rounded-xl px-4 py-3 text-sm text-[#C43D3D] flex items-center justify-between gap-4">
          <span>{error}</span>

          <button
            type="button"
            onClick={() => setError(null)}
            className="text-[#C43D3D]"
          >
            <FiX size={16} />
          </button>
        </div>
      )}

      {showForm && (
        <div className="bg-white border border-black/[0.07] rounded-2xl mb-6 overflow-hidden">
          <div className="px-6 py-5 border-b border-black/[0.06] flex items-center justify-between">
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 rounded-xl bg-[#FFF4CC] flex items-center justify-center">
                <FiMapPin size={18} />
              </div>

              <div>
                <h2 className="font-display font-bold text-lg text-[#111116]">
                  {editingId ? "Edit Alamat" : "Tambah Alamat"}
                </h2>

                <p className="text-xs text-black/40 mt-0.5">
                  Isi informasi alamat dengan lengkap
                </p>
              </div>
            </div>

            <button
              type="button"
              onClick={resetForm}
              className="w-9 h-9 rounded-lg hover:bg-black/5 flex items-center justify-center text-black/40"
            >
              <FiX size={18} />
            </button>
          </div>

          <form
            onSubmit={handleSubmit}
            className="p-6"
          >
            <div className="mb-5">
              <label className="block text-sm font-bold text-[#111116] mb-2">
                Label Alamat
              </label>

              <input
                type="text"
                name="label"
                value={form.label}
                onChange={handleChange}
                placeholder="Contoh: Rumah, Kos, Kantor"
                className="w-full border border-black/[0.12] rounded-xl px-4 py-3.5 text-sm outline-none focus:border-[#FFC800] focus:ring-2 focus:ring-[#FFC800]/20"
                required
              />
            </div>

            <div className="mb-5">
              <label className="block text-sm font-bold text-[#111116] mb-2">
                Alamat Lengkap
              </label>

              <textarea
                name="jalan"
                value={form.jalan}
                onChange={handleChange}
                placeholder="Masukkan nama jalan, nomor rumah, RT/RW"
                rows={4}
                className="w-full border border-black/[0.12] rounded-xl px-4 py-3.5 text-sm outline-none focus:border-[#FFC800] focus:ring-2 focus:ring-[#FFC800]/20 resize-none"
                required
              />
            </div>

            <div className="grid md:grid-cols-2 gap-5 mb-5">
              <div>
                <label className="block text-sm font-bold text-[#111116] mb-2">
                  Kecamatan
                </label>

                <input
                  type="text"
                  name="kecamatan"
                  value={form.kecamatan}
                  onChange={handleChange}
                  placeholder="Kecamatan"
                  className="w-full border border-black/[0.12] rounded-xl px-4 py-3.5 text-sm outline-none focus:border-[#FFC800] focus:ring-2 focus:ring-[#FFC800]/20"
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-bold text-[#111116] mb-2">
                  Kota / Kabupaten
                </label>

                <input
                  type="text"
                  name="kota"
                  value={form.kota}
                  onChange={handleChange}
                  placeholder="Kota atau Kabupaten"
                  className="w-full border border-black/[0.12] rounded-xl px-4 py-3.5 text-sm outline-none focus:border-[#FFC800] focus:ring-2 focus:ring-[#FFC800]/20"
                  required
                />
              </div>
            </div>

            <div className="grid md:grid-cols-2 gap-5 mb-6">
              <div>
                <label className="block text-sm font-bold text-[#111116] mb-2">
                  Provinsi
                </label>

                <input
                  type="text"
                  name="provinsi"
                  value={form.provinsi}
                  onChange={handleChange}
                  placeholder="Provinsi"
                  className="w-full border border-black/[0.12] rounded-xl px-4 py-3.5 text-sm outline-none focus:border-[#FFC800] focus:ring-2 focus:ring-[#FFC800]/20"
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-bold text-[#111116] mb-2">
                  Kode Pos
                </label>

                <input
                  type="text"
                  name="kodePos"
                  value={form.kodePos}
                  onChange={handleChange}
                  placeholder="Kode Pos"
                  className="w-full border border-black/[0.12] rounded-xl px-4 py-3.5 text-sm outline-none focus:border-[#FFC800] focus:ring-2 focus:ring-[#FFC800]/20"
                  required
                />
              </div>
            </div>

            <div className="flex justify-end gap-3">
              <button
                type="button"
                onClick={resetForm}
                className="px-5 py-3 rounded-xl text-sm font-semibold text-black/50 hover:bg-black/5 transition"
              >
                Batal
              </button>

              <button
                type="submit"
                disabled={saving}
                className="inline-flex items-center gap-2 bg-[#FFC800] text-[#111116] px-5 py-3 rounded-xl text-sm font-bold hover:brightness-95 disabled:opacity-50 disabled:cursor-not-allowed transition"
              >
                {saving ? (
                  <>
                    <div className="w-4 h-4 border-2 border-black/20 border-t-black rounded-full animate-spin" />
                    Menyimpan...
                  </>
                ) : (
                  <>
                    <FiCheck size={16} />
                    {editingId
                      ? "Simpan Perubahan"
                      : "Simpan Alamat"}
                  </>
                )}
              </button>
            </div>
          </form>
        </div>
      )}

      <div className="bg-white border border-black/[0.07] rounded-2xl overflow-hidden">
        <div className="px-6 py-5 border-b border-black/[0.06]">
          <h2 className="font-display font-bold text-lg text-[#111116]">
            Alamat Tersimpan
          </h2>

          <p className="text-xs text-black/40 mt-1">
            {loading
              ? "Memuat alamat..."
              : `${alamatList.length} alamat tersimpan`}
          </p>
        </div>

        {loading && (
          <div className="p-12 text-center">
            <div className="w-7 h-7 border-2 border-black/10 border-t-[#111116] rounded-full animate-spin mx-auto mb-3" />

            <p className="text-sm text-black/40">
              Memuat alamat...
            </p>
          </div>
        )}

        {!loading && alamatList.length === 0 && (
          <div className="py-16 px-6 text-center">
            <div className="w-14 h-14 rounded-2xl bg-[#F5F5F6] flex items-center justify-center mx-auto mb-4">
              <FiHome
                size={24}
                className="text-black/25"
              />
            </div>

            <h3 className="font-display font-bold text-lg text-[#111116]">
              Belum ada alamat
            </h3>

            <p className="text-sm text-black/40 mt-2 max-w-sm mx-auto">
              Tambahkan alamat agar proses pemesanan layanan
              menjadi lebih cepat.
            </p>

            <button
              type="button"
              onClick={() => setShowForm(true)}
              className="inline-flex items-center gap-2 mt-5 bg-[#FFC800] text-[#111116] px-5 py-3 rounded-xl text-sm font-bold"
            >
              <FiPlus size={15} />
              Tambah Alamat
            </button>
          </div>
        )}

        {!loading && alamatList.length > 0 && (
          <div className="divide-y divide-black/[0.06]">
            {alamatList.map((alamat, index) => {
              const alamatId = getAlamatId(alamat);

              return (
                <div
                  key={alamatId ?? index}
                  className="p-6 hover:bg-[#FAFAFA] transition"
                >
                  <div className="flex items-start justify-between gap-5">
                    <div className="flex gap-4 min-w-0">
                      <div className="w-11 h-11 rounded-xl bg-[#FFF4CC] flex items-center justify-center shrink-0">
                        <FiMapPin
                          size={18}
                          className="text-[#111116]"
                        />
                      </div>

                      <div className="min-w-0">
                        <div className="flex items-center gap-2 mb-2 flex-wrap">
                          <h3 className="font-display font-bold text-base text-[#111116]">
                            {alamat.label || "Alamat"}
                          </h3>

                          {index === 0 && (
                            <span className="px-2 py-1 rounded-full bg-[#F1F1F2] text-[10px] font-bold text-black/45">
                              Alamat utama
                            </span>
                          )}
                        </div>

                        <p className="text-sm text-black/60 leading-relaxed">
                          {alamat.jalan}
                        </p>

                        <p className="text-xs text-black/40 mt-1">
                          {alamat.kecamatan}

                          {alamat.kota &&
                            `, ${alamat.kota}`}

                          {alamat.provinsi &&
                            `, ${alamat.provinsi}`}

                          {alamat.kodePos &&
                            ` ${alamat.kodePos}`}
                        </p>
                      </div>
                    </div>

                    <button
                      type="button"
                      onClick={() => handleEdit(alamat)}
                      className="w-9 h-9 rounded-lg flex items-center justify-center text-black/35 hover:text-black hover:bg-black/5 transition shrink-0"
                      title="Edit alamat"
                    >
                      <FiEdit2 size={16} />
                    </button>
                  </div>
                </div>
              );
            })}
          </div>
        )}
      </div>
    </div>
  );
}