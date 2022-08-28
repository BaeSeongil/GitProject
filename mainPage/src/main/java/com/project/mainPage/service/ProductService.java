package com.project.mainPage.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
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
	@Transactional
	public int modifyProductRemoveProductImg(Product product,
			int [] productImgNos) throws Exception {
		int modify = 0;
		if (productImgNos!=null) { //선택한 삭제될 board_img.board_img_no
			for (int no : productImgNos) {
				ProductImg productImg= productImgMapper.selectOne(no);
				File f = new File (savePath+"/"+productImg.getImg_path());
				System.out.println("product의 이미지 파일 삭제: " + f.delete());
				int removeProductImg = productImgMapper.deleteOne(no);
				System.out.println("product의 Product_img 삭제: " + removeProductImg);
			}
		}
		if (product.getProductImgs()!=null) {
			for (ProductImg productImg : product.getProductImgs()) {
				int registProductImg = productImgMapper.insertOne(productImg);
				System.out.println("product의 Product_img 등록: " + registProductImg);
			}
		}
 		modify = productMapper.updateOne(product);
		return modify;
	}

	@Transactional
	public int removeProduct(int productid) throws Exception {
		int remove = 0;
		List<ProductImg> productImgs = productImgMapper.selectProductId(productid);
		if (productImgs!=null) {
			productImgs.stream()
				.map(ProductImg::getImg_path)
				.forEach((img)->{
					File f = new File(savePath+"/"+img);
					System.out.println("상품 이미지 삭제: " + f.delete());
				});
		}
		remove = productMapper.deleteOne(productid);
		return remove;
	}
}