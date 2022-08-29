package com.project.mainPage.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class tmp_IndexConfig {
    private Long configId;  // 설정 인덱스
    private String configName;  // 설정명
    private Byte configType;    // 인덱스 타입(1: 검색바 2: 검색 드롭다운 3: 히트상품 4: 신상품 5: 추천상품)
    private Long productId;   // 상품명
    private String redirectUrl; // 연결할 url
    private Integer configRank; // 정렬값(생성순으로 정렬되는 것이 아니라 중요한 인덱스가 위에 올 수 있도록)
    private Byte isDeleted; // ID 필드 삭제 (0: 삭제되지 않음 1: 삭제됨)
    // 생성일, 생성한 유저
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Integer createUser;
    // 갱신일, 갱신한 유저
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private Integer updateUser;
}