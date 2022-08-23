package com.project.mainPage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.mainPage.dto.Criteria;
import com.project.mainPage.dto.Product;
import com.project.mainPage.mapper.ProductMapper;

@Service
public class ProductService {
	@Autowired
	private ProductMapper productMapper;
	
	public List<Product> searchProduct(Criteria cri){
		return productMapper.searchProduct(cri);
	}
	public int productsGetTotal(Criteria cri) {
		return productMapper.productsGetTotal(cri);
	}
}
