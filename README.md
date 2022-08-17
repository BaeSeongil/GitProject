# GitProject

프로젝트 5조

## 목차

1. 기능
2. [프로젝트 구조](#프로젝트-구조)
3. [데이터베이스](#데이터베이스)
4. [변경점](#변경점)

-

## 프로젝트 구조

- src.main
  - java.com.project.mainPage
    - MainPageApplication
    - config
    - controller
    - dto
    - mapper
    - service
  - resources
    - mybatis_mapper
    - static
    - templates

## 데이터베이스

- mysql

### 구조

- board
  | Field | Type | Null | Key | Default | Extra |
  | board_no | int | NO | PRI | NULL | auto_increment |
  | title | varchar(255) | NO | | NULL | |
  | contents | varchar(255) | YES | | | |
  | post_time | datetime | YES | | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
  | userid | varchar(255) | NO | MUL | NULL | |
  | good | int | NO | | 0 | |
  | bad | int | NO | | 0 | |
  | views | int | NO | | 0 | |

- board_img
  | Field | Type | Null | Key | Default | Extra |
  | board_img_no | int | NO | PRI | NULL | auto_increment |
  | board_no | int | NO | MUL | NULL | |
  | img_path | varchar(255) | NO | | NULL | |

- board_prefer
  | Field | Type | Null | Key | Default | Extra |
  | board_prefer_no | int | NO | PRI | NULL | auto_increment |
  | board_no | int | NO | MUL | NULL | |
  | prefer | tinyint(1) | YES | | NULL | |
  | userid | varchar(255) | NO | MUL | NULL | |

- category
  | Field | Type | Null | Key | Default | Extra |
  | categoryId | int | NO | PRI | NULL | |
  | categoryName | int | NO | MUL | NULL | |

- notice
  | Field | Type | Null | Key | Default | Extra |
  | notice_no | int | NO | PRI | NULL | auto_increment |
  | title | varchar(255) | NO | | NULL | |
  | contents | varchar(255) | YES | | | |
  | post_time | datetime | YES | | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
  | userid | varchar(255) | NO | MUL | NULL | |
  | good | int | NO | | 0 | |
  | bad | int | NO | | 0 | |
  | views | int | NO | | 0 | |

- notice_img
  | Field | Type | Null | Key | Default | Extra |
  | notice_img_no | int | NO | PRI | NULL | auto_increment |
  | notice_no | int | NO | MUL | NULL | |
  | img_path | varchar(255) | NO | | NULL | |

- product
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

- product_img

- reply
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

- reply_prefer
  | Field | Type | Null | Key | Default | Extra |
  | reply_prefer_no | int | NO | PRI | NULL | auto_increment |
  | reply_no | int | NO | MUL | NULL | |
  | prefer | tinyint(1) | YES | | NULL | |
  | userid | varchar(255) | NO | MUL | NULL | |

- users
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
