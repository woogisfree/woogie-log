const api = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 1000,
    withCredentials: true,
});

const getCookie = (name) => {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
    return null;
};

api.interceptors.request.use(
    config => {
        const accessToken = getCookie('accessToken');
        if (accessToken) {
            config.headers.Authorization = `Bearer ${accessToken}`;
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
)

export default api;