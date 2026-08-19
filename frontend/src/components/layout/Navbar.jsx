import { Link, NavLink } from "react-router-dom";
import logo from "../../assets/logo-tinjaku.png";

const NAV_ITEMS = [
  { label: "Home", to: "/" },
  { label: "About Us", to: "/tentang-kami" },
  { label: "Services", to: "/layanan" },
  { label: "Blog", to: "/blog" },
];

export default function Navbar() {
  return (
    <header className="max-w-[1440px] mx-auto flex items-center justify-between gap-6 px-16 py-7 max-md:px-6 max-md:py-5">
      
      
      <Link to="/" className="flex items-center">
      <img src={logo} alt="Tinjaku" className="h-9 w-auto" />
      </Link>

      
      <nav className="flex items-center gap-3 max-md:hidden">
        {NAV_ITEMS.map((item) => (
          <NavLink
            key={item.to}
            to={item.to}
            end={item.to === "/"}
            className={({ isActive }) =>
              `
              font-body font-semibold text-sm tracking-wide uppercase
              px-4 py-3 rounded-full transition-all duration-200
              ${
                isActive
                  ? "text-ink bg-accent"
                  : "text-ink hover:bg-accent/30"
              }
              `
            }
          >
            {item.label}
          </NavLink>
        ))}
      </nav>

      
      <div className="flex items-center gap-3">
        <NavLink
          to="/login"
          className={({ isActive }) =>
            `
            font-body font-semibold text-sm uppercase
            px-4 py-3 rounded-full transition-all duration-200
            ${
              isActive
                ? "text-ink bg-accent"
                : "text-ink hover:bg-accent/30"
            }
            `
          }
        >
          Login
        </NavLink>

        <NavLink
          to="/register"
          className={({ isActive }) =>
            `
            font-body font-bold text-sm uppercase
            px-6 py-3.5 rounded-full whitespace-nowrap
            transition-all duration-200
            ${
              isActive
                ? "text-ink bg-accent"
                : "text-ink hover:bg-accent/30"
            }
            `
          }
        >
          Get Started
        </NavLink>
      </div>
    </header>
  );
}