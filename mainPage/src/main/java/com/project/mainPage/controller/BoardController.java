package com.project.mainPage.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.SessionAttribute;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.mainPage.dto.BoardImg;
import com.project.mainPage.dto.UsersDto;
import com.project.mainPage.service.BoardService;
import com.project.mainPage.dto.Board;
import com.project.mainPage.dto.BoardPrefer;
import com.project.mainPage.dto.Criteria;
import com.project.mainPage.dto.Pagination;
import com.project.mainPage.dto.Product;
import com.project.mainPage.dto.Reply;
import com.project.mainPage.dto.ReplyPrefer;
import com.project.mainPage.mapper.BoardImgMapper;
import com.project.mainPage.mapper.BoardMapper;

import com.project.mainPage.mapper.BoardPreferMapper;
import com.project.mainPage.mapper.ReplyMapper;
import com.project.mainPage.mapper.UsersMapper;


@Controller
@RequestMapping("/board")
public class BoardController {
	
	// board > board_img 의 수를 5개로 제한 
	private final static int BOARD_IMG_LIMIT = 5; 
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private BoardImgMapper boardImgMapper;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardPreferMapper boardPreferMapper;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private ReplyMapper replyMapper;
	
	@Value("${spring.servlet.multipart.location}") //파일이 임시저장되는 경로+파일을 저장할 경로
	private String savePath;
	
	@GetMapping("/list/{page}")
	public String list(@PathVariable int page, Model model, HttpSession session) {
		int row =10;
		int startRow = (page-1)*row;
		List<Board> boardList = boardMapper.selectPageAll(startRow, row);
		int count = boardMapper.selectPageAllCount();
		
		Pagination pagination = new Pagination(page, count, "/board/list/", row);
		model.addAttribute("pagination", pagination);
		model.addAttribute("boardList", boardList);
		model.addAttribute("row", row);
		model.addAttribute("count", count);
		model.addAttribute("page", page);
		return "/board/list";
	}
	

	@GetMapping("/detail/{boardNo}")
	public String detail(
			@PathVariable int boardNo,
			Model model,
			@SessionAttribute(required = false) UsersDto loginUsers, 
			@RequestParam( defaultValue = "1") int replyPage,
			HttpServletRequest req,
			HttpServletResponse resp) {
		
		Board board = null;		
		BoardPrefer boardPrefer = null;  // 로그인이 안되면 null
		int row=5;
		System.out.println(replyPage);
		int startRow=(replyPage-1)*row;
		String pagingUrl="/reply/list/"+boardNo;
		Pagination pagination = null;
		String loginUsersId=null;
		try {
			if(loginUsers != null) {
				loginUsersId=loginUsers.getUserid();
				board = boardService.boardUpdateView(boardNo,loginUsers.getUserid());
				boardPrefer = boardPreferMapper.selectFindUserIdAndBoardNo(loginUsers.getUserid(),boardNo);
//				for(Reply reply : board.getReplys()) {
//					for(ReplyPrefer replyPrefer : reply.getGood_prefers()) {
//						if(replyPrefer.getUserid().equals(loginUsers.getUserid())){
//							reply.setPrefer_active(true);
//						}
//					}
//					for(ReplyPrefer replyPrefer : reply.getGood_prefers()) {
//						if(replyPrefer.getUserid().equals(loginUsers.getUserid())){
//							reply.setPrefer_active(false);
//						}
//					}
//				}
				int replySize = replyMapper.selectBoardNoCount(boardNo);
				if(replySize>0) {
					pagination = new Pagination(replyPage, replySize, pagingUrl, row);
					List<Reply> replies=replyMapper.selectBoardNoPage(boardNo, startRow, row, loginUsersId);
					board.setReplys(replies);
					
					model.addAttribute("pagination",pagination);
				}
			}else {
				board = boardMapper.selectOne(boardNo);
				int replySize = replyMapper.selectBoardNoCount(boardNo);
				if(replySize>0) {
					pagination = new Pagination(replyPage, replySize, pagingUrl, row);
					List<Reply> replies=replyMapper.selectBoardNoPage(boardNo, startRow, row, loginUsersId);
					board.setReplys(replies);
					
					model.addAttribute("pagination",pagination);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(board != null) {
			model.addAttribute("boardPrefer",boardPrefer);
			model.addAttribute("board",board);
			System.out.println(board);
			return "/board/detail";			
		}else {
			return "redirect:/board/list/1";
		}
	}
	@GetMapping("/insert.do")
	public String insert(HttpSession session) {
		//로그인 한사람만 등록 페이지 가능
		if(session.getAttribute("loginUsers")!=null) {
			return "/board/insert";
		}else {
			return "redirect:/users/login.do";
		}
	}
		
	@PostMapping("/insert.do")
	public String insert(
				Board  board,
				List <MultipartFile> imgFiles,
				@SessionAttribute(required = false) UsersDto loginUser,
				HttpSession session) {
		System.out.println(board);
		System.out.println(savePath);
		int insert=0;
		String msg="";
		try {
			//이미지 저장 및 처리
			if(imgFiles!=null) {
				List<BoardImg> boardImgs=new ArrayList<BoardImg>();
				// imgFiles 가 null이면 여기서 오류 발생!! 
				for(MultipartFile imgFile:imgFiles) {		
					String type=imgFile.getContentType();
					System.out.println(imgFile.getContentType());
					if(type.split("/")[0].equals("image")) {
						String newFileName="board_"+System.nanoTime()+"."+type.split("/")[1]; //"image/jpeg"
						Path newFilePath=Paths.get(savePath+"/"+newFileName);
						imgFile.transferTo(newFilePath);
						BoardImg boardImg=new BoardImg();
						boardImg.setImg_path(newFileName);
						boardImgs.add(boardImg);
					}
				}
				if(boardImgs.size()>0) {
					board.setBoardImgs(boardImgs);
				}
			}
			insert=boardService.registBoard(board);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(insert>0) {
			msg="게시글 등록 성공";
			session.setAttribute("msg", msg);
			return "redirect:/board/list/1";
		}else {
			msg="게시글 등록 실패";
			session.setAttribute("msg", msg);
			return "redirect:/board/insert.do";
		}
	}
	
	// 게시글 삭제 
	@GetMapping("/delete/{boardNo}/{userId}")
	public String delete(
			@PathVariable int boardNo,
			@PathVariable String userId,
			@SessionAttribute(name ="loginUsers",required = false) UsersDto loginUser,
			HttpSession session) {
		System.out.println("loginUser : "+loginUser);
		String msg="";
		if(loginUser.getUserid().equals(userId)) {
			int delete=0;
			try {
				delete = boardService.removeBorad(boardNo);
			} catch(Exception e) {e.printStackTrace();}
			if(delete>0) {
				msg="게시글 삭제 성공";
				session.setAttribute("msg", msg);
				return "redirect:/board/list/1";
			}else {
				msg="게시글 삭제 실패";
				session.setAttribute("msg", msg);
				return "redirect:/board/update/"+boardNo;			
			}			
		}else {
			msg="로그인 하셔야 이용가능합니다.";
			session.setAttribute("msg", msg);
			return "redirect:/user/login.do";
		}
		
	};
	
	@GetMapping("/update/{boardNo}")
	public String update(@PathVariable int boardNo,Model model,HttpSession session) {
		Board board = null;
		board = boardMapper.selectDetailOneAll(boardNo);
		Object loginUsers_obj = session.getAttribute("loginUsers");
		if(board.getUsers().getUserid().equals(((UsersDto)loginUsers_obj).getUserid())) {
			model.addAttribute("board",board);
			return "/board/update";			
		}else {
			return "redirect:/users/login.do";
		}
	}
	
	// 게시글 수정 
	@PostMapping("/update.do")
	public String update(
			Board board, Model model,
			@RequestParam(name="boardImgNo", required = false) int [] boardImgNos, // // required = false : 아무것도 안올 수 있는 경우
			@RequestParam(name="imgFile", required=false) MultipartFile[] imgFiles,
 			HttpSession session
			) { 
		int update = 0; 
		Object loginUsers_obj = session.getAttribute("loginUsers");
		System.out.println(board);
		if( ((UsersDto)loginUsers_obj).getUserid().equals(board.getUsers().getUserid()) ) {
			try {

				int boardImgCount = boardImgMapper.selectCountBoardNo(board.getBoard_no());  // baordImg 등록된 개수 
				int insertBoardImgLength = BOARD_IMG_LIMIT - boardImgCount + ((boardImgNos != null) ? boardImgNos.length : 0); // 5 -  boardImgCount + 등록될 이미지 개수 
				// 이미지 저장 
				if(imgFiles != null && insertBoardImgLength>0) {
					List<BoardImg> boardImgs = new ArrayList<BoardImg>();
					for(MultipartFile imgFile : imgFiles) { 
						String[] types = imgFile.getContentType().split("/");
						System.out.println("types: "+types);
						if(types[0].equals("image")) {
							String newFileName = "board_"+System.nanoTime()+"."+types[1];
							Path path = Paths.get(savePath+"/"+newFileName);
							imgFile.transferTo(path);
							BoardImg boardImg = new BoardImg();
							boardImg.setBoard_no(board.getBoard_no());
							boardImg.setImg_path(newFileName);
							boardImgs.add(boardImg);
							if(--insertBoardImgLength == 0 ) break; // 이미지 수가 5개면 반목문 종료 
						}
					}
					if(boardImgs.size()>0) {
						board.setBoardImgs(boardImgs);
					}
				}
				update=boardService.modifyBoardRemoveBoardImg(board, boardImgNos);
			} catch (Exception e) {e.printStackTrace();}
			if(update>0) {
				return "redirect:/board/detail/"+board.getBoard_no();
			}else {
				return "redirect:/board/update/"+board.getBoard_no();
			}			
		}else{
			return "redirect:/users/login.do";
		}     
	}
	
	@GetMapping("/prefer/{boardNo}/{prefer}")
	public String preferModfy(
			Model model,
			@PathVariable int boardNo,
			@PathVariable boolean prefer,
			@SessionAttribute(required = false) UsersDto loginUsers,
			HttpSession session) {
		String msg=(prefer)?"좋아요":"싫어요";
		Board board = boardMapper.selectOne(boardNo,loginUsers.getUserid());
		int modify=0;
		try {
			BoardPrefer boardPrefer=boardPreferMapper.selectFindUserIdAndBoardNo(loginUsers.getUserid(), boardNo);
			
			if(boardPrefer==null) {//좋아요 싫어요를 한번도 한적이 없을 때
				msg+=" 등록";
				boardPrefer=new BoardPrefer();
				boardPrefer.setBoard_no(boardNo);
				boardPrefer.setPrefer(prefer);
				boardPrefer.setUserid(loginUsers.getUserid());
				modify=boardPreferMapper.insertOne(boardPrefer);
			}else if(prefer==boardPrefer.isPrefer()) {//좋아요 싫어요를 삭제할 때
				msg+=" 삭제";
				boardPrefer.setPrefer(prefer);
				modify=boardPreferMapper.deleteOne(boardPrefer.getBoard_prefer_no());
			}else if(prefer!=boardPrefer.isPrefer()) {//좋아요에서 싫어요로 바꿀때 or 싫어요에서 좋아요롤 바꿀때
				msg+=" 수정";
				boardPrefer.setPrefer(prefer);
				modify=boardPreferMapper.updateOne(boardPrefer);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg+=" (db오류)";
		}
		if(modify>0) {
			msg+=" 성공!";
		}else {
			msg+=" 실패!";
		}
		session.setAttribute("msg", msg);
		return "redirect:/board/detail/"+boardNo;
	}

	
	@GetMapping("/search/{page}")
	public String searchProduct(
			@RequestParam(value = "type") String type,
			@RequestParam(value = "keyword") String keyword,
			@PathVariable int page, Criteria cri, Model model) {
		int row = 10;
		int startRow = (page - 1) * row;
		cri.setAmount(row);
		cri.setSkip(startRow);
		List<Board> list= boardMapper.searchBoard(cri);
		int count = boardMapper.boardGetTotal(cri);
		  if(!list.isEmpty()) { model.addAttribute("list",list);
		  }else { model.addAttribute("listCheck","empty"); return "/board/search"; }
		Pagination pagination = new Pagination(page, count, "/board/search/", row);
		System.out.println(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("list", list);
		System.out.println("list : "+list);
		model.addAttribute("row", row);
		model.addAttribute("count", count);
		model.addAttribute("page", page);
		return "/board/search";
	}	
	
}
	
	
	
	

