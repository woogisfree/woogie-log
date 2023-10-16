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

| Article | ADMIN |       USER        |
|:-------:|:-----:|:-----------------:|
| Create  |   O   |         O         |
|  Read   |   O   |         O         |
| Update  |   O   | Can only one' own |
| Delete  |   O   | Can only one' own |
| Swagger |   O   |         X         |

### Want to Build

- [ ] Login -> Can Read All user's articles.
- [ ] Can Create images and files as well as text. 
- [ ] Create a tab from the admin page to Swagger.
- [ ] Enter password twice when registering as a member.

<br>

## Trouble Shooting

1. Add Test Database [Reference](https://bepoz-study-diary.tistory.com/371)
2. Spring Security + Redis

<br>

## Bug

- [ ] After adding Spring Security, can't open swagger page even if login
