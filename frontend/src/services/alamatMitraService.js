import api from "./api";

export const alamatMitraService = {
  tambahAlamatMitra: async (mitraId, { labelMitra, jalan, kelurahan, kecamatan, kota, provinsi }) => {
    const response = await api.post(`/alamat/${mitraId}/alamat-mitra`, {
      labelMitra,
      jalan,
      kelurahan,
      kecamatan,
      kota,
      provinsi,
    });
    return response.data;
  },
};