import api from "./api.js";

function logout() {
    api.post('/sign-out', {
        headers: {
            'Content-Type': 'application/json',
        },
    }).then(response => {
        if (response.status === 200) {
            document.cookie = 'accessToken=; Path=/; Max-Age=0;';
            window.location.reload();
        } else {
            alert('로그아웃에 실패했습니다.');
        }
    }).catch(error => {
        console.error('로그아웃 중 오류 발생:', error);
        alert('로그아웃 중 오류가 발생했습니다.');
    });
}

window.logout = logout;