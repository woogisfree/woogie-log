# Woogie's Toy

Please. When you build something, check the official document first..

<br>

## Concept

### After Siging in.

You can see all user's articles.

- Filtering by **title**.
- Sorting by **Like counts**, **Created at**, **Comments count**.

And you can Create, Read, Update, Delete something. Actually, It's all up to ROLE.

| ⭐Articles | ADMIN |       USER        |
|:---------:|:-----:|:-----------------:|
|  Create   |   O   |         O         |
|   Read    |   O   |         O         |
|  Update   |   O   | Can only one' own |
|  Delete   |   O   | Can only one' own |

| ⭐Comments | ADMIN |       USER        |
|:---------:|:-----:|:-----------------:|
|  Create   |   O   |         O         |
|   Read    |   O   |         O         |
|  Update   |   O   | Can only one' own |
|  Delete   |   O   | Can only one' own |

|    ⭐Likes    | ADMIN | USER |
|:------------:|:-----:|:----:|
|     Read     |   O   |  O   |
| Update(+, -) |   O   |  O   |

Plus, when you leave a comment or click like button, a notification message will appear on the top right.

### Admin Page.

On the Admin Page, you can see the number of users who have registered as members, their activities something like writing, likes, comments.

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
    - [ ] JWT
    - [ ] OAuth2
    - [ ] Redis

[//]: # (kafka, docker-compose)

<br>

## Trouble Shooting

1. Add Test Database [Reference](https://bepoz-study-diary.tistory.com/371)
2. Spring Security + Redis

<br>

## Bug

- [x] After adding Spring Security, can't open swagger page even if login
