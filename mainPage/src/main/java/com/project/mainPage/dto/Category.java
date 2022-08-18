package com.project.mainPage.dto;

import java.util.List;

import lombok.Data;

@Data
public class Category {
	private int categoryId;
	private String categoryName;
	
	private List<Product> products; 
}
