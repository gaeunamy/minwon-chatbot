# 🏛 AI 민원 안내 챗봇
<br/>

## 📝 작품 소개
공공기관의 민원 정보를 빠르고 정확하게 안내하기 위해 개발된 **AI 기반 챗봇 서비스 및 관리자 시스템**입니다.
형태소 분석기를 통한 키워드 매칭과 OpenAI API를 결합하여 사용자 질문에 응답하며, AI가 임의로 생성한 답변은 관리자의 승인(PENDING -> APPROVED)을 거쳐 정식 FAQ로 등록되는 안전한 데이터 검증 프로세스를 구축했습니다.

<br/>

## 🌁 주요 기능

- **하이브리드 챗봇 엔진 (NLP + 생성형 AI)**
  - `Komoran` 형태소 분석기를 이용해 사용자 질문에서 명사/동사 키워드를 추출 및 유의어 사전 기반 필터링
  - DB 내 기존 FAQ와 일치율이 80% 이상이면 즉시 답변, 미만일 경우 `OpenAI API`를 호출하여 답변 생성

- **관리자 승인 대기열 (Pending System)**
  - AI가 생성한 새로운 답변은 즉시 노출되지 않고 `PENDING` 상태로 DB에 임시 저장
  - 관리자가 내용의 정확성을 검토한 후 승인(`APPROVED`)해야 정식 FAQ로 반영되는 데이터 무결성 보장 로직

- **Spring Security 기반 관리자 페이지**
  - 관리자 전용 페이지(`/admin.html`) 및 FAQ 관리 API에 접근 권한(Role) 제어 적용
  - 직관적인 Tab UI를 통해 승인된 FAQ와 승인 대기 중인 항목을 분리하여 관리

- **API 명세서 자동화 및 전역 예외 처리**
  - `Swagger`를 도입하여 프론트엔드와의 협업을 위한 API 명세서 자동 생성
  - `@RestControllerAdvice`를 활용해 일관된 에러 응답(JSON) 규격(Global Exception Handling) 적용

<br/>

## 📷 화면 구성

<table>
  <tr>
    <td align="center" width="50%">
      <img src="여기에_챗봇_채팅화면_이미지_링크를_넣어주세요" width="100%" />
    </td>
    <td align="center" width="50%">
      <img src="여기에_관리자_대기열_승인_이미지_링크를_넣어주세요" width="100%" />
    </td>
  </tr>
  <tr>
    <td align="center" style="border: none;">
      <p align="center">사용자 채팅 및 AI 응답</p>
    </td>
    <td align="center" style="border: none;">
      <p align="center">관리자 AI 답변 승인 대기열</p>
    </td>
  </tr>
</table>

<br/>

## 🧠 Trouble Shooting
<details>
<summary><strong>1. AI 할루시네이션 방지를 위한 상태 관리 도입</strong></summary>
<div markdown="1">
<br>

- **문제:** AI가 생성한 검증되지 않은 답변이 공공기관 FAQ에 바로 등록될 경우, 잘못된 정보가 확산될 위험성 존재.
- **해결:** `Faq` 엔티티에 `FaqStatus` Enum(APPROVED, PENDING)을 도입. AI의 답변은 무조건 `PENDING`으로 저장하고, 관리자 페이지에서 리뷰 후 `APPROVED`로 변경해야만 정식 데이터로 사용되도록 프로세스를 개선함.
</div>
</details>

<details>
<summary><strong>2. 프론트엔드 협업을 고려한 예외 처리 및 API 문서화</strong></summary>
<div markdown="1">
<br>

- **문제:** 백엔드에서 에러 발생 시 Spring Boot의 기본 HTML 에러 페이지가 반환되어 프론트엔드에서 처리하기 까다로움.
- **해결:** `@RestControllerAdvice`를 사용해 커스텀 예외(`ResourceNotFoundException`) 발생 시 상태 코드와 메시지를 포함한 JSON 규격(`ErrorResponse`)으로 반환하도록 변경. 추가로 `Swagger`를 도입해 API 명세서를 시각화함.
</div>
</details>

<br/>

## 🔧 Stack
- **Backend**: Java 21, Spring Boot 3.x, Spring Data JPA, Spring Security
- **Frontend**: HTML5, CSS3, Vanilla JS
- **Database**: MySQL
- **AI & NLP**: Spring AI (OpenAI API), Komoran (한국어 형태소 분석기)
- **Docs**: Swagger (Springdoc)

<br/>

## 🚀 실행 방법

1. `application.yaml` 파일 내 데이터베이스 및 API 키 설정 (환경변수 세팅 권장)
   ```yaml
   spring:
     datasource:
       password: ${DB_PASSWORD}
     ai:
       openai:
         api-key: ${OPENAI_API_KEY}
   ```
2. 프로젝트 빌드 및 실행 후 `http://localhost:8080` 접속
3. **Swagger API 문서 확인:** `http://localhost:8080/swagger-ui/index.html`

### 💡 데모 계정 안내
평가 및 테스트를 위해 데모 관리자 계정을 제공하고 있습니다. 실제 배포 시에는 환경변수(`ADMIN_PASSWORD`)를 통해 안전하게 관리되도록 설계되었습니다.
- **관리자 로그인 주소:** `http://localhost:8080/login.html`
- **테스트용 ID:** `admin`
- **테스트용 PW:** `admin1234`

<br/>

## 🙋‍♂️ Developer
| Fullstack |               
| :--------: | 
| [김가은](https://github.com/gaeunamy) |
