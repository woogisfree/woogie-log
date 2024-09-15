# 목차

- [개요](#개요)
- [기능 상세](#기능-상세)
- [테스트](#테스트)
  - [단위 테스트](#단위-테스트)
  - [통합 테스트](#통합-테스트)
- [문서화](#문서화)
  - [API 문서화](#api-문서화)
  - [사용자 가이드](#사용자-가이드)

## 개요

- 프로젝트 이름: woogie-log
- 기술 스택
  - 백엔드: Spring Boot, Spring Security + JWT
  - 데이터베이스: Spring Data JPA, Querydsl + PostgreSQL
  - 뷰 템플릿: Thymeleaf
  - 테스트 및 문서화: Junit5, Swagger
  - 배포 및 CI/CD: AWS, Github Actions (예정)

## 기능 상세

### 사용자 인증 및 권한 (OAuth2 추가 예정)

- **회원가입 절차**

  1. **비밀번호 일치 여부 확인**: 비밀번호(`password`)와 확인 비밀번호(`confirmPassword`)가 동일한지 확인합니다.
  2. **이메일 중복 확인**: 사용자가 입력한 이메일이 이미 데이터베이스에 존재하는지 확인합니다.
  3. **비밀번호 인코딩**: 비밀번호를 그대로 저장하지 않고, `passwordEncoder`를 이용해 비밀번호를 암호화한 후 저장합니다. 이는 보안 강화를 위한 조치입니다.

- **로그인 절차**

    1. **인증 처리**: 서버는 `username`과 `password`를 사용해 인증을 시도합니다. 이를 위해 `UsernamePasswordAuthenticationToken`을 생성하고, `AuthenticationManager`를 통해
       인증을 수행합니다. 인증이 성공하면, JWT 토큰이 발급됩니다.

  2. **JWT 토큰 발급**: 인증에 성공한 후, 서버는 다음과 같은 두 가지 JWT 토큰을 생성합니다.

      - **Access Token**: 리소스에 접근할 때 사용하는 짧은 유효기간을 가진 토큰.
      - **Refresh Token**: 액세스 토큰이 만료되었을 때 새로운 액세스 토큰을 발급받기 위한 토큰.

  3. **토큰 전송**

      - **Access Token**: HTTP 응답의 `Authorization` 헤더에 `Bearer` 형식으로 포함됩니다.
      - **Refresh Token**: HTTP 쿠키에 `HttpOnly` 속성을 설정하여 전송됩니다. 이 쿠키는 브라우저에서 접근할 수 없으며, 서버에서만 사용할 수 있습니다.

  ```http
  HTTP/1.1 200 OK
  Authorization: Bearer <Access Token>
  Set-Cookie: refreshToken=<Refresh Token>; HttpOnly; Secure; Max-Age=<유효기간>
  ```

  4. **`Redis`에 Refresh Token 저장**: `Refresh Token`은 `Redis`에 `username`을 키로 하여 저장됩니다. 이 토큰은 설정된 유효기간 동안 `Redis`에 저장되어 관리됩니다. 이를 통해 로그아웃 시
     토큰을 무효화하거나 만료된 토큰을 관리할 수 있습니다.

  5. **응답 데이터**: 서버는 로그인 요청에 성공하면 다음과 같은 사용자 정보를 응답으로 반환합니다:

      - `username`: 로그인한 사용자 이름
      - `role`: 사용자의 권한(Role)
      - `expiresIn`: `Access Token`의 남은 유효시간(초 단위), 클라이언트는 이 값을 이용하여 `Access Token`을 자동 갱신할 수 있습니다.

  ```
  {
    "username": "사용자 이름",
    "role": "ROLE_USER",
    "expiresIn": ${jwt.access-token-validity-in-milliseconds}
  }
  ```

    6. 클라이언트에서 `Access Token` 사용: 클라이언트는 받은 Access Token을 HTTP 요청의 Authorization 헤더에 담기 위해 axios 인스턴스를 사용합니다. `axios instance`에
       Authorization
     헤더를 설정하여 API 요청을 보낼 수 있습니다.

- **로그아웃 절차**

    1. **토큰 파싱 및 사용자 정보 추출**: 서버는 `Authorization` 헤더에서 `Access Token`을 추출한 후, “Bearer “ 접두어를 제거하여 실제 JWT 토큰을 파싱합니다. 이후 tokenProvider를 사용하여
       JWT
     토큰에서 사용자 이름(username)을 추출합니다.
    2. **Redis에서 Refresh Token 삭제**: 추출한 username을 키로 하여 Redis에 저장된 해당 사용자의 리프레시 토큰을 삭제합니다. 이를 통해 서버는 로그아웃한 사용자가 리프레시 토큰을 사용해 새로운 액세스 토큰을
       발급받지 못하도록 합니다.
    3. **쿠키에서 Refresh Token 삭제**: 클라이언트 측에서 사용 중인 Refresh Token을 제거하기 위해, 서버는 HttpOnly로 설정된 refreshToken 쿠키를 만료시킵니다. 이를 위해 Max-Age를 0으로
       설정하고,쿠키를 클라이언트로 전달합니다. 이렇게 하면 클라이언트 브라우저에 있는 refreshToken이 삭제됩니다.

  <!--

### 게시글 관리

- 게시글 등록

  - 비동기 처리

    - 이미지나 동영상 업로드 후 썸네일을 생성하거나 외부 API 호출이 필요한 작업을 Kafka를 통해 비동기적으로 처리할 수 있습니다.

    - 사용 예시:  
      사용자가 블로그에 이미지를 업로드하면, 이를 Kafka로 전송하고 백그라운드에서 썸네일을 생성하는 작업을 처리합니다.
      Kafka에 저장된 메시지를 처리하는 소비자는 작업이 완료되면 결과를 데이터베이스에 저장하거나 사용자에게 알림을 보낼 수 있습니다.

- 게시글 조회
  - 게시글의 수가 많아지는 경우 처리
  - 필터링
  - 검색 기능
  - markdown 에디터를 이용해서 markdown preview 제공
- 게시글 수정
- 게시글 삭제

### 마이페이지

- 프로필 이미지 추가
- 댓글 알림 설정
- 회원 탈퇴

- [ ] 게시글 목록 필터링(내 글 모아보기), 검색 기능 추가
- [ ] 회원 권한 추가, velog 글 크롤링 작업
- [ ] 웹소켓 적용

## 테스트

- Repository -> Service -> Controller -> Integration Test
- Jacoco 적용

## 문서화

- Spring Rest docs + Swagger

-->
