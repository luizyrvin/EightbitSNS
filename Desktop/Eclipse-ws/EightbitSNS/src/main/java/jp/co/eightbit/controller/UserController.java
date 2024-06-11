package jp.co.eightbit.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.eightbit.entity.Tweet;
import jp.co.eightbit.entity.User;
import jp.co.eightbit.service.TweetService;
import jp.co.eightbit.service.UserService;

@Controller
public class UserController {
	private final UserService userService;
	private final TweetService tweetService;
	
	@Autowired
	public  UserController(UserService userService,TweetService tweetService) {
		this.userService = userService;
		this.tweetService = tweetService;
	}
	
//	@GetMapping("/")
//	public String indexPage() {
//		return "index";	
//	}
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
//	@PostMapping("/login")
//	public String login(@RequestParam("email") String email, @RequestParam("password") String password,RedirectAttributes redirectAttributes) {
//		if(userService.authenticateUser(email, password)) {
//			return "redirect:/index";
//		}else {
//			redirectAttributes.addFlashAttribute("error", "ログインに失敗しました");
//			return "redirect:/login";
//		}
//	}
//	
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@RequestParam("username") String username,@RequestParam("email")String email,@RequestParam("password")String password,@RequestParam("tweetUserId")String tweetUserId,RedirectAttributes redirectAttributes) {
		String message ="";
//	public String register(@ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		//あとからバリデーション処理を記述	
		
//		ユーザー登録処理
		if(username != null && !username.isEmpty() && email != null && !email.isEmpty()) {
			User user = new User();
			user.setUserName(username);
			user.setTweetUserId(tweetUserId);
			user.setEmail(email);
			user.setPassword(password);
			user.setRegisterDate(LocalDateTime.now());
			user.setProfileImageUrl("/img/default.jpg");
			
			if(user.getIntroduction() == null || user.getIntroduction().isEmpty()) {
				user.setIntroduction("よろしくお願いします");
			}
			
			userService.registerUser(user);
			
	
			
			message = "アカウントの登録が完了しました";
			redirectAttributes.addFlashAttribute("message", message);
			System.out.println("tweetUserId: " + tweetUserId);
		}else {
			message = "アカウントの登録中にエラーが発生しました";
			redirectAttributes.addFlashAttribute("message", message);
		}
		return "redirect:/login";
	}
	
	@GetMapping("/profile/{userId}")
	public String userProfile(@PathVariable("userId") Long userId,Model model) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
    	Long loginUserId = userService.getUserIdByEmail(email);
		
		System.out.println(email);
		System.out.println(loginUserId);

		User profile = userService.getUserById(userId);
		List<Tweet> userTweets = tweetService.getTweetsByUserId(userId);
		Collections.reverse(userTweets);
		
		model.addAttribute("userTweets",userTweets);
		model.addAttribute("profile",profile);
		model.addAttribute("loginUserId",loginUserId);
		
		return "profile";
	}
	


}