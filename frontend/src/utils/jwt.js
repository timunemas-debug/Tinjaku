export function decodeToken(token) {
  if (!token) return null;

  try {
    const payload = token.split(".")[1];
    const decoded = atob(payload.replace(/-/g, "+").replace(/_/g, "/"));
    return JSON.parse(decoded);
  } catch (error) {
    console.error("Gagal decode token:", error);
    return null;
  }
}

export function isTokenExpired(token) {
  const decoded = decodeToken(token);
  if (!decoded?.exp) return true;
  const nowInSeconds = Date.now() / 1000;
  return decoded.exp < nowInSeconds;
}

export function getUserFromToken(token) {
  const decoded = decodeToken(token);
  if (!decoded) return null;

  return {
    email: decoded.sub,
    role: decoded.role,
    userId: decoded.id,
  };
}