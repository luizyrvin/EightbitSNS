package jp.co.eightbit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.eightbit.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    List<User> findByUserNameContaining(String keyword);
    Optional<User> findByEmail(String email);
	User findByUserName(String username);
	User findByTweetUserId(String email);
//	Optional<User> findByEmail(String email);

}
