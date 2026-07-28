export default function AuthCard({ title, children }) {
  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-accent via-amber-400 to-orange-500 px-4 py-10">
      <div className="w-full max-w-sm relative">
        {/* Ninja hood */}
        <div className="relative z-0 w-40 h-24 bg-ink rounded-t-[80px] rounded-b-[36px] mx-auto flex items-start justify-center pt-8">
          <span className="w-10 h-4 bg-accent rounded-full" />
        </div>

        {/* Card putih */}
        <div className="relative z-10 -mt-10 bg-white rounded-[32px] shadow-2xl pt-14 px-8 pb-8">
          <h1 className="font-display font-extrabold text-3xl text-ink text-center mb-8">
            {title}
          </h1>

          {children}
        </div>
      </div>
    </div>
  );
}