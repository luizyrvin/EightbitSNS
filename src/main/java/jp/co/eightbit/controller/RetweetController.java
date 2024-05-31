package jp.co.eightbit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.eightbit.service.RetweetService;

@Controller
public class RetweetController {
	private final RetweetService retweetService;
	
	@Autowired
	public RetweetController(RetweetService retweetService) {
		this.retweetService = retweetService;
	}
	
	@PostMapping("/retweet")
	@ResponseBody
	public ResponseEntity<?> retweet(@RequestParam("userId") Long userId, @RequestParam("tweetId") Long tweetId){
		try {
			retweetService.retweet(userId,tweetId);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retweet: " + e.getMessage()); 
		}
	}
	

	
}
