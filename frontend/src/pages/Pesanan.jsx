import { useState } from "react";

function Pesanan() {
  const [form, setForm] = useState({
    nama: "",
    noHp: "",
    alamat: "",
    kota: "",
    tanggal: "",
    jam: "",
    catatan: "",
  });

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    console.log(form);

    alert("Pesanan berhasil dikirim!");
  };

  return (
    <div className="min-h-screen bg-gray-100 py-10 px-5">

      <div className="max-w-2xl mx-auto bg-white rounded-2xl shadow-lg p-8">

        <h1 className="text-3xl font-bold text-center text-blue-600">
        Form Pemesanan
        </h1>

        <p className="text-center text-gray-500 mt-2">
          Isi data berikut untuk melakukan pemesanan layanan.
        </p>

        <form
          onSubmit={handleSubmit}
          className="space-y-5 mt-8"
        >

          <div>
            <label>Nama</label>

            <input
              type="text"
              name="nama"
              value={form.nama}
              onChange={handleChange}
              className="w-full border rounded-lg p-3 mt-2"
            />
          </div>

          <div>
            <label>No HP</label>

            <input
              type="text"
              name="noHp"
              value={form.noHp}
              onChange={handleChange}
              className="w-full border rounded-lg p-3 mt-2"
            />
          </div>

          <div>
            <label>Alamat</label>

            <textarea
              name="alamat"
              value={form.alamat}
              onChange={handleChange}
              rows="3"
              className="w-full border rounded-lg p-3 mt-2"
            />
          </div>

          <div>
            <label>Kota</label>

            <input
              type="text"
              name="kota"
              value={form.kota}
              onChange={handleChange}
              className="w-full border rounded-lg p-3 mt-2"
            />
          </div>

          <div className="grid grid-cols-2 gap-4">

            <div>
              <label>Tanggal</label>

              <input
                type="date"
                name="tanggal"
                value={form.tanggal}
                onChange={handleChange}
                className="w-full border rounded-lg p-3 mt-2"
              />
            </div>

            <div>
              <label>Jam</label>

              <input
                type="time"
                name="jam"
                value={form.jam}
                onChange={handleChange}
                className="w-full border rounded-lg p-3 mt-2"
              />
            </div>

          </div>

          <div>
            <label>Catatan</label>

            <textarea
              name="catatan"
              value={form.catatan}
              onChange={handleChange}
              rows="4"
              className="w-full border rounded-lg p-3 mt-2"
            />
          </div>

          <button
            className="w-full bg-blue-600 text-white py-3 rounded-lg hover:bg-blue-700"
          >
            Pesan Sekarang
          </button>

        </form>

      </div>

    </div>
  );
}

export default Pesanan;