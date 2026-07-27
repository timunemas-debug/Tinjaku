import api from "./api";

export const authService = {
  register: async ({ namaDepan, namaBelakang, email, password }) => {
    const response = await api.post("/auth/regist", {
      namaDepan,
      namaBelakang,
      email,
      password,
    });
    return response.data;
  },

  login: async ({ email, password }) => {
    const response = await api.post("/auth/login", { email, password });
    return response.data;
  },
};