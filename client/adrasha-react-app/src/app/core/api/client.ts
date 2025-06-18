// src/app/core/api/client.ts
import axios, { AxiosError, AxiosRequestConfig } from "axios";
import { AXIOS_INSTANCE } from "./interceptor";

export const customInstance = async <T>(
  config: AxiosRequestConfig
): Promise<T> => {
  const source = axios.CancelToken.source();

  const promise = AXIOS_INSTANCE({
    ...config,
    cancelToken: source.token,
  }).then(({ data }) => data);

  // // Optional: attach cancel function
  // (promise as any).cancel = () => {
  //   source.cancel("Request canceled by user.");
  // };

  return promise;
};

export type ErrorType<Error> = AxiosError<Error>;

export default customInstance;
