import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080"
});

export const productService = {
  getAll: async () => {
    const response = await api.get("/products");
    return response.data;
  },

  create: async (data) => {
    await api.post("/products", data);
  },

  delete: async (id) => {
    await api.delete(`/products/${id}`);
  }
};

export const rawMaterialService = {
  getAll: async () => {
    const response = await api.get("/raw-materials");
    return response.data;
  },

  create: async (data) => {
    await api.post("/raw-materials", data);
  },

  delete: async (id) => {
    await api.delete(`/raw-materials/${id}`);
  }
};

export const productionService = {
  calculate: async () => {
    const response = await api.get("/production");
    return response.data;
  }
};

export default api;
