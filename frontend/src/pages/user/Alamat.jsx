import { useEffect, useState } from "react";
import {
  FiMapPin,
  FiPlus,
  FiEdit2,
  FiX,
} from "react-icons/fi";

import {
  getAlamat,
  createAlamat,
  updateAlamat,
} from "../../services/alamatService";

import { useAuth } from "../../hooks/useAuth";

const LABEL_OPTIONS = [
  "RUMAH",
  "KANTOR",
  "APARTMENT",
  "HOTEL",
  "KOS",
  "LAINNYA",
];

const KOTA_OPTIONS = [
  "JAKARTA",
  "BOGOR",
  "DEPOK",
  "TANGERANG",
  "BEKASI",
];

export default function Alamat() {
  const { user } = useAuth();

  const [alamatList, setAlamatList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  const [showForm, setShowForm] = useState(false);
  const [editingId, setEditingId] = useState(null);

  const [form, setForm] = useState({
    label: "RUMAH",
    jalan: "",
    kelurahan: "",
    kecamatan: "",
    kota: "TANGERANG",
    provinsi: "Banten",
  });

  useEffect(() => {
    loadAlamat();
  }, []);

  const loadAlamat = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await getAlamat();
      setAlamatList(data || []);
    } catch (err) {
      setError(
        err?.response?.data?.message ||
          err?.message ||
          "Gagal mengambil alamat."
      );
    } finally {
      setLoading(false);
    }
  };

  const resetForm = () => {
    setForm({
      label: "RUMAH",
      jalan: "",
      kelurahan: "",
      kecamatan: "",
      kota: "TANGERANG",
      provinsi: "Banten",
    });

    setEditingId(null);
    setShowForm(false);
    setError("");
  };

  const handleChange = (e) => {
    setForm((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!user?.userId) {
      setError("Silakan login kembali.");
      return;
    }

    try {
      setSaving(true);
      setError("");

      if (editingId) {
        await updateAlamat(editingId, form);
      } else {
        await createAlamat(form);
      }

      await loadAlamat();
      resetForm();
    } catch (err) {
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
    setForm({
      label: alamat.label || "RUMAH",
      jalan: alamat.jalan || "",
      kelurahan: alamat.kelurahan || "",
      kecamatan: alamat.kecamatan || "",
      kota: alamat.kota || "TANGERANG",
      provinsi: alamat.provinsi || "Banten",
    });

    setEditingId(alamat.idALamat);
    setShowForm(true);
    setError("");

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
            Kelola alamat yang kamu gunakan untuk layanan Tinjaku.
          </p>
        </div>

        {!showForm && (
          <button
            onClick={() => setShowForm(true)}
            className="flex items-center gap-2 bg-[#FFC800] px-5 py-3 rounded-xl text-sm font-bold hover:brightness-95 transition"
          >
            <FiPlus size={17} />
            Tambah Alamat
          </button>
        )}
      </div>

      {error && (
        <div className="mb-6 flex items-center justify-between bg-red-50 border border-red-100 text-red-500 rounded-xl px-4 py-3 text-sm">
          <span>{error}</span>

          <button onClick={() => setError("")}>
            <FiX size={17} />
          </button>
        </div>
      )}

      {showForm && (
        <div className="bg-white border border-black/[0.07] rounded-2xl mb-6 overflow-hidden">
          <div className="flex items-center justify-between px-6 py-5 border-b border-black/[0.06]">
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 rounded-xl bg-[#FFF4CC] flex items-center justify-center">
                <FiMapPin size={18} />
              </div>

              <div>
                <h2 className="font-display font-bold text-lg">
                  {editingId ? "Edit Alamat" : "Tambah Alamat"}
                </h2>

                <p className="text-xs text-black/40">
                  Lengkapi alamat kamu
                </p>
              </div>
            </div>

            <button
              onClick={resetForm}
              className="w-9 h-9 rounded-lg flex items-center justify-center text-black/40 hover:bg-black/5"
            >
              <FiX size={18} />
            </button>
          </div>

          <form onSubmit={handleSubmit} className="p-6">
            <div className="grid md:grid-cols-2 gap-5">
              <div>
                <label className="block text-sm font-bold mb-2">
                  Label
                </label>

                <select
                  name="label"
                  value={form.label}
                  onChange={handleChange}
                  className="w-full border border-black/[0.12] rounded-xl px-4 py-3.5 text-sm bg-white outline-none focus:border-[#FFC800] focus:ring-2 focus:ring-[#FFC800]/20"
                  required
                >
                  {LABEL_OPTIONS.map((item) => (
                    <option key={item} value={item}>
                      {item}
                    </option>
                  ))}
                </select>
              </div>

              <div>
                <label className="block text-sm font-bold mb-2">
                  Kota
                </label>

                <select
                  name="kota"
                  value={form.kota}
                  onChange={handleChange}
                  className="w-full border border-black/[0.12] rounded-xl px-4 py-3.5 text-sm bg-white outline-none focus:border-[#FFC800] focus:ring-2 focus:ring-[#FFC800]/20"
                  required
                >
                  {KOTA_OPTIONS.map((item) => (
                    <option key={item} value={item}>
                      {item}
                    </option>
                  ))}
                </select>
              </div>

              <div className="md:col-span-2">
                <label className="block text-sm font-bold mb-2">
                  Jalan
                </label>

                <input
                  type="text"
                  name="jalan"
                  value={form.jalan}
                  onChange={handleChange}
                  placeholder="Contoh: Jl. Sudirman No. 10"
                  className="w-full border border-black/[0.12] rounded-xl px-4 py-3.5 text-sm outline-none focus:border-[#FFC800] focus:ring-2 focus:ring-[#FFC800]/20"
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-bold mb-2">
                  Kelurahan
                </label>

                <input
                  type="text"
                  name="kelurahan"
                  value={form.kelurahan}
                  onChange={handleChange}
                  placeholder="Kelurahan"
                  className="w-full border border-black/[0.12] rounded-xl px-4 py-3.5 text-sm outline-none focus:border-[#FFC800] focus:ring-2 focus:ring-[#FFC800]/20"
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-bold mb-2">
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
                <label className="block text-sm font-bold mb-2">
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
            </div>

            <div className="flex justify-end gap-3 mt-6">
              <button
                type="button"
                onClick={resetForm}
                className="px-5 py-3 rounded-xl text-sm font-semibold text-black/50 hover:bg-black/5"
              >
                Batal
              </button>

              <button
                type="submit"
                disabled={saving}
                className="px-6 py-3 rounded-xl bg-[#FFC800] text-[#111116] text-sm font-bold disabled:opacity-50"
              >
                {saving
                  ? "Menyimpan..."
                  : editingId
                  ? "Simpan Perubahan"
                  : "Simpan Alamat"}
              </button>
            </div>
          </form>
        </div>
      )}

      <div className="bg-white border border-black/[0.07] rounded-2xl overflow-hidden">
        <div className="px-6 py-5 border-b border-black/[0.06]">
          <h2 className="font-display font-bold text-lg">
            Alamat Tersimpan
          </h2>

          <p className="text-xs text-black/40 mt-1">
            {alamatList.length} alamat
          </p>
        </div>

        {loading && (
          <div className="p-10 text-center text-sm text-black/40">
            Memuat alamat...
          </div>
        )}

        {!loading && alamatList.length === 0 && (
          <div className="p-10 text-center">
            <div className="w-14 h-14 rounded-2xl bg-[#FFF4CC] flex items-center justify-center mx-auto mb-4">
              <FiMapPin size={23} />
            </div>

            <h3 className="font-display font-bold text-lg">
              Belum ada alamat
            </h3>

            <p className="text-sm text-black/40 mt-2">
              Tambahkan alamat untuk mempermudah pemesanan.
            </p>

            {!showForm && (
              <button
                onClick={() => setShowForm(true)}
                className="mt-5 inline-flex items-center gap-2 bg-[#FFC800] px-5 py-3 rounded-xl text-sm font-bold"
              >
                <FiPlus size={16} />
                Tambah Alamat
              </button>
            )}
          </div>
        )}

        {!loading && alamatList.length > 0 && (
          <div className="divide-y divide-black/[0.06]">
            {alamatList.map((alamat, index) => (
              <div
                key={alamat.idALamat ?? index}
                className="p-6 hover:bg-[#FAFAFA] transition"
              >
                <div className="flex items-start justify-between gap-5">
                  <div className="flex gap-4 min-w-0">
                    <div className="w-11 h-11 rounded-xl bg-[#FFF4CC] flex items-center justify-center shrink-0">
                      <FiMapPin size={18} />
                    </div>

                    <div className="min-w-0">
                      <div className="flex items-center gap-2 mb-2">
                        <h3 className="font-display font-bold">
                          {alamat.label || "Alamat"}
                        </h3>

                        {index === 0 && (
                          <span className="px-2 py-1 rounded-full bg-black/[0.05] text-[10px] font-bold text-black/40">
                            Utama
                          </span>
                        )}
                      </div>

                      <p className="text-sm text-black/60">
                        {alamat.jalan}
                      </p>

                      <p className="text-xs text-black/40 mt-1">
                        {alamat.kelurahan &&
                          `${alamat.kelurahan}, `}
                        {alamat.kecamatan &&
                          `${alamat.kecamatan}, `}
                        {alamat.kota &&
                          `${alamat.kota}, `}
                        {alamat.provinsi}
                      </p>
                    </div>
                  </div>

                  <button
                    onClick={() => handleEdit(alamat)}
                    className="w-9 h-9 rounded-lg flex items-center justify-center text-black/35 hover:text-black hover:bg-black/5"
                  >
                    <FiEdit2 size={16} />
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}