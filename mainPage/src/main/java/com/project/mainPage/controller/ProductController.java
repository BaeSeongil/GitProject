package com.project.mainPage.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.project.mainPage.dto.Category;
import com.project.mainPage.dto.Criteria;
import com.project.mainPage.dto.IdCheck;
import com.project.mainPage.dto.Pagination;

import com.project.mainPage.dto.Product;
import com.project.mainPage.dto.ProductImg;
import com.project.mainPage.dto.UsersDto;
import com.project.mainPage.mapper.CategoryMapper;
import com.project.mainPage.mapper.ProductImgMapper;
import com.project.mainPage.mapper.ProductMapper;
import com.project.mainPage.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	private final static int PRODUCT_IMG_LIMIT = 5;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private ProductImgMapper productImgMapper;
	
	@Autowired
	private ProductService productService;
	
	@Value("${spring.servlet.multipart.location}")
	private String savePath;
	
	
	
	@GetMapping("/list/{page}")
	public String list(@PathVariable int page, Model model) {
		int row = 10;
		int startRow = (page - 1) * row;
		List<Product> productList = productMapper.selectAll(startRow, row);
		int count = productMapper.selectAllCount();
		
		Pagination pagination = new Pagination(page, count, "/product/list/", row);
		System.out.println(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("productList", productList);
		model.addAttribute("row", row);
		model.addAttribute("count", count);
		model.addAttribute("page", page);
		return "/product/list";
	}
 
	@GetMapping("/detail/{productid}") 
	public String detail(@PathVariable int productid, Model model) {
		Product product = null;
		try {
			product = productMapper.selectOne(productid); 
			System.out.println(product);
			if (product != null) {

			
				List<Product> products =
				productMapper.selectByProductName(product.getProductName()); 
				System.out.println(products);

				model.addAttribute("products",products);
				model.addAttribute("product",product); 

				return "/product/detail";
			} else {
				return "redirect:/product/cate/1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/product/cate/1";
	}
	

	@GetMapping("/update/{productid}")
	public String update(@PathVariable int productid, Model model) {
		Product product = null;
		product = productMapper.selectOne(productid);
		System.out.println(product);
		model.addAttribute(product);
		return "/product/update";
	}
	
	@PostMapping("/update.do")
	public String update(
			Product product,
			@RequestParam(name="productImgNo", required = false) int [] productImgNos,
			@RequestParam(name="imgFile", required = false) MultipartFile[] imgFiles,
			HttpSession session
			) {
		int update = 0;
		
		try {
			int productImgCount = productImgMapper.selectCountProductId(product.getProductid());
			int insertProductImgLength = 
					PRODUCT_IMG_LIMIT - productImgCount + ((productImgNos!=null)?productImgNos.length:0);

			if (imgFiles!=null && insertProductImgLength>0) {
				List<ProductImg> productImgs = new ArrayList<ProductImg>();

				for (MultipartFile imgFile : imgFiles) {
					String [] types = imgFile.getContentType().split("/");
					System.out.println("types: " + types);
					if (types[0].equals("image")) {
						String newFileName = "product_" + System.nanoTime()+"."+types[1];
						Path newFilePath = Paths.get(savePath+"/"+newFileName);
						imgFile.transferTo(newFilePath);
						ProductImg productImg = new ProductImg();
						productImg.setProductid(product.getProductid());
						productImg.setImg_path(newFileName);
						productImgs.add(productImg);
						if (--insertProductImgLength == 0) {
							break;
						}
					}
				}
				if (productImgs.size()>0) {
					product.setProductImgs(productImgs);
				}
			}
			update = productService.modifyProductRemoveProductImg(product, productImgNos);
			System.out.println(product.getProductName());
			System.out.println(update);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (update>0) {
			System.out.println("?????? ??????!" + update);
			return "redirect:/product/detail/"+product.getProductid();
		} else {
			return "redirect:/product/update/"+product.getProductid();
		}
		
	}

	@GetMapping("/insert.do")
	public void insert() {};
	@PostMapping("insert.do")
	public String insert(
			Product product,
			List<MultipartFile> imgFiles,
			HttpSession session) {
		System.out.println(savePath);
		int insert = 0;
		String msg = "";
		try {
			if(imgFiles!=null) {
				List<ProductImg> productImgs = new ArrayList<ProductImg>();
				for (MultipartFile imgFile : imgFiles) {
					String type= imgFile.getContentType();
					if (type.split("/")[0].equals("image")) {
						String newFileName = "product_" + System.nanoTime()+"."+type.split("/")[1];
						Path newFilePath = Paths.get(savePath+"/"+newFileName);
						imgFile.transferTo(newFilePath);
						ProductImg productImg = new ProductImg();
						productImg.setImg_path(newFileName);
						productImgs.add(productImg);
					} 
				}
				if (productImgs.size()>0) {
					product.setProductImgs(productImgs);
				}
			}
			insert = productService.registProduct(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (insert>0) {
			msg = "????????? ??????????????? ?????????????????????.";
			session.setAttribute("msg", msg);
			System.out.println("product ??????: "+product);
			return "redirect:/product/list/1";
		} else {
			msg = "?????? ????????? ?????????????????????.";
			session.setAttribute("msg", msg);		
			return "redirect:/product/insert.do";
		}
	}
	
	@GetMapping("/delete/{productid}")
	public String delete(
			@PathVariable int productid,
			HttpSession session
			) {
		String msg = "";
		int delete = 0;
		try {
			delete = productService.removeProduct(productid);
			System.out.println(delete);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (delete>0) {
			msg = "?????? ?????? ??????";
			session.setAttribute("msg", msg);
			return "redirect:/product/list/1";
		} else {
			msg = "?????? ?????? ??????";
			session.setAttribute("msg", msg);
			return "redirect:/product/update/"+productid;
		}
	}
	
	@GetMapping("/idCheck/{categoryId}")
	public @ResponseBody IdCheck idCheck(@PathVariable int categoryId) {
		IdCheck idCheck = new IdCheck();
		Category cate = categoryMapper.selectOne(categoryId);
		if(cate!=null) {
			idCheck.idCheck=true;
			idCheck.cate=cate;
		}
		return idCheck;
	}
	@GetMapping("/search/{page}")
	public String searchProduct(
			@PathVariable int page, Criteria cri, Model model) {
		int row = 10;
		int startRow = (page - 1) * row;
		cri.setAmount(row);
		cri.setSkip(startRow);
		List<Product> list=productMapper.searchProduct(cri);
		int count = productMapper.productsGetTotal(cri);
		  if(!list.isEmpty()) { model.addAttribute("list",list);
		  }else { model.addAttribute("listCheck","empty"); return "/product/search"; }
		Pagination pagination = new Pagination(page, count, "/product/search/", row);
		System.out.println(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("list", list);
		System.out.println("list : "+list);
		model.addAttribute("row", row);
		model.addAttribute("count", count);
		model.addAttribute("page", page);
		return "/product/search";
	}
}