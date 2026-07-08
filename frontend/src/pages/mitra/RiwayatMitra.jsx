import AdminLayout from "../../layouts/AdminLayout";

function RiwayatMitra() {
  return (
    <AdminLayout>

      <h1 className="text-3xl font-bold mb-8">
        Riwayat Pekerjaan
      </h1>

      <div className="bg-white rounded-xl shadow p-6">

        <table className="w-full">

          <thead className="border-b">

            <tr>
              <th className="py-4">Nama</th>
              <th>Alamat</th>
              <th>Tanggal</th>
              <th>Pendapatan</th>
            </tr>

          </thead>

          <tbody>

            <tr className="text-center">

              <td className="py-5">Andi</td>
              <td>Jl. Merdeka</td>
              <td>08 Juli 2026</td>
              <td>Rp250.000</td>

            </tr>

          </tbody>

        </table>

      </div>

    </AdminLayout>
  );
}

export default RiwayatMitra;