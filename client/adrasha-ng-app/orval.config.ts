import { defineConfig } from 'orval';
import { environment } from './src/environments/environment';

const baseApiUrl = environment.apiBaseUrl || 'http://localhost:8080';

export default defineConfig({
  'auth-service': {
    input: {
      target: './openAPI/auth-service.json',
    },
    output: {
      target: 'src/app/core/api/auth-service',
      schemas:  'src/app/core/model/auth-service',
      client: 'angular',
      mock: true,
      mode: 'tags-split',
      baseUrl: baseApiUrl,
    },
  },
  'user-service': {
    input: {
      target: './openAPI/user-service.json',
    },
    output: {
      target: 'src/app/core/api/user-service',
      schemas: 'src/app/core/model/user-service',
      client: 'angular',
      mock: true,
      mode: 'tags-split',
      baseUrl: baseApiUrl,
    },
  },
  'data-service': {
    input: {
      target: './openAPI/data-service.json',
    },
    output: {
      target: 'src/app/core/api/data-service',
      schemas: 'src/app/core/model/data-service',
      client: 'angular',
      mock: true,
      mode: 'tags-split',
      baseUrl: baseApiUrl,
    },
  },
  'masterdata-service': {
    input: {
      target: './openAPI/masterdata-service.json',
    },
    output: {
      target: 'src/app/core/api/masterdata-service',
      schemas: 'src/app/core/model/masterdata-service',
      client: 'angular',
      mock: true,
      mode: 'tags-split',
      baseUrl: baseApiUrl,
    },
  },
  'analytics-service': {
    input: {
      target: './openAPI/analytics-service.json',
    },
    output: {
      target: 'src/app/core/api/analytics-service',
      schemas: 'src/app/core/model/analytics-service',
      client: 'angular',
      mock: true,
      mode: 'tags-split',
      baseUrl: baseApiUrl,
    },
  },
});
