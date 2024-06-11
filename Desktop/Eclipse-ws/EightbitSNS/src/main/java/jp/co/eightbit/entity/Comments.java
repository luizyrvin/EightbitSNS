package jp.co.eightbit.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public class Comments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "tweet_id")
	private Long tweetId;
	@Column(name = "user_id")
	private Long user_Id;
	@Column(name = "text")
	private String text;
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	@Column(name = "username")
	private String userName;
	@Column(name = "tweet_user_id")
	private String tweetUserId;
	
	@ManyToOne
	@JoinColumn(name = "user_id",referencedColumnName = "user_id", insertable = false,updatable = false)
    private User user;
	
//	private String TweetUserId;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTweetId() {
		return tweetId;
	}
	public void setTweetId(Long tweetId) {
		this.tweetId = tweetId;
	}
	public Long getUser_Id() {
		return user_Id;
	}
	public void setUser_Id(Long user_Id) {
		this.user_Id = user_Id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

public String getUserName() {
		return user.getUserName();
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTweetUserId() {
		return tweetUserId;
	}
	public void setTweetUserId(String tweetUserId) {
		this.tweetUserId = tweetUserId;
	}
	//	public String getTweetUserId() {
//		return TweetUserId;
//	}
//	public void setTweetUserId(String tweetUserId) {
//		TweetUserId = tweetUserId;
//	}
	@Override
	public String toString() {
		return "Comments [id=" + id + ", tweetId=" + tweetId + ", user_Id=" + user_Id + ", text=" + text
				+ ", createdAt=" + createdAt + "]";
	}

	
	
	
}
