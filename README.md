# GitProject

프로젝트 5조

## 목차

1. 기능
2. [프로젝트 구조](#프로젝트-구조)
3. [데이터베이스](#데이터베이스)
4. [변경점](#변경점)

## 프로젝트 구조

-   src.main
    -   java.com.project.mainPage
        -   MainPageApplication
        -   config
        -   controller
        -   dto
        -   mapper
        -   service
    -   resources
        -   mybatis_mapper
        -   static
        -   templates

## 데이터베이스

-   mysql

### 구조

-   board

| Field     | Type         | Null | Key | Default           | Extra             |
| --------- | ------------ | ---- | --- | ----------------- | ----------------- |
| board_no  | int          | NO   | PRI | NULL              | auto_increment    |
| title     | varchar(255) | NO   |     | NULL              |                   |
| contents  | varchar(255) | YES  |     |                   |                   |
| post_time | datetime     | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
| userid    | varchar(255) | NO   | MUL | NULL              |                   |
| good      | int          | NO   |     | 0                 |                   |
| bad       | int          | NO   |     | 0                 |                   |
| views     | int          | NO   |     | 0                 |                   |

-   board_img

| Field        | Type         | Null | Key | Default | Extra          |
| ------------ | ------------ | ---- | --- | ------- | -------------- |
| board_img_no | int          | NO   | PRI | NULL    | auto_increment |
| board_no     | int          | NO   | MUL | NULL    |                |
| img_path     | varchar(255) | NO   |     | NULL    |                |

-   board_prefer

| Field           | Type         | Null | Key | Default | Extra          |
| --------------- | ------------ | ---- | --- | ------- | -------------- |
| board_prefer_no | int          | NO   | PRI | NULL    | auto_increment |
| board_no        | int          | NO   | MUL | NULL    |                |
| prefer          | tinyint(1)   | YES  |     | NULL    |                |
| userid          | varchar(255) | NO   | MUL | NULL    |                |

-   category

| Field        | Type | Null | Key | Default | Extra |
| ------------ | ---- | ---- | --- | ------- | ----- |
| categoryId   | int  | NO   | PRI | NULL    |       |
| categoryName | int  | NO   | MUL | NULL    |       |

-   notice

| Field     | Type         | Null | Key | Default           | Extra             |
| --------- | ------------ | ---- | --- | ----------------- | ----------------- |
| notice_no | int          | NO   | PRI | NULL              | auto_increment    |
| title     | varchar(255) | NO   |     | NULL              |                   |
| contents  | varchar(255) | YES  |     |                   |                   |
| post_time | datetime     | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
| userid    | varchar(255) | NO   | MUL | NULL              |                   |
| good      | int          | NO   |     | 0                 |                   |
| bad       | int          | NO   |     | 0                 |                   |
| views     | int          | NO   |     | 0                 |                   |

-   notice_img

| Field         | Type         | Null | Key | Default | Extra          |
| ------------- | ------------ | ---- | --- | ------- | -------------- |
| notice_img_no | int          | NO   | PRI | NULL    | auto_increment |
| notice_no     | int          | NO   | MUL | NULL    |                |
| img_path      | varchar(255) | NO   |     | NULL    |                |

-   product

| Field        | Type        | Null | Key | Default | Extra |
| ------------ | ----------- | ---- | --- | ------- | ----- |
| productid    | int         | NO   | PRI | NULL    |       |
| productName  | varchar(45) | NO   |     | NULL    |       |
| productSize  | varchar(45) | NO   |     | NULL    |       |
| productColor | varchar(45) | NO   |     | NULL    |       |
| productGroup | varchar(45) | NO   |     | NULL    |       |
| productInfo  | text        | YES  |     | NULL    |       |
| productStock | int         | YES  |     | NULL    |       |
| productDate  | date        | YES  |     | NULL    |       |
| price        | int         | NO   |     | NULL    |       |
| categoryid   | int         | YES  | MUL | NULL    |       |

-   product_img

-   reply

| Field     | Type         | Null | Key | Default           | Extra             |
| --------- | ------------ | ---- | --- | ----------------- | ----------------- |
| reply_no  | int          | NO   | PRI | NULL              | auto_increment    |
| title     | varchar(255) | NO   |     | NULL              |                   |
| contents  | varchar(255) | YES  |     |                   |                   |
| post_time | datetime     | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
| img_path  | varchar(255) | YES  |     | NULL              |                   |
| good      | int          | NO   |     | 0                 |                   |
| bad       | int          | NO   |     | 0                 |                   |
| board_no  | int          | NO   | MUL | NULL              |                   |
| userid    | varchar(255) | NO   | MUL | NULL              |                   |

-   reply_prefer

| Field           | Type         | Null | Key | Default | Extra          |
| --------------- | ------------ | ---- | --- | ------- | -------------- |
| reply_prefer_no | int          | NO   | PRI | NULL    | auto_increment |
| reply_no        | int          | NO   | MUL | NULL    |                |
| prefer          | tinyint(1)   | YES  |     | NULL    |                |
| userid          | varchar(255) | NO   | MUL | NULL    |                |

-   users

| Field    | Type         | Null | Key | Default           | Extra             |
| -------- | ------------ | ---- | --- | ----------------- | ----------------- |
| userid   | varchar(45)  | NO   | PRI | NULL              |                   |
| username | varchar(45)  | NO   |     | NULL              |                   |
| userpw   | varchar(45)  | NO   |     | NULL              |                   |
| phone    | varchar(20)  | NO   | UNI | NULL              |                   |
| email    | varchar(45)  | NO   | UNI | NULL              |                   |
| birth    | date         | NO   |     | NULL              |                   |
| add1     | varchar(100) | NO   |     | NULL              |                   |
| add2     | varchar(100) | NO   |     | NULL              |                   |
| add3     | varchar(100) | YES  |     | NULL              |                   |
| adminCk  | int          | YES  |     | 0                 |                   |
| signup   | datetime     | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |

## 변경점

-   [2022-08-18 update](#2022-08-18)
-   [2022-08-19 update](#2022-08-19)
-   [2022-08-20 update](#2022-08-20)
-   [2022-08-21 update](#2022-08-21)
-   [2022-08-22 update](#2022-08-22)
-   [2022-08-23~24 update](#2022-08-23~24)
-   [2022-08-25 update](#2022-08-25)
-   [2022-08-26 update](#2022-08-26)
-   [2022-08-27~28 update](#2022-08-27~28)

### 2022-08-18 update

<details>

-   resources/templates 폴더 내부에 admin, mall, error 폴더 추가
    -   admin: 쇼핑몰 관리 페이지
    -   mall: 쇼핑몰 웹페이지
    -   error: error 발생했을 경우
        -   [admin 화면 구성요소 참고한 자료](https://adminlte.io/)
-   java/com/project/mainPage/controller 폴더 내부에 admin, common, mall, vo 폴더 추가
    -   admin: 관리자 요청 처리
    -   mall: 쇼핑몰 요청 처리
    -   common: 관리자 페이지, 쇼핑몰 외 공통 기능 요청 처리
    -   vo: Database에서 Data를 얻어 Service나 Controller 등으로 보낼 때 사용하는 객체
-   java/com/project/mainPage 폴더 내부에 common/config/interceptor 폴더 추가
    -   common: 공통으로 사용되는 객체(상수, Form, requset, response 객체 등)
    -   config: 스프링 설정
    -   interceptor: 인터셉터 부분
-   database에 테이블 추가
    -   테이블과 DTO 파일명 앞에 tmp\_ 붙임
    -   adminUser: 관리자 정보(ID, Password 등)
    -   user: 사용자
    -   product: 상품
    -   category: 카테고리
    -   cartItem: 장바구니
    -   order: 주문
    -   orderItem: 주문 상품
    -   stockNum: 재고 수
    -   indexConfig: 홈페이지 설정
-   pom.xml에 JS, CSS Dependencies 추가 - ex: SpringBoot, Jquery etc...

</details>

### 2022-08-19 update

<details>

-   mapper.xml mapper.java 추가 - 파일명 앞에 tmp\_ 붙임
-   common에 상수 추가
-   interceptor에 로그인 인증 및 권한 추가
-   config에 웹페이지와 인터셉터 연결
-   controller/vo에 VO 객체 생성
    -   [VO와 DTO 차이 참고한 자료](https://velog.io/@gillog/Entity-DTO-VO-바로-알기)

</details>

### 2022-08-20 update

<details>

-   controller 추가
-   service 추가
-   Constants 외 상수 추가
-   임시 sql 추가 테이블명 앞에 TB\_붙임
-   mapper.xml 수정

</details>

### 2022-08-21 update

<details>

-   로그인 및 회원가입
    -   [비밀번호 MD5 암호화 적용](https://m.blog.naver.com/ntower/220702935388)
-   [프론트엔드 쇼핑몰 페이지 http://127.0.0.1:8080/mall](http://127.0.0.1:8080/mall)
    -   ※ 미완성 로그인:(ID:user PASSWORD:123456)
-   [백엔드 관리자 페이지 http://127.0.0.1:8080/admin](http://127.0.0.1:8080/admin)
    -   로그인:(ID:admin PASSWORD:123456)
    -   DB관리
        -   카테고리
            -   대분류/중분류/소분류 3단계로 구분
        -   상품
            -   추가/수정 기능 구현할 예정
            -   상품의 분류 카테고리는 소분류(3단계)에 속해있어야함
        -   회원
            -   차단, 해제 기능
        -   주문
            -   프론트(구매 기능)과 연계해서 추가할 예정
        -   관리자 개인정보, 비밀번호 변경

</details>

### 2022-08-22 update

<details>

-   홈페이지 설정
    -   카테고리 구성을 바꾸면 바뀐 카테고리가 메뉴에 출력되도록 수정
    -   카테고리는 3단계 소분류까지 지정해야 출력됨

**오류 발생**

-   /admin 도메인 충돌 발생
-   21일자 백엔드 관리자 페이지를 보려면 이 프로그램을 지우시오

    -   java/com/project/mainPage/InterceptorConnfig.java
    -   java/com/project/mainPage/controller/AdminController.java

-   현재 진행 중인 프로젝트를 보려면 이 프로그램을 지우시오

    -   java/com/project/mainPage/common/config/WebMvcConfig.java
    -   java/com/project/mainPage/common/config/interceptoe/AdminLoginInterceptor.java
    -   java/com/project/mainPage/controller/admin/tmp_AdminController.java

-   프론트엔드 필요한 페이지 몇가지 추가
-   임시적으로 웹페이지 템플릿이 필요해졌기 때문에 외부에서 template 추가
    -   외부 사이트: [TemplateMo 559 Zay Shop](https://templatemo.com/tm-559-zay-shop)

</details>

### 2022-08-23~24 update

<details>

-   프론트엔드 페이지 정리
    -   프론트엔드 url mapping
-   html, css, js 파일명 수정
    -   파일 명명 규칙에 의해 소문자 사용, 통일성을 위해 밑줄(\_) -> 하이픈(-) 로 변경
-   상품 등록 페이지 글편집기(WYSIWYG 에디터) 추가
    -   [CKEditor](https://ckeditor.com/)
-   검색 기능 구현

</details>

### 2022-08-25 update

<details>

**오류 발생**

-   메뉴가 index페이지를 제외한 다른 페이지에서 출력되지않음
-   상품 상세설명 페이지(detail)에서 장바구니로 보낸 후 알림창(swal)이 나타나지 않고 페이지 reload됨

</details>

### 2022-08-26 update

<details>

-   [문제 해결](#2022-08-25)
    -   button의 type를 submit으로 하면 event작동 후 페이지 리로드됨
    -   type을 button으로 수정함
-   기능 추가
    -   장바구니
    -   주문
    -   주문 확인
        -   주문 완료 전 배송지 변경
        -   [kakao 우편번호 서비스](https://postcode.map.daum.net/guide)

</details>

### 2022-08-27~28 update

<details>

-   결제
    -   tmpPay에서 결제되도록 넘김
    -   결제되면 주문정보 페이지로 넘어가도록 했음
-   관리자 페이지에서 포장, 배송 테스트 가능
-   메뉴에서 장바구니에 담은 상품 수량 표시 되는 기능

</details>
