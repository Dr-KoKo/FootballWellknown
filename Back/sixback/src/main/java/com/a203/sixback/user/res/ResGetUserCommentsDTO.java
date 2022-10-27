//package com.a203.sixback.user.res;
//
//import com.a203.sixback.db.entity.CommentMongo;
//import com.a203.sixback.util.model.BaseResponseBody;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class ResGetUserCommentsDTO extends BaseResponseBody {
//
//    List<UserCommentsDTO> userCommentsDTOList;
//
//    public static ResGetUserCommentsDTO of(Integer statusCode, String message, List<CommentMongo> commentMongoList){
//        ResGetUserCommentsDTO body = new ResGetUserCommentsDTO();
//
//        body.setStatusCode(statusCode);
//        body.setMessage(message);
//
//        body.userCommentsDTOList = commentMongoList.stream().map(UserCommentsDTO::new).collect(Collectors.toList());
//
//        return body;
//    }
//
//    private static class UserCommentsDTO{
//        private Long boardId;
//        private String comment;
//        private LocalDateTime createDate;
//
//        private UserCommentsDTO(CommentMongo commentMongo){
//            this.boardId = commentMongo.getBoardId();
//            this.comment = commentMongo.getComment();
//            this.createDate = commentMongo.getCreateDate();
//        }
//    }
//
//}
