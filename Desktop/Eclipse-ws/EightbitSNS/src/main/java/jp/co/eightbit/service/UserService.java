package jp.co.eightbit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jp.co.eightbit.entity.LoginUserDetails;
import jp.co.eightbit.entity.User;
import jp.co.eightbit.repository.TweetRepository;
import jp.co.eightbit.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public User getUserById(Long userId) {
    	return userRepo.findById(userId).orElse(null);
    }
    
    public Long getUserIdByUserName(String username) {
    	User user = userRepo.findByUserName(username);
    	if(user != null) {
    		return user.getUserId();
    	}else {
    		return null;
    	}
    }
    
    public Long getUserIdByEmail(String email) {
    	return userRepo.findByEmail(email).get().getUserId();
    }

	public String getTweetUserIdByEmail(String email) {
		User user = userRepo.findByEmail(email).orElse(null);
		if(user != null) {
			return user.getTweetUserId();
		}
		return null;
	}
	
	public String getUserNameByEmail(String email) {
		return userRepo.findByEmail(email).get().getUserName();
	}
    
    public List<User> getAllUsers(){
    	return userRepo.findAll();
    }
    
    public List<User> searchUserByKeyword(String keyword){
    	return userRepo.findByUserNameContaining(keyword);
    }
    
    
    @Transactional
    public void registerUser(User user) {
    	//パスワードのハッシュ化
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    	user.setUserName(user.getUserName());
    	user.setEmail(user.getEmail());
    	user.setProfileImageUrl(user.getProfileImageUrl());
    	user.setRegisterDate(user.getRegisterDate());
    	user.setRoles("USER");
    	
    	userRepo.save(user);
    }
    
//    public boolean authenticateUser(String email, String password) {
//    	User user = userRepo.findByEmail(email);
//    	return user != null && passwordEncoder.matches(password, user.getPassword());
//    }
    
    public User updateUser(Long userId, User updatedUser) {
    	User editUser = userRepo.findById(userId).orElse(null);
    	if(editUser != null) {
    		editUser.setUserName(updatedUser.getUserName());
    		editUser.setEmail(updatedUser.getEmail());
    		editUser.setProfileImageUrl(updatedUser.getProfileImageUrl());
    		editUser.setRegisterDate(updatedUser.getRegisterDate());
    		
    		return userRepo.save(editUser);
    	}
    	return null;
    }
    
    public void deleteUser(Long userId) {
    	userRepo.deleteById(userId);
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
    	Optional<User> _user = userRepo.findByEmail(email);
    	return _user.map(user -> new LoginUserDetails(user))
    		.orElseThrow(() -> new UsernameNotFoundException("not found email=" + email));
    }

}
