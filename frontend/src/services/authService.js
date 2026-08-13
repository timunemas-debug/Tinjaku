import api from "./api";

export const authService = {
  
  registerUser: async ({ namaDepan, namaBelakang, email, password }) => {
    const response = await api.post("/auth/regist-user", {
      namaDepan,
      namaBelakang,
      email,
      password,
    });
    return response.data; 
  },

  
  registerMitra: async ({ namaMitra, email, password }) => {
    const response = await api.post("/auth/regist-mitra", {
      namaMitra,
      email,
      password,
    });
    return response.data; 
  },

  
  loginUser: async ({ email, password }) => {
    const response = await api.post("/auth/login-user", { email, password });
    return response.data; 
  },

  
  loginMitra: async ({ email, password }) => {
    const response = await api.post("/auth/login-mitra", { email, password });
    return response.data; 
  },

  
  logout: async () => {
    await api.post("/auth/logout");
  },
};