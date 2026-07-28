import { useEffect, useState } from "react";
import { getMitraById, setMitraOnline } from "../../services/mitraService";
import { useAuth } from "../../hooks/useAuth";

function ProfileMitra() {
  const { user } = useAuth();
  const [mitra, setMitra] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [toggling, setToggling] = useState(false);

  const fetchMitra = async () => {
    try {
      const data = await getMitraById(user.userId);
      setMitra(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchMitra();
  }, [user.userId]);

  const toggleOnline = async () => {
    setToggling(true);
    try {
      const nextStatus = mitra.statusOnOff === "ONLINE" ? "OFFLINE" : "ONLINE";
      await setMitraOnline(user.userId, nextStatus);
      await fetchMitra();
    } catch (err) {
      setError(err.message);
    } finally {
      setToggling(false);
    }
  };

  if (loading) return <p className="text-gray-500">Memuat profil...</p>;
  if (error) return <p className="text-red-600">{error}</p>;
  if (!mitra) return null;

  return (
    <div>
      <h1 className="text-3xl font-bold mb-6">Profile Mitra</h1>

      <div className="bg-white rounded-xl shadow-lg p-8">
        <div className="flex items-center justify-between">
          <div>
            <h2 className="text-2xl font-bold">{mitra.nama}</h2>
            <p className="text-gray-500">Mitra Tinjaku</p>
          </div>

          <button
            onClick={toggleOnline}
            disabled={toggling}
            className={`px-5 py-2.5 rounded-full text-white font-semibold ${
              mitra.statusOnOff === "ONLINE" ? "bg-green-600" : "bg-gray-400"
            }`}
          >
            {toggling ? "..." : mitra.statusOnOff}
          </button>
        </div>

        <div className="grid md:grid-cols-2 gap-6 mt-10">
          <div>
            <p className="font-semibold">Rating</p>
            <p>{mitra.ratingMitra != null ? mitra.ratingMitra.toFixed(1) : "-"} ⭐</p>
          </div>
          <div>
            <p className="font-semibold">Total Rating</p>
            <p>{mitra.totalRating ?? 0}</p>
          </div>
        </div>

        <div className="mt-10">
          <p className="font-semibold mb-3">Alamat Terdaftar</p>
          {mitra.alamat?.length ? (
            <ul className="flex flex-col gap-2">
              {mitra.alamat.map((a, i) => (
                <li key={i} className="text-sm text-gray-600 border border-gray-200 rounded-lg p-3">
                  {a.labelMitra} — {a.jalan}, {a.kecamatan}, {a.kota}
                </li>
              ))}
            </ul>
          ) : (
            <p className="text-sm text-gray-400">Belum ada alamat.</p>
          )}
        </div>
      </div>
    </div>
  );
}

export default ProfileMitra;