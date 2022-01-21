package com.cafe.teria.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe.teria.dto.CafeDTO;
import com.cafe.teria.dto.RecommentDTO;
import com.cafe.teria.service.CafeService;

@Controller
public class HomeController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired CafeService service;

	// String int // 
	// 뷰 => 컨트롤러 => 서비스 => DAO(인터페이스) => MAPPER.xml [흐름]
	
// 20220121 문의 글쓰기 페이지 요청
	@RequestMapping(value = "/qstPage", method = RequestMethod.GET)
	public String qstPage(Model model) {
		logger.info("확인해보기");
		
		// 1. 페이지 이동		
		return "qstWrite";
		
		// 2. 여기까지 하고 한번 확인하기
	}


// 20220121 문의 글쓰기
	@RequestMapping(value = "/qstWrite", method = RequestMethod.POST)
	public String qstWrite(
			Model model, 
			@RequestParam HashMap<String, Object> params) 
	{
		logger.info("글쓰기 요청 도착");
		// 왔냐?
		logger.info("파라미터 잘 왔나? : {}", params.size());
		
		// 이것도 외우는게 편함
		// DB 연결 구문 4개 - SELECT / INSERT / UPDATE / DELETE
		// SELECT 반환값 => DTO or String or INT
		// 나머지 세개 => int
		
		int result = service.writeQst(params);
		
		return "main";
	}
	
	
	
	
    @RequestMapping(value = "/testlist", method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("임시 리스트 페이지 이동");
		
		ArrayList<CafeDTO> list = service.list();
		model.addAttribute("list",list);
		
		return "testlist";
	}
	
	
	@RequestMapping(value = "/detail")
	public String detail(Model model, @RequestParam("idx") String cafe_idx) {
		
		//logger.info("detail 요청 : {}",cafe_idx);
		
		HashMap<String, String> cafeBmem = service.cafeBmem(cafe_idx);
		List<HashMap<String, String>>diet = service.detail(cafe_idx,"detail");
		List<HashMap<String, String>> cafeReply = service.cafeReply(cafe_idx);
		List<HashMap<String, String>> recomment = service.recomment(cafe_idx);
		
		
		//System.out.println(cafeBmem);
		//System.out.println(diet);
		
		//System.out.println(cafeReply);
		//ystem.out.println(recomment);
		
		
		model.addAttribute("cafe",cafeBmem);
		model.addAttribute("diet",diet);
		model.addAttribute("reply",cafeReply);
		model.addAttribute("recomment",recomment);
		
		return "cafeDetail";
	}
	
	
	
	@RequestMapping(value = "/replyList", method = RequestMethod.POST)
	@ResponseBody
	public List<HashMap<String, Object>> replyList(@RequestParam int idx) {
		
		List<HashMap<String, Object>> map = service.replyList(idx);
		
		//logger.info("컨트롤러에서 받아온 ReplyList : {}",map);
		
		return map;
	}
	
	
	
	
	
	
	@RequestMapping(value = "/recommentCall", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> recommentCall(@RequestParam int idx) {
		//logger.info("대댓글 요청{}",idx);
		
		HashMap<String, Object> recommentCall = new HashMap<String, Object> ();
		
		ArrayList<RecommentDTO> listCall = service.recoCall(idx);
		
		//logger.info("리스트콜 값 가져오기 : {}",listCall.get(0).getRecomment_posttime());
		
		recommentCall.put("listcall", listCall);
		
		return recommentCall;
	}
	
	
	@RequestMapping(value = "/addreply", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> addreply(@RequestParam HashMap<String, String> param) {
		
		//logger.info("댓글 등록 요청 : {}",param);

		service.addreply(param);
		
		 HashMap<String, Object> success = new  HashMap<String, Object>();
		 
		 success.put("success", 1);
		
		return success; 
	
	}
	
	
	@RequestMapping(value = "/addRecomment", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> addRecomment(@RequestParam HashMap<String, String> param) {
		
		//logger.info("대댓글 등록 요청 : {}",param);

		service.addRecomment(param, "addRecomment");
		
		 HashMap<String, Object> success = new  HashMap<String, Object>();
		 
		 success.put("success", 1);

		return success; 
	
	}
	
	@RequestMapping(value = "/updateReply", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> updateReply(@RequestParam HashMap<String, String> param) {
		
		//logger.info("댓글 수정 요청 : {}",param);
		
		service.updateReply(param,"updateReply");

		HashMap<String, Object> success = new  HashMap<String, Object>();
		 
		 success.put("success", 1);

		return success; 
	
	}
	
	
	
	@RequestMapping(value = "/replyDel", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> replyDel(@RequestParam int idx) {
		
		service.replyDel(idx);

		HashMap<String, Object> success = new  HashMap<String, Object>();
		 
		 success.put("success", 1);

		return success; 
	
	}
	
	@RequestMapping(value = "/updateRecom", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> updateRecom(@RequestParam HashMap<String, String> param) {
		
		//logger.info("컨트롤러에서 받아온 정보 : {}",param);
		
		service.updateRecom(param);
		
		HashMap<String, Object> success = new  HashMap<String, Object>();
		 
		success.put("success", 1);

		return success; 
	
	}
	
	@RequestMapping(value = "/recoDel", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> recoDel(@RequestParam HashMap<String, String> param) {
		
		service.recoDel(param,"disrewritecnt");

		HashMap<String, Object> success = new  HashMap<String, Object>();
		 
		 success.put("success", 1);

		return success; 
	
	}
	
	
	
	@RequestMapping(value = "/idChk", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> idChk(@RequestParam HashMap<String, Object> idChk) {
		
		//logger.info("받아온 idChk 값  : {}",idChk);
		
		HashMap<String, Object> idChkinfo = new HashMap<String, Object>();
		
		idChkinfo.put("idChk", service.idChk(idChk));
		
		//logger.info("idChkInfo : {} ",idChkinfo);

		return idChkinfo; 
	
	}
	
	
	  
	   @RequestMapping(value = "/testImage", method = RequestMethod.GET)
	   public String testImage(Model model) {
	      
	      return "testImage";
	   }
	
	
	   
		@RequestMapping(value = "/bmemchk", method = RequestMethod.POST)
		@ResponseBody
		public HashMap<String, Object> bmemchk(@RequestParam HashMap<String, Object> idChk) {
			
			//logger.info("받아온 idChk 값  : {}",idChk);
			
			HashMap<String, Object> idChkinfo = new HashMap<String, Object>();
			
			idChkinfo.put("idChk", service.bmemchk(idChk));
			
			//logger.info("idChkInfo : {} ",idChkinfo);

			return idChkinfo; 
		
		}
	
		
		@RequestMapping(value = "/imgChk", method = RequestMethod.POST)
		@ResponseBody
		public List<HashMap<String, Object>> imgChk() {
			
			List<HashMap<String, Object>> imgInfo = service.imgChk();
			
			System.out.print(imgInfo);
			
			return imgInfo; 
		
		}
		
		
		
	
}
