package com.a203.sixback.user;

import com.a203.sixback.auth.UserPrincipal;
import com.a203.sixback.db.entity.User;
import com.a203.sixback.ranking.RankingService;
import com.a203.sixback.user.res.*;
import com.a203.sixback.util.model.BaseResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RankingService rankingService;

    @Autowired
    public UserController(UserService userService, RankingService rankingService) {
        this.userService = userService;
        this.rankingService = rankingService;
    }

    @GetMapping("")
    public ResponseEntity<? extends BaseResponseBody> getUserDetails(){
        ResGetUserDetailsDTO responseBody;
        try{
            responseBody = userService.getUserDetails();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(BaseResponseBody.of(400,"잘못된 요청입니다."));
        }
        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/boards/{page}")
    public ResponseEntity<? extends BaseResponseBody> getUserBoards(@RequestParam(value = "page", required = false) Integer page){
        ResGetUserBoardsDTO responseBody;
        int pages = 1;

        if(page != null) {
            pages = page;
        }

        try{
            responseBody = userService.getUserBoards(pages);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(BaseResponseBody.of(400,"잘못된 요청입니다."));
        }
        return ResponseEntity.ok().body(responseBody);

    }

    @GetMapping("/comments")
    public ResponseEntity<? extends BaseResponseBody> getUserComments(){
        ResGetUserCommentsDTO responseBody;
        try{
            responseBody = userService.getUserComments();
        } catch (Exception e){
            return ResponseEntity.badRequest().body(BaseResponseBody.of(400,"잘못된 요청입니다."));
        }
        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/predicts")
    public ResponseEntity<? extends BaseResponseBody> getUserPredicts(){
        ResGetUserPredictsDTO responseBody;
        try{
            responseBody = userService.getUserPredicts();
        } catch (Exception e){
            return ResponseEntity.badRequest().body(BaseResponseBody.of(400,"잘못된 요청입니다."));
        }
        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/points/{page}")
    public ResponseEntity<? extends BaseResponseBody> getUserPoints(@RequestParam(value = "page", required = false) Integer page){
        ResGetUserPointDTO responseBody;

        int pages = 1;

        if(page != null) {
            pages = page;
        }

        try{
            responseBody = userService.getUserPoint(pages);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(BaseResponseBody.of(400,"잘못된 요청입니다."));
        }
        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/ranks")
    public ResponseEntity<? extends BaseResponseBody> getUserRank(){
        ResGetUserRankDTO responseBody;

        try{
            responseBody = userService.getUserRank();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(BaseResponseBody.of(400, "잘못된 요청입니다."));
        }
        return ResponseEntity.ok().body(responseBody);
    }
}
