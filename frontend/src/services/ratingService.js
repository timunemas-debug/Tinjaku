import api from "./api";

export const ratingService = {
  beriRating: async (pesananId, { rating, deskripsi }) => {
    const response = await api.post(`/pesanan/${pesananId}/rating`, {
      rating,
      deskripsi,
    });
    return response.data;
  },

  getRatingByMitra: async (mitraId) => {
    const response = await api.get(`/mitra/${mitraId}/ratings`);
    return response.data;
  },

  getAverageRating: async (mitraId) => {
    const response = await api.get(`/mitra/${mitraId}/avg-ratings`);
    return response.data;
  },

  hapusRating: async (ratingId) => {
    await api.delete(`/mitra/${ratingId}/hapus-rating`);
  },
};