# Woogie's Toy

Please. When you build something, check the official document first..

<br>

## Roadmap

- [x] Simple Article Project
    - [ ] Pagination + Sort
    - [x] Swagger
    - [ ] Spring Rest Docs + Swagger
- [x] Add Test Code in Article project
    - [x] Add Test Database (H2)
- [ ] Add Security
    - [x] Spring Security (Session - Form login)
    - [x] JWT
    - [ ] OAuth2
    - [ ] Redis

<br>

## Build what?

### Role

| Article | ADMIN |       USER        | ANONYMOUS |
|:-------:|:-----:|:-----------------:|:---------:|
| Create  |   O   |         O         |     X     |
|  Read   |   O   |         O         |     O     |
| Update  |   O   | Can only one' own |     X     |
| Delete  |   O   | Can only one' own |     X     |
| Swagger |   O   |         X         |     X     |

### Want to Build

- [ ] Anonymous : Only can read all user's articles. But can't do anything.
- [ ] Admin, User :
- [ ] Can Create images and files as well as text.
- [ ] Create a tab from the admin page to Swagger.
- [ ] Enter password twice when registering as a member.

[//]: # (localhost:8080 에 접속하면, 모든 사용자들의 게시글이 다 보임. 그리고 로그인 버튼이 있음.)

[//]: # (로그인을 하지 않은 사용자는 읽기밖에 못함)

[//]: # (로그인을 한 User는 글을 작성할 수 있고, 다른 유저의 글에 좋아요와 댓글을 달 수 있다. 수정은 본인 글만)

[//]: # (ADMIN 은 모든 글에 대한 권한이 있다.)

[//]: # (Admin Page 에는 회원가입한 유저의 수, 해당 유저의 활동 : 글 작성, 좋아요 누른 글, 댓글 단 내용과 댓글단 글 다 볼수있음, Swagger 로 넘어가는 탭도 있음)

<br>

## Trouble Shooting

1. Add Test Database [Reference](https://bepoz-study-diary.tistory.com/371)
2. Spring Security + Redis

<br>

## Bug

- [ ] After adding Spring Security, can't open swagger page even if login
