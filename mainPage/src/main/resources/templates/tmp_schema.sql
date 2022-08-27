SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for TB_ADMIN_USER
-- ----------------------------
DROP TABLE IF EXISTS `TB_ADMIN_USER`;
CREATE TABLE `TB_ADMIN_USER`  (
  `admin_user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '관리자 인덱스',
  `login_user_name` varchar(50) COMMENT '관리자 ID',
  `login_password` varchar(50) COMMENT '관리자 Password',
  `nick_name` varchar(50) COMMENT '관리자 닉네임',
  `locked` tinyint(4) NULL DEFAULT 0 COMMENT '차단설정 0:N 1:Y',
  PRIMARY KEY (`admin_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of TB_ADMIN_USER
-- ID: admin, Password: 123456
-- ----------------------------
INSERT INTO `TB_ADMIN_USER` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin', 0);

-- ----------------------------
-- Table structure for TB_PRODUCT_CATEGORY
-- ----------------------------
DROP TABLE IF EXISTS `TB_PRODUCT_CATEGORY`;
CREATE TABLE `TB_PRODUCT_CATEGORY`  (
  `category_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '카테고리 인덱스',
  `category_level` tinyint(4) NOT NULL DEFAULT 0 COMMENT '1: 대분류, 2: 중분류, 3: 소분류',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '상위 카테고리 id',
  `category_name` varchar(50) DEFAULT '' COMMENT '카테고리 명',
  `category_rank` int(11) NOT NULL DEFAULT 0 COMMENT '정렬값(값이 클수록 앞에 위치)',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '인덱스 필드 삭제 (0: 삭제되지 않음 1: 삭제됨)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성날짜',
  `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '데이터를 생성한 유저',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경날짜',
  `update_user` int(11) NULL DEFAULT 0 COMMENT '데이터를 업데이트한 유저',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 65 ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of TB_PRODUCT_CATEGORY
-- ----------------------------
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (1, 1, 0, '상의', 505, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (2, 1, 0, '하의', 504, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (3, 1, 0, '아우터', 503, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (4, 1, 0, '신발', 502, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (5, 1, 0, '모자', 501, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);

INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (6, 2, 1, '티셔츠', 383, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (7, 2, 1, '니트ㆍ가디건', 382, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (8, 2, 1, '셔츠ㆍ블라우스', 381, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (9, 2, 1, '후드ㆍ집업ㆍ맨투맨', 380, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);

INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (10, 2, 2, '팬츠', 361, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (11, 2, 2, '스커트', 360, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);

INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (12, 2, 3, '바람막이ㆍ트랙자켓', 345, 1, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (13, 2, 3, '플리스', 344, 1, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (14, 2, 3, '패딩', 343, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (15, 2, 3, '점퍼', 342, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (16, 2, 3, '자켓', 341, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (17, 2, 3, '코트', 340, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);

INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (18, 2, 4, '스니커즈', 323, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (19, 2, 4, '샌들ㆍ슬리퍼', 322, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (20, 2, 4, '구두', 321, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (21, 2, 4, '부츠', 320, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);

INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (22, 2, 5, '야구모자ㆍ캡', 303, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (23, 2, 5, '베레모', 302, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (24, 2, 5, '버킷햇', 301, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (25, 2, 5, '비니', 300, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);

INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (26, 3, 6, '반소매티셔츠', 203, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (27, 3, 6, '긴소매티셔츠', 202, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (28, 3, 6, '슬리브티셔츠', 201, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (29, 3, 6, '터틀넥티셔츠', 200, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);

INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (30, 3, 7, '가디건', 194, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (31, 3, 7, '라운드넥니트', 193, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (32, 3, 7, '브이넥니트', 192, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (33, 3, 7, '터틀넥니트', 191, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (34, 3, 7, '베스트', 190, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);

INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (35, 3, 8, '화이트셔츠', 185, 0, '1822-08-01 12:00:00', 0, '1822-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (36, 3, 8, '컬러셔츠', 184, 0, '1822-08-01 12:00:00', 0, '1822-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (37, 3, 8, '체크ㆍ패턴셔츠', 183, 0, '1822-08-01 12:00:00', 0, '1822-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (38, 3, 8, '데님셔츠', 182, 0, '1822-08-01 12:00:00', 0, '1822-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (39, 3, 8, '카라블라우스', 181, 0, '1822-08-01 12:00:00', 0, '1822-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (40, 3, 8, '노카라블라우스', 180, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);

INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (41, 3, 9, '맨투맨', 172, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (42, 3, 9, '후드', 171, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (43, 3, 9, '후드집업', 170, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);

INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (44, 3, 10, '트레이닝팬츠', 164, 0, '1622-08-01 12:00:00', 0, '1622-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (45, 3, 10, '데님팬츠', 163, 0, '1622-08-01 12:00:00', 0, '1622-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (46, 3, 10, '숏ㆍ하프팬츠', 162, 0, '1622-08-01 12:00:00', 0, '1622-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (47, 3, 10, '코튼팬츠', 161, 0, '1622-08-01 12:00:00', 0, '1622-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (48, 3, 10, '슬랙스', 160, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);

INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (49, 3, 11, '미니스커트', 151, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (50, 3, 11, '미들ㆍ롱스커트', 150, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);

INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (51, 3, 12, '바람막이', 141, 1, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (52, 3, 12, '트랙자켓', 140, 1, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);

INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (53, 3, 14, '숏패딩', 121, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (54, 3, 14, '롱패딩', 120, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);

INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (55, 3, 15, '바람막이', 112, 0, '1622-08-01 12:00:00', 0, '1622-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (56, 3, 15, '사파리ㆍ야상', 111, 0, '1622-08-01 12:00:00', 0, '1622-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (57, 3, 15, '캐주얼점퍼', 110, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);

INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (58, 3, 16, '테일러드자켓', 103, 0, '1622-08-01 12:00:00', 0, '1622-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (59, 3, 16, '캐주얼자켓', 102, 0, '1622-08-01 12:00:00', 0, '1622-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (60, 3, 16, '데님자켓', 101, 0, '1622-08-01 12:00:00', 0, '1622-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (61, 3, 16, '가죽ㆍ라이더자켓', 100, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);

INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (62, 3, 17, '트렌치코트', 92, 0, '1722-08-01 12:00:00', 0, '1722-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (63, 3, 17, '롱코트', 91, 0, '1722-08-01 12:00:00', 0, '1722-08-01 12:00:00', 0);
INSERT INTO `TB_PRODUCT_CATEGORY` VALUES (64, 3, 17, '숏ㆍ하프코트', 90, 0, '2022-08-01 12:00:00', 0, '2022-08-01 12:00:00', 0);

-- ----------------------------
-- Table structure for TB_PRODUCT_INFO
-- ----------------------------
DROP TABLE IF EXISTS `TB_PRODUCT_INFO`;
CREATE TABLE `TB_PRODUCT_INFO`  (
  `product_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '상품 인덱스',
  `product_name` varchar(200) DEFAULT '' COMMENT '상품명',
  `product_intro` varchar(200) DEFAULT '' COMMENT '상품정보',
  `product_category_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '카테고리 인덱스',
  `product_cover_img` varchar(200) DEFAULT '/admin/dist/img/no-img.png' COMMENT '상품 대표 이미지',
  `product_detail_content` text COMMENT '상품 세부 정보',
  `original_price` int(11) NOT NULL DEFAULT 1 COMMENT '할인 전 가격',
  `selling_price` int(11) NOT NULL DEFAULT 1 COMMENT '현재 판매가(할인이 적용되거나 아니거나)',
  `stock_num` int(11) NOT NULL DEFAULT 0 COMMENT '재고수량',
  `tag` varchar(20) DEFAULT '' COMMENT '상품라벨',
  `product_sell_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '진열상태 0:N 1:Y',
  `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '생성유저',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
  `update_user` int(11) NOT NULL DEFAULT 0 COMMENT '업데이트유저',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트일',
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of TB_PRODUCT_CATEGORY
-- ----------------------------
INSERT INTO `TB_PRODUCT_INFO`(`product_id`,`product_name`,`product_intro`,`product_category_id`,`product_cover_img`,`product_detail_content`,`original_price`,`selling_price`,`stock_num`,`tag`,`product_sell_status`,`create_user`,`create_time`,`update_user`,`update_time`) VALUES (1,'테스트 상품','test 상품입니다',64,'/img/knit.jpg','<div id=\"activity_header\" style=\"margin:0px;padding:0px;color:#666666;font-family:tahoma, arial, \" background-color:#ffffff;\"=\"\">\n<div style=\"margin:0px;padding:0px;text-align:center;\">\n	<br />\n</div>\n	</div>\n<div id=\"J-detail-content\" style=\"margin:0px;padding:0px;color:#666666;font-family:tahoma, arial, \" background-color:#ffffff;\"=\"\">\n	<div style=\"margin:0px auto;padding:0px;\">\n		<img class=\"\" src=\"/img/dress.jpg\" /><img border=\"0\" class=\"\" src=\"/img/knit.jpg\" />\n	</div>\n</div>',10000,10,1000,'',0,0,'2022-08-21 12:26:35',0,'2022-08-21 12:26:35');

	
-- ----------------------------
-- Table structure for TB_INDEX_CONFIG
-- ----------------------------
DROP TABLE IF EXISTS `TB_INDEX_CONFIG`;
CREATE TABLE `TB_INDEX_CONFIG`  (
  `config_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '홈페이지 설정 인덱스',
  `config_name` varchar(50) DEFAULT '' COMMENT '보여줄 문자(검색 설정 시 null일 수 없음)',
  `config_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '인덱스 타입(1: 검색바 2: 검색 드롭다운 3: 히트상품 4: 신상품 5: 추천상품)',
  `product_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '상품 인덱스 기본값은 0',
  `redirect_url` varchar(100) DEFAULT '##' COMMENT '리디렉션 주소(기본적으로 리디렉션 하지 않음)',
  `config_rank` int(11) NOT NULL DEFAULT 0 COMMENT '정렬값(값이 클수록 앞에 위치)',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT 'ID 필드 삭제 (0: 삭제되지 않음 1: 삭제됨)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성날짜',
  `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '데이터를 생성한 유저',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경날짜',
  `update_user` int(11) NULL DEFAULT 0 COMMENT '데이터를 업데이트한 유저',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of TB_INDEX_CONFIG
-- ----------------------------

-- ----------------------------
-- Table structure for TB_ORDER
-- ----------------------------
DROP TABLE IF EXISTS `TB_ORDER`;
CREATE TABLE `TB_ORDER`  (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '주문 인덱스',
  `order_no` varchar(20) DEFAULT '' COMMENT '주문번호',
  `user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '사용자 인덱스',
  `total_price` int(11) NOT NULL DEFAULT 1 COMMENT '총 구매가',
  `pay_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '결제 상태(0: 미지급, 1: 지불 성공, -1: 지불 실패)',
  `pay_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0: 없음',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '결제 시간',
  `order_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '주문상태(0: 미입금 1: 결제완료 2: 포장완료 3: 배송완료 4: 수령완료 -1: 수동취소 -2: 시간초과 -3: 판매종료',
  `extra_info` varchar(100) DEFAULT '' COMMENT '주문정보',
  `user_name` varchar(30) DEFAULT '' COMMENT '사용자 이름',
  `user_phone` varchar(11) DEFAULT '' COMMENT '사용자 전화번호',
  `user_address` varchar(100) DEFAULT '' COMMENT '배송지',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT 'ID 필드 삭제 (0: 삭제되지 않음 1: 삭제됨)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성날짜',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경날짜',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of TB_ORDER
-- ----------------------------

-- ----------------------------
-- Table structure for TB_ORDER_ITEM
-- ----------------------------
DROP TABLE IF EXISTS `TB_ORDER_ITEM`;
CREATE TABLE `TB_ORDER_ITEM`  (
  `order_item_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '주문관련 상품항목 인덱스',
  `order_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '주문 인덱스',
  `product_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '상품 인덱스',
  `product_name` varchar(200) DEFAULT '' COMMENT '상품명',
  `product_cover_img` varchar(200) DEFAULT '' COMMENT '상품 대표 이미지',
  `selling_price` int(11) NOT NULL DEFAULT 1 COMMENT '상품 판매가',
  `product_count` int(11) NOT NULL DEFAULT 1 COMMENT '상품 개수',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성날짜',
  PRIMARY KEY (`order_item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of TB_ORDER_ITEM
-- ----------------------------

-- ----------------------------
-- Table structure for TB_CART_ITEM
-- ----------------------------
DROP TABLE IF EXISTS `TB_CART_ITEM`;
CREATE TABLE `TB_CART_ITEM`  (
  `cart_item_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '장바구니 인덱스',
  `user_id` bigint(20) NOT NULL COMMENT '사용자 인덱스',
  `product_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '상품 인덱스',
  `product_count` int(11) NOT NULL DEFAULT 1 COMMENT '장바구니 내 상품 개수(최대 5개)',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT 'ID 필드 삭제 (0: 삭제되지 않음 1: 삭제됨)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성날짜',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경날짜',
  PRIMARY KEY (`cart_item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT =1 ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for TB_USER
-- ----------------------------
DROP TABLE IF EXISTS `TB_USER`;
CREATE TABLE `TB_USER`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '사용자 인덱스',
  `nick_name` varchar(50) DEFAULT '' COMMENT '닉네임',
  `login_name` varchar(11) DEFAULT '' COMMENT 'ID',
  `password_md5` varchar(32) DEFAULT '' COMMENT 'Password(암호화됨)',
  `introduce_sign` varchar(100) DEFAULT '' COMMENT '상태메시지',
  `address` varchar(100) DEFAULT '' COMMENT '주소',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT 'ID 필드 삭제 (0: 삭제되지 않음 1: 삭제됨)',
  `locked_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '차단설정 0:N 1:Y',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성날짜',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of TB_USER
-- ID: user, Password: 123456
-- ----------------------------
INSERT INTO `TB_USER` VALUES (1, 'user', 'user', 'e10adc3949ba59abbe56e057f20f883e', '상태메시지', NULL, 0, 0, '2022-08-01 12:00:00');