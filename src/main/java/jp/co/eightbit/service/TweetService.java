package jp.co.eightbit.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jp.co.eightbit.entity.Tweet;
import jp.co.eightbit.entity.UserLikes;
import jp.co.eightbit.repository.TweetRepository;
import jp.co.eightbit.repository.UserLikesRepository;

@Service
public class TweetService {
	@Autowired
	private final TweetRepository tweetRepo;
    @Autowired
    private UserLikesRepository userLikesRepo;

	
	
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

	public List<Tweet> getRelatedTweets(Long tweetId){
	    Tweet tweet = tweetRepo.findById(tweetId).orElse(null);
	    
	    List<Tweet> relatedTweets = new ArrayList<>();
	    if(tweet != null) {
	    	relatedTweets.add(tweet);
	    }
	    return relatedTweets;
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
	
//	public int likeTweet(Long userId, Long tweetId) {
//		Tweet tweet = tweetRepo.findById(tweetId).orElse(null);
//		if(tweet != null) {
//			tweet.setLikes(tweet.getLikes() + 1);
//			tweetRepo.save(tweet);
//			return tweet.getLikes(); //いいねの数を返す
//		}
//		return 0;  //ツイートが見つからなければ0
//	}
//	
	
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
	
    // いいねを追加するメソッド
    public void likeTweet(Long userId, Long tweetId) {
        UserLikes userLikes = new UserLikes(userId, tweetId, true);
        userLikesRepo.save(userLikes);
    }	
    // いいねを解除するメソッド
    public void unlikeTweet(Long userId, Long tweetId) {
    	userLikesRepo.deleteByUserIdAndTweetId(userId, tweetId);
    }
    // いいねの総数を取得するメソッド
    public int getLikesCount(Long tweetId) {
        return userLikesRepo.countByTweetId(tweetId);
    }
    public int getCommentsCount(Long tweetId) {
    	return userLikesRepo.countByTweetId(tweetId);
    }
    // ユーザーが指定されたツイートにいいねをしているかどうかを確認するメソッド
    public boolean isLikedByUser(Long userId, Long tweetId) {
        return userLikesRepo.existsByUserIdAndTweetId(userId, tweetId);
    }

	public void saveLike(Long tweetId, Long userId) {
	    UserLikes userLikes = new UserLikes();
	    userLikes.setTweetId(tweetId);
	    userLikes.setUserId(userId);
	    userLikesRepo.save(userLikes);
	}

	@Transactional
	public void incrementLikesCount(Long tweetId, Long userId) {
		Tweet tweet = tweetRepo.findById(tweetId).orElse(null);
		if(tweet != null) {
			int currentLikes = tweet.getLikes();
			tweet.setLikes(currentLikes + 1);
			tweetRepo.save(tweet);
		}
	}

	public void decrementLikesCount(Long tweetId, Long userId) {
		Tweet tweet = tweetRepo.findById(tweetId).orElse(null);
		if(tweet != null) {
			int currentLikes = tweet.getLikes();
			tweet.setLikes(currentLikes - 1);
			tweetRepo.save(tweet);
		}
	}
	
	public void incrementCommentsCount(Long tweetId, Long userId) {
		Tweet tweet = tweetRepo.findById(tweetId).orElse(null);
		if(tweet != null) {
			int currentComments = tweet.getComments();
			tweet.setComments(currentComments + 1);
			tweetRepo.save(tweet);
		}
	}
	
	public void decrementCommentsCount(Long tweetId, Long userId) {
		Tweet tweet = tweetRepo.findById(tweetId).orElse(null);
		if(tweet != null) {
			int currentComments = tweet.getComments();
			tweet.setComments(currentComments - 1);
			tweetRepo.save(tweet);
		}
	}



	
	
	
//	//いいねの追加機能
//	public void incrementLikesCount(Long tweetId) {
//		Tweet tweet = tweetRepo.findById(tweetId).orElse(null);
//		if(tweet != null) {
//			int currentLikesCount = tweet.getLikes();
//			if(currentLikesCount < 0) {
//				tweet.setLikes(currentLikesCount + 1);
//				tweetRepo.save(tweet);
//			}
//		}
//	}
//	
//	
//	
//	//いいねの解除機能
//	public void decrementLikesCount(Long tweetId) {
//		Tweet tweet = tweetRepo.findById(tweetId).orElse(null);
//		if(tweet != null) {
//			int currentLikesCount = tweet.getLikes();
//			if(currentLikesCount > 0) {
//				tweet.setLikes(currentLikesCount - 1);
//				tweetRepo.save(tweet);
//			}
//		}
//	}
	
	
	

	
//	public void saveTweet(Tweet tweet) {
//	     LocalDateTime now = LocalDateTime.now(); 
//	     LocalDateTime expirationTime = now.plusHours(3); 
//	     tweet.setExpirationTime(expirationTime); 
//	     tweetRepository.save(tweet); 
//	} 
}

