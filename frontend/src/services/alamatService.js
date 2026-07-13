import api from "./api";

export const createAlamat = (userId, data) =>
  api.post(`/alamat/${userId}`, data).then((res) => res.data);

export const getAlamat = () => api.get("/alamat").then((res) => res.data);

export const getAlamatById = (id) =>
  api.get(`/alamat/${id}`).then((res) => res.data);

export const deleteAlamat = (id) =>
  api.delete(`/alamat/${id}`).then((res) => res.data);