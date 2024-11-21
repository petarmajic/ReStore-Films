import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      // Proxy API requests to your backend API
      '/api': 'https://restore-films-backend.onrender.com', // Your backend server address
    },
  },
});
