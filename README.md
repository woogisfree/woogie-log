# Woogie's Toy Project

## Feature
- [ ] feature1

## Trouble Shooting
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