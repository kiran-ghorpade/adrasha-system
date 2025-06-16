// src/app/core/api/client.ts

const baseURL = process.env.VITE_API_BASE_URL || 'http://localhost:8080';

import axios, { AxiosRequestConfig } from 'axios';

export const customInstance = async <T>(
  config: AxiosRequestConfig
): Promise<T> => {
  const axiosInstance = axios.create({
    baseURL,
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token') || ''}`,
    },
  });

  const response = await axiosInstance.request<T>(config);
  return response.data;
};
