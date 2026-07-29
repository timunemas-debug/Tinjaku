import { useEffect, useState } from "react";
import { FiPlus, FiMapPin, FiX } from "react-icons/fi";
import { getAlamat, createAlamat } from "../../services/alamatService";
import { useAuth } from "../../hooks/useAuth";

const LABEL_OPTIONS = ["RUMAH", "KANTOR", "APARTEMENT", "HOTEL", "GUDANG", "PABRIK"];
const KOTA_OPTIONS = ["JAKARTA", "BOGOR", "DEPOK", "TANGERANG", "BEKASI"];

const EMPTY_FORM = {
  label: "RUMAH",
  jalan: "",
  kelurahan: "",
  kecamatan: "",
  kota: "JAKARTA",
  provinsi: "",
};

export default function Alamat() {
  const { user } = useAuth();

  const [alamatList, setAlamatList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState(EMPTY_FORM);
  const [submitting, setSubmitting] = useState(false);
  const [formError, setFormError] = useState("");

  useEffect(() => {
    getAlamat()
      .then(setAlamatList)
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  }, []);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setFormError("");
    setSubmitting(true);
    try {
      const newAlamat = await createAlamat(user.userId, form);
      setAlamatList((prev) => [...prev, newAlamat]);
      setShowForm(false);
      setForm(EMPTY_FORM);
    } catch (err) {
      setFormError(err.message);
    } finally {
      setSubmitting(false);
    }
  };

  const inputStyle =
    "w-full border border-gray-200 rounded-xl px-4 py-2.5 font-body text-sm text-ink outline-none focus:border-ink";

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <div>
          <h2 className="font-display font-bold text-xl text-ink">Alamat Saya</h2>
          <p className="font-body text-sm text-ink/50 mt-1">
            Kelola daftar alamat pengiriman kamu.
          </p>
        </div>

        <button
          onClick={() => setShowForm((s) => !s)}
          className="flex items-center gap-2 font-body font-bold text-sm text-ink bg-accent px-5 py-2.5 rounded-full hover:brightness-95"
        >
          {showForm ? <FiX size={16} /> : <FiPlus size={16} />}
          {showForm ? "Batal" : "Tambah Alamat"}
        </button>
      </div>

      {showForm && (
        <form
          onSubmit={handleSubmit}
          className="bg-white rounded-2xl border border-gray-100 shadow-sm p-6 mb-6"
        >
          <h3 className="font-display font-bold text-base text-ink mb-4">
            Alamat Baru
          </h3>

          {formError && (
            <p className="text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2 mb-4">
              {formError}
            </p>
          )}

          <div className="grid md:grid-cols-2 gap-4">
            <div>
              <label className="font-body font-semibold text-xs text-ink/60 mb-1.5 block">
                Label
              </label>
              <select name="label" value={form.label} onChange={handleChange} className={inputStyle}>
                {LABEL_OPTIONS.map((opt) => (
                  <option key={opt} value={opt}>{opt}</option>
                ))}
              </select>
            </div>

            <div>
              <label className="font-body font-semibold text-xs text-ink/60 mb-1.5 block">
                Kota
              </label>
              <select name="kota" value={form.kota} onChange={handleChange} className={inputStyle}>
                {KOTA_OPTIONS.map((opt) => (
                  <option key={opt} value={opt}>{opt}</option>
                ))}
              </select>
            </div>

            <div className="md:col-span-2">
              <label className="font-body font-semibold text-xs text-ink/60 mb-1.5 block">
                Nama Jalan
              </label>
              <input
                name="jalan"
                value={form.jalan}
                onChange={handleChange}
                required
                className={inputStyle}
              />
            </div>

            <div>
              <label className="font-body font-semibold text-xs text-ink/60 mb-1.5 block">
                Kelurahan
              </label>
              <input
                name="kelurahan"
                value={form.kelurahan}
                onChange={handleChange}
                required
                className={inputStyle}
              />
            </div>

            <div>
              <label className="font-body font-semibold text-xs text-ink/60 mb-1.5 block">
                Kecamatan
              </label>
              <input
                name="kecamatan"
                value={form.kecamatan}
                onChange={handleChange}
                required
                className={inputStyle}
              />
            </div>

            <div className="md:col-span-2">
              <label className="font-body font-semibold text-xs text-ink/60 mb-1.5 block">
                Provinsi
              </label>
              <input
                name="provinsi"
                value={form.provinsi}
                onChange={handleChange}
                required
                className={inputStyle}
              />
            </div>
          </div>

          <button
            type="submit"
            disabled={submitting}
            className="w-full mt-5 font-body font-bold text-sm text-ink bg-accent rounded-full py-3 hover:brightness-95 disabled:opacity-60"
          >
            {submitting ? "Menyimpan..." : "Simpan Alamat"}
          </button>
        </form>
      )}

      {loading && (
        <p className="font-body text-sm text-ink/50">Memuat alamat...</p>
      )}

      {error && (
        <p className="font-body text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2">
          {error}
        </p>
      )}

      {!loading && !error && alamatList.length === 0 && (
        <div className="bg-white rounded-2xl border-2 border-dashed border-gray-200 p-12 text-center">
          <FiMapPin size={32} className="mx-auto text-ink/20 mb-3" />
          <p className="font-body text-sm text-ink/50">
            Belum ada alamat tersimpan. Klik "Tambah Alamat" buat mulai.
          </p>
        </div>
      )}

      <div className="grid md:grid-cols-2 gap-4">
        {alamatList.map((a, i) => (
          <div
            key={i}
            className="bg-white rounded-2xl border border-gray-100 shadow-sm p-5 flex gap-4"
          >
            <div className="w-11 h-11 rounded-full bg-accent/25 flex items-center justify-center shrink-0">
              <FiMapPin size={18} className="text-ink" />
            </div>
            <div>
              <span className="inline-block font-body font-bold text-xs text-ink bg-accent/30 px-2.5 py-1 rounded-full mb-2">
                {a.label}
              </span>
              <p className="font-body text-sm text-ink font-medium">
                {a.jalan}, {a.kelurahan}, {a.kecamatan}
              </p>
              <p className="font-body text-sm text-ink/50">
                {a.kota}, {a.provinsi}
              </p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}