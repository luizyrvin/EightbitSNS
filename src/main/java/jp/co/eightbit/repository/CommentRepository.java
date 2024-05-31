package jp.co.eightbit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.eightbit.entity.Comments;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long>{

	List<Comments> findByTweetId(Long tweetId);

}
