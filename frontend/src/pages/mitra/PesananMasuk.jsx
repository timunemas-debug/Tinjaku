import { useState } from "react";
import AdminLayout from "../../layouts/AdminLayout";

function PesananMasuk() {
  const [pesanan, setPesanan] = useState([
    {
      id: 1,
      nama: "Andi",
      alamat: "Jl. Merdeka No.10",
      tanggal: "08 Juli 2026",
      biaya: "Rp250.000",
      status: "Menunggu",
    },
    {
      id: 2,
      nama: "Budi",
      alamat: "Jl. Sudirman No.25",
      tanggal: "09 Juli 2026",
      biaya: "Rp300.000",
      status: "Menunggu",
    },
  ]);

  const ambilPesanan = (id) => {
    setPesanan(
      pesanan.map((item) =>
        item.id === id
          ? { ...item, status: "Diproses" }
          : item
      )
    );
  };

  return (
    <AdminLayout>

      <h1 className="text-3xl font-bold mb-8">
        Pesanan Masuk
      </h1>

      <div className="bg-white rounded-xl shadow overflow-hidden">

        <table className="w-full">

          <thead className="bg-blue-600 text-white">

            <tr>
              <th className="p-4">Nama</th>
              <th>Alamat</th>
              <th>Tanggal</th>
              <th>Biaya</th>
              <th>Status</th>
              <th>Aksi</th>
            </tr>

          </thead>

          <tbody>

            {pesanan.map((item) => (

              <tr
                key={item.id}
                className="border-b text-center"
              >

                <td className="p-4">{item.nama}</td>

                <td>{item.alamat}</td>

                <td>{item.tanggal}</td>

                <td>{item.biaya}</td>

                <td>

                  <span
                    className={`px-3 py-1 rounded-full text-white ${
                      item.status === "Menunggu"
                        ? "bg-yellow-500"
                        : "bg-blue-600"
                    }`}
                  >
                    {item.status}
                  </span>

                </td>

                <td>

                  <button
                    onClick={() => ambilPesanan(item.id)}
                    className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
                  >
                    Ambil
                  </button>

                </td>

              </tr>

            ))}

          </tbody>

        </table>

      </div>

    </AdminLayout>
  );
}

export default PesananMasuk;