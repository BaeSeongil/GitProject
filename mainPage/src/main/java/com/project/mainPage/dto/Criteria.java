package com.project.mainPage.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Criteria {
	 	/* 현재 페이지 번호 */
	    private int pageNum;
	    /* 페이지 표시 개수 */
	    private int amount;
	    /* 페이지 skip */
	    private int skip;
	    /* 검색 타입 */
	    private String type;
	    /* 검색 키워드 */
	    private String keyword;
	    /* 상품 리스트 */
		private String[] productArr;
//		/* 카테고리 코드 */
//		private String cateCode;
	    
	    /* Criteria 생성자 */
	    public Criteria(int pageNum, int amount) {
	        this.pageNum = pageNum;
	        this.amount = amount;
	        this.skip = (pageNum -1) * amount;
	    }
	    /* Criteria 기본 생성자 */
	    public Criteria(){
	        this(1,10);
	    }
	    /* 검색 타입 데이터 배열 변환 */
	    public String[] getTypeArr() {
	        return type == null? new String[] {}:type.split("");
	    }
}