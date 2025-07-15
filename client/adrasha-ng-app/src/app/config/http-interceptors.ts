// src/app/config/http-interceptors.ts
import { loadingInterceptor, loggingInterceptor, tokenInterceptor } from '@core/interceptors';

export const interceptors = [tokenInterceptor, loggingInterceptor, loadingInterceptor];
