import { useScrollReveal } from "../../hooks/useScrollReveal";


export default function Reveal({ children, delay = 0, direction = "up", className = "" }) {
  const { ref, isVisible } = useScrollReveal();

  const directionOffset = {
    up: "translate-y-8",
    down: "-translate-y-8",
    left: "translate-x-8",
    right: "-translate-x-8",
  };

  return (
    <div
      ref={ref}
      className={`transition-all duration-700 ease-out ${
        isVisible
          ? "opacity-100 translate-x-0 translate-y-0"
          : `opacity-0 ${directionOffset[direction]}`
      } ${className}`}
      style={{ transitionDelay: isVisible ? `${delay}ms` : "0ms" }}
    >
      {children}
    </div>
  );
}