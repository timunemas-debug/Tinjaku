import { useEffect, useState } from "react";
import { getAlamat, createAlamat } from "../../services/alamatService";
import { useAuth } from "../../hooks/useAuth";

export default function Profile() {
  const { user } = useAuth();

  const [alamatList, setAlamatList] = useState([]);
  const [loadingAlamat, setLoadingAlamat] = useState(true);
  const [errorAlamat, setErrorAlamat] = useState("");

  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState({
    label: "RUMAH",
    jalan: "",
    kelurahan: "",
    kecamatan: "",
    kota: "JAKARTA",
    provinsi: "",
  });
  const [submitting, setSubmitting] = useState(false);
  const [formError, setFormError] = useState("");

  useEffect(() => {
    getAlamat()
      .then(setAlamatList)
      .catch((err) => setErrorAlamat(err.message))
      .finally(() => setLoadingAlamat(false));
  }, []);

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setFormError("");
    setSubmitting(true);
    try {
      const newAlamat = await createAlamat(user.userId, form);
      setAlamatList((prev) => [...prev, newAlamat]);
      setShowForm(false);
      setForm({
        label: "RUMAH",
        jalan: "",
        kelurahan: "",
        kecamatan: "",
        kota: "JAKARTA",
        provinsi: "",
      });
    } catch (err) {
      setFormError(err.message);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="min-h-[80vh] bg-[#FAFAFA] px-4 py-12">
      <div className="max-w-md mx-auto">
        <h1 className="font-[Baloo_2] font-extrabold text-2xl text-[#0A0A0A] mb-6">
          Profil Saya
        </h1>

        <div className="bg-white border-2 border-[#0A0A0A]/10 rounded-3xl p-8 text-center mb-6">
          <div className="w-20 h-20 rounded-full bg-[#FFC800] text-[#0A0A0A] font-[Baloo_2] font-extrabold text-2xl flex items-center justify-center mx-auto mb-4">
            {user?.email?.[0]?.toUpperCase() ?? "?"}
          </div>
          <p className="font-[Baloo_2] font-bold text-[#0A0A0A] mb-1">
            {user?.email}
          </p>
          <p className="text-sm text-[#6B7280]">{user?.role}</p>
        </div>

        <div className="bg-white border-2 border-[#0A0A0A]/10 rounded-3xl p-8">
          <div className="flex items-center justify-between mb-4">
            <h2 className="font-[Baloo_2] font-bold text-lg text-[#0A0A0A]">
              Alamat Saya
            </h2>
            <button
              onClick={() => setShowForm((s) => !s)}
              className="text-sm font-bold text-[#0A0A0A] bg-[#FFC800] px-4 py-2 rounded-full"
            >
              {showForm ? "Batal" : "+ Tambah"}
            </button>
          </div>

          {showForm && (
            <form onSubmit={handleSubmit} className="flex flex-col gap-3 mb-6">
              {formError && (
                <p className="text-sm text-[#D64545] bg-[#D64545]/10 rounded-lg px-3 py-2">
                  {formError}
                </p>
              )}

              <select
                name="label"
                value={form.label}
                onChange={handleChange}
                className="border-2 border-[#0A0A0A]/15 rounded-xl px-4 py-2.5 text-sm"
              >
                {["RUMAH", "KANTOR", "APARTEMENT", "HOTEL", "GUDANG", "PABRIK"].map((opt) => (
                  <option key={opt} value={opt}>{opt}</option>
                ))}
              </select>

              <select
                name="kota"
                value={form.kota}
                onChange={handleChange}
                className="border-2 border-[#0A0A0A]/15 rounded-xl px-4 py-2.5 text-sm"
              >
                {["JAKARTA", "BOGOR", "DEPOK", "TANGERANG", "BEKASI"].map((opt) => (
                  <option key={opt} value={opt}>{opt}</option>
                ))}
              </select>

              <input
                name="jalan"
                placeholder="Nama jalan"
                value={form.jalan}
                onChange={handleChange}
                required
                className="border-2 border-[#0A0A0A]/15 rounded-xl px-4 py-2.5 text-sm"
              />
              <input
                name="kelurahan"
                placeholder="Kelurahan"
                value={form.kelurahan}
                onChange={handleChange}
                required
                className="border-2 border-[#0A0A0A]/15 rounded-xl px-4 py-2.5 text-sm"
              />
              <input
                name="kecamatan"
                placeholder="Kecamatan"
                value={form.kecamatan}
                onChange={handleChange}
                required
                className="border-2 border-[#0A0A0A]/15 rounded-xl px-4 py-2.5 text-sm"
              />
              <input
                name="provinsi"
                placeholder="Provinsi"
                value={form.provinsi}
                onChange={handleChange}
                required
                className="border-2 border-[#0A0A0A]/15 rounded-xl px-4 py-2.5 text-sm"
              />

              <button
                type="submit"
                disabled={submitting}
                className="bg-[#0A0A0A] text-white font-bold text-sm rounded-xl py-3 disabled:opacity-60"
              >
                {submitting ? "Menyimpan..." : "Simpan Alamat"}
              </button>
            </form>
          )}

          {loadingAlamat && <p className="text-sm text-[#6B7280]">Memuat alamat...</p>}
          {errorAlamat && <p className="text-sm text-[#D64545]">{errorAlamat}</p>}
          {!loadingAlamat && !errorAlamat && alamatList.length === 0 && (
            <p className="text-sm text-[#6B7280]">Belum ada alamat tersimpan.</p>
          )}

          <div className="flex flex-col gap-3">
            {alamatList.map((a, i) => (
              <div key={i} className="border-2 border-[#0A0A0A]/10 rounded-xl p-4">
                <span className="inline-block text-xs font-bold text-[#0A0A0A] bg-[#FFC800]/40 px-2.5 py-1 rounded-full mb-2">
                  {a.label}
                </span>
                <p className="text-sm text-[#0A0A0A]">
                  {a.jalan}, {a.kelurahan}, {a.kecamatan}
                </p>
                <p className="text-sm text-[#6B7280]">{a.kota}, {a.provinsi}</p>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}