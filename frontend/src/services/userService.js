import api from "./api";

export const getUser = () =>
  api.get("/user");

export const getUserById = (id) =>
  api.get(`/user/${id}`);

export const tambahUser = (data) =>
  api.post("/user", data);

export const hapusUser = (id) =>
  api.delete(`/user/${id}`);