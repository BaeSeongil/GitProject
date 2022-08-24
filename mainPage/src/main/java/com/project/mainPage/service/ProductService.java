package com.project.mainPage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.mainPage.dto.Product;
import com.project.mainPage.dto.ProductImg;
import com.project.mainPage.mapper.ProductImgMapper;
import com.project.mainPage.mapper.ProductMapper;

//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.project.mainPage.dto.Criteria;
//import com.project.mainPage.dto.Product;
//import com.project.mainPage.mapper.ProductMapper;

@Service
public class ProductService {

	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ProductImgMapper productImgMapper;
	
	@Value("${spring.servlet.multipart.location}")
	String savePath;
	
	public int registProduct(Product product) throws Exception {
		int regist = 0;
		regist = productMapper.insertOne(product);
		int imgRegist = 0;
		if (regist>0 && product.getProductImgs()!=null) {
			for (ProductImg productImg : product.getProductImgs()) {
				productImg.setProductid(product.getProductid());
				imgRegist += productImgMapper.insertOne(productImg);
			}
		}
		System.out.println("상품 등록: " + regist);
		System.out.println("상품 이미지 등록: "+ imgRegist);
		return regist;
	}
	
}
//	@Autowired
//	private ProductMapper productMapper;
//	
//	public List<Product> searchProduct(Criteria cri){
//		String type = cri.getType();
//		String[] typeArr = type.split("");
//		String[] productArr = productMapper.getProductIdList(cri.getKeyword());
//		if(type.equals("N") || type.equals("S") || type.equals("C")|| type.equals("P")) {
//			if(productArr.length == 0) {
//				return new ArrayList();
//			}
//		}
//		for(String t : typeArr) {
//			if(t.equals("P")) {
//				cri.setProductArr(productArr);
//			}
//		}
//		return productMapper.searchProduct(cri);
//	}
//	public int productsGetTotal(Criteria cri) {
//		return productMapper.productsGetTotal(cri);
//	}
//}


