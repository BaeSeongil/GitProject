package com.project.mainPage.dto;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class Pagination {
	private int page; 	// 요청한 페이지 (현재 위치)
	private int count;	// 총개수 
	private String url;
	private int row = 10; 
	
	private int start;
	private int end; 
	private int firstPage = 1;
	private int lastPage;
	private int previousPage;
	private int nextPage;
	
	private boolean yourFirst;
	private boolean yourLast;
	private boolean yourPreviousPage;
	private boolean yourNext;
	
	public Pagination(int page, int count, String url) {
		this.page = page;
		this.count = count;
		this.url = url;
		this.setAll();
	}
	
	public Pagination(int page, int count, String url, int row) {
		this.page = page;
		this.count = count;
		this.url = url;
		this.row = row;
		this.setAll();
	}
	
	public void setAll() {
		this.lastPage = count/row + ((count%row>0)? 1 : 0);
		this.previousPage = page - 1;
		this.nextPage = page + 1;
		
		
		this.yourFirst = (page > firstPage)? true : false;
		this.yourPreviousPage = (page > firstPage)? true : false; 
		
		this.yourLast = (page < lastPage)? true : false;
		this.yourNext = (page < lastPage)? true : false;
		
		this.start = this.page - 2;
		this.end = this.page + 2;
		
		if(this.start < firstPage) {
			end = end - start + 1;
			if(end < lastPage) {
				start = lastPage;
			}
			start = firstPage;
		}
		
		if(end > lastPage) {
			start = start - end + lastPage;
			if(start < firstPage) {
				start = firstPage;
			}
			end = lastPage;
		}
	}
}
