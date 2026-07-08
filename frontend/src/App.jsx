import { Routes, Route } from "react-router-dom";

import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Pesanan from "./pages/Pesanan";
import Riwayat from "./pages/Riwayat";
import Profile from "./pages/Profile";
import Dashboard from "./pages/admin/Dashboard";
import DataPesanan from "./pages/admin/DataPesanan";
import DataPelanggan from "./pages/admin/DataPelanggan";
import DataMitra from "./pages/admin/DataMitra";  
import DetailPesanan from "./pages/admin/DetailPesanan";
import DashboardMitra from "./pages/mitra/DashboardMitra";
import PesananMasuk from "./pages/mitra/PesananMasuk";
import RiwayatMitra from "./pages/mitra/RiwayatMitra";
import ProfileMitra from "./pages/mitra/ProfileMitra";


function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/pesanan" element={<Pesanan />} />
      <Route path="/riwayat" element={<Riwayat />} />
      <Route path="/profile" element={<Profile />} />
      <Route path="/admin" element={<Dashboard />} />
      <Route path="/admin/pesanan" element={<DataPesanan />} />
      <Route path="/admin/pelanggan" element={<DataPelanggan />} />
      <Route path="/admin/mitra" element={<DataMitra />} />
      <Route path="/admin/pesanan/:id" element={<DetailPesanan/>} />
      <Route path="/mitra/dashboard" element={<DashboardMitra />} />
      <Route path="/mitra/pesanan" element={<PesananMasuk />} />
      <Route path="/mitra/riwayat" element={<RiwayatMitra />} />
      <Route path="/mitra/profile" element={<ProfileMitra />} />
      
      
    </Routes>
  );
}

export default App;