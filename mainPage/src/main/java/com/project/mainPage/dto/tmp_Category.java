package com.project.mainPage.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class tmp_Category {
    private Long categoryId;    // 카테고리 인덱스
    private Byte categoryLevel; // 1: 대분류, 2: 중분류, 3: 소분류
    private Long parentId;  // 상위 인덱스(CategoryLevel이 1이면 0)
    private String categoryName;    // 카테고리명
    private Integer categoryRank;   // 정렬값(생성순으로 정렬되는 것이 아니라 중요한 카테고리가 위에 올 수 있도록)
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