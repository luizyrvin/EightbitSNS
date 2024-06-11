package jp.co.eightbit.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.eightbit.entity.Comments;
import jp.co.eightbit.entity.Tweet;
import jp.co.eightbit.repository.CommentRepository;

@Service
public class CommentService {
	 @Autowired
	 private CommentRepository commentRepo;
	 
	 
	 
	 
	 
	 public List<Comments> getAllComments() {
	     return commentRepo.findAll();
	 }
	
	 public Comments getCommentById(Long commentId) {
	     return commentRepo.findById(commentId).orElse(null);   
	 }
	 
	 public void saveComment(Comments comment) {
		 commentRepo.save(comment);
	 }

	public List<Comments> getAllCommentsByTweetId(Long tweetId) {
		return commentRepo.findByTweetId(tweetId);
	}
	
	 
	 
	 
	 
}
