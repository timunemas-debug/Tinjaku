import api from "./api";

export const getUsers = () => api.get("/user").then((res) => res.data);

export const getUserById = (userId) =>
  api.get(`/user/${userId}`).then((res) => res.data);

export const createUser = (data) =>
  api.post("/user", data).then((res) => res.data);

export const deleteUser = (userId) =>
  api.delete(`/user/${userId}`).then((res) => res.data);