import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthCard from "../../components/auth/AuthCard";
import { requestPasswordReset, verifyOtp, resetPassword } from "../../services/passwordService";

const STEP = {
  EMAIL: 1,
  OTP: 2,
  NEW_PASSWORD: 3,
};

export default function ForgotPassword() {
  const navigate = useNavigate();

  const [step, setStep] = useState(STEP.EMAIL);
  const [email, setEmail] = useState("");
  const [otp, setOtp] = useState("");
  const [resetToken, setResetToken] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const [error, setError] = useState("");
  const [info, setInfo] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  
  const handleSubmitEmail = async (e) => {
    e.preventDefault();
    setError("");
    setIsSubmitting(true);
    try {
      const res = await requestPasswordReset(email);
      setInfo(res.message || "Kode OTP sudah dikirim ke email kamu.");
      setStep(STEP.OTP);
    } catch (err) {
      setError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  
  const handleSubmitOtp = async (e) => {
    e.preventDefault();
    setError("");
    setIsSubmitting(true);
    try {
      const res = await verifyOtp(email, otp);
      setResetToken(res.resetToken);
      setInfo(res.message || "OTP terverifikasi.");
      setStep(STEP.NEW_PASSWORD);
    } catch (err) {
      setError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  
  const handleSubmitNewPassword = async (e) => {
    e.preventDefault();
    setError("");

    if (newPassword !== confirmPassword) {
      setError("Konfirmasi password tidak cocok.");
      return;
    }

    setIsSubmitting(true);
    try {
      await resetPassword(resetToken, newPassword);
      navigate("/login");
    } catch (err) {
      setError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  const inputStyle =
    "w-full px-4 py-3 rounded-full bg-white border border-gray-200 shadow-[0_2px_0_rgba(0,0,0,0.08)] outline-none font-body text-sm text-ink placeholder:text-gray-400 focus:border-ink";

  const titleByStep = {
    [STEP.EMAIL]: "Lupa Password",
    [STEP.OTP]: "Verifikasi OTP",
    [STEP.NEW_PASSWORD]: "Password Baru",
  };

  return (
    <AuthCard title={titleByStep[step]}>
      {error && (
        <p className="text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2 mb-4">
          {error}
        </p>
      )}

      {info && !error && (
        <p className="text-sm text-green-700 bg-green-50 border border-green-200 rounded-lg px-3 py-2 mb-4">
          {info}
        </p>
      )}

      
      {step === STEP.EMAIL && (
        <form onSubmit={handleSubmitEmail} className="flex flex-col gap-5">
          <p className="font-body text-sm text-ink/60 -mt-2 mb-1">
            Masukkan email akun kamu, kami kirim kode OTP buat reset password.
          </p>

          <div>
            <label className="font-body font-bold text-sm text-ink mb-2 block">Email</label>
            <input
              type="email"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className={inputStyle}
            />
          </div>

          <button
            type="submit"
            disabled={isSubmitting}
            className="font-body font-bold text-white bg-accent rounded-full py-3.5 hover:brightness-95 disabled:opacity-60"
          >
            {isSubmitting ? "Mengirim..." : "Kirim Kode OTP"}
          </button>
        </form>
      )}

      
      {step === STEP.OTP && (
        <form onSubmit={handleSubmitOtp} className="flex flex-col gap-5">
          <p className="font-body text-sm text-ink/60 -mt-2 mb-1">
            Masukkan kode OTP yang dikirim ke <span className="font-semibold">{email}</span>.
          </p>

          <div>
            <label className="font-body font-bold text-sm text-ink mb-2 block">Kode OTP</label>
            <input
              type="text"
              placeholder="Masukkan 6 digit kode"
              value={otp}
              onChange={(e) => setOtp(e.target.value)}
              required
              className={`${inputStyle} text-center tracking-[0.3em] font-bold`}
            />
          </div>

          <button
            type="submit"
            disabled={isSubmitting}
            className="font-body font-bold text-white bg-accent rounded-full py-3.5 hover:brightness-95 disabled:opacity-60"
          >
            {isSubmitting ? "Memverifikasi..." : "Verifikasi OTP"}
          </button>

          <button
            type="button"
            onClick={() => setStep(STEP.EMAIL)}
            className="font-body text-sm text-ink/50 hover:text-ink"
          >
            ← Ganti email
          </button>
        </form>
      )}

      
      {step === STEP.NEW_PASSWORD && (
        <form onSubmit={handleSubmitNewPassword} className="flex flex-col gap-5">
          <div>
            <label className="font-body font-bold text-sm text-ink mb-2 block">Password Baru</label>
            <input
              type="password"
              placeholder="Minimal 8 karakter"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              required
              className={inputStyle}
            />
          </div>

          <div>
            <label className="font-body font-bold text-sm text-ink mb-2 block">Konfirmasi Password</label>
            <input
              type="password"
              placeholder="Ulangi password baru"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
              className={inputStyle}
            />
          </div>

          <button
            type="submit"
            disabled={isSubmitting}
            className="font-body font-bold text-white bg-accent rounded-full py-3.5 hover:brightness-95 disabled:opacity-60"
          >
            {isSubmitting ? "Menyimpan..." : "Simpan Password Baru"}
          </button>
        </form>
      )}

      <p className="text-center font-body text-sm text-ink mt-6">
        Ingat password kamu?{" "}
        <Link to="/login" className="text-blue-600 hover:underline">
          Login
        </Link>
      </p>
    </AuthCard>
  );
}