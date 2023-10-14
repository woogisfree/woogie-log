package com.example.woogisfree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WoogisfreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WoogisfreeApplication.class, args);
	}

}


/**
 * - **과제 1. 사용자 회원가입 엔드포인트**
 *   - 이메일과 비밀번호로 회원가입할 수 있는 엔드포인트를 구현해 주세요.
 *   - 이메일과 비밀번호에 대한 유효성 검사를 구현해 주세요.
 *     - 이메일 조건: **@** 포함
 *     - 비밀번호 조건: 8자 이상
 *     - 비밀번호는 반드시 암호화하여 저장해 주세요.
 *     - 이메일과 비밀번호의 유효성 검사는 위의 조건만으로 진행해 주세요. 추가적인 유효성 검사 조건은 포함하지 마세요.
 * - **과제 2. 사용자 로그인 엔드포인트**
 *   - 사용자가 올바른 이메일과 비밀번호를 제공하면, 사용자 인증을 거친 후에 JWT(JSON Web Token)를 생성하여 사용자에게 반환하도록 해주세요.
 *   - 과제 1과 마찬가지로 회원가입 엔드포인트에 이메일과 비밀번호의 유효성 검사기능을 구현해주세요.
 * - **과제 3. 새로운 게시글을 생성하는 엔드포인트**
 * - **과제 4. 게시글 목록을 조회하는 엔드포인트**
 *   - 반드시 Pagination 기능을 구현해 주세요.
 * - **과제 5. 특정 게시글을 조회하는 엔드포인트**
 *   - 게시글의 ID를 받아 해당 게시글을 조회하는 엔드포인트를 구현해 주세요.
 * - **과제 6. 특정 게시글을 수정하는 엔드포인트**
 *   - 게시글의 ID와 수정 내용을 받아 해당 게시글을 수정하는 엔드포인트를 구현해 주세요.
 *   - 게시글을 수정할 수 있는 사용자는 게시글 작성자만이어야 합니다.
 * - **과제 7. 특정 게시글을 삭제하는 엔드포인트**
 *   - 게시글의 ID를 받아 해당 게시글을 삭제하는 엔드포인트를 구현해 주세요.
 *   - 게시글을 삭제할 수 있는 사용자는 게시글 작성자만이어야 합니다.
 */