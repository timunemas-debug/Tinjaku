// src/utils/statusPesananMap.js

export const STATUS_PESANAN = {
  MENUNGGU: "MENUNGGU",
  DITERIMA: "DITERIMA",
  DALAM_PERJALANAN: "DALAM_PERJALANAN",
  DIKERJAKAN: "DIKERJAKAN",
  DITOLAK: "DITOLAK",
  SELESAI: "SELESAI",
};

export const statusPesananMap = {
  MENUNGGU: { label: "Menunggu", color: "bg-yellow-100 text-yellow-800" },
  DITERIMA: { label: "Diterima", color: "bg-blue-100 text-blue-800" },
  DALAM_PERJALANAN: { label: "Dalam Perjalanan", color: "bg-indigo-100 text-indigo-800" },
  DIKERJAKAN: { label: "Dikerjakan", color: "bg-purple-100 text-purple-800" },
  DITOLAK: { label: "Ditolak", color: "bg-red-100 text-red-800" },
  SELESAI: { label: "Selesai", color: "bg-green-100 text-green-800" },
};

export function getStatusInfo(status) {
  return statusPesananMap[status] ?? { label: status, color: "bg-gray-100 text-gray-800" };
}

export const KOTA_OPTIONS = ["JAKARTA", "BOGOR", "DEPOK", "TANGERANG", "BEKASI"];
export const LABEL_ALAMAT_OPTIONS = ["RUMAH", "KANTOR", "APARTEMENT", "HOTEL", "GUDANG", "PABRIK"];
export const LABEL_MITRA_OPTIONS = ["GARASI", "KANTOR", "CABANG", "WORKSHOP"];

export const ROLE = {
  USER: "ROLE_USER",
  MITRA: "ROLE_MITRA",
  ADMIN: "ROLE_ADMIN",
};