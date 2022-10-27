package com.a203.sixback.user.res;

import com.a203.sixback.db.entity.Board;
import com.a203.sixback.db.entity.Matches;
import com.a203.sixback.db.entity.User;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ResGetUserBoardsDTO extends BaseResponseBody {

    private List<GetBoardDTO> boardList;

    public static ResGetUserBoardsDTO of(Integer statusCode, String message, List<GetBoardDTO> boardList) {
        ResGetUserBoardsDTO body = new ResGetUserBoardsDTO();

        body.setStatusCode(statusCode);
        body.setMessage(message);

        body.setBoardList(boardList);

        return body;
    }

    @Getter
    public static class GetBoardDTO{
        private String title;
        private String category;
        private String content;
        private String writer;
        private LocalDateTime createDateTime;
        private Long matchId;

        public GetBoardDTO(Board board, User author, Long match){
            this.title = board.getTitle();
            this.category = board.getCategory().getCtgName().toString();
            this.content = board.getContent();
            this.writer = author.getNickname();
            this.createDateTime = board.getCreateDate();
            this.matchId = match;
        }
    }
}
