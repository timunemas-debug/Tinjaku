import api from "./api";

export const getPesanan = () => api.get("/pesanan");

export const getDetailPesanan = (id) =>
  api.get(`/pesanan/${id}`);

export const tambahPesanan = (userId, data) =>
  api.post(`/pesanan/${userId}`, data);

export const updatePesanan = (id, data) =>
  api.put(`/pesanan/${id}`, data);

export const hapusPesanan = (id) =>
  api.delete(`/pesanan/${id}`);

export const tolakPesanan = (id) =>
  api.patch(`/pesanan/${id}/tolak`);

export const terimaPesanan = (id, mitraId) =>
  api.patch(`/pesanan/${id}/terima/${mitraId}`);

export const dalamPerjalanan = (id) =>
  api.patch(`/pesanan/${id}/dalam-perjalanan`);

export const selesaiPesanan = (id) =>
  api.patch(`/pesanan/${id}/selesai`);