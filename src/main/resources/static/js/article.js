import api from "./api.js";

document.addEventListener('DOMContentLoaded', () => {
    // 공통된 DOM 요소 미리 가져오기
    const deleteButton = document.getElementById('delete-btn');
    const modifyButton = document.getElementById('modify-btn');
    const createButton = document.getElementById('create-btn');
    const quitButton = document.getElementById('quit-button');

    const getArticleId = () => document.getElementById('article-id')?.value;
    const getTitle = () => document.getElementById('title')?.value;
    const getContent = () => document.getElementById('content')?.value;

    // 공통된 에러 핸들링 함수
    const handleError = (error, message) => {
        console.error(message, error);
        alert(message);
    };

    // 삭제 버튼 이벤트 처리
    if (deleteButton) {
        deleteButton.addEventListener('click', event => {
            const id = getArticleId();
            if (id) {
                api.delete(`/api/v1/articles/${id}`)
                    .then(() => {
                        alert('삭제가 완료되었습니다.');
                        location.replace('/articles');
                    })
                    .catch(error => handleError(error, '게시글 삭제 중 오류가 발생했습니다.'));
            }
        });
    }

    // 수정 버튼 이벤트 처리
    if (modifyButton) {
        modifyButton.addEventListener('click', event => {
            event.preventDefault();
            let params = new URLSearchParams(location.search);
            let id = params.get('id');
            let title = getTitle();
            let content = getContent();

            if (id && title && content) {
                api.patch(`/api/v1/articles/${id}`, {title, content})
                    .then(() => {
                        alert('수정이 완료되었습니다.');
                        location.replace(`/articles/${id}`);
                    })
                    .catch(error => handleError(error, '게시글 수정 중 오류가 발생했습니다.'));
            }
        });
    }

    // 생성 버튼 이벤트 처리
    if (createButton) {
        createButton.addEventListener('click', event => {
            event.preventDefault();
            let title = getTitle();
            let content = getContent();

            if (title && content) {
                api.post('/api/v1/articles', {title, content})
                    .then(() => {
                        alert('게시글이 생성되었습니다.');
                        location.replace('/articles');
                    })
                    .catch(error => handleError(error, '게시글 생성 중 오류가 발생했습니다.'));
            }
        });
    }

    // 댓글 날짜 처리 함수
    const timeAgo = (dateString) => {
        const date = new Date(dateString);
        const now = new Date();
        const diff = (now.getTime() - date.getTime()) / 1000;

        if (diff <= 60) return '방금 전';
        if (diff < 3600) return `${parseInt(diff / 60)}분 전`;
        if (diff < 86400) return `${parseInt(diff / 3600)}시간 전`;
        if (diff < 604800) return `${parseInt(diff / 86400)}일 전`;

        return dateString;
    };

    // 댓글 날짜 표시 갱신
    document.querySelectorAll('.comment-date').forEach(dateElement => {
        const dateString = dateElement.textContent;
        dateElement.textContent = timeAgo(dateString);
    });

    // 종료 버튼 이벤트 처리
    if (quitButton) {
        quitButton.addEventListener('click', event => {
            event.preventDefault();
            const articleId = getArticleId();
            if (!articleId) {
                location.replace('/articles');
            } else {
                location.replace(`/articles/${articleId}`);
            }
        });
    }
});