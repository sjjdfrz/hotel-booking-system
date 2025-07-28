import axios from 'axios';
import { defineStore } from 'pinia';

export const useAuth = defineStore('auth', {
  state: () => ({
    error: null,
    loading: false,
    secretKey: null,
    pendingEmail: null,
    requiresOtp: false,
    mfaQrCodeImage: null,
    token: localStorage.getItem('token') || null,
  }),

  getters: {
    isLoggedIn: (state) => !!state.token,
  },

  actions: {
    async register(userData) {
      try {
        this.error = null;
        this.loading = true;

        const response = await axios.post('http://localhost:8080/auth/register', userData);
        const data = response.data.data;
        this.mfaQrCodeImage = data.mfaQrCodeImage;
        this.secretKey = data.secretKey;
        
        return true;
      } catch (error) {
        const errorData = error.response?.data;
        if (errorData && typeof errorData === 'object') {
          this.error = Object.values(errorData).join(' | ');
        } else {
          this.error = 'خطای ناشناخته‌ای رخ داد';
        }
        return { error: true };
      } finally {
        this.loading = false;
      }
    },

    async completeMfaSetup() {
      try {
        this.loading = true;
        this.error = null;
        this.mfaQrCodeImage = null;
        this.secretKey = null;
        return true;
      } catch (error) {
        this.error = error.message;
        return false;
      } finally {
        this.loading = false;
      }
    },

    async login(credentials) {
      try {
        this.error = null;
        this.loading = true;

        await axios.post('http://localhost:8080/auth/login', credentials);
        this.requiresOtp = true;
        this.pendingEmail = credentials.email;

        return { requiresOtp: true };
      } catch (error) {
        const errorData = error.response?.data;
        if (errorData && typeof errorData === 'object') {
          this.error = Object.values(errorData).join(' | ');
        } else {
          this.error = 'خطای ناشناخته‌ای رخ داد';
        }
        return { error: true };
      } finally {
        this.loading = false;
      }
    },

    async verifyOtp({ code }) {
      try {
        this.loading = true;
        this.error = null;
        const email = this.pendingEmail;

        const response = await axios.post('http://localhost:8080/auth/verify-otp', {
          email,
          code,
        });

        const data = response.data.data;
        this.completeAuthentication(data);
        return true;
      } catch (error) {
        const errorData = error.response?.data;
        if (errorData && typeof errorData === 'object') {
          this.error = Object.values(errorData).join(' | ');
        } else {
          this.error = 'کد صحت سنجی معتبر نیست';
        }
        return false;
      } finally {
        this.loading = false;
      }
    },

    completeAuthentication(authData) {
      this.token = authData.accessToken;
      localStorage.setItem('token', authData.accessToken);
      this.requiresOtp = false;
      this.mfaQrCodeImage = null;
      this.secretKey = null;
      this.pendingEmail = null;
    },

    clearAuth() {
      this.token = null;
      this.requiresOtp = false;
      this.mfaQrCodeImage = null;
      this.secretKey = null;
      this.pendingEmail = null;
      localStorage.removeItem('token');
    },
  },
});
