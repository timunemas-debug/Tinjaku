import api from "./api";

export const getAlamat = () => api.get("/alamat/user").then((res) => res.data);

export const createAlamat = (data) =>
  api.post("/alamat/tambah-alamat-user", data).then((res) => res.data);

export const updateAlamat = (alamatId, data) =>
  api.put(`/alamat/${alamatId}/update-alamat`, data).then((res) => res.data);