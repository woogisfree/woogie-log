const createCommentButton = document.getElementById('create-comment-btn');

if (createCommentButton) {
    createCommentButton.addEventListener('click', async event => {
        event.preventDefault();

        try {
            let articleId = document.getElementById('article-id').value;
            let content = document.getElementById('comment-content').value;

            const response = await fetch(`/api/v1/isLoggedIn`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            const isLoggedIn = await response.json();
            if (isLoggedIn) {
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
                await Swal.fire({
                    title: '로그인이 필요한 서비스입니다.',
                    html:
`
        <input type="text" id="username" class="swal2-input" placeholder="Username">
        <input type="password" id="password" class="swal2-input" placeholder="Password">
`,
                    confirmButtonText: 'Login',
                    showCancelButton: true,
                    cancelButtonText: '돌아가기',
                    focusConfirm: false,
                    preConfirm: () => {
                        const username = Swal.getPopup().querySelector('#username').value;
                        const password = Swal.getPopup().querySelector('#password').value;
                        if (!username) {
                            Swal.showValidationMessage(`아이디를 입력해 주세요.`);
                        } else if (!password) {
                            Swal.showValidationMessage(`비밀번호를 입력해 주세요.`);
                        }
                        return { username: username, password: password };
                    }
                }).then(async result => {
                    if (result.isConfirmed) {
                        // Handle the login here with result.value.login and result.value.password
                        const signInResponse = await fetch('/api/v1/sign-in', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({
                                username: result.value.username,
                                password: result.value.password
                            })
                        });

                        if (signInResponse.status === 200) {
                            await Swal.fire({
                                icon: 'success',
                                title: '로그인 성공',
                                showConfirmButton: false,
                                timer: 1500
                            });

                            await fetch(`/api/v1/comments/${articleId}`, {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json',
                                },
                                body: JSON.stringify({
                                    content: content,
                                }),
                            });
                            location.reload();
                        }
                    }
                });
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