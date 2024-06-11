package jp.co.eightbit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.eightbit.entity.Retweet;

public interface RetweetRepository extends JpaRepository<Retweet, Long>{

}
