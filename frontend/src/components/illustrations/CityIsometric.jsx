export default function CityIsometric() {
  return (
    <svg
      viewBox="0 0 620 520"
      xmlns="http://www.w3.org/2000/svg"
      style={{ width: "100%", height: "auto" }}
    >
      {/* garis penghubung putus-putus antar titik */}
      <g stroke="#D8D8DD" strokeWidth="2" strokeDasharray="6 6" fill="none">
        <path d="M300 260 L180 340" />
        <path d="M300 260 L430 190" />
        <path d="M300 260 L470 340" />
        <path d="M300 260 L260 400" />
        <path d="M430 190 L560 260" />
        <path d="M470 340 L560 400" />
      </g>

      {/* Bangunan: Pabrik (kiri atas) */}
      <g transform="translate(340,60)">
        <polygon points="0,60 60,30 120,60 60,90" fill="#F4F4F6" stroke="#101014" strokeWidth="2.5" />
        <polygon points="0,60 0,110 60,140 60,90" fill="#EAEAEE" stroke="#101014" strokeWidth="2.5" />
        <polygon points="60,90 60,140 120,110 120,60" fill="#FAFAFB" stroke="#101014" strokeWidth="2.5" />
        <rect x="10" y="10" width="10" height="30" fill="#F4F4F6" stroke="#101014" strokeWidth="2.5" />
        <rect x="26" y="0" width="10" height="40" fill="#F4F4F6" stroke="#101014" strokeWidth="2.5" />
        <rect x="42" y="18" width="10" height="22" fill="#F4F4F6" stroke="#101014" strokeWidth="2.5" />
      </g>

      {/* Bangunan tinggi (kanan atas) */}
      <g transform="translate(470,80)">
        <polygon points="0,40 45,20 90,40 45,60" fill="#F4F4F6" stroke="#101014" strokeWidth="2.5" />
        <polygon points="0,40 0,170 45,190 45,60" fill="#EAEAEE" stroke="#101014" strokeWidth="2.5" />
        <polygon points="45,60 45,190 90,170 90,40" fill="#FAFAFB" stroke="#101014" strokeWidth="2.5" />
        {[0, 1, 2, 3, 4].map((row) => (
          <g key={row}>
            <rect x="8" y={55 + row * 24} width="10" height="12" fill="#101014" opacity="0.85" />
            <rect x="24" y={55 + row * 24} width="10" height="12" fill="#101014" opacity="0.85" />
          </g>
        ))}
      </g>

      {/* Bangunan (kiri bawah) */}
      <g transform="translate(120,230)">
        <polygon points="0,40 45,20 90,40 45,60" fill="#F4F4F6" stroke="#101014" strokeWidth="2.5" />
        <polygon points="0,40 0,150 45,170 45,60" fill="#EAEAEE" stroke="#101014" strokeWidth="2.5" />
        <polygon points="45,60 45,170 90,150 90,40" fill="#FAFAFB" stroke="#101014" strokeWidth="2.5" />
        {[0, 1, 2, 3].map((row) => (
          <g key={row}>
            <rect x="8" y={55 + row * 22} width="10" height="11" fill="#101014" opacity="0.85" />
            <rect x="24" y={55 + row * 22} width="10" height="11" fill="#101014" opacity="0.85" />
          </g>
        ))}
      </g>

      {/* Bangunan (kanan bawah) */}
      <g transform="translate(470,260)">
        <polygon points="0,40 45,20 90,40 45,60" fill="#F4F4F6" stroke="#101014" strokeWidth="2.5" />
        <polygon points="0,40 0,150 45,170 45,60" fill="#EAEAEE" stroke="#101014" strokeWidth="2.5" />
        <polygon points="45,60 45,170 90,150 90,40" fill="#FAFAFB" stroke="#101014" strokeWidth="2.5" />
        <rect x="8" y="70" width="28" height="80" fill="#101014" opacity="0.85" />
      </g>

      {/* Rumah (bawah tengah) */}
      <g transform="translate(210,340)">
        <polygon points="0,40 40,20 80,40 40,60" fill="#F4F4F6" stroke="#101014" strokeWidth="2.5" />
        <polygon points="0,40 0,90 40,110 40,60" fill="#EAEAEE" stroke="#101014" strokeWidth="2.5" />
        <polygon points="40,60 40,110 80,90 80,40" fill="#FAFAFB" stroke="#101014" strokeWidth="2.5" />
        <polygon points="12,10 40,-4 68,10 40,24" fill="#FFC629" stroke="#101014" strokeWidth="2.5" />
      </g>

      {/* Pin lokasi bertema ninja Tinjaku, di titik pusat */}
      <g transform="translate(270,190)">
        <path
          d="M30 0C13.4 0 0 13.4 0 30c0 22.5 30 48 30 48s30-25.5 30-48C60 13.4 46.6 0 30 0Z"
          fill="#101014"
        />
        <rect x="14" y="22" width="32" height="12" rx="6" fill="#FFC629" />
        <rect x="20" y="25" width="7" height="6" rx="1.5" fill="#101014" />
        <rect x="33" y="25" width="7" height="6" rx="1.5" fill="#101014" />
      </g>
    </svg>
  );
}   