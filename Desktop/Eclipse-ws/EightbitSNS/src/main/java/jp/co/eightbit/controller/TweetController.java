package jp.co.eightbit.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jp.co.eightbit.entity.LoginUserDetails;
import jp.co.eightbit.entity.Tweet;
import jp.co.eightbit.entity.User;
import jp.co.eightbit.repository.TweetRepository;
import jp.co.eightbit.service.TweetService;
import jp.co.eightbit.service.UserService;


@Controller
public class TweetController {
	@Autowired
	private TweetService tweetService;
	@Autowired
	private UserService userService;
	
    public TweetController(TweetService tweetService, UserService userService) {
        this.tweetService = tweetService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

    	Long userId = 	userService.getUserIdByEmail(email);
    	String tweetUserId = userService.getTweetUserIdByEmail(email);
    	String username = userService.getUserNameByEmail(email);
    	
    	System.out.println("userId :" + userId);
    	System.out.println("tweetUserId :" + tweetUserId);
    	System.out.println("username :" + username);
    	
        List<Tweet> latestTweets = tweetService.getLatestTweets();
        model.addAttribute("tweets", latestTweets);
        model.addAttribute("userId",userId);
        model.addAttribute("tweetUserId",tweetUserId);
        model.addAttribute("username", username);
        
        
        
        return "index";
    }
	
	
	@PostMapping("/posttweet")
	public String postTweet(@RequestParam("userId")Long userId,@RequestParam("tweetUserId")String tweetUserId
		,@RequestParam("username")String username,@RequestParam("tweetText") String tweetText) {
		Tweet tweet = new Tweet();
		tweet.setUserId(userId);
		tweet.setUserName(username);
		tweet.setTweetUserId(tweetUserId);
		tweet.setTweetText(tweetText);
		tweet.setPostDate(LocalDateTime.now());
		tweetService.saveTweet(tweet);		
		return "redirect:/";	
	}
	
	@PostMapping("/like")
	@ResponseBody
	public ResponseEntity<Object> likeTweet(@RequestParam("tweetId") Long tweetId,@RequestParam("userId")Long userId) {
		tweetService.likeTweet(userId, tweetId);
	    return ResponseEntity.ok().build();
	}
	
	@GetMapping("/search")
	public String searchTweets(@RequestParam("keyword")String keyword,Model model) {
		List<Tweet> tweets = tweetService.searchTweetsByKeyword(keyword);
		model.addAttribute("tweets",tweets);
		return "index";
	}
	
	@GetMapping("/tweet/{id}")
	public String getTweet(@PathVariable("id")Long id,Model model) {
		Tweet tweet = tweetService.getTweet(id);
		model.addAttribute("tweet",tweet);
		return "tweet"; 
	}

	
	
	
}
