package jp.co.eightbit.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.eightbit.entity.Retweet;
import jp.co.eightbit.entity.Tweet;
import jp.co.eightbit.repository.RetweetRepository;
import jp.co.eightbit.repository.TweetRepository;
import java.time.LocalDateTime;

@Service
public class RetweetService {
	
    private final RetweetRepository retweetRepo;
    private final TweetRepository tweetRepo;

    @Autowired
    public RetweetService(RetweetRepository retweetRepository, TweetRepository tweetRepository) {
        this.retweetRepo = retweetRepository;
        this.tweetRepo = tweetRepository;
    }
    
    
    public void retweet(Long userId, Long tweetId) {
        // リツイートを作成し、リポジトリに保存します
        Retweet retweet = new Retweet();
        retweet.setUserId(userId);
        retweet.setTweetId(tweetId);
        retweet.setCreatedAt(LocalDateTime.now());
        retweetRepo.save(retweet);
    }
    
//    public void retweet(Long userId, Long tweetId) {
//    	//tweeIdからリツイート元のツイートを取得
//    	Optional<Tweet> optionalTweet = tweetRepo.findById(tweetId);
//    	
//    	if(optionalTweet.isPresent()) {
//    		Tweet originalTweet = optionalTweet.get();
//    		//リツイート情報を作成してDBに保存
//    		Retweet retweet = new Retweet();
//    		retweet.setUserId(userId);
//    		retweet.setTweetId(originalTweet.getTweetId());
//    	}
//    }
    
    

}
