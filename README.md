## Newspeed 프로젝트

<br>

### 프로젝트 소개
- 한 줄 정리 : SNS RESTful API
- 뉴스피드 : 친구들의 가장 최근에 업데이트된 게시물들을 볼 수 있는 페이지
- 로그인하여 JWT을 발급받아 쿠키에 저장하여 통하여 인증된 사용자에게만 접근을 허용하였습니다.
- Github의 issue, projects 를 이용하여 역할분담 및 진행 사항, 코드 리뷰를 진행하였습니다.

<br>

### 프로젝트 조건
- DB 스키마 설계, JPA를 통한 연동하기
- GIT을 사용해 소스 코드 버전을 관리하기
- 협업하여 Pull Request, 코드 리뷰를 경험하기
- 와이어프레임, ERD, API 명세서 작성하여 프로젝트 설계하기

<br>

### 프로젝트 실행법
1. application.properties에서 mysql 설정에 맞게 아이디 비밀번호 설정
2. schedule_app DB를 생성 : `CREATE DATABASE newspeed;`
3. postman으로 api명세서의 요청 형식에 맞춰서 요청
4. 인증 필요 시 회원가입하여 로그인 후 쿠키로 응답받아 저장사용

### 프로젝트 설계
  - #### ERD 작성
    !![ERD.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/f5d14843-36a2-46ef-81cf-ed32606a13ea/ERD.png)

  - #### API 명세서
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/e784e06d-d669-4721-a16a-5890095d0dcd/image.png)

  - #### 와이어 프레임
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/5d1d9e99-bba8-4a0c-b5b0-30db889db88e/image.png)

### 구현된 기능
  - #### 일정 CRUD
  - #### 댓글 CRUD
  - #### 유저 CRUD
    - JWT을 활용한 로그인 인증, 필터 사용
    - 비밀번호는 DB에 인코딩하여 저장
  - #### 친구 기능
  - #### 예외처리
  - #### 좋아요 기능
