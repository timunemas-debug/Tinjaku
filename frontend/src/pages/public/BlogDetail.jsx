import { useParams, Link, Navigate } from "react-router-dom";
import { BLOG_POSTS } from "../../data/blogPosts";

export default function BlogDetail() {
  const { slug } = useParams();
  const post = BLOG_POSTS.find((p) => p.slug === slug);

  if (!post) {
    return <Navigate to="/blog" replace />;
  }

  return (
    <div className="max-w-3xl mx-auto px-6 py-16">
      <Link to="/blog" className="font-body text-sm text-ink/50 hover:text-ink">
        ← Kembali ke Blog
      </Link>

      <div className="h-56 bg-ink rounded-3xl flex items-center justify-center my-8">
        <post.icon size={64} className="text-accent" strokeWidth={1.5} />
      </div>

      <p className="font-body text-xs text-ink/40 mb-2">{post.date}</p>
      <h1 className="font-display font-extrabold text-3xl text-ink mb-6">
        {post.title}
      </h1>
      
      <div className="flex items-center gap-3 font-body text-xs text-ink/40 mb-6">
        <span>{post.author}</span>
        <span>•</span>
        <span>{post.readTime}</span>
      </div>

      <div className="font-body text-base text-ink/80 leading-relaxed flex flex-col gap-4">
        {post.content.map((paragraph, i) => (
          <p key={i}>{paragraph}</p>
        ))}
      </div>
    </div>
  );
}