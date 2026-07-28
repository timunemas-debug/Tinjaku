import { Routes, Route } from "react-router-dom";
import Dashboard from "../pages/admin/Dashboard";

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />

      {/* Admin */}
      <Route path="/admin" element={<Dashboard />} />
    </Routes>
  );
}