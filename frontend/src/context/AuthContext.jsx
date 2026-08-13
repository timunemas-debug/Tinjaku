// src/context/AuthContext.jsx
import { createContext, useState, useEffect } from "react";
import { authService } from "../services/authService";
import { decodeToken, isTokenExpired, getUserFromToken } from "../utils/jwt";

export const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (token && !isTokenExpired(token)) {
      setUser(getUserFromToken(token));
    } else {
      localStorage.removeItem("token");
    }

    setLoading(false);
  }, []);

  const login = async ({ email, password, asMitra = false }) => {
    const { token } = asMitra
      ? await authService.loginMitra({ email, password })
      : await authService.loginUser({ email, password });
// gapapa gagal, tetep lanjut hapus token lokal
    localStorage.setItem("token", token);
    const decodedUser = getUserFromToken(token);
    setUser(decodedUser);

    return decodedUser;
  };

  const register = async (data, asMitra = false) => {
    return asMitra
      ? authService.registerMitra(data)
      : authService.registerUser(data);
  };

  const logout = async () => {
    try {
      await authService.logout();
    } catch (err) {
      
    }
    localStorage.removeItem("token");
    setUser(null);
  };

  const value = {
    user,
    isAuthenticated: !!user,
    loading,
    login,
    register,
    logout,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}