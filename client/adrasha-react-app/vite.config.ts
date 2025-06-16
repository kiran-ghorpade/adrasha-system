import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import path from 'path'

export default defineConfig({
  base: '/app/',
  plugins: [react()],
  optimizeDeps: {
    include: [
      '@emotion/react',
      '@emotion/styled',
      '@mui/material/Tooltip'
    ],
  },
  resolve: {
    alias: {
      '@core': path.resolve(__dirname, 'src/app/core'),
      '@features': path.resolve(__dirname, 'src/app/features'),
      '@shared': path.resolve(__dirname, 'src/app/shared'),
    },
  },
})
