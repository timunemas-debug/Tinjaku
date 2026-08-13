import api from "./api";

export const getAlamat = () => api.get("/alamat/user").then((res) => res.data);

export const getAlamatById = (id) =>
  api.get(`/alamat/${id}`).then((res) => res.data);

export const createAlamat = (userId, data) =>
  api.post(`/alamat/${userId}`, data).then((res) => res.data);

export const updateAlamat = (idAlamat, data) =>
  api.put(`/alamat/${idAlamat}/update-alamat`, data).then((res) => res.data);

export const deleteAlamat = (id) =>
  api.delete(`/alamat/${id}`).then((res) => res.data);