import { Link } from "react-router-dom";

const NAV_ITEMS = [
  { label: "Home", to: "/" },
  { label: "About Us", to: "/tentang-kami" },
  { label: "Services", to: "/layanan" },
  { label: "Blog", to: "/blog" },
];

export default function Navbar() {
  return (
    <header className="max-w-[1440px] mx-auto flex items-center justify-between gap-6 px-16 py-7 max-md:px-6 max-md:py-5">
      <Link to="/" className="flex items-center gap-2.5 font-display font-extrabold text-xl tracking-wide text-ink">
        <span className="w-[30px] h-[30px] rounded-[50%_50%_50%_6px] bg-ink flex items-center justify-center">
          <span className="w-4 h-1.5 bg-accent rounded-sm" />
        </span>
        TINJAKU
      </Link>

      <nav className="flex items-center gap-9 max-md:hidden">
        {NAV_ITEMS.map((item) => (
          <Link
            key={item.to}
            to={item.to}
            className="font-body font-semibold text-sm tracking-wide uppercase text-ink hover:opacity-60"
          >
            {item.label}
          </Link>
        ))}
      </nav>

      <div className="flex items-center gap-6">
        <Link to="/login" className="font-body font-semibold text-sm uppercase text-ink">
          Login
        </Link>
        <Link
          to="/register"
          className="font-body font-bold text-sm uppercase text-ink bg-accent px-6 py-3.5 rounded-full whitespace-nowrap hover:bg-yellow-300"
        >
          Get Started
        </Link>
      </div>
    </header>
  );
}