import { Routes, Route } from "react-router-dom";

import PublicLayout from "./layouts/PublicLayout";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Pesanan from "./pages/Pesanan";
import Riwayat from "./pages/Riwayat";
import Profile from "./pages/Profile";
import TentangKami from "./pages/TentangKami";
import Layanan from "./pages/Layanan";

import AdminLayout from "./layouts/AdminLayout";
import Dashboard from "./pages/admin/Dashboard";
import DataPesanan from "./pages/admin/DataPesanan";
import DataPelanggan from "./pages/admin/DataPelanggan";
import DataMitra from "./pages/admin/DataMitra";
import DetailPesanan from "./pages/admin/DetailPesanan";

import MitraLayout from "./layouts/MitraLayout";
import DashboardMitra from "./pages/mitra/DashboardMitra";
import PesananMasuk from "./pages/mitra/PesananMasuk";
import RiwayatMitra from "./pages/mitra/RiwayatMitra";
import ProfileMitra from "./pages/mitra/ProfileMitra";

function App() {
  return (
    <Routes>
      {/* Halaman pelanggan — dibungkus PublicLayout */}
      <Route path="/" element={<PublicLayout />}>
        <Route index element={<Home />} />
        <Route path="tentang-kami" element={<TentangKami />} />
        <Route path="layanan" element={<Layanan />} />
        <Route path="login" element={<Login />} />
        <Route path="register" element={<Register />} />
        <Route path="pesanan" element={<Pesanan />} />
        <Route path="riwayat" element={<Riwayat />} />
        <Route path="profile" element={<Profile />} />
      </Route>

      {/* Halaman admin */}
      <Route path="/admin" element={<AdminLayout />}>
        <Route index element={<Dashboard />} />
        <Route path="pesanan" element={<DataPesanan />} />
        <Route path="pelanggan" element={<DataPelanggan />} />
        <Route path="mitra" element={<DataMitra />} />
        <Route path="pesanan/:id" element={<DetailPesanan />} />
      </Route>

      {/* Halaman mitra */}
      <Route path="/mitra" element={<MitraLayout />}>
        <Route path="dashboard" element={<DashboardMitra />} />
        <Route path="pesanan" element={<PesananMasuk />} />
        <Route path="riwayat" element={<RiwayatMitra />} />
        <Route path="profile" element={<ProfileMitra />} />
      </Route>
    </Routes>
  );
}

export default App;