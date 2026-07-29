import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { createPesanan } from "../../services/pesananService";
import { getAlamat } from "../../services/alamatService";
import Button from "../../components/common/Button";
import { useAuth } from "../../hooks/useAuth";

export default function Pesanan() {
  const { user } = useAuth();
  const navigate = useNavigate();

  const [alamatList, setAlamatList] = useState([]);
  const [loadingAlamat, setLoadingAlamat] = useState(true);

  const [form, setForm] = useState({ namaPenerima: "", alamatId: "", keluhan: "" });
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    getAlamat()
      .then(setAlamatList)
      .catch((err) => setError(err.message))
      .finally(() => setLoadingAlamat(false));
  }, []);

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    try {
      await createPesanan(user.userId, {
        ...form,
        alamatId: Number(form.alamatId),
      });
      navigate("/riwayat");
    } catch (err) {
      setError(err.message);
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
            Nama Penerima
          </label>
          <input
            name="namaPenerima"
            value={form.namaPenerima}
            onChange={handleChange}
            className="w-full border-2 border-[#0A0A0A]/15 rounded-xl px-4 py-3 text-sm focus:outline-none focus:border-[#FFC800]"
            required
          />
        </div>

        <div className="mb-4">
          <label className="block text-sm font-bold text-[#0A0A0A] mb-1.5">
            Alamat
          </label>
          {loadingAlamat ? (
            <p className="text-sm text-[#6B7280]">Memuat alamat...</p>
          ) : alamatList.length === 0 ? (
            <p className="text-sm text-[#D64545]">
              Belum ada alamat tersimpan. Tambahkan dulu di halaman Profile.
            </p>
          ) : (
            <select
              name="alamatId"
              value={form.alamatId}
              onChange={handleChange}
              className="w-full border-2 border-[#0A0A0A]/15 rounded-xl px-4 py-3 text-sm focus:outline-none focus:border-[#FFC800]"
              required
            >
              <option value="" disabled>Pilih alamat</option>
              {alamatList.map((a, i) => (
                <option key={i} value={i}>
                  {a.label} — {a.jalan}, {a.kecamatan}
                </option>
              ))}
            </select>
          )}
        </div>

        <div className="mb-6">
          <label className="block text-sm font-bold text-[#0A0A0A] mb-1.5">
            Keluhan
          </label>
          <textarea
            name="keluhan"
            value={form.keluhan}
            onChange={handleChange}
            rows={3}
            className="w-full border-2 border-[#0A0A0A]/15 rounded-xl px-4 py-3 text-sm focus:outline-none focus:border-[#FFC800] resize-none"
            required
          />
        </div>

        <Button type="submit" variant="primary" size="lg" className="w-full" disabled={loading}>
          {loading ? "Memproses..." : "Kirim Pesanan"}
        </Button>
      </form>
    </div>
  );
}