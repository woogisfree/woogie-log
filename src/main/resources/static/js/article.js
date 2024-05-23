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

function timeAgo(dateString) {
    const date = new Date(dateString);
    const now = new Date();
    const diff = (now.getTime() - date.getTime()) / 1000;

    if (diff <= 60) {
        return '방금 전';
    } else if (diff < 3600) {
        return parseInt(diff / 60) + '분 전';
    } else if (diff < 86400) {
        return parseInt(diff / 3600) + '시간 전';
    } else if (diff < 604800) {
        return parseInt(diff / 86400) + '일 전';
    }
    return dateString;
}

document.querySelectorAll('.comment-date').forEach(dateElement => {
    const dateString = dateElement.textContent;
    dateElement.textContent = timeAgo(dateString);
})

const quitButton = document.getElementById('quit-button')
if (quitButton) {
    quitButton.addEventListener('click', event => {
        console.log(document.getElementById('article-id').value);
        event.preventDefault();
        const articleId = document.getElementById('article-id').value;
        if (articleId === '') {
            location.replace('/articles');
            return;
        }
        location.replace(`/articles/${articleId}`);
    })
}
