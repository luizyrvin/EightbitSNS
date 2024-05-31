package jp.co.eightbit.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jp.co.eightbit.entity.Tweet;
import jp.co.eightbit.repository.TweetRepository;

@Service
public class TweetService {
	private final TweetRepository tweetRepo;
	
	@Autowired
	public TweetService(TweetRepository tweetRepository) {
		this.tweetRepo = tweetRepository;
	}
	
	public List<Tweet> getLatestTweets(){
		return tweetRepo.findAllByOrderByPostDateDesc();
	}
	
	public List<Tweet> searchTweetsByKeyword(String keyword){
		return tweetRepo.findByTweetTextContaining(keyword);
	}
	
	public Tweet getTweet(Long tweetId){
		return tweetRepo.findById(tweetId).orElse(null);
	}
	
	
	public Long getTweetId(Long tweetId) {
	    Tweet tweet = tweetRepo.findById(tweetId).orElse(null);
	    return (tweet != null) ? tweet.getTweetId() : null;
	}
	
	public void updateTweet(Long tweetId, String newTweetText) {
		Tweet tweet = tweetRepo.findById(tweetId).orElse(null);
		if(tweet != null) {
			tweet.setTweetText(newTweetText);
			tweetRepo.save(tweet);
		}
	}
	
	public void deleteTweet(Long tweetId) {
		tweetRepo.deleteById(tweetId);
	}
	
	public void likeTweet(Long userId, Long tweetId) {
		Tweet tweet = tweetRepo.findById(tweetId).orElse(null);
		if(tweet != null) {
			tweet.setLikes(tweet.getLikes() + 1);
			tweetRepo.save(tweet);
		}
	}
	
	
	public void retweet(Long userId, Long tweetId,String tweetUserId) {
		Tweet originalTweet = tweetRepo.findById(tweetId).orElse(null);
		if(originalTweet != null) {
			Tweet retweet = new Tweet();
			retweet.setTweetUserId(tweetUserId);
			retweet.setTweetText(originalTweet.getTweetText());
			
			tweetRepo.save(retweet);
		}
	}
	
	public void commentOnTweet(Long userId, Long tweetId, String commentText,String tweetUserId) {
		Tweet originalTweet = tweetRepo.findById(tweetId).orElse(null);
		
		if(originalTweet != null) {
			Tweet comment = new Tweet();
			comment.setTweetUserId(tweetUserId);
			comment.setTweetText(commentText);
			comment.setPostDate(LocalDateTime.now());
			comment.setRetweets(0);
			comment.setLikes(0);
			comment.setComments(0);
			comment.setTweetId(tweetId);
			
			tweetRepo.save(comment);
		}
	}
	
	public void saveTweet(Tweet tweet) {
		tweet.setPostDate(LocalDateTime.now());
		
		tweetRepo.save(tweet);
	}
	

	
//	public void saveTweet(Tweet tweet) {
//	     LocalDateTime now = LocalDateTime.now(); 
//	     LocalDateTime expirationTime = now.plusHours(3); 
//	     tweet.setExpirationTime(expirationTime); 
//	     tweetRepository.save(tweet); 
//	} 
}

