import { useEffect, useState } from "react";
import { FiMapPin } from "react-icons/fi";
import { setMitraOnline, updateMitraProfile } from "../../services/mitraService";
import { getAlamatMitra } from "../../services/alamatMitraService";
import { useAuth } from "../../hooks/useAuth";

function ProfileMitra() {
  const { user } = useAuth();

  const [alamatList, setAlamatList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [statusOnOff, setStatusOnOff] = useState("OFFLINE");
  const [toggling, setToggling] = useState(false);

  const [form, setForm] = useState({ namaMitra: "", email: user?.email ?? "" });
  const [saving, setSaving] = useState(false);
  const [saveMessage, setSaveMessage] = useState("");

  useEffect(() => {
    getAlamatMitra()
      .then(setAlamatList)
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  }, []);

  const toggleOnline = async () => {
    setToggling(true);
    try {
      const nextStatus = statusOnOff === "ONLINE" ? "OFFLINE" : "ONLINE";
      await setMitraOnline(user.userId, nextStatus);
      setStatusOnOff(nextStatus);
    } catch (err) {
      setError(err.message);
    } finally {
      setToggling(false);
    }
  };

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSaveProfile = async (e) => {
    e.preventDefault();
    setSaving(true);
    setSaveMessage("");
    try {
      await updateMitraProfile(form);
      setSaveMessage("Profil berhasil diperbarui.");
    } catch (err) {
      setError(err.message);
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="flex flex-col gap-6">
      <h1 className="font-display font-bold text-2xl text-ink">Profile Mitra</h1>

      {error && (
        <p className="font-body text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2">
          {error}
        </p>
      )}

      <div className="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
        <div className="flex items-center justify-between mb-6">
          <div>
            <h2 className="font-display font-bold text-lg text-ink">Status Ketersediaan</h2>
            <p className="font-body text-sm text-ink/50 mt-1">
              Aktifkan status Online biar bisa menerima pesanan.
            </p>
          </div>

          <button
            onClick={toggleOnline}
            disabled={toggling}
            className={`font-body font-bold text-sm px-5 py-2.5 rounded-full transition-colors ${
              statusOnOff === "ONLINE" ? "bg-green-600 text-white" : "bg-gray-200 text-ink/60"
            }`}
          >
            {toggling ? "..." : statusOnOff}
          </button>
        </div>

        <form onSubmit={handleSaveProfile} className="grid md:grid-cols-2 gap-4">
          <div>
            <label className="font-body font-semibold text-sm text-ink mb-1.5 block">Nama Mitra</label>
            <input
              name="namaMitra"
              value={form.namaMitra}
              onChange={handleChange}
              required
              className="w-full border border-gray-200 rounded-xl px-4 py-2.5 font-body text-sm text-ink outline-none focus:border-ink"
            />
          </div>

          <div>
            <label className="font-body font-semibold text-sm text-ink mb-1.5 block">Email</label>
            <input
              type="email"
              name="email"
              value={form.email}
              onChange={handleChange}
              required
              className="w-full border border-gray-200 rounded-xl px-4 py-2.5 font-body text-sm text-ink outline-none focus:border-ink"
            />
          </div>

          <div className="md:col-span-2">
            {saveMessage && (
              <p className="font-body text-sm text-green-700 bg-green-50 border border-green-200 rounded-lg px-3 py-2 mb-3">
                {saveMessage}
              </p>
            )}
            <button
              type="submit"
              disabled={saving}
              className="font-body font-bold text-sm text-white bg-ink px-6 py-3 rounded-full hover:brightness-110 disabled:opacity-60"
            >
              {saving ? "Menyimpan..." : "Simpan Perubahan"}
            </button>
          </div>
        </form>
      </div>

      <div className="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
        <h2 className="font-display font-bold text-lg text-ink mb-4">Alamat Terdaftar</h2>

        {loading && <p className="font-body text-sm text-ink/50">Memuat alamat...</p>}

        {!loading && alamatList.length === 0 && (
          <p className="font-body text-sm text-ink/40">Belum ada alamat.</p>
        )}

        <div className="flex flex-col gap-3">
          {alamatList.map((a, i) => (
            <div key={a.idAlamat ?? i} className="flex gap-3 border border-gray-100 rounded-xl p-4">
              <div className="w-9 h-9 rounded-full bg-accent/25 flex items-center justify-center shrink-0">
                <FiMapPin size={16} className="text-ink" />
              </div>
              <div>
                <p className="font-body font-semibold text-sm text-ink">{a.labelMitra}</p>
                <p className="font-body text-sm text-ink/60">
                  {a.jalan}, {a.kecamatan}, {a.kota}
                </p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default ProfileMitra;