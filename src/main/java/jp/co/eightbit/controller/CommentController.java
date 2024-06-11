package jp.co.eightbit.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.events.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;
import jp.co.eightbit.entity.Comments;
import jp.co.eightbit.repository.CommentRepository;
import jp.co.eightbit.service.CommentService;
import jp.co.eightbit.service.TweetService;
import jp.co.eightbit.service.UserService;

@Controller
public class CommentController {
	@Autowired
	private final CommentRepository commentRepo;
	@Autowired
    private final CommentService commentService;
	@Autowired
	private final UserService userService;
	@Autowired
	private final TweetService tweetService;
	

	
	public CommentController(CommentRepository commentRepo, CommentService commentService,UserService userService,TweetService tweetService) {
		this.commentRepo = commentRepo;
		this.commentService = commentService;
		this.userService = userService;
		this.tweetService = tweetService;
	}
	
//	@PostMapping("/comment")
//	public String postComment(@ModelAttribute("comment") Comments comment, 
//			@RequestParam("userId") Long userId, @RequestParam("tweetId") Long tweetId) {
//		
//		comment.setUser_Id(userId);
//		comment.setTweetId(tweetId);
//
//		commentService.saveComment(comment);
//		return "redirect:/";
//	}
	
	
	@PostMapping("/comment")
	public String postComment(@ModelAttribute("comment") Comments comment, 
			@RequestParam("userId") Long userId, @RequestParam("tweetId") Long tweetId,
			@RequestParam("text") String text,@RequestParam("username") String userName,
			@RequestParam("tweetUserId")String tweetUserId) {
//		String tweetUserId = userService.getTweetUserIdByUserId(userId);
//		
//		comment.setTweetUserId(tweetUserId);
		comment.setUser_Id(userId);
		comment.setTweetId(tweetId);
		comment.setText(text);
		comment.setCreatedAt(LocalDateTime.now());
		comment.setUserName(userName);
		comment.setTweetUserId(tweetUserId);
		comment.setCreatedAt(LocalDateTime.now());
		System.out.println(comment);
		//DBにコメントを保存
		commentService.saveComment(comment);
		tweetService.incrementCommentsCount(tweetId, userId);
		
		return "redirect:/";
	}
	
//	@PostMapping("/comment")
//	public String postComment(@ModelAttribute("comment") Comments comment, 
//			@RequestParam("userId") Long userId, @RequestParam("tweetId") Long tweetId,
//			@RequestParam("text") String text,RedirectAttributes redirectAttributes,Model model) {
//		
//		comment.setUser_Id(userId);
//		comment.setTweetId(tweetId);
//		comment.setText(text);
//		comment.setCreatedAt(LocalDateTime.now());
//		System.out.println(comment);
//
//		commentService.saveComment(comment);
//		model.addAttribute("comment",comment);
//		return "/";
//	}
	
	
	
	
	
	@GetMapping("/comment")
	public String commentList(Model model) {
		List<Comments> comments = commentService.getAllComments();
        model.addAttribute("comments", comments);
        
        return "index";
	}
}
