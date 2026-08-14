import { useEffect, useRef, useState } from "react";
import { FiBell } from "react-icons/fi";
import {
  getNotifications,
  hasUnreadNotifications,
  markAsRead,
  markAllRead,
} from "../../services/notificationService";

function timeAgo(dateString) {
  const diffMs = Date.now() - new Date(dateString).getTime();
  const minutes = Math.floor(diffMs / 60000);
  if (minutes < 1) return "Baru saja";
  if (minutes < 60) return `${minutes} menit lalu`;
  const hours = Math.floor(minutes / 60);
  if (hours < 24) return `${hours} jam lalu`;
  const days = Math.floor(hours / 24);
  return `${days} hari lalu`;
}

export default function NotificationBell() {
  const [open, setOpen] = useState(false);
  const [notifications, setNotifications] = useState([]);
  const [hasUnread, setHasUnread] = useState(false);
  const [loading, setLoading] = useState(false);
  const wrapperRef = useRef(null);

  
  useEffect(() => {
    const checkUnread = () => {
      hasUnreadNotifications()
        .then(setHasUnread)
        .catch(() => setHasUnread(false));
    };

    checkUnread();
    const interval = setInterval(checkUnread, 30000);
    return () => clearInterval(interval);
  }, []);

  
  useEffect(() => {
    function handleClickOutside(e) {
      if (wrapperRef.current && !wrapperRef.current.contains(e.target)) {
        setOpen(false);
      }
    }
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  const toggleOpen = () => {
    const nextOpen = !open;
    setOpen(nextOpen);

    if (nextOpen) {
      setLoading(true);
      getNotifications()
        .then(setNotifications)
        .catch(() => setNotifications([]))
        .finally(() => setLoading(false));
    }
  };

  const handleMarkOne = async (id) => {
    try {
      await markAsRead(id);
      setNotifications((prev) =>
        prev.map((n) => (n.notificationId === id ? { ...n, isRead: true } : n))
      );
      setHasUnread(notifications.some((n) => n.notificationId !== id && !n.isRead));
    } catch (err) {
    
    }
  };

  const handleMarkAll = async () => {
    try {
      await markAllRead();
      setNotifications((prev) => prev.map((n) => ({ ...n, isRead: true })));
      setHasUnread(false);
    } catch (err) {
      
    }
  };

  return (
    <div className="relative" ref={wrapperRef}>
      <button
        onClick={toggleOpen}
        className="relative w-10 h-10 rounded-full flex items-center justify-center hover:bg-gray-100 transition-colors"
        aria-label="Notifikasi"
      >
        <FiBell size={19} className="text-ink" />
        {hasUnread && (
          <span className="absolute top-2 right-2 w-2 h-2 rounded-full bg-red-500" />
        )}
      </button>

      {open && (
        <div className="absolute right-0 mt-2 w-80 bg-white rounded-2xl border border-gray-100 shadow-xl z-50 overflow-hidden">
          <div className="flex items-center justify-between px-4 py-3 border-b border-gray-100">
            <span className="font-display font-bold text-sm text-ink">Notifikasi</span>
            {notifications.some((n) => !n.isRead) && (
              <button
                onClick={handleMarkAll}
                className="font-body text-xs text-blue-600 hover:underline"
              >
                Tandai semua dibaca
              </button>
            )}
          </div>

          <div className="max-h-80 overflow-y-auto">
            {loading && (
              <p className="font-body text-sm text-ink/50 text-center py-6">
                Memuat...
              </p>
            )}

            {!loading && notifications.length === 0 && (
              <p className="font-body text-sm text-ink/50 text-center py-6">
                Belum ada notifikasi.
              </p>
            )}

            {notifications.map((n) => (
              <button
                key={n.notificationId}
                onClick={() => !n.isRead && handleMarkOne(n.notificationId)}
                className={`w-full text-left px-4 py-3 border-b border-gray-50 last:border-0 hover:bg-gray-50 transition-colors ${
                  !n.isRead ? "bg-accent/10" : ""
                }`}
              >
                <div className="flex items-start gap-2">
                  {!n.isRead && (
                    <span className="w-1.5 h-1.5 rounded-full bg-accent mt-1.5 shrink-0" />
                  )}
                  <div className={!n.isRead ? "" : "pl-3.5"}>
                    <p className="font-body text-sm text-ink">{n.message}</p>
                    <p className="font-body text-xs text-ink/40 mt-1">
                      {timeAgo(n.createdAt)}
                    </p>
                  </div>
                </div>
              </button>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}