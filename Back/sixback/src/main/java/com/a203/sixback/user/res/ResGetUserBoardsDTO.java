package com.a203.sixback.user.res;

import com.a203.sixback.db.entity.Board;
import com.a203.sixback.db.entity.User;
import com.a203.sixback.util.model.BaseResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ResGetUserBoardsDTO extends BaseResponseBody {

    private List<GetBoardDTO> boardList;

    public static ResGetUserBoardsDTO of(Integer statusCode, String message, List<Board> boardList) {
        ResGetUserBoardsDTO body = new ResGetUserBoardsDTO();

        body.setStatusCode(statusCode);
        body.setMessage(message);

        body.boardList = boardList.stream().map(GetBoardDTO::new).collect(Collectors.toList());

        return body;
    }

    private static class GetBoardDTO{
        private String title;
        private String category;
        private String content;
        private String writer;
        private LocalDateTime createDateTime;
        private Long matchId;

        private GetBoardDTO(Board board){
            this.title = board.getTitle();
            this.category = board.getCategory().toString();
            this.content = board.getContent();
            this.writer = board.getUser().getNickname();
            this.createDateTime = board.getCreateDate();
            this. matchId = board.getMatch().getId();
        }
    }
}
