import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getPesananById } from "../../services/pesananService";
import { getStatusInfo } from "../../utils/statusPesananMap";

export default function DetailPesanan() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [pesanan, setPesanan] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function fetchDetail() {
      try {
        const data = await getPesananById(id);
        setPesanan(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }

    fetchDetail();
  }, [id]);

  if (loading) {
    return <p className="font-body text-sm text-ink/50">Memuat detail pesanan...</p>;
  }

  if (error) {
    return (
      <p className="font-body text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-3 py-2 inline-block">
        {error}
      </p>
    );
  }

  if (!pesanan) return null;

  const statusInfo = getStatusInfo(pesanan.status);

  const fields = [
    { label: "ID Pesanan", value: `#${pesanan.id}` },
    { label: "Pelanggan", value: pesanan.namaLengkap ?? "-" },
    { label: "Nama Penerima", value: pesanan.namaPenerima ?? "-" },
    { label: "Mitra", value: pesanan.namaMitra ?? "Belum ada mitra" },
    { label: "Keluhan", value: pesanan.keluhan },
    { label: "Alamat Lengkap", value: pesanan.alamatLengkap },
    { label: "Kelurahan", value: pesanan.kelurahan },
    { label: "Kecamatan", value: pesanan.kecamatan },
    { label: "Kota", value: pesanan.kota },
    { label: "Provinsi", value: pesanan.provinsi },
  ];

  return (
    <div>
      <button
        onClick={() => navigate(-1)}
        className="font-body text-sm text-ink/60 hover:text-ink mb-4"
      >
        ← Kembali
      </button>

      <div className="bg-white rounded-2xl border border-gray-200 p-6">
        <div className="flex items-center justify-between mb-6">
          <h1 className="font-display font-bold text-2xl text-ink">
            Detail Pesanan
          </h1>
          <span className={`px-4 py-1.5 rounded-full text-sm font-semibold ${statusInfo.color}`}>
            {statusInfo.label}
          </span>
        </div>

        <div className="grid md:grid-cols-2 gap-5">
          {fields.map((field) => (
            <div key={field.label}>
              <p className="font-body text-xs text-ink/50 mb-1">{field.label}</p>
              <p className="font-body text-sm text-ink font-medium">{field.value}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}