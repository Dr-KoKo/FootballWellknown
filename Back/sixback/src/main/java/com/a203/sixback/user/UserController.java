package com.a203.sixback.user;

import com.a203.sixback.user.res.*;
import com.a203.sixback.util.model.BaseResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
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

    @GetMapping("/boards")
    public ResponseEntity<? extends BaseResponseBody> getUserBoards(){
        ResGetUserBoardsDTO responseBody;
        try{
            responseBody = userService.getUserBoards();
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

    @GetMapping("/points")
    public ResponseEntity<? extends BaseResponseBody> getUserPoints(){
        ResGetUserPointDTO responseBody;
        try{
            responseBody = userService.getUserPoint();
        } catch (Exception e){
            return ResponseEntity.badRequest().body(BaseResponseBody.of(400,"잘못된 요청입니다."));
        }
        return ResponseEntity.ok().body(responseBody);
    }

}
