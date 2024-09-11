const api = axios.create({
    //TODO baseURL 나중에 배포했을 때 환경변수 설정 해줘야함
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