// src/app/config/http-interceptors.ts
import {
  errorInterceptor,
  loadingInterceptor,
  loggingInterceptor,
  tokenInterceptor,
} from '@core/interceptors';

export const interceptors = [
  tokenInterceptor,
  errorInterceptor, 
  loggingInterceptor,
  loadingInterceptor,
];
