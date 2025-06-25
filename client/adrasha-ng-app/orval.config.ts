import { defineConfig } from 'orval';
import { environment } from '@env/environment';

export default defineConfig({
  petstore: {
    output: {
      baseUrl: environment.apiBaseUrl || 'http://localhost:8080/',

      mode: 'tags-split',

      target: 'src/app/core/api/',

      schemas: 'src/app/core/model',

      client: 'angular',

      mock: true,
    },

    input: {
      target: './openAPI/petstore.yaml',
    },
    hooks: {
      afterAllFilesWrite: 'prettier --write',
    },
  },
});
