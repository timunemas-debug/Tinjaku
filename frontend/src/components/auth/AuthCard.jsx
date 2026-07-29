import authBg from "../../assets/auth-background.png";

export default function AuthCard({ title, children, plain = false }) {
  return (
    <div
      className="min-h-screen flex items-center justify-center px-4 py-10 bg-cover bg-center"
      style={{ backgroundImage: `url(${authBg})` }}
    >
      <div className="w-full max-w-md rounded-[56px] overflow-hidden shadow-2xl">
        {!plain && (
          <div
            className="bg-ink pt-6 pb-10 flex items-center justify-center"
            style={{
              borderBottomLeftRadius: "50% 40px",
              borderBottomRightRadius: "50% 40px",
            }}
          >
            <svg width="72" height="30" viewBox="0 0 72 30" fill="none">
              <path
                d="M8 20c4-14 16-14 20 0"
                stroke="#FFC629"
                strokeWidth="7"
                strokeLinecap="round"
                fill="none"
              />
              <path
                d="M44 20c4-14 16-14 20 0"
                stroke="#FFC629"
                strokeWidth="7"
                strokeLinecap="round"
                fill="none"
              />
            </svg>
          </div>
        )}

        <div className="bg-white px-10 pt-8 pb-9">
          {plain ? (
            <div className="flex items-center justify-center gap-1.5 mb-8">
              <span className="font-display font-extrabold text-3xl text-ink">Sign</span>
              <span className="w-9 h-9 rounded-full bg-ink flex items-center justify-center">
                <span className="w-5 h-2 bg-accent rounded-full" />
              </span>
              <span className="font-display font-extrabold text-3xl text-ink">p</span>
            </div>
          ) : (
            <h1 className="font-display font-extrabold text-4xl text-ink text-center mb-8">
              {title}
            </h1>
          )}

          {children}
        </div>
      </div>
    </div>
  );
}