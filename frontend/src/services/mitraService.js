import api from "./api";

export const getMitra = () =>
  api.get("/mitra");

export const dashboardMitra = (mitraId) =>
  api.get(`/mitra/${mitraId}/dashboard`);

export const pesananMitra = (mitraId) =>
  api.get(`/mitra/${mitraId}/pesanan`);