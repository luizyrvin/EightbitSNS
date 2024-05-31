package jp.co.eightbit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.eightbit.entity.UserLikes;

@Repository
public interface UserLikesRepository extends JpaRepository<UserLikes, Long>{
    boolean existsByUserIdAndTweetId(Long userId, Long tweetId);
    void deleteByUserIdAndTweetId(Long userId,Long tweetId);
	int countByTweetId(Long tweetId);
}
