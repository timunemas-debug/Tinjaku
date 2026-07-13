import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createPesanan } from "../services/pesananService";
import Button from "../components/Button";

// ⚠️ SEMENTARA: hardcode userId, ganti setelah auth/JWT sudah jalan
const TEMP_USER_ID = 1;

export default function Pesanan() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ nama: "", noHp: "", alamat: "" });
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    try {
      await createPesanan(TEMP_USER_ID, form);
      navigate("/riwayat");
    } catch (err) {
      setError(err.response?.data?.message || err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-[80vh] flex items-center justify-center bg-[#FAFAFA] px-4 py-12">
      <form
        onSubmit={handleSubmit}
        className="bg-white border-2 border-[#0A0A0A]/10 rounded-3xl shadow-sm w-full max-w-md p-8"
      >
        <h1 className="font-[Baloo_2] font-extrabold text-2xl text-[#0A0A0A] text-center mb-2">
          Form Pemesanan
        </h1>
        <p className="text-sm text-[#6B7280] text-center mb-8">
          Isi data berikut untuk melakukan pemesanan layanan.
        </p>

        {error && (
          <p className="text-sm text-[#D64545] bg-[#D64545]/10 rounded-lg px-3 py-2 mb-4">
            {error}
          </p>
        )}

        <div className="mb-4">
          <label className="block text-sm font-bold text-[#0A0A0A] mb-1.5">
            Nama
          </label>
          <input
            name="nama"
            value={form.nama}
            onChange={handleChange}
            className="w-full border-2 border-[#0A0A0A]/15 rounded-xl px-4 py-3 text-sm focus:outline-none focus:border-[#FFC800]"
            required
          />
        </div>

        <div className="mb-4">
          <label className="block text-sm font-bold text-[#0A0A0A] mb-1.5">
            No HP
          </label>
          <input
            name="noHp"
            type="tel"
            value={form.noHp}
            onChange={handleChange}
            className="w-full border-2 border-[#0A0A0A]/15 rounded-xl px-4 py-3 text-sm focus:outline-none focus:border-[#FFC800]"
            required
          />
        </div>

        <div className="mb-6">
          <label className="block text-sm font-bold text-[#0A0A0A] mb-1.5">
            Alamat
          </label>
          <textarea
            name="alamat"
            value={form.alamat}
            onChange={handleChange}
            rows={3}
            className="w-full border-2 border-[#0A0A0A]/15 rounded-xl px-4 py-3 text-sm focus:outline-none focus:border-[#FFC800] resize-none"
            required
          />
        </div>

        <Button
          type="submit"
          variant="primary"
          size="lg"
          className="w-full"
          disabled={loading}
        >
          {loading ? "Memproses..." : "Kirim Pesanan"}
        </Button>
      </form>
    </div>
  );
}