# README.md

# 📰 NEWNEEK 클론코딩

유튜브 시연영상 링크: 

## 1.  프로젝트 소개 🗒


- 주제 : 뉴스 구독 페이지 [뉴닉](https://newneek.co/) 클론코딩
- 기능
    - **회원가입**
        - 이메일 형식 확인
        - 비밀번호 8자 이상
        - 닉네임 중복 허용
        - 회원가입시 구독 체크하여 회원가입하면 자동으로 구독 여부 설정
    - **구글로그인**
        
        
    - **로그인**
        - JWT방식(access-token만 발급), 성공 시 토큰에 email, nickname 담아 전송
    - **게시글 조회**
        - 전체 조회(Content summary만 보이도록)
        - 상세 (Content 전체 내용 + 좋아요 개수)
        - 카테고리별(Content summary만 보이도록)
    - **키워드 검색**
        - 키워드와 일치하는 내용이 있는 뉴스 목록 전달
    - **게시글 좋아요**
        - 토글 기능
    - **마이페이지**
        - 내가 좋아요한 게시글 확인 가능
        - 닉네임, 비밀번호, 구독 여부 수정 가능
        - 계정 삭제 가능
    - **비회원 구독하기**
        - 메인 화면에서 구독 신청 / 게시글 상세조회 화면에서 구독 신청
        - 이미 DB에 존재하는 이메일로 구독 신청 시 신청불가
        - 받은 닉네임은 이메일에서 부르는 호칭
    - **구독 메일링 서비스**
        - 매일 오전 7시에 구독한 이메일로 뉴스 전송

---
## 2. 제작기간 & 팀원소개 🏃‍🏃‍♀️ 💨



| 날짜 | BE 수행 사항 |
| --- | --- |
| 2022.09.09 | 주제 선정, API 설계, 구현 기능 분배 |
| 2022.09.10 | 담당 기능 구현 |
| 2022.09.11 | 담당 기능 구현 |
| 2022.09.12 | 작업 Merge, 프론트와 회의, 배포 |
| 2022.09.13 | 배포한 서버 기능 오류 해결, 추가 기능 구현 |
| 2022.09.14 | 프론트 CORS설정 후 서버 최종 배포 |
| 2022.09.15 | 프론트 리액트 서버와 연동후 테스트, 최종 미팅, 유튜브 시연 영상 촬영 |

| 이름 | 주특기 | 담당 기능 |
| --- | --- | --- |
| 민지영 | BE | 회원가입, 로그인, 좋아요 |
| 박세린 | BE | 게시글 조회, 키워드 검색, 마이페이지 |
| 김학준 | BE | 구글 로그인, 비회원 구독하기, 메일링 서비스 |
| 전혜림 | FE | 뉴스 상세 페이지, 메인 페이지, 카테고리별 페이지, 좋아요 목록 페이지 |
| 홍준형 | FE | 로그인 페이지, 회원가입 페이지, 마이페이지 |

---
## 3. 기술 스택 🛠


- Language: **`java`**
- Framework: **`SPRING`, `SPRINGBOOT`**
- Build Tool: **`Gradle`**
- DB: **`MySQL`**
- Server: **`AWS`**
- Other Tools : **`Git`, `Github`, `notion`, `slack`**

---
## 4. API 명세서 📃



| 기능 | Method | URL | Request | Response |
| --- | --- | --- | --- | --- |
| 회원가입 |  POST | /api/user/signup | {"email":"dudgh123@naver.com", "password":"abcdefg", ”passwordConfirm”:”abcdefg”,  "nickname":"정영호", ”isSubscribe”:true} | {   success: true,   data: Signup Success,   error: null} |
| 로그인 | POST | /api/user/login | {"email":"dudgh123@naver.com", "password":"abcdefg"} | header : access-token{   success: true,   data: Login Success,   error: null} |
| 구글 로그인 | GET | /api/user/login/google | - | header : access-token{   success: true,   data: Google OAuth Success,   error: null} |
| 게시글 전체 조회 | GET | /api/news | - | {   “success”: true,    "data":     [        {            "newsId": "1",            "category": "정치",            "title": "제목",            "contentSum": "일부 내용 …",            "date": "2022/09/07"        }, ...(여러개)    ],    “error”: null} |
| 게시글 상세 조회 | GET | /api/news/{newsid} | - | {   “success”: true,    "data": {        "newsId" : "1",        "category": "정치",        "title" : "제목",        "content" : "내용",        "date": "2022/09/07"        "likeCnt": "2",    },    “error”: null} |
| 게시글 카테고리별 조회 | GET | /api/news/category/{category} | - | {   “success”: true,    "data":     [        {            "newsId": "1",            "category": "정치",            "title": "제목",            "contentSum": "일부 내용 …",            "date": "2022/09/07"        }, ...(여러개)    ],    “error”: null} |
| 키워드 검색 | GET | /api/news/search/{keyword} | - | {   “success”: true,    "data":     [        {            "newsId": "10",            "category": "경제",            "title": "제목",            "contentSum": "일부 내용 …",            "date": "2022/09/07"        }, ...(여러개)    ],    “error”: null} |
| 좋아요 기능 | POST | /api/auth/news/{newsid} | - | {   success: true,   data: Like Success,   error: null}{   success: true,   data: Like Cancel,   error: null} |
| 좋아요한 게시글 조회 | GET | /api/auth/mypage/like | - | {   “success”: true,    "data":     [        {           "newsId": "1",            "category": "정치",            "title": "제목",            "contentSum": "일부 내용 …",            "date": "2022/09/07"        }, ...(여러개)    ]    “error”: null} |
| 프로필 설정 | GET, PATCH, DELETE | /api/auth/mypage/profile | PATCH: {”nickname”:nickname, ”password”:pass123, ”isSubscribe”:true}    세 요소 중 선택적으로 요청,  DELETE: {”password”:pass123} | {   success: true,   data: Profile Changed,   error: null} |
| 비회원 구독하기1 | POST | /api/main/subscribe | {”nickname”:nickname, ”email”:email@email.com} | {   success: true,   data: Subscribe Success,   error: null} |
| 비회원 구독하기2 | POST | /api/detail/subscribe | {”email”:email@email.com} | {   success: true,   data: Subscribe Success,   error: null}|
---

## 5. ERD 🗂


![image](https://user-images.githubusercontent.com/110282569/190306824-8a2052e4-9ee1-4c52-ad45-18f78fc3a1a7.png)

