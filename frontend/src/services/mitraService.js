import api from "./api";

export const getMitra = () => api.get("/mitra").then((res) => res.data);

export const getMitraById = (mitraId) =>
  api.get(`/mitra/${mitraId}`).then((res) => res.data);

export const createMitra = (data) =>
  api.post("/mitra", data).then((res) => res.data);

export const deleteMitra = (mitraId) =>
  api.delete(`/mitra/${mitraId}`).then((res) => res.data);

export const getPesananByMitra = (mitraId) =>
  api.get(`/mitra/${mitraId}/pesanan`).then((res) => res.data);

export const getDashboardMitra = (mitraId) =>
  api.get(`/mitra/${mitraId}/dashboard`).then((res) => res.data);

export const getKotaMitra = () =>
  api.get("/mitra/kota").then((res) => res.data);