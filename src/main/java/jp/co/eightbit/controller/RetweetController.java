package jp.co.eightbit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.eightbit.entity.Retweet;
import jp.co.eightbit.service.RetweetService;

@RestController
public class RetweetController {
    private final RetweetService retweetService;

    @Autowired
    public RetweetController(RetweetService retweetService) {
        this.retweetService = retweetService;
    }

    @PostMapping("/retweets")
    public ResponseEntity<?> retweet(@RequestBody Retweet retweet) {
        try {
            retweetService.retweet(retweet.getUserId(), retweet.getTweetId());
            Retweet responseDTO = new Retweet("Retweeted successfully");
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to retweet: " + e.getMessage());
        }
    }
}
