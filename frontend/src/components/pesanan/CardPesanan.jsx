const STATUS_CONFIG = {
  MENUNGGU: { label: "Menunggu", color: "#FFC800", fill: 20 },
  DITERIMA: { label: "Diterima", color: "#0A0A0A", fill: 40 },
  DALAM_PERJALANAN: { label: "Dalam Perjalanan", color: "#2563EB", fill: 60 },
  DIKERJAKAN: { label: "Dikerjakan", color: "#7C3AED", fill: 80 },
  SELESAI: { label: "Selesai", color: "#2F855A", fill: 100 },
  DITOLAK: { label: "Ditolak", color: "#D64545", fill: 100 },
};

function StatusGauge({ status }) {
  const config = STATUS_CONFIG[status] || STATUS_CONFIG.MENUNGGU;
  return (
    <div className="flex items-center gap-2">
      <div className="w-16 h-2 rounded-full bg-[#0A0A0A]/10 overflow-hidden">
        <div
          className="h-full rounded-full transition-all"
          style={{ width: `${config.fill}%`, backgroundColor: config.color }}
        />
      </div>
      <span className="font-[Baloo_2] text-xs font-bold uppercase" style={{ color: config.color }}>
        {config.label}
      </span>
    </div>
  );
}

export default function CardPesanan({ pesanan, onClick }) {
  return (
    <div
      onClick={onClick}
      className="bg-white border-2 border-[#0A0A0A]/10 rounded-2xl p-4 hover:border-[#FFC800] hover:shadow-md transition-all cursor-pointer"
    >
      <div className="flex items-start justify-between mb-3">
        <div>
          <p className="text-xs text-[#6B7280] font-mono">
            #{String(pesanan.id).padStart(4, "0")}
          </p>
          <p className="font-[Baloo_2] font-bold text-[#0A0A0A]">
            {pesanan.alamatLengkap || "Alamat belum diisi"}
          </p>
        </div>
      </div>
      <StatusGauge status={pesanan.status} />
      {pesanan.keluhan && (
        <p className="text-sm text-[#6B7280] mt-3 line-clamp-2">{pesanan.keluhan}</p>
      )}
    </div>
  );
}