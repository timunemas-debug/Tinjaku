import api from "./api";

export const getNotifications = () =>
  api.get("/notification").then((res) => res.data);

export const getUnreadNotifications = () =>
  api.get("/notification/unread").then((res) => res.data);

export const hasUnreadNotifications = () =>
  api.get("/notification/has-unread").then((res) => res.data);

export const markAsRead = (notificationId) =>
  api.put(`/notification/${notificationId}/read`).then((res) => res.data);

export const markAllRead = () =>
  api.put("/notification/read-all").then((res) => res.data);