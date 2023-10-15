# Woogie's Toy

## Roadmap

- [x] Simple Board(Article) Project
    - [ ] Pagination
    - [x] Swagger
    - [ ] Spring Rest Docs + Swagger
- [x] Add Test Code in Board project
    - [x] Add Test Database (H2)
- [ ] Add Security
    - [ ] Spring Security (Session - Form login)
    - [ ] JWT
    - [ ] OAuth2
    - [ ] Redis

## Build what?

### Role

|        | ADMIN |       USER        |
|:------:|:-----:|:-----------------:|
| Create |   O   |         O         |
|  Read  |   O   |         O         |
| Update |   O   | Can only one' own |
| Delete |   O   | Can only one' own |

Login -> Can Read All user's articles. <br>
Can Create images and files as well as text.

## Trouble Shooting
1. Add Test Database [Reference](https://bepoz-study-diary.tistory.com/371)
2. Spring Security + Redis

## Bug
- [ ] After adding Spring Security, can't open swagger page even if login 