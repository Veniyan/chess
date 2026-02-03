import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8081/api',
    headers: {
        'Content-Type': 'application/json',
    }
});

// Add a request interceptor to add the basic auth header if credentials exist
api.interceptors.request.use((config) => {
    try {
        const user = JSON.parse(localStorage.getItem('user'));
        console.log('Axios interceptor - retrieved user:', user);
        if (user && user.authdata) {
            config.headers.Authorization = `Basic ${user.authdata}`;
            console.log('Axios interceptor - added auth header:', config.headers.Authorization);
        } else {
            console.log('Axios interceptor - NO auth data found');
        }
    } catch (e) {
        console.error("Invalid user data in localStorage, clearing it.", e);
        localStorage.removeItem('user');
    }
    console.log('Axios request config:', config.url, config.headers);
    return config;
});

export default api;
