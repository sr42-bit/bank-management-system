import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080/api",
});

// ✅ INTERCEPTOR
API.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  console.log("🔥 TOKEN:", token); // DEBUG

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default API;