import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: { "Content-Type": "application/json" },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    const status = error.response?.status;
    const backendMessage = error.response?.data?.message;
    const requestUrl = error.config?.url || "";

    
    const isAuthEndpoint = requestUrl.includes("/auth/");

    if ((status === 401 || status === 403) && !isAuthEndpoint) {
      localStorage.removeItem("token");
      if (window.location.pathname !== "/login") {
        window.location.href = "/login";
      }
    }

    const message = backendMessage || error.message || "Terjadi kesalahan, coba lagi.";
    return Promise.reject(new Error(message));
  }
);
export default api;