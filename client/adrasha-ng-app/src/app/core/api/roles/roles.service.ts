/**
 * Generated by orval v7.10.0 🍺
 * Do not edit manually.
 * ADRASHA AUTH-SERVICE API Docs
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
  RoleUpdateDTO,
  UserDTO
} from '../../model/authService';



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
export class RolesService {
  constructor(
    private http: HttpClient,
  ) {} addRole<TData = UserDTO>(
    roleUpdateDTO: RoleUpdateDTO, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'body' }
  ): Observable<TData>;
    addRole<TData = UserDTO>(
    roleUpdateDTO: RoleUpdateDTO, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'response' }
  ): Observable<AngularHttpResponse<TData>>;
    addRole<TData = UserDTO>(
    roleUpdateDTO: RoleUpdateDTO, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'events' }
  ): Observable<HttpEvent<TData>>;addRole<TData = UserDTO>(
    roleUpdateDTO: RoleUpdateDTO, options?: HttpClientOptions
  ): Observable<TData>  {
    return this.http.put<TData>(
      `http://localhost:8080/auth/roles`,
      roleUpdateDTO,options
    );
  }
 removeRole<TData = UserDTO>(
    roleUpdateDTO: RoleUpdateDTO, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'body' }
  ): Observable<TData>;
    removeRole<TData = UserDTO>(
    roleUpdateDTO: RoleUpdateDTO, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'response' }
  ): Observable<AngularHttpResponse<TData>>;
    removeRole<TData = UserDTO>(
    roleUpdateDTO: RoleUpdateDTO, options?: Omit<HttpClientOptions, 'observe'> & { observe?: 'events' }
  ): Observable<HttpEvent<TData>>;removeRole<TData = UserDTO>(
    roleUpdateDTO: RoleUpdateDTO, options?: HttpClientOptions
  ): Observable<TData>  {
    return this.http.delete<TData>(
      `http://localhost:8080/auth/roles`,{body:
      roleUpdateDTO, ...options}
    );
  }
};

export type AddRoleClientResult = NonNullable<UserDTO>
export type RemoveRoleClientResult = NonNullable<UserDTO>
