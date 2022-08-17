package com.project.mainPage.dto;

import lombok.Data;

@Data
public class Category {
	public int categoryId;
	public String categoryName;
	
	private Product product;
}
