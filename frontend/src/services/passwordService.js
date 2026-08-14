import api from "./api";

export const requestPasswordReset = (email) =>
  api.post("/password/forgot-password", { email }).then((res) => res.data);

export const verifyOtp = (email, otp) =>
  api.post("/password/forgot-password/verify", { email, otp }).then((res) => res.data);

export const resetPassword = (resetToken, newPassword) =>
  api.post("/password/reset-password", { resetToken, newPassword }).then((res) => res.data);