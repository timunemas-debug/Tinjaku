import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthCard from "../../components/auth/AuthCard";
import { useAuth } from "../../hooks/useAuth";

export default function Register() {
  const { register } = useAuth();
  const navigate = useNavigate();

  const [form, setForm] = useState({
    namaDepan: "",
    namaBelakang: "",
    email: "",
    password: "",
  });
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setIsSubmitting(true);

    try {
      await register(form);
      navigate("/login");
    } catch (err) {
      setError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <AuthCard title="Register">
      <form onSubmit={handleSubmit} className="flex flex-col gap-5">
        {error && (
          <p className="text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2">
            {error}
          </p>
        )}

        <div className="grid grid-cols-2 gap-3">
          <div>
            <label className="font-body font-semibold text-sm text-ink mb-1.5 block">
              Nama Depan
            </label>
            <input
              type="text"
              name="namaDepan"
              placeholder="Nama depan"
              value={form.namaDepan}
              onChange={handleChange}
              required
              className="w-full border border-gray-300 rounded-full px-4 py-2.5 outline-none font-body text-sm text-ink placeholder:text-gray-400 focus:border-ink"
            />
          </div>

          <div>
            <label className="font-body font-semibold text-sm text-ink mb-1.5 block">
              Nama Belakang
            </label>
            <input
              type="text"
              name="namaBelakang"
              placeholder="Nama belakang"
              value={form.namaBelakang}
              onChange={handleChange}
              className="w-full border border-gray-300 rounded-full px-4 py-2.5 outline-none font-body text-sm text-ink placeholder:text-gray-400 focus:border-ink"
            />
          </div>
        </div>

        <div>
          <label className="font-body font-semibold text-sm text-ink mb-1.5 block">
            Email
          </label>
          <div className="flex items-center gap-2 border border-gray-300 rounded-full px-4 py-2.5 focus-within:border-ink">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" className="text-gray-400 shrink-0">
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
              className="w-full outline-none font-body text-sm text-ink placeholder:text-gray-400"
            />
          </div>
        </div>

        <div>
          <label className="font-body font-semibold text-sm text-ink mb-1.5 block">
            Password
          </label>
          <div className="flex items-center gap-2 border border-gray-300 rounded-full px-4 py-2.5 focus-within:border-ink">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" className="text-gray-400 shrink-0">
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
              className="w-full outline-none font-body text-sm text-ink placeholder:text-gray-400"
            />
            <button type="button" onClick={() => setShowPassword((s) => !s)}>
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" className="text-gray-400 shrink-0">
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
          {isSubmitting ? "Memproses..." : "Register"}
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