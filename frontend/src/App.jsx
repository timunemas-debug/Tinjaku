import { Routes, Route } from "react-router-dom";

import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Pesanan from "./pages/Pesanan";
import Riwayat from "./pages/Riwayat";
import Profile from "./pages/Profile";
import Dashboard from "./pages/admin/Dashboard";

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
    </Routes>
  );
}

export default App;