package jp.co.eightbit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "userlikes")
public class UserLikes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "tweet_id")
	private Long tweetId;
	@Column(name = "liked")
	private boolean liked;
	
	
	
	
	public UserLikes() {}

	public UserLikes(Long userId, Long tweetId, boolean liked) {
		this.userId = userId;
		this.tweetId = tweetId;
		this.liked = liked;
	}
	public UserLikes(Long userId, Long tweetId) {
		this.userId = userId;
		this.tweetId = tweetId;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTweetId() {
		return tweetId;
	}

	public void setTweetId(Long tweetId) {
		this.tweetId = tweetId;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	@Override
	public String toString() {
		return "UserLikes [id=" + id + ", userId=" + userId + ", tweetId=" + tweetId + ", liked=" + liked + "]";
	} 
	
}
