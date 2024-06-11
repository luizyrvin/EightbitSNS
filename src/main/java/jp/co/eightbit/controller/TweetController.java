package jp.co.eightbit.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jp.co.eightbit.entity.Comments;
import jp.co.eightbit.entity.LoginUserDetails;
import jp.co.eightbit.entity.Tweet;
import jp.co.eightbit.entity.User;
import jp.co.eightbit.repository.TweetRepository;
import jp.co.eightbit.service.CommentService;
import jp.co.eightbit.service.TweetService;
import jp.co.eightbit.service.UserService;


@Controller
public class TweetController {
	@Autowired
	private TweetService tweetService;
	@Autowired
	private UserService userService;
	@Autowired
	private CommentService commentService;
	
    public TweetController(TweetService tweetService, UserService userService) {
        this.tweetService = tweetService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model,HttpSession session) {
    	// flashAttribute を使ってリダイレクト元から属性を取得
        List<Comments> commentList = commentService.getAllComments();
        
    	
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

    	Long userId = 	userService.getUserIdByEmail(email);
    	String tweetUserId = userService.getTweetUserIdByEmail(email);
    	String username = userService.getUserNameByEmail(email);
    	String profileImage = userService.getUserProfileImageByEmail(email);
    	String introduction = userService.getUserIntroduction(email);
    	
    	session.setAttribute("profileImage", profileImage);
        session.setAttribute("username", username);
        session.setAttribute("tweetUserId", tweetUserId);

    	
    	System.out.println("userId :" + userId);
    	System.out.println("tweetUserId :" + tweetUserId);
    	System.out.println("username :" + username);
    	
//    	各リツイートからの情報をList<Tweet>につっこむ
        List<Tweet> latestTweets = tweetService.getLatestTweets();
        model.addAttribute("tweets", latestTweets);
        model.addAttribute("userId",userId);
        model.addAttribute("tweetUserId",tweetUserId);
        model.addAttribute("username", username);
        model.addAttribute("profileImage", profileImage);
        model.addAttribute("introduction",introduction);
        
   
        
   
       
        model.addAttribute("commentList", commentList);
        System.out.println("commentList :" + commentList);

        return "index";
    }
    
  
	
	
	@PostMapping("/posttweet")
	public String postTweet(@RequestParam("userId")Long userId,@RequestParam("tweetUserId")String tweetUserId
		,@RequestParam("username")String username,@RequestParam("tweetText") String tweetText,HttpSession session) {
		String profileImage = (String)session.getAttribute("profileImage");
		
		Tweet tweet = new Tweet();
		tweet.setUserId(userId);
		tweet.setUserName(username);
		tweet.setTweetUserId(tweetUserId);
		tweet.setTweetText(tweetText);
		tweet.setPostDate(LocalDateTime.now());
		tweet.setProfileImageUrl(profileImage);
		tweetService.saveTweet(tweet);		
		return "redirect:/";	
	}
	
	
	
	@PostMapping("/likeTweet")
	@ResponseBody
	@Transactional
	public ResponseEntity<Object> likeTweet(@RequestParam("tweetId") Long tweetId,@RequestParam("userId")Long userId) {
	    boolean isLiked = tweetService.isLikedByUser(userId, tweetId);
	    
	    if(isLiked) {
	        tweetService.unlikeTweet(userId, tweetId);
	        tweetService.decrementLikesCount(tweetId, userId);
	    }else {
	        tweetService.likeTweet(userId, tweetId);
	        tweetService.incrementLikesCount(tweetId, userId); // ここでDBに保存
	    }

		int likes = tweetService.getLikesCount(tweetId);
	    return ResponseEntity.ok().body(Map.of("likes",likes));
	}
	
	@PostMapping("/commentTweet")
	@ResponseBody
	@Transactional
	public ResponseEntity<Object> commentTweet(@RequestParam("tweetId")Long tweetId){
		int comments = tweetService.getCommentsCount(tweetId);
		return ResponseEntity.ok().body(Map.of("comments", comments));
	}
	
	
//無限いいねだったら動くコード
//	@PostMapping("/likeTweet")
//	@ResponseBody
//	public ResponseEntity<Object> likeTweet(@RequestParam("tweetId") Long tweetId,@RequestParam("userId")Long userId) {
//		
//		int likes = tweetService.likeTweet(userId, tweetId);
//	    return ResponseEntity.ok().body(Map.of("likes",likes));
//	}
	
	@GetMapping("/search")
	public String searchTweets(@RequestParam(value = "keyword" ,required = false)String keyword,Model model,RedirectAttributes redirectAttributes,HttpSession session) {
		if(keyword == null || keyword.isEmpty()) {
			redirectAttributes.addFlashAttribute("nullKeyword","キーワードが空欄です");
			return "redirect:/";
		}else {
			
			//ここは必要な情報だけを引っ張ってくるべき。またはsessionスコープに格納して抜き取る。コード要修正
			List<Tweet> tweets = tweetService.searchTweetsByKeyword(keyword);
	        Collections.reverse(tweets); // リストを逆順にする
			model.addAttribute("tweets",tweets);
			model.addAttribute("profileImage",session.getAttribute("profileImage"));
			model.addAttribute("username",session.getAttribute("username"));			
			model.addAttribute("tweetUserId",session.getAttribute("tweetUserId"));
	        redirectAttributes.addAttribute("keyword", keyword);
	        
	        List<Comments> commentList = commentService.getAllComments();
	        
	    	
	        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String email = authentication.getName();

	    	Long userId = 	userService.getUserIdByEmail(email);
	    	String tweetUserId = userService.getTweetUserIdByEmail(email);
	    	String username = userService.getUserNameByEmail(email);
	    	String profileImage = userService.getUserProfileImageByEmail(email);
	    	String introduction = userService.getUserIntroduction(email);
	    	
	    	session.setAttribute("profileImage", profileImage);
	        session.setAttribute("username", username);
	        session.setAttribute("tweetUserId", tweetUserId);

	    	
	    	System.out.println("userId :" + userId);
	    	System.out.println("tweetUserId :" + tweetUserId);
	    	System.out.println("username :" + username);
	    	
//	    	各リツイートからの情報をList<Tweet>につっこむ
	        model.addAttribute("userId",userId);
	        model.addAttribute("tweetUserId",tweetUserId);
	        model.addAttribute("username", username);
	        model.addAttribute("profileImage", profileImage);
	        model.addAttribute("introduction",introduction);
	        model.addAttribute("commentList", commentList);

		}
		return "index";
	}
	
	
	@GetMapping("/myPage")
	public String showMyPage(Model model) {
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String email = authentication.getName();

	    Long userId = 	userService.getUserIdByEmail(email);
	    String tweetUserId = userService.getTweetUserIdByEmail(email);
	    String username = userService.getUserNameByEmail(email);
	    String profileImage = userService.getUserProfileImageByEmail(email);
    	String introduction = userService.getUserIntroduction(email);

	    
	    model.addAttribute("userId", userId);
	    model.addAttribute("tweetUserId", tweetUserId);
	    model.addAttribute("username", username);
	    model.addAttribute("profileImage", profileImage);
	    model.addAttribute("introduction",introduction);
	    
		return "myPage";
	}
	
	
    @PostMapping("delete")
    public String deleteTweet(@RequestParam("tweetId")Long tweetId) {
    	tweetService.deleteTweet(tweetId);    	
    	return "redirect:/";
    }
	
	
    
    
//    @PostMapping("/retweet")
//    public ResponseEntity<?> retweet(@RequestParam Long tweetId, @RequestParam Long userId) {
//        // リツイート処理
//        try {
//            Tweet originalTweet = tweetService.findById(tweetId);
//            User user = userService.findById(userId);
//
//            Tweet retweet = new Tweet();
//            retweet.setOriginalTweet(originalTweet);
//            retweet.setUser(user);
//            retweet.setIsRetweet(true);
//            retweet.setRetweetUserName(user.getUsername());
//            retweet.setRetweetUserId(user.getId());
//
//            tweetService.save(retweet);
//
//            return ResponseEntity.ok().body("リツイート成功");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("リツイート失敗");
//        }
//    }
//	
//	@GetMapping("/tweet/{id}")
//	public String getTweet(@PathVariable("id")Long id,Model model) {
//		Tweet tweet = tweetService.getTweet(id);
//		model.addAttribute("tweet",tweet);
//		return "tweet"; 
//	}
//	
//	@GetMapping("/tweetDetail")
//	public String getTweetDetail(@RequestParam("tweetId") Long tweetId, Model model){
//		Tweet tweet = tweetService.getTweet(tweetId);
//		model.addAttribute("tweet",tweet);
//		return "index";
//	}
	
	
//	@GetMapping("/tweetDetail")
//	public ResponseEntity<?> getTweetDetail(@RequestParam("tweetId") Long tweetId){
//		List<Tweet> tweets = tweetService.getRelatedTweets(tweetId);
//		
//		if(tweets.isEmpty()) {
//			return ResponseEntity.notFound().build();
//		}
//		
//		Map<String,Object> response = new HashMap<>();
//		response.put("tweet", tweets);
//		
//		return ResponseEntity.ok(response);
//	}
	
	
	
	
	

	
	
	
}
