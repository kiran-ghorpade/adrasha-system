/**
 * Generated by orval v7.10.0 🍺
 * Do not edit manually.
 * ADRASHA MASTERDATA-SERVICE API Docs
 * OpenAPI spec version: 1.0.0
 */
import {
  HttpClient
} from '@angular/common/http';
import type {
  HttpContext,
  HttpEvent,
  HttpHeaders,
  HttpParams,
  HttpResponse as AngularHttpResponse
} from '@angular/common/http';

import {
  Injectable
} from '@angular/core';

import {
  Observable
} from 'rxjs';

import type {
  GetAllNCDParams,
  GetCountParams,
  NCDCreateDTO,
  NCDPageResponseDTO,
  NCDResponseDTO,
  NCDUpdateDTO
} from '../../model/masterdataService';



type HttpClientOptions = {
  headers?: HttpHeaders | {
      [header: string]: string | string[];
  };
  context?: HttpContext;
  observe?: any;
  params?: HttpParams | {
    [param: string]: string | number | boolean | ReadonlyArray<string | number | boolean>;
  };
  reportProgress?: boolean;
  responseType?: any;
  withCredentials?: boolean;
};



@Injectable({ providedIn: 'root' })
export class NcdService {
  constructor(
    private http: HttpClient,
  ) {} getNCD<TData = NCDResponseDTO>(
    id: string, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'body' }
  ): Observable<TData>;
    getNCD<TData = NCDResponseDTO>(
    id: string, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'response' }
  ): Observable<AngularHttpResponse<TData>>;
    getNCD<TData = NCDResponseDTO>(
    id: string, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'events' }
  ): Observable<HttpEvent<TData>>;getNCD<TData = NCDResponseDTO>(
    id: string, options?: HttpClientOptions
  ): Observable<TData>  {
    return this.http.get<TData>(
      `http://localhost:8080/masterdata/ncd/${id}`,options
    );
  }
 updateNCD<TData = NCDResponseDTO>(
    id: string,
    nCDUpdateDTO: NCDUpdateDTO, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'body' }
  ): Observable<TData>;
    updateNCD<TData = NCDResponseDTO>(
    id: string,
    nCDUpdateDTO: NCDUpdateDTO, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'response' }
  ): Observable<AngularHttpResponse<TData>>;
    updateNCD<TData = NCDResponseDTO>(
    id: string,
    nCDUpdateDTO: NCDUpdateDTO, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'events' }
  ): Observable<HttpEvent<TData>>;updateNCD<TData = NCDResponseDTO>(
    id: string,
    nCDUpdateDTO: NCDUpdateDTO, options?: HttpClientOptions
  ): Observable<TData>  {
    return this.http.put<TData>(
      `http://localhost:8080/masterdata/ncd/${id}`,
      nCDUpdateDTO,options
    );
  }
 deleteNCD<TData = void>(
    id: string, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'body' }
  ): Observable<TData>;
    deleteNCD<TData = void>(
    id: string, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'response' }
  ): Observable<AngularHttpResponse<TData>>;
    deleteNCD<TData = void>(
    id: string, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'events' }
  ): Observable<HttpEvent<TData>>;deleteNCD<TData = void>(
    id: string, options?: HttpClientOptions
  ): Observable<TData>  {
    return this.http.delete<TData>(
      `http://localhost:8080/masterdata/ncd/${id}`,options
    );
  }
 getAllNCD<TData = NCDPageResponseDTO>(
    params: GetAllNCDParams, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'body' }
  ): Observable<TData>;
    getAllNCD<TData = NCDPageResponseDTO>(
    params: GetAllNCDParams, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'response' }
  ): Observable<AngularHttpResponse<TData>>;
    getAllNCD<TData = NCDPageResponseDTO>(
    params: GetAllNCDParams, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'events' }
  ): Observable<HttpEvent<TData>>;getAllNCD<TData = NCDPageResponseDTO>(
    params: GetAllNCDParams, options?: HttpClientOptions
  ): Observable<TData>  {
    return this.http.get<TData>(
      `http://localhost:8080/masterdata/ncd`,{
    ...options,
        ...params, ...options?.params,}
    );
  }
 createNCD<TData = NCDResponseDTO>(
    nCDCreateDTO: NCDCreateDTO, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'body' }
  ): Observable<TData>;
    createNCD<TData = NCDResponseDTO>(
    nCDCreateDTO: NCDCreateDTO, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'response' }
  ): Observable<AngularHttpResponse<TData>>;
    createNCD<TData = NCDResponseDTO>(
    nCDCreateDTO: NCDCreateDTO, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'events' }
  ): Observable<HttpEvent<TData>>;createNCD<TData = NCDResponseDTO>(
    nCDCreateDTO: NCDCreateDTO, options?: HttpClientOptions
  ): Observable<TData>  {
    return this.http.post<TData>(
      `http://localhost:8080/masterdata/ncd`,
      nCDCreateDTO,options
    );
  }
 getCount<TData = string>(
    params: GetCountParams, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'body' }
  ): Observable<TData>;
    getCount<TData = string>(
    params: GetCountParams, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'response' }
  ): Observable<AngularHttpResponse<TData>>;
    getCount<TData = string>(
    params: GetCountParams, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'events' }
  ): Observable<HttpEvent<TData>>;getCount<TData = string>(
    params: GetCountParams, options?: HttpClientOptions
  ): Observable<TData>  {
    return this.http.get<TData>(
      `http://localhost:8080/masterdata/ncd/count`,{
    ...options,
        ...params, ...options?.params,}
    );
  }
};

export type GetNCDClientResult = NonNullable<NCDResponseDTO>
export type UpdateNCDClientResult = NonNullable<NCDResponseDTO>
export type DeleteNCDClientResult = NonNullable<void>
export type GetAllNCDClientResult = NonNullable<NCDPageResponseDTO>
export type CreateNCDClientResult = NonNullable<NCDResponseDTO>
export type GetCountClientResult = NonNullable<string>
