import { useState } from "react";

function Profile() {
  const [profile, setProfile] = useState({
    nama: "Muhammad Fachriansyah",
    email: "fachri@gmail.com",
    noHp: "081234567890",
    alamat: "Jl. Merdeka No.10",
  });

  const handleChange = (e) => {
    setProfile({
      ...profile,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    console.log(profile);

    alert("Profil berhasil diperbarui!");
  };

  return (
    <div className="min-h-screen bg-gray-100 py-10 px-4">

      <div className="max-w-xl mx-auto bg-white rounded-2xl shadow-lg p-8">

        <div className="flex flex-col items-center">

          <div className="w-28 h-28 rounded-full bg-blue-100 flex items-center justify-center text-5xl">
            👤
          </div>

          <h1 className="text-3xl font-bold text-blue-600 mt-4">
            Profil Saya
          </h1>

        </div>

        <form
          onSubmit={handleSubmit}
          className="space-y-5 mt-8"
        >

          <div>
            <label>Nama Lengkap</label>

            <input
              type="text"
              name="nama"
              value={profile.nama}
              onChange={handleChange}
              className="w-full border rounded-lg p-3 mt-2"
            />
          </div>

          <div>
            <label>Email</label>

            <input
              type="email"
              name="email"
              value={profile.email}
              onChange={handleChange}
              className="w-full border rounded-lg p-3 mt-2"
            />
          </div>

          <div>
            <label>Nomor HP</label>

            <input
              type="text"
              name="noHp"
              value={profile.noHp}
              onChange={handleChange}
              className="w-full border rounded-lg p-3 mt-2"
            />
          </div>

          <div>
            <label>Alamat</label>

            <textarea
              name="alamat"
              value={profile.alamat}
              onChange={handleChange}
              rows="4"
              className="w-full border rounded-lg p-3 mt-2"
            />
          </div>

          <button
            className="w-full bg-blue-600 hover:bg-blue-700 text-white py-3 rounded-lg"
          >
            Simpan Perubahan
          </button>

        </form>

      </div>

    </div>
  );
}

export default Profile;