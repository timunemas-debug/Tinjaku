import api from "./api";

export const getAlamat = () =>
  api.get("/alamat");

export const tambahAlamat = (userId, data) =>
  api.post(`/alamat/${userId}`, data);

export const hapusAlamat = (id) =>
  api.delete(`/alamat/${id}`);