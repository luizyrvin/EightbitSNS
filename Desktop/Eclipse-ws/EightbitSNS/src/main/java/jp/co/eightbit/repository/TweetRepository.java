package jp.co.eightbit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.eightbit.entity.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long>{
	List<Tweet> findByTweetTextContaining(String keyword);
	List<Tweet> findAllByOrderByPostDateDesc();
    Tweet findByTweetUserId(String userId);
    
    String findByTweetId(Long tweetId);
    List<Tweet> findAllByUserId(Long userId);

	
}
