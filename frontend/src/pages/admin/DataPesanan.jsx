import { useState } from "react";
import { Link } from "react-router-dom";
import AdminLayout from "../../layouts/AdminLayout";

function DataPesanan() {
  const [pesanan, setPesanan] = useState([
    {
      id: 1,
      nama: "Andi",
      alamat: "Jl. Merdeka No.10",
      tanggal: "08 Juli 2026",
      status: "Menunggu",
    },
    {
      id: 2,
      nama: "Budi",
      alamat: "Jl. Sudirman No.5",
      tanggal: "09 Juli 2026",
      status: "Diproses",
    },
    {
      id: 3,
      nama: "Rina",
      alamat: "Jl. Ahmad Yani No.15",
      tanggal: "10 Juli 2026",
      status: "Selesai",
    },
  ]);

  const ubahStatus = (id) => {
    setPesanan(
      pesanan.map((item) => {
        if (item.id === id) {
          if (item.status === "Menunggu") {
            return { ...item, status: "Diproses" };
          } else if (item.status === "Diproses") {
            return { ...item, status: "Selesai" };
          }
        }
        return item;
      })
    );
  };

  const hapusPesanan = (id) => {
    setPesanan(pesanan.filter((item) => item.id !== id));
  };

  return (
    <AdminLayout>
      <h1 className="text-3xl font-bold mb-6">
        Data Pesanan
      </h1>

      <div className="bg-white rounded-xl shadow-lg overflow-x-auto">

        <table className="w-full">

          <thead className="bg-blue-600 text-white">
            <tr>
              <th className="p-4">ID</th>
              <th>Nama</th>
              <th>Alamat</th>
              <th>Tanggal</th>
              <th>Status</th>
              <th>Aksi</th>
            </tr>
          </thead>

          <tbody>

            {pesanan.map((item) => (

              <tr key={item.id} className="border-b text-center">

                <td className="p-4">{item.id}</td>

                <td>{item.nama}</td>

                <td>{item.alamat}</td>

                <td>{item.tanggal}</td>

                <td>
                  <span
                    className={`px-3 py-1 rounded-full text-white ${
                      item.status === "Menunggu"
                        ? "bg-yellow-500"
                        : item.status === "Diproses"
                        ? "bg-blue-500"
                        : "bg-green-600"
                    }`}
                  >
                    {item.status}
                  </span>
                </td>

                <td className="space-x-2">

                  <Link
                    to={`/admin/pesanan/${item.id}`}
                    className="bg-green-600 text-white px-3 py-2 rounded hover:bg-green-700"
                  >
                    Detail
                  </Link>

                  <button
                    onClick={() => ubahStatus(item.id)}
                    className="bg-blue-600 text-white px-3 py-2 rounded hover:bg-blue-700"
                  >
                    Status
                  </button>

                  <button
                    onClick={() => hapusPesanan(item.id)}
                    className="bg-red-600 text-white px-3 py-2 rounded hover:bg-red-700"
                  >
                    Hapus
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

export default DataPesanan;