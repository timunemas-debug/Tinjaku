import api from "./api";

export const getPesanan = () => api.get("/pesanan").then((res) => res.data);

export const getPesananById = (id) =>
  api.get(`/pesanan/${id}`).then((res) => res.data);

export const getPesananByStatus = (status) =>
  api.get(`/pesanan/status/${status}`).then((res) => res.data);

export const getTotalPesanan = () =>
  api.get("/pesanan/total").then((res) => res.data);

export const createPesanan = (userId, data) =>
  api.post(`/pesanan/${userId}`, data).then((res) => res.data);

export const updatePesanan = (id, data) =>
  api.put(`/pesanan/${id}`, data).then((res) => res.data);

export const deletePesanan = (id) =>
  api.delete(`/pesanan/${id}`).then((res) => res.data);

export const tolakPesanan = (pesananId) =>
  api.patch(`/pesanan/${pesananId}/tolak`).then((res) => res.data);

export const terimaPesanan = (pesananId, mitraId) =>
  api.patch(`/pesanan/${pesananId}/terima/${mitraId}`).then((res) => res.data);

export const pesananDalamPerjalanan = (pesananId) =>
  api.patch(`/pesanan/${pesananId}/dalam-perjalanan`).then((res) => res.data);

export const selesaikanPesanan = (pesananId) =>
  api.patch(`/pesanan/${pesananId}/selesai`).then((res) => res.data);