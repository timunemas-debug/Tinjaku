import axios from "axios";

const api = axios.create({
  baseURL: "https://tinjaku-production.up.railway.app",
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    const status = error.response?.status;
    const url = error.config?.url || "";

    // Jangan logout untuk endpoint auth
    const isAuthEndpoint =
      url.includes("/auth/login") ||
      url.includes("/auth/register");

    // Logout HANYA jika token benar-benar tidak valid / expired
    if (status === 401 && !isAuthEndpoint) {
      localStorage.removeItem("token");
      localStorage.removeItem("user");

      window.location.href = "/login";
    }

    // 403 jangan langsung logout
    if (status === 403) {
      console.warn("Akses ditolak:", url);
    }

    return Promise.reject(error);
  }
);

export default api;