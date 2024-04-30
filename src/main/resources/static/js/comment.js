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

window.addEventListener('DOMContentLoaded', () => {
    const deleteCommentButtons = document.querySelectorAll('#delete-comment-btn');

    deleteCommentButtons.forEach(button => {
        button.addEventListener('click', event => {
            const card = event.target.closest('.card');
            const commentId = card.querySelector('.comment-id').value;

            fetch(`/api/v1/comments/${commentId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
            }).then(() => {
                card.remove();
            })
        });
    });
});