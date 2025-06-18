// src/app/core/api/client.ts
import axios from "axios";

const baseURL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

export const AXIOS_INSTANCE = axios.create({ baseURL });

AXIOS_INSTANCE.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("auth_token"); 
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);