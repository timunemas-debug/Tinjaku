import { useEffect, useState } from "react";
import {
  FiUser,
  FiMail,
  FiShield,
  FiEdit2,
  FiLock,
  FiLogOut,
  FiCheckCircle,
  FiX,
  FiSave,
} from "react-icons/fi";

import { useAuth } from "../../hooks/useAuth";
import { updateUserProfile } from "../../services/userService";

export default function Profile() {
  const { user, logout } = useAuth();

  const [editing, setEditing] = useState(false);
  const [loading, setLoading] = useState(false);

  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");

  const [form, setForm] = useState({
    namaDepan: "",
    namaBelakang: "",
    email: "",
  });

  
  useEffect(() => {
    if (!user) return;

    setForm({
      namaDepan: user.namaDepan || "",
      namaBelakang: user.namaBelakang || "",
      email: user.email || "",
    });
  }, [user]);

  const handleChange = (e) => {
    const { name, value } = e.target;

    setForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleEdit = () => {
    setSuccess("");
    setError("");
    setEditing(true);
  };

  const handleCancel = () => {
    setForm({
      namaDepan: user?.namaDepan || "",
      namaBelakang: user?.namaBelakang || "",
      email: user?.email || "",
    });

    setSuccess("");
    setError("");
    setEditing(false);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    setLoading(true);
    setSuccess("");
    setError("");

    try {
      const response = await updateUserProfile({
        namaDepan: form.namaDepan.trim(),
        namaBelakang: form.namaBelakang.trim(),
        email: form.email.trim(),
      });

      
      if (response) {
        setForm({
          namaDepan: response.namaDepan ?? form.namaDepan,
          namaBelakang: response.namaBelakang ?? form.namaBelakang,
          email: response.email ?? form.email,
        });
      }

      setSuccess("Profil berhasil diperbarui.");
      setEditing(false);
    } catch (err) {
      setError(err.message || "Gagal memperbarui profil.");
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = async () => {
    const yakin = window.confirm("Yakin ingin keluar dari akun?");

    if (!yakin) return;

    await logout();
  };

  const namaLengkap =
    `${form.namaDepan} ${form.namaBelakang}`.trim() || "Pengguna";

  const role =
    user?.role?.replace("ROLE_", "") ||
    "USER";

  const inisial =
    `${form.namaDepan?.charAt(0) || ""}${form.namaBelakang?.charAt(0) || ""}`
      .toUpperCase() || "U";

  return (
    <div className="max-w-4xl mx-auto">
      <div className="mb-8">
        <p className="font-body text-sm text-ink/50 mb-1">
          Pengaturan akun
        </p>

        <h1 className="font-display font-extrabold text-3xl text-ink">
          Profile
        </h1>

        <p className="font-body text-sm text-ink/60 mt-2">
          Kelola informasi akun Tinjaku kamu.
        </p>
      </div>

      
      {success && (
        <div className="mb-5 flex items-center gap-3 bg-green-50 border border-green-200 text-green-700 rounded-xl px-4 py-3">
          <FiCheckCircle size={18} />

          <p className="font-body text-sm font-medium">
            {success}
          </p>
        </div>
      )}

      {error && (
        <div className="mb-5 flex items-center gap-3 bg-red-50 border border-red-200 text-red-600 rounded-xl px-4 py-3">
          <FiX size={18} />

          <p className="font-body text-sm font-medium">
            {error}
          </p>
        </div>
      )}

      
      <div className="bg-ink rounded-3xl p-8 mb-6 relative overflow-hidden">
        <div className="relative z-10 flex items-center gap-5">
          
          <div className="w-20 h-20 rounded-2xl bg-accent flex items-center justify-center shrink-0">
            <span className="font-display font-extrabold text-2xl text-ink">
              {inisial}
            </span>
          </div>

          
          <div>
            <p className="font-body text-white/50 text-sm mb-1">
              Akun Tinjaku
            </p>

            <h2 className="font-display font-extrabold text-2xl text-white capitalize">
              {namaLengkap}
            </h2>

            <p className="font-body text-white/60 text-sm mt-1">
              {form.email || "Email belum tersedia"}
            </p>
          </div>
        </div>

        
        <div className="absolute -right-10 -top-10 w-40 h-40 rounded-full bg-accent/10" />
        <div className="absolute -right-4 -bottom-16 w-48 h-48 rounded-full bg-accent/5" />
      </div>

      
      <form onSubmit={handleSubmit}>
        <div className="bg-white rounded-2xl border border-gray-100 shadow-sm overflow-hidden">
          
          <div className="flex items-center justify-between px-6 py-5 border-b border-gray-100">
            <div>
              <h2 className="font-display font-bold text-lg text-ink">
                Informasi Pribadi
              </h2>

              <p className="font-body text-xs text-ink/50 mt-1">
                Informasi yang digunakan pada akun kamu.
              </p>
            </div>

            {!editing && (
              <button
                type="button"
                onClick={handleEdit}
                className="inline-flex items-center gap-2 px-4 py-2 rounded-full bg-accent text-ink font-body font-bold text-sm hover:brightness-95 transition"
              >
                <FiEdit2 size={14} />
                Edit
              </button>
            )}
          </div>

          
          <div className="p-6 space-y-5">
            
            <div>
              <label className="flex items-center gap-2 font-body text-sm font-bold text-ink mb-2">
                <FiUser size={15} />
                Nama Depan
              </label>

              <input
                type="text"
                name="namaDepan"
                value={form.namaDepan}
                onChange={handleChange}
                disabled={!editing}
                required
                className={`w-full px-4 py-3 rounded-xl border-2 text-sm font-body outline-none transition ${
                  editing
                    ? "border-ink/15 focus:border-accent bg-white"
                    : "border-gray-100 bg-gray-50 text-ink/70"
                }`}
              />
            </div>

            
            <div>
              <label className="flex items-center gap-2 font-body text-sm font-bold text-ink mb-2">
                <FiUser size={15} />
                Nama Belakang
              </label>

              <input
                type="text"
                name="namaBelakang"
                value={form.namaBelakang}
                onChange={handleChange}
                disabled={!editing}
                required
                className={`w-full px-4 py-3 rounded-xl border-2 text-sm font-body outline-none transition ${
                  editing
                    ? "border-ink/15 focus:border-accent bg-white"
                    : "border-gray-100 bg-gray-50 text-ink/70"
                }`}
              />
            </div>

            
            <div>
              <label className="flex items-center gap-2 font-body text-sm font-bold text-ink mb-2">
                <FiMail size={15} />
                Email
              </label>

              <input
                type="email"
                name="email"
                value={form.email}
                onChange={handleChange}
                disabled={!editing}
                required
                className={`w-full px-4 py-3 rounded-xl border-2 text-sm font-body outline-none transition ${
                  editing
                    ? "border-ink/15 focus:border-accent bg-white"
                    : "border-gray-100 bg-gray-50 text-ink/70"
                }`}
              />
            </div>

            
            <div>
              <label className="flex items-center gap-2 font-body text-sm font-bold text-ink mb-2">
                <FiShield size={15} />
                Role
              </label>

              <div className="flex items-center gap-2 w-full px-4 py-3 rounded-xl border-2 border-gray-100 bg-gray-50">
                <span className="px-3 py-1 rounded-full bg-accent/30 text-ink text-xs font-bold">
                  {role}
                </span>

                <span className="text-xs text-ink/40">
                  Role akun tidak dapat diubah.
                </span>
              </div>
            </div>
          </div>

          
          {editing && (
            <div className="px-6 py-5 border-t border-gray-100 flex justify-end gap-3">
              <button
                type="button"
                onClick={handleCancel}
                disabled={loading}
                className="inline-flex items-center gap-2 px-5 py-3 rounded-full border border-gray-200 text-ink font-body font-bold text-sm hover:bg-gray-50 transition disabled:opacity-50"
              >
                <FiX size={15} />
                Batal
              </button>

              <button
                type="submit"
                disabled={loading}
                className="inline-flex items-center gap-2 px-5 py-3 rounded-full bg-accent text-ink font-body font-bold text-sm hover:brightness-95 transition disabled:opacity-50"
              >
                <FiSave size={15} />

                {loading ? "Menyimpan..." : "Simpan Perubahan"}
              </button>
            </div>
          )}
        </div>
      </form>

      
      <div className="grid md:grid-cols-2 gap-4 mt-6">
        
        <button
          type="button"
          className="bg-white border border-gray-100 rounded-2xl p-5 text-left shadow-sm hover:border-accent transition group"
        >
          <div className="flex items-center gap-4">
            <div className="w-11 h-11 rounded-xl bg-accent/20 flex items-center justify-center group-hover:bg-accent transition">
              <FiLock size={18} className="text-ink" />
            </div>

            <div>
              <p className="font-display font-bold text-sm text-ink">
                Ubah Password
              </p>

              <p className="font-body text-xs text-ink/50 mt-1">
                Perbarui keamanan akun kamu.
              </p>
            </div>
          </div>
        </button>

        
        <button
          type="button"
          onClick={handleLogout}
          className="bg-white border border-red-100 rounded-2xl p-5 text-left shadow-sm hover:bg-red-50 transition group"
        >
          <div className="flex items-center gap-4">
            <div className="w-11 h-11 rounded-xl bg-red-50 flex items-center justify-center group-hover:bg-red-100 transition">
              <FiLogOut size={18} className="text-red-500" />
            </div>

            <div>
              <p className="font-display font-bold text-sm text-red-600">
                Keluar
              </p>

              <p className="font-body text-xs text-ink/50 mt-1">
                Keluar dari akun Tinjaku.
              </p>
            </div>
          </div>
        </button>
      </div>
    </div>
  );
}