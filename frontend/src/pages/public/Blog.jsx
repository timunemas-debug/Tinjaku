import { Link } from "react-router-dom";
import Reveal from "../../components/common/Reveal";
import { BLOG_POSTS } from "../../data/blogPosts";

const CATEGORY_COLOR = {
  Perawatan: "border-l-accent",
  Biaya: "border-l-blue-400",
  Peringatan: "border-l-red-400",
};

export default function Blog() {
  const [featured, ...rest] = BLOG_POSTS;
  const FeaturedIcon = featured.icon;

  return (
    <div className="max-w-[1440px] mx-auto px-16 py-20 max-md:px-6 max-md:py-12">
      <Reveal direction="up">
        <div className="max-w-2xl mb-12">
          <h1 className="font-display font-extrabold text-[42px] text-ink mb-4 max-md:text-3xl">
            Blog Tinjaku
          </h1>
          <p className="font-body text-ink/70 text-base leading-relaxed">
            Tips, info, dan panduan seputar perawatan septic tank.
          </p>
        </div>
      </Reveal>

      
      <Reveal direction="up">
        <Link
          to={`/blog/${featured.slug}`}
          className="grid md:grid-cols-2 gap-8 bg-ink rounded-3xl overflow-hidden mb-8 hover:shadow-xl transition-shadow group relative"
        >
          <div className="p-10 flex flex-col justify-center relative z-10">
            <span className="inline-block w-fit font-body font-bold text-xs uppercase tracking-wide text-ink bg-accent px-3 py-1.5 rounded-full mb-5">
              {featured.category} · Artikel Terbaru
            </span>
            <h2 className="font-display font-extrabold text-3xl text-white mb-3 leading-tight group-hover:text-accent transition-colors">
              {featured.title}
            </h2>
            <p className="font-body text-white/60 text-sm leading-relaxed mb-5">
              {featured.excerpt}
            </p>
            <div className="flex items-center gap-3 font-body text-xs text-white/40">
              <span>{featured.author}</span>
              <span>•</span>
              <span>{featured.date}</span>
              <span>•</span>
              <span>{featured.readTime}</span>
            </div>
          </div>

          
          <div className="relative flex items-center justify-center bg-white/[0.03] min-h-[220px] max-md:min-h-[160px] overflow-hidden">
            <div
              className="absolute inset-0 opacity-[0.06]"
              style={{
                backgroundImage: `radial-gradient(circle, #FFC629 1.5px, transparent 1.5px)`,
                backgroundSize: "24px 24px",
              }}
            />
            <FeaturedIcon
              size={96}
              strokeWidth={1.2}
              className="text-accent relative z-10 group-hover:scale-110 transition-transform duration-500"
            />
          </div>
        </Link>
      </Reveal>

      
      <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
        {rest.map((post, i) => {
          const Icon = post.icon;
          const borderColor = CATEGORY_COLOR[post.category] ?? "border-l-accent";

          return (
            <Reveal key={post.slug} direction="up" delay={i * 80}>
              <Link
                to={`/blog/${post.slug}`}
                className={`block bg-white border border-gray-200 border-l-4 ${borderColor} rounded-2xl overflow-hidden hover:shadow-lg hover:-translate-y-0.5 transition-all h-full`}
              >
                <div className="p-6">
                  <div className="flex items-center justify-between mb-4">
                    <Icon size={26} strokeWidth={1.5} className="text-ink/40" />
                    <span className="font-body font-semibold text-[10px] uppercase tracking-wide text-ink/40 bg-gray-100 px-2.5 py-1 rounded-full">
                      {post.category}
                    </span>
                  </div>

                  <p className="font-body text-xs text-ink/40 mb-2">
                    {post.date} · {post.readTime}
                  </p>

                  <h3 className="font-display font-bold text-lg text-ink mb-2">
                    {post.title}
                  </h3>

                  <p className="font-body text-sm text-ink/60 leading-relaxed line-clamp-3">
                    {post.excerpt}
                  </p>
                </div>
              </Link>
            </Reveal>
          );
        })}
      </div>
    </div>
  );
}