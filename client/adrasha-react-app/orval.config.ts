import { defineConfig } from 'orval';

export default defineConfig({
  auth: {
    input: './openapi/auth.json',
    output: {
      target: './src/app/core/api/orval/auth.ts', 
      client: 'react-query',                    
      mode: 'single',                            
      schemas: './src/app/core/api/orval/auth-schemas.ts',
      override: {
        mutator: {
          path: './src/app/core/api/client.ts',   
          name: 'customInstance',                
        },
      },
    },
  },
});
