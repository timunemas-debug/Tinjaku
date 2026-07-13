export default function Button({
  children,
  variant = "primary",
  size = "md",
  className = "",
  ...props
}) {
  const base =
    "font-[Baloo_2] font-bold uppercase tracking-wide rounded-full transition-all duration-150 disabled:opacity-40 disabled:cursor-not-allowed inline-flex items-center justify-center gap-2";

  const variants = {
    primary: "bg-[#FFC800] text-[#0A0A0A] hover:bg-[#e6b400] active:scale-95",
    secondary: "bg-[#0A0A0A] text-white hover:bg-[#1a1a1a] active:scale-95",
    outline: "border-2 border-[#0A0A0A] text-[#0A0A0A] hover:bg-[#0A0A0A] hover:text-white",
    danger: "bg-[#D64545] text-white hover:bg-[#c13a3a]",
    ghost: "text-[#0A0A0A] hover:bg-[#0A0A0A]/5",
  };

  const sizes = {
    sm: "text-xs px-4 py-2",
    md: "text-sm px-5 py-3",
    lg: "text-base px-7 py-3.5",
  };

  return (
    <button
      className={`${base} ${variants[variant]} ${sizes[size]} ${className}`}
      {...props}
    >
      {children}
    </button>
  );
}