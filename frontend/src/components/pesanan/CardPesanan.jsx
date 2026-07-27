const STATUS_CONFIG = {
  menunggu: { label: "Menunggu", color: "#FFC800", fill: 25 },
  diterima: { label: "Diterima", color: "#0A0A0A", fill: 50 },
  "dalam-perjalanan": { label: "Dalam Perjalanan", color: "#2563EB", fill: 75 },
  selesai: { label: "Selesai", color: "#2F855A", fill: 100 },
  ditolak: { label: "Ditolak", color: "#D64545", fill: 100 },
};

function StatusGauge({ status }) {
  const config = STATUS_CONFIG[status] || STATUS_CONFIG.menunggu;
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
            {pesanan.alamat || "Alamat belum diisi"}
          </p>
        </div>
      </div>
      <StatusGauge status={pesanan.status} />
      {pesanan.catatan && (
        <p className="text-sm text-[#6B7280] mt-3 line-clamp-2">{pesanan.catatan}</p>
      )}
    </div>
  );
}