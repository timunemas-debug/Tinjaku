import api from "./api";

export const getAlamatMitra = () =>
  api.get("/alamat/mitra").then((res) => res.data);

export const createAlamatMitra = (data) =>
  api.post("/alamat/alamat-mitra", data).then((res) => res.data);

export const updateAlamatMitra = (idAlamat, data) =>
  api.put(`/alamat/${idAlamat}/update-alamat-mitra`, data).then((res) => res.data);