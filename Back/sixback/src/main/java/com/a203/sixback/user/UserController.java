package com.a203.sixback.user;

import com.a203.sixback.user.res.ResGetUserBoardsDTO;
import com.a203.sixback.user.res.ResGetUserDetailsDTO;
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
            return ResponseEntity.badRequest().body(BaseResponseBody.of(400,"잘못된 요청입니다."));
        }
        return ResponseEntity.ok().body(responseBody);
    }

//    @GetMapping("/boards")
//    public ResponseEntity<? extends BaseResponseBody> getUserBoards(){
//        ResGetUserBoardsDTO responseBody;
//        try{
//            responseBody = userService.getUserBoards();
//        } catch (Exception e){
//            return ResponseEntity.badRequest().body(responseBody.of(400,"잘못된 요청입니다."));
//        }
//        return ResponseEntity.ok().body(responseBody);
//
//    }
}
