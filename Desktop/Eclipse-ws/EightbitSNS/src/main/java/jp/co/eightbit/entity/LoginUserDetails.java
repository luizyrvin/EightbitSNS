package jp.co.eightbit.entity;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginUserDetails implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String email;
	private final String password;
	private final String name;
	private final Collection <? extends GrantedAuthority> authorities;
	private final Long userId;
	
	public LoginUserDetails(User user) {
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.name = user.getUserName();
		this.authorities = Arrays.stream(user.getRoles().split(","))
				.map(role -> new SimpleGrantedAuthority(role))
				.toList();
		this.userId = user.getUserId();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		//ロールのコレクションを返す
		return authorities;
	}
	
	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	public String getName() {
		return name;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		//ユーザーが期限切れでなければtrueを返す
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		//ユーザーがロックされていなければtrueを返す
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		//ユーザーが期限切れでなければtrueを返す
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		//ユーザーが有効ならtrueを返す
		return true;
	}

	
	
}
