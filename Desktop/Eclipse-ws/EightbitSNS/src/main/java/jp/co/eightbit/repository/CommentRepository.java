package jp.co.eightbit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.co.eightbit.entity.Comments;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long>{

    @Query(value = "SELECT * FROM comments WHERE tweet_id = ?1", nativeQuery = true)
	List<Comments> findByTweetId(Long tweetId);

}
