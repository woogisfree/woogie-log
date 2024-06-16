const createCommentButton = document.getElementById('create-comment-btn');

if (createCommentButton) {
    createCommentButton.addEventListener('click', async event => {
        event.preventDefault();

        try {
            const response = await fetch(`/api/v1/isLoggedIn`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            const isLoggedIn = await response.json();
            if (isLoggedIn) {
                let articleId = document.getElementById('article-id').value;
                let content = document.getElementById('comment-content').value;

                await fetch(`/api/v1/comments/${articleId}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        content: content,
                    }),
                });
                await location.replace('/articles/' + articleId);
            } else {

            }
        } catch (error) {
            console.log('Error: ', error);
        }
    });
}

window.addEventListener('DOMContentLoaded', () => {
    const modifyCommentButtons = document.querySelectorAll('#modify-comment-btn');

    modifyCommentButtons.forEach(button => {
        button.addEventListener('click', event => {
            const card = event.target.closest('.card');
            const content = card.querySelector('.content');
            const realModifyButton = card.querySelector('#real-modify-comment-btn');
            const cancelButton = card.querySelector('#cancel-comment-btn');

            button.style.display = 'none';
            content.style.display = 'none';

            const textarea = card.querySelector('.edit-comment');
            textarea.style.display = 'block';
            realModifyButton.style.display = 'block';
            cancelButton.style.display = 'block';

            textarea.value = content.textContent;
            textarea.scrollIntoView({behavior: 'smooth'});
        });
    });
});

window.addEventListener('DOMContentLoaded', () => {
    const realModifyButtons = document.querySelectorAll('#real-modify-comment-btn');

    realModifyButtons.forEach(button => {
        button.addEventListener('click', event => {
            const card = event.target.closest('.card');
            const commentId = card.querySelector('.comment-id').value;
            const textarea = card.querySelector('.edit-comment');
            const cancelButton = card.querySelector('#cancel-comment-btn');
            const newContent = textarea.value;

            fetch(`/api/v1/comments/${commentId}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    content: newContent,
                }),
            }).then(() => {
                const content = card.querySelector('.content');
                content.textContent = newContent;

                textarea.style.display = 'none';
                button.style.display = 'none';

                const modifyButton = card.querySelector('#modify-comment-btn');
                modifyButton.style.display = 'inline';
                content.style.display = 'block';
                cancelButton.style.display = 'none';
            })
        });
    });
});

window.addEventListener('DOMContentLoaded', () => {
    const cancelButtonButtons = document.querySelectorAll('#cancel-comment-btn');

    cancelButtonButtons.forEach(button => {
        button.addEventListener('click', event => {
            const card = event.target.closest('.card');
            const content = card.querySelector('.content');
            const realModifyButton = card.querySelector('#real-modify-comment-btn');
            const modifyButton = card.querySelector('#modify-comment-btn');

            const textarea = card.querySelector('.edit-comment');
            textarea.style.display = 'none';
            realModifyButton.style.display = 'none';
            button.style.display = 'none';

            modifyButton.style.display = 'inline';
            content.style.display = 'block';
        });
    });
});

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