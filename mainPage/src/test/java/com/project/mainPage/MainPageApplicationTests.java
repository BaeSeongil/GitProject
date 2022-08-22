package com.project.mainPage;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.mainPage.dto.Criteria;
import com.project.mainPage.dto.Product;
import com.project.mainPage.mapper.ProductMapper;

@SpringBootTest
class MainPageApplicationTests {
	@Autowired
	ProductMapper productMapper;
	
	@Test
	void contextLoads() {
	}
//	@Test
//	public void getProductId() {
//		String keyword = "shirt";
//		
//		String[] list = productMapper.getProductIdList(keyword);
//		
//		System.out.println("결과 : " + list.toString());
//		
//		for(String id : list) {
//			System.out.println("개별 결과 : " + id);
//		}
//	}
//	@Test
//	public void getProductList() {
//		Criteria cri = new Criteria();
//		cri.setKeyword("shirt");
//		System.out.println("cri : "+cri);
//		List<Product> list=productMapper.searchProduct(cri);
//		System.out.println("list : "+list);
//		int productTotal = productMapper.productsGetTotal(cri);
//		System.out.println("total : "+productTotal);
//		
//	}
	
}
