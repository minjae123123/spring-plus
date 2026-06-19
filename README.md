# 일정 관리 서비스 과제

Spring Boot 기반의 일정 관리 API를 개선하며  
트랜잭션, JWT, JPA, 테스트, AOP, Cascade, N+1, QueryDSL, Spring Security를 학습한 과제입니다.

# 주요 학습 내용

이번 과제를 통해 다음 내용을 학습했습니다.

- @Transactional의 readOnly 옵션과 쓰기 작업의 관계
- JWT에 사용자 정보를 추가하는 방법
- JPA 동적 검색 조건 처리
- Controller 테스트 작성 및 예외 검증
- AOP를 활용한 관리자 요청 로그 기록
- Cascade를 활용한 연관 엔티티 자동 저장
- Fetch Join을 통한 N+1 문제 해결
- QueryDSL을 활용한 타입 안정적인 쿼리 작성
- Spring Security 기반 인증/인가 구조
- REQUIRES_NEW를 활용한 트랜잭션 분리

# 사용 기술
- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT
- QueryDSL
- AOP
- JUnit5
- MockMvc
- MySQL / H2

---

## API 목록

**Auth**

| Method | URL | 설명 |
|--------|-----|------|
| POST | `/auth/signup` | 회원가입 |
| POST | `/auth/signin` | 로그인 |

**User**

| Method | URL | 설명 |
|--------|-----|------|
| GET | `/users/{userId}` | 사용자 조회 |
| PUT | `/users` | 비밀번호 변경 |
| PATCH | `/admin/users/{userId}` | 사용자 권한 변경 |

**Todo**

| Method | URL | 설명 |
|--------|-----|------|
| POST | `/todos` | 일정 생성 |
| GET | `/todos` | 일정 목록 조회 |
| GET | `/todos/{todoId}` | 일정 단건 조회 |
| GET | `/todos/search` | 일정 검색 |

**Comment**

| Method | URL | 설명 |
|--------|-----|------|
| POST | `/todos/{todoId}/comments` | 댓글 등록 |
| GET | `/todos/{todoId}/comments` | 댓글 목록 조회 |

**Manager**

| Method | URL | 설명 |
|--------|-----|------|
| POST | `/todos/{todoId}/managers` | 담당자 등록 |
| GET | `/todos/{todoId}/managers` | 담당자 목록 조회 |
| DELETE | `/todos/{todoId}/managers/{managerId}` | 담당자 삭제 |

---
