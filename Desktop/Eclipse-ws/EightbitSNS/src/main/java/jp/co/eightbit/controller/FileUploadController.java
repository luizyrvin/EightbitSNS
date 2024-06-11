package jp.co.eightbit.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.util.StringUtils;

import jp.co.eightbit.entity.Tweet;
import jp.co.eightbit.entity.User;
import jp.co.eightbit.repository.UserRepository;
import jp.co.eightbit.service.TweetService;
import jp.co.eightbit.service.UserService;

@Controller
public class FileUploadController {
    
    private final UserService userService;
    private final UserRepository userRepo;
    private final Path fileLocation;
    private final Tweet tweet;
    private final TweetService tweetService;
    
    @Autowired
    public FileUploadController(UserService userService, UserRepository userRepository,Tweet tweet,TweetService tweetService) {
        this.userService = userService;
        this.userRepo = userRepository;
        this.tweet = tweet;
        this.tweetService = tweetService;
        
        // ファイルの保存先を設定する。必要に応じて適切なパスに変更する。
        this.fileLocation = Paths.get("src/main/resources/static/img");
    }
    
    @PostMapping("/editProfile")
    public String handleFileUpload(@RequestParam(name = "profileImage", required = false) MultipartFile file, @RequestParam("username") String username,
                                   @RequestParam("introduction") String introduction, Model model, RedirectAttributes redirectAttributes) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Long userId = userService.getUserIdByEmail(email);

        try {
            // ユーザーのprofile_image_urlを更新する
            User user = userService.getUserById(userId);
            user.setIntroduction(introduction);
            user.setUserName(username);
            
           if(username != user.getUserName()) {
        	   Tweet tweets = (Tweet) tweetService.getTweetsByUserId(userId);
        	   tweets.setUserName(username);
           }

            if (file != null && !file.isEmpty()) {
                // アップロードされたファイルの拡張子がjpgであるか確認
                String originalFilename = file.getOriginalFilename();
                if (!originalFilename.toLowerCase().endsWith(".jpg")) {
                    redirectAttributes.addFlashAttribute("errorMessage", "JPG形式のファイルのみアップロード可能です");
                    return "redirect:/myPage";
                }

                // ファイルを保存する
                String filename = userId + ".jpg";
                Path filePath = fileLocation.resolve(filename);
                user.setProfileImageUrl("/img/" + filename);
                // ファイルが存在する場合は削除してから新しいファイルを保存
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
                Files.copy(file.getInputStream(), filePath);
            }

            model.addAttribute("profileImage", user.getProfileImageUrl());
            userRepo.save(user);

            redirectAttributes.addFlashAttribute("successMessage", "更新が完了しました");

            return "redirect:/myPage";
        } catch (IOException e) {
            e.printStackTrace();
            return "ファイルのアップロード中にエラーが発生しました";
        }
    }

    
    

}