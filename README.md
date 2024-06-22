# Woogie's Toy Project

## Feature
### 기능
- [x] 로그아웃 구현
- [x] articles CRUD 리팩토링
  - [x] 게시글 생성 버그 해결 (415 Unsupported Media Type)
  - [x] api 단 코드 수정 (Response에 Entity를 직접 반환하는 대신 DTO로 변환하여 반환)
- [x] Article Entity에 createdBy, updatedBy 적용
- [x] article - comment Service refactoring
- [x] HTML, CSS 정리
- [x] article markdown 적용
- [x] Spring Security 적용 위치 변경
- [ ] header fragments로 분리
- [ ] 마이페이지 구현
- [ ] 카카오 로그인 구현
- [ ] 게시글 목록 필터링(내 글 모아보기), 검색 기능 추가
- [ ] 회원 권한 추가, velog 글 크롤링 작업
- [ ] 웹소켓 적용

### 테스트
- [ ] Article - Test Code 작성 (Repository -> Service -> Controller -> Integration Test)
- [x] Test Code 의 실행 순서에 따라 테스트가 실패함 (순서에 의존하지 않도록 수정)
- [ ] Jacoco 적용
- [ ] Comment - Test Code 작성 (Repository -> Service -> Controller -> Integration Test)

### 아키텍처 및 배포
- [ ] AWS 배포
- [ ] CI/CD (Jenkins / Github Actions / Travis CI 중 택 1)

### 문서화
- [ ] Spring Rest docs + Swagger

---

## Trouble Shooting - 추후 게시글로 모두 옮길 예정
- Test Code를 작성할 때 main DB에 영향을 미쳐 의도하지 않은 상황 발생
  - application.yml를 수정하여 test DB를 따로 생성하여 해결
  - Test Class에는 `@ActiveProfiles("test")` 어노테이션을 추가하여 해결

```yaml
spring:
  config:
    activate:
      on-profile: test
  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:mem:testdb
    username: sa
    password:
  h2:
    console:
      enabled: true
```

- 로그인할 때 아이디 또는 비밀번호 불일치시 에러메시지 뜨지 않음
  - Client - Server 간 통신을 한번 더 해서 BindingResult 를 이용하려다가, 네트워크를 한번 더 타는 것 보다는 Client 단에서 해결하는게 나을 것 같다는 판단
  - 처음에는 `display: none` 인 상태로 두고, 서버에서 에러메세지가 오면 `display: block`으로 변경하는 방식으로 구현
```html
<div class="alert alert-danger" style="display: none"></div>
```
```javascript
if (response.status === 401) {
  return response.text().then(message => {
    document.querySelector('.alert-danger').textContent = message;
    document.querySelector('.alert-danger').style.display = 'block';  
})};
```

- 회원가입시 비밀번호와 비밀번호 확인이 불일치하거나, 이메일이 중복일때 RuntimeException 이 터지면서 whitelabel Error Page 표출
  - 이를 해결하기 위해 `GlobalExceptionHandler` 를 만들어 예외처리를 해주었음
  - `org.springframework.web.ErrorResponse` interface 를 사용해보았더니 에러 메시지가 가독성이 너무 떨어져서 ErrorResponse DTO 를 새로 만듬
```java
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordMismatchException(PasswordMismatchException e) {
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage())
                        .build());
    }
}
```
아래는 `org.springframework.web.ErrorResponse` 를 사용했을 때의 Response
```json
{
    "statusCode": "BAD_REQUEST",
    "headers": {},
    "detailMessageCode": "problemDetail.com.example.woogisfree.domain.user.exception.PasswordMismatchException",
    "detailMessageArguments": null,
    "titleMessageCode": "problemDetail.title.com.example.woogisfree.domain.user.exception.PasswordMismatchException",
    "body": {
        "type": "about:blank",
        "title": "Bad Request",
        "status": 400,
        "detail": "Password and confirmation password do not match."
    }
}
```

- 게시글 생성시 createdBy, updatedBy를 적용하려고 하였으나, `org.springframework.data.domain.AuditorAware` 를 구현하지 않아서 에러가 발생
  - `org.springframework.data.domain.AuditorAware` 를 구현하여 `org.springframework.data.annotation.CreatedBy` 와 `org.springframework.data.annotation.LastModifiedBy` 를 사용할 수 있도록 함
  - 참고 - https://docs.spring.io/spring-data/jpa/docs/1.7.0.DATAJPA-580-SNAPSHOT/reference/html/auditing.html
```java
@Configuration
@EnableJpaAuditing
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
}

public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.ofNullable(
            ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                    .getUsername());
  }
}
```

- 실수로 다른 브랜치에서 작업한 걸 커밋만 하고 푸시를 안한 상태에서 병합하고 브랜치를 지움;;
```shell
git reflog 명령어를 사용하여 최근 모든 git 작업 나열

d272f5e (HEAD -> main, origin/main, origin/HEAD) HEAD@{0}: pull: Fast-forward
509a32f HEAD@{1}: checkout: moving from restore-css-branch to main
197d963 (origin/restore-css-branch, restore-css-branch) HEAD@{2}: checkout: moving from main to restore-css-branch
fe8dc91 HEAD@{3}: checkout: moving from articleList-css to main
197d963 (origin/restore-css-branch, restore-css-branch) HEAD@{4}: commit: feat: update 관련 js 수정
7d88eb3 HEAD@{5}: commit: feat: articleList grid 적용
63d09cd HEAD@{6}: checkout: moving from main to articleList-css
fe8dc91 HEAD@{7}: checkout: moving from main to main
fe8dc91 HEAD@{8}: checkout: moving from articleList-css to main

git branch <branch-name> <commit-ID> 명령을 사용하여 새 브랜치를 생성
이때 commit-ID는 삭제된 브랜치에서 마지막으로 작업한 커밋 ID

잘 복구되었는지 확인 후 재병합
```

- repository에서는 dto를 반환하는 것보다 순수한 entity 자체를 반환하는 것이 좋다
  - repository에서 entity를 반환하고, service에서 dto로 변환하여 반환하는 방식으로 변경

- @DataJpaTest 는 기본적으로 트랜잭션 안에서 실행된다. 즉, 테스트 후 DB에 생긴 모든 변화들이 롤백된다.
  - 하지만 트랜잭션을 사용하지 않는 테스트를 작성할 때는 `@Transactional(propagation = Propagation.NOT_SUPPORTED)` 를 사용한다.
```java
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ArticleRepositoryTest {
  // ... 
}

@Test
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public void testMethodWithoutTransactions() {
  // ... 
}
```

- Controller의 Test Code를 작성할 때, 권한이 필요한 경우 `@WithMockUser` 어노테이션을 사용하여 권한을 부여할 수 있다.
- `@WithMockUser`을 사용하여 username, roles 등을 설정할 수 있다. 즉, 가짜 사용자를 만드는 것이다.
- 시행착오 : @WithMockUser 어노테이션을 사용하지 않고, SecurityContextHolder를 사용하여 Principal을 직접 설정하려고 했으나 실패함
```java 
@Test
@WithMockUser(username = "user", roles = "USER")
void addArticle() throws Exception {
    //given
    AddArticleRequest request = new AddArticleRequest("title", "content", 1L);
    ApplicationUser user = new ApplicationUser("firstname", "lastname", "username", "email", "password", UserRole.USER);
    Article article = new Article("title", "content", user);

    when(userService.getUserIdFromUserDetails(any())).thenReturn(1L);
    when(articleService.save(any())).thenReturn(new ArticleResponse(article));

    //then
    mockMvc.perform(post("/api/v1/articles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());
}
```