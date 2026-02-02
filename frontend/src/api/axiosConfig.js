import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json',
    }
});

// Add a request interceptor to add the basic auth header if credentials exist
api.interceptors.request.use((config) => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user && user.authdata) {
        config.headers.Authorization = `Basic ${user.authdata}`;
    }
    return config;
});

export default api;
