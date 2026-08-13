import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthCard from "../../components/auth/AuthCard";
import { useAuth } from "../../hooks/useAuth";

export default function Register() {
  const { register } = useAuth();
  const navigate = useNavigate();

  const [asMitra, setAsMitra] = useState(false);
  const [form, setForm] = useState({
    namaDepan: "",
    namaBelakang: "",
    namaMitra: "",
    email: "",
    password: "",
  });
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setIsSubmitting(true);
    try {
      const payload = asMitra
        ? { namaMitra: form.namaMitra, email: form.email, password: form.password }
        : { namaDepan: form.namaDepan, namaBelakang: form.namaBelakang, email: form.email, password: form.password };

      await register(payload, asMitra);
      navigate("/login");
    } catch (err) {
      setError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  const inputStyle =
    "w-full px-4 py-3 rounded-full bg-white border border-gray-200 shadow-[0_2px_0_rgba(0,0,0,0.08)] outline-none font-body text-sm text-ink placeholder:text-gray-400 focus:border-ink";

  return (
    <AuthCard plain>
      
      <div className="flex bg-gray-100 rounded-full p-1 mb-6">
        <button
          type="button"
          onClick={() => setAsMitra(false)}
          className={`flex-1 py-2 rounded-full font-body font-bold text-sm transition-colors ${
            !asMitra ? "bg-ink text-white" : "text-ink/50"
          }`}
        >
          Daftar sebagai User
        </button>
        <button
          type="button"
          onClick={() => setAsMitra(true)}
          className={`flex-1 py-2 rounded-full font-body font-bold text-sm transition-colors ${
            asMitra ? "bg-ink text-white" : "text-ink/50"
          }`}
        >
          Daftar sebagai Mitra
        </button>
      </div>

      <form onSubmit={handleSubmit} className="flex flex-col gap-4">
        {error && (
          <p className="text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2">
            {error}
          </p>
        )}

        {asMitra ? (
          <div>
            <label className="font-body font-bold text-sm text-ink mb-1.5 block">Nama Mitra / Usaha</label>
            <input
              name="namaMitra"
              value={form.namaMitra}
              onChange={handleChange}
              required
              className={inputStyle}
            />
          </div>
        ) : (
          <>
            <div>
              <label className="font-body font-bold text-sm text-ink mb-1.5 block">Nama Depan</label>
              <input
                name="namaDepan"
                value={form.namaDepan}
                onChange={handleChange}
                required
                className={inputStyle}
              />
            </div>

            <div>
              <label className="font-body font-bold text-sm text-ink mb-1.5 block">Nama Belakang</label>
              <input
                name="namaBelakang"
                value={form.namaBelakang}
                onChange={handleChange}
                className={inputStyle}
              />
            </div>
          </>
        )}

        <div>
          <label className="font-body font-bold text-sm text-ink mb-1.5 block">Email</label>
          <div className="relative">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-400">
              <rect x="2" y="4" width="20" height="16" rx="2" />
              <path d="m22 7-10 5L2 7" />
            </svg>
            <input
              type="email"
              name="email"
              placeholder="Enter your email"
              value={form.email}
              onChange={handleChange}
              required
              className={`${inputStyle} pl-11`}
            />
          </div>
        </div>

        <div>
          <label className="font-body font-bold text-sm text-ink mb-1.5 block">Password</label>
          <div className="relative">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-400">
              <rect x="3" y="11" width="18" height="10" rx="2" />
              <path d="M7 11V7a5 5 0 0 1 10 0v4" />
            </svg>
            <input
              type={showPassword ? "text" : "password"}
              name="password"
              placeholder="Enter your password"
              value={form.password}
              onChange={handleChange}
              required
              className={`${inputStyle} pl-11 pr-10`}
            />
            <button
              type="button"
              onClick={() => setShowPassword((s) => !s)}
              className="absolute right-4 top-1/2 -translate-y-1/2 text-gray-400"
            >
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8Z" />
                <circle cx="12" cy="12" r="3" />
              </svg>
            </button>
          </div>
        </div>

        <button
          type="submit"
          disabled={isSubmitting}
          className="font-body font-bold text-white bg-accent rounded-full py-3.5 hover:brightness-95 disabled:opacity-60 mt-2"
        >
          {isSubmitting ? "Memproses..." : `Daftar sebagai ${asMitra ? "Mitra" : "User"}`}
        </button>

        <p className="text-center font-body text-sm text-ink">
          Sudah punya akun?{" "}
          <Link to="/login" className="text-blue-600 hover:underline">
            Login
          </Link>
        </p>
      </form>
    </AuthCard>
  );
}