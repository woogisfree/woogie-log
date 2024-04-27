const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        fetch(`/api/v1/articles/${id}`, {
            method: 'DELETE'
        })
            .then(() => {
                alert('삭제가 완료되었습니다.');
                location.replace('/articles')
            })
    })
}

const modifyButton = document.getElementById('modify-btn')

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
            event.preventDefault();
            let params = new URLSearchParams(location.search);
            let id = params.get('id');
            let title = document.getElementById('title').value;
            let content = document.getElementById('content').value;

            fetch(`/api/v1/articles/${id}`, {
                method: 'PATCH',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    title: title,
                    content: content,
                })
            }).then(() => {
                alert('수정이 완료되었습니다.')
                location.replace(`/articles/${id}`)
            })
        }
    )
}

const createButton = document.getElementById('create-btn')

if (createButton) {
    createButton.addEventListener('click', event => {
        event.preventDefault();
        let title = document.getElementById('title').value;
        let content = document.getElementById('content').value;

        fetch('/api/v1/articles', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                title: title,
                content: content,
            }),
        }).then(() => {
            alert('게시글이 생성되었습니다.');
            location.replace('/articles');
        })
    })
}

window.addEventListener('DOMContentLoaded', () => {
    const textarea = document.querySelector('textarea');
    textarea.addEventListener('keydown', autoResize, false);

    function autoResize() {
        this.style.height = 'auto';
        this.style.height = this.scrollHeight + 'px';
    }
})

const createCommentButton = document.getElementById('create-comment-btn');

if (createCommentButton) {
    createCommentButton.addEventListener('click', event => {
        event.preventDefault();
        let articleId = document.getElementById('article-id').value;
        let content = document.getElementById('comment-content').value;

        fetch(`/api/v1/comments/${articleId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                content: content,
            }),
        }).then(() => {
            location.replace('/articles/' + articleId);
        })
    })
}

// 댓글 수정, 삭제 버튼 -> js
// 방금 전 https://velog.io/@nanyong0214/JS%EC%8B%9C%EA%B0%84-%EA%B3%84%EC%82%B0-%ED%91%9C%EC%8B%9C
// 댓글 ui 수정
