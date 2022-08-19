# GitProject

프로젝트 5조

## 목차

1. 기능
2. [프로젝트 구조](#프로젝트-구조)
3. [데이터베이스](#데이터베이스)
4. [변경점](#변경점)

-

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
    | Field | Type | Null | Key | Default | Extra |
    | board_no | int | NO | PRI | NULL | auto_increment |
    | title | varchar(255) | NO | | NULL | |
    | contents | varchar(255) | YES | | | |
    | post_time | datetime | YES | | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
    | userid | varchar(255) | NO | MUL | NULL | |
    | good | int | NO | | 0 | |
    | bad | int | NO | | 0 | |
    | views | int | NO | | 0 | |

-   board_img
    | Field | Type | Null | Key | Default | Extra |
    | board_img_no | int | NO | PRI | NULL | auto_increment |
    | board_no | int | NO | MUL | NULL | |
    | img_path | varchar(255) | NO | | NULL | |

-   board_prefer
    | Field | Type | Null | Key | Default | Extra |
    | board_prefer_no | int | NO | PRI | NULL | auto_increment |
    | board_no | int | NO | MUL | NULL | |
    | prefer | tinyint(1) | YES | | NULL | |
    | userid | varchar(255) | NO | MUL | NULL | |

-   category
    | Field | Type | Null | Key | Default | Extra |
    | categoryId | int | NO | PRI | NULL | |
    | categoryName | int | NO | MUL | NULL | |

-   notice
    | Field | Type | Null | Key | Default | Extra |
    | notice_no | int | NO | PRI | NULL | auto_increment |
    | title | varchar(255) | NO | | NULL | |
    | contents | varchar(255) | YES | | | |
    | post_time | datetime | YES | | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
    | userid | varchar(255) | NO | MUL | NULL | |
    | good | int | NO | | 0 | |
    | bad | int | NO | | 0 | |
    | views | int | NO | | 0 | |

-   notice_img
    | Field | Type | Null | Key | Default | Extra |
    | notice_img_no | int | NO | PRI | NULL | auto_increment |
    | notice_no | int | NO | MUL | NULL | |
    | img_path | varchar(255) | NO | | NULL | |

-   product
    | Field | Type | Null | Key | Default | Extra |
    | productid | int | NO | PRI | NULL | |
    | productName | varchar(45) | NO | | NULL | |
    | productSize | varchar(45) | NO | | NULL | |
    | productColor | varchar(45) | NO | | NULL | |
    | productGroup | varchar(45) | NO | | NULL | |
    | productInfo | text | YES | | NULL | |
    | productStock | int | YES | | NULL | |
    | productDate | date | YES | | NULL | |
    | price | int | NO | | NULL | |
    | categoryid | int | YES | MUL | NULL | |

-   product_img

-   reply
    | Field | Type | Null | Key | Default | Extra |
    | reply_no | int | NO | PRI | NULL | auto_increment |
    | title | varchar(255) | NO | | NULL | |
    | contents | varchar(255) | YES | | | |
    | post_time | datetime | YES | | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
    | img_path | varchar(255) | YES | | NULL | |
    | good | int | NO | | 0 | |
    | bad | int | NO | | 0 | |
    | board_no | int | NO | MUL | NULL | |
    | userid | varchar(255) | NO | MUL | NULL | |

-   reply_prefer
    | Field | Type | Null | Key | Default | Extra |
    | reply_prefer_no | int | NO | PRI | NULL | auto_increment |
    | reply_no | int | NO | MUL | NULL | |
    | prefer | tinyint(1) | YES | | NULL | |
    | userid | varchar(255) | NO | MUL | NULL | |

-   users
    | Field | Type | Null | Key | Default | Extra |
    | userid | varchar(45) | NO | PRI | NULL | |
    | username | varchar(45) | NO | | NULL | |
    | userpw | varchar(45) | NO | | NULL | |
    | phone | varchar(20) | NO | UNI | NULL | |
    | email | varchar(45) | NO | UNI | NULL | |
    | birth | date | NO | | NULL | |
    | add1 | varchar(100) | NO | | NULL | |
    | add2 | varchar(100) | NO | | NULL | |
    | add3 | varchar(100) | YES | | NULL | |
    | adminCk | int | YES | | 0 | |
    | signup | datetime | YES | | CURRENT_TIMESTAMP | DEFAULT_GENERATED |

## 변경점

-   [2022-08-18 update](#2022-08-18)
<details><summary>## 2022-08-18 update</summary>

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
-   pom.xml에 JS, CSS Dependencies 추가
    -   ex: SpringBoot, Jquery etc...

</details>

-   [2022-08-19 update](#2022-08-19)
<details><summary>## 2022-08-19 update</summary>
-   mapper.xml mapper.java 추가 - 파일명 앞에 tmp\_ 붙임
-   common에 상수 추가
-   interceptor에 로그인 인증 및 권한 추가
-   config에 웹페이지와 인터셉터 연결
-   controller/vo에 VO 객체 생성
    -   [VO와 DTO 차이](https://velog.io/@gillog/Entity-DTO-VO-바로-알기)

</details>
