// src/App.jsx
import { Routes, Route } from "react-router-dom";

// Layouts
import PublicLayout from "./layouts/PublicLayout";
import UserLayout from "./layouts/UserLayout";
import MitraLayout from "./layouts/MitraLayout";
import AdminLayout from "./layouts/AdminLayout";

// Public pages
import Home from "./pages/public/Home";
import Login from "./pages/public/Login";
import Register from "./pages/public/Register";
import ForgotPassword from "./pages/public/ForgotPassword";
import Layanan from "./pages/public/Layanan";
import TentangKami from "./pages/public/TentangKami";

// User pages
import Pesanan from "./pages/user/Pesanan";
import UserDashboard from "./pages/user/Dashboard";
import Profile from "./pages/user/Profile";
import Alamat from "./pages/user/Alamat";
import Riwayat from "./pages/user/Riwayat";

// Mitra pages
import DashboardMitra from "./pages/mitra/DashboardMitra";
import PesananMasuk from "./pages/mitra/PesananMasuk";
import ProfileMitra from "./pages/mitra/ProfileMitra";
import RiwayatMitra from "./pages/mitra/RiwayatMitra";

// Admin pages
import Dashboard from "./pages/admin/Dashboard";
import DataMitra from "./pages/admin/DataMitra";
import DataPelanggan from "./pages/admin/DataPelanggan";
import DataPesanan from "./pages/admin/DataPesanan";
import DetailPesanan from "./pages/admin/DetailPesanan";

import { ProtectedRoute } from "./routes/ProtectedRoute";
import { ROLE } from "./utils/statusPesananMap";

export default function App() {
  return (
    <Routes>
    
      <Route element={<PublicLayout />}>
        <Route path="/" element={<Home />} />
        <Route path="/layanan" element={<Layanan />} />
        <Route path="/tentang-kami" element={<TentangKami />} />
      </Route>

      
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/forgot-password" element={<ForgotPassword />} />

      
      <Route
        element={
          <ProtectedRoute allowedRoles={[ROLE.USER]}>
            <UserLayout />
          </ProtectedRoute>
        }
      >
        <Route path="/pesanan" element={<Pesanan />} />
        <Route path="/dashboard" element={<UserDashboard />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/alamat" element={<Alamat />} />
        <Route path="/riwayat" element={<Riwayat />} />
      </Route>

      
      <Route
        element={
          <ProtectedRoute allowedRoles={[ROLE.MITRA]}>
            <MitraLayout />
          </ProtectedRoute>
        }
      >
        <Route path="/mitra/dashboard" element={<DashboardMitra />} />
        <Route path="/mitra/pesanan-masuk" element={<PesananMasuk />} />
        <Route path="/mitra/profile" element={<ProfileMitra />} />
        <Route path="/mitra/riwayat" element={<RiwayatMitra />} />
      </Route>

      
      <Route
        element={
          <ProtectedRoute allowedRoles={[ROLE.ADMIN]}>
            <AdminLayout />
          </ProtectedRoute>
        }
      >
        <Route path="/admin/dashboard" element={<Dashboard />} />
        <Route path="/admin/data-mitra" element={<DataMitra />} />
        <Route path="/admin/data-pelanggan" element={<DataPelanggan />} />
        <Route path="/admin/data-pesanan" element={<DataPesanan />} />
        <Route path="/admin/data-pesanan/:id" element={<DetailPesanan />} />
      </Route>
    </Routes>
  );
}