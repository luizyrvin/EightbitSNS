package jp.co.eightbit.entity;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "tweets")
public class Tweet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tweet_id")
	private Long tweetId;//主キー
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "tweet_user_id")
    private String tweetUserId;//usersテーブルのuser_idに対応する列
	@Column(name = "username")
	private String userName;//Userエンティティのusernameと一緒
	@Column(name = "tweet_text")
    private String tweetText;
	@Column(name = "media_file")
    private String mediaFile;
	@Column(name = "post_date")
    private LocalDateTime postDate;
	@Column(name = "likes")
    private int likes;
	@Column(name = "retweets")
    private int retweets;
	@Column(name = "comments")
    private int comments;
	@Column(name = "profile_image_url")
	private String profileImageUrl;
	
	@ManyToOne
	@JoinColumn(name = "user_id",referencedColumnName = "user_id", insertable = false,updatable = false)
    private User user;
    
    
    //getter,setter
	public Long getTweetId() {
		return tweetId;
	}
	public void setTweetId(Long tweetId) {
		this.tweetId = tweetId;
	}
	public String getTweetUserId() {
		return tweetUserId;
	}
	public void setTweetUserId(String tweetUserId) {
		this.tweetUserId = tweetUserId;
	}


	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return user.getUserName();
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTweetText() {
		return tweetText;
	}
	public void setTweetText(String tweetText) {
		this.tweetText = tweetText;
	}
	public String getMediaFile() {
		return mediaFile;
	}
	public void setMediaFile(String mediaFile) {
		this.mediaFile = mediaFile;
	}
	public LocalDateTime getPostDate() {
		return postDate;
	}
	public void setPostDate(LocalDateTime postDate) {
		this.postDate = postDate;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getRetweets() {
		return retweets;
	}
	public void setRetweets(int retweets) {
		this.retweets = retweets;
	}
	public int getComments() {
		return comments;
	}
	public void setComments(int comments) {
		this.comments = comments;
	}
	public String getProfileImageUrl() {
		return user.getProfileImageUrl();
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	@Override
	public String toString() {
		return "Tweet [tweetId=" + tweetId + ", tweetUserId=" + tweetUserId + ", tweetText=" + tweetText
				+ ", mediaFile=" + mediaFile + ", postDate=" + postDate + ", likes=" + likes + ", retweets=" + retweets
				+ ", comments=" + comments + "]";
	}


    
    
    
}
