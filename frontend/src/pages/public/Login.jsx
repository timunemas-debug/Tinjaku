import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthCard from "../../components/auth/AuthCard";
import { useAuth } from "../../hooks/useAuth";
import { ROLE } from "../../utils/statusPesananMap";

export default function Login() {
  const { login } = useAuth();
  const navigate = useNavigate();

  const [form, setForm] = useState({ email: "", password: "" });
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setIsSubmitting(true);
    try {
      const user = await login(form);
      if (user.role === ROLE.ADMIN) navigate("/admin/dashboard");
      else if (user.role === ROLE.MITRA) navigate("/mitra/dashboard");
      else navigate("/pesanan");
    } catch (err) {
      setError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  const inputStyle =
    "w-full pl-11 pr-10 py-3 rounded-full bg-white border border-gray-200 shadow-[0_2px_0_rgba(0,0,0,0.08)] outline-none font-body text-sm text-ink placeholder:text-gray-400 focus:border-ink";

  return (
    <AuthCard title="Login">
      <form onSubmit={handleSubmit} className="flex flex-col gap-5">
        {error && (
          <p className="text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2">
            {error}
          </p>
        )}

        <div>
          <label className="font-body font-bold text-base text-ink mb-2 block">Email</label>
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
              className={inputStyle}
            />
          </div>
        </div>

        <div>
          <label className="font-body font-bold text-base text-ink mb-2 block">Password</label>
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
              className={inputStyle}
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

        <div className="text-right -mt-2">
          <Link to="/forgot-password" className="font-body text-sm text-blue-600 hover:underline">
            Forgot password?
          </Link>
        </div>

        <button
          type="submit"
          disabled={isSubmitting}
          className="font-body font-bold text-white bg-accent rounded-full py-3.5 hover:brightness-95 disabled:opacity-60"
        >
          {isSubmitting ? "Memproses..." : "Login"}
        </button>

        <p className="text-center font-body text-sm text-ink">
          Not Registered yet?{" "}
          <Link to="/register" className="text-blue-600 hover:underline">
            Sign Up
          </Link>
        </p>
      </form>
    </AuthCard>
  );
}