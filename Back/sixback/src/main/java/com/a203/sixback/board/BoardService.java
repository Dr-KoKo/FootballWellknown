package com.a203.sixback.board;


import com.a203.sixback.board.dto.GetBoardDetailResDTO;
import com.a203.sixback.board.dto.GetBoardResDTO;
import com.a203.sixback.board.dto.PostBoardReqDTO;
import com.a203.sixback.board.dto.UpdateBoardReqDTO;
import com.a203.sixback.board.res.BoardRes;
import com.a203.sixback.db.entity.Board;
import com.a203.sixback.db.entity.Category;
import com.a203.sixback.db.entity.Match;
import com.a203.sixback.db.entity.User;
import com.a203.sixback.db.repo.BoardRepo;
import com.a203.sixback.db.repo.CtgRepo;
import com.a203.sixback.db.repo.MatchRepo;
import com.a203.sixback.db.repo.UserRepo;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepo boardRepo;
    private final UserRepo userRepo;

    private final CtgRepo ctgRepo;

    private final MatchRepo matchRepo;

    public List<GetBoardResDTO> findAll() {
        List<Board> boards =  boardRepo.findAll();
        List<GetBoardResDTO> getBoards = new LinkedList<>();

        for(Board board : boards){
            getBoards.add(new GetBoardResDTO().builder()
                    .id(board.getId())
                    .title(board.getTitle())
                        // author 이름으로 나중에 바꾸기
                    .author(board.getUser().getNickname())
                    .build()
            );
                        
//            System.out.println(board);
        }
        return getBoards;
    }

    public ResponseEntity createBoard(PostBoardReqDTO postBoardReqDTO, Long userId) {
        User user = null;
        try {
            user = userRepo.findById(userId).get();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400,  "No User"));
        }

        Category category = ctgRepo.findByCtgName(postBoardReqDTO.getCtgName());
        if(postBoardReqDTO.getMatchId() == null) {
            Board board = Board.builder()
                    .title(postBoardReqDTO.getTitle())
                    .content(postBoardReqDTO.getContent())
                    .category(category)
                    .user(user)
                    .build();
            boardRepo.save(board);
        }
        else {
            Match match = null;
            try {
                match = matchRepo.findById(postBoardReqDTO.getMatchId()).get();
            } catch (Exception e) {
                return ResponseEntity.status(400).body(BaseResponseBody.of(400,  "No Match"));
            }
            Board board = Board.builder()
                    .title(postBoardReqDTO.getTitle())
                    .content(postBoardReqDTO.getContent())
                    .category(category)
                    .user(user)
                    .build();
            boardRepo.save(board);
        }
        return ResponseEntity.ok(BaseResponseBody.of(200, "Post Board Success"));
    }

    public GetBoardDetailResDTO getBoardDetail(long boardId) {
        Board board = boardRepo.findById(boardId).get();
        GetBoardDetailResDTO getDetail = new GetBoardDetailResDTO().builder()
                .id(boardId)
                .title(board.getTitle())
                .ctgName(board.getCategory().getCtgName())
                .content(board.getContent())
//                .author(board.getUser().getUserName)
                .author(board.getUser().getId())
                .createDate(board.getCreateDate())
                .build();
    return getDetail;

    }

    public ResponseEntity updateBoard(UpdateBoardReqDTO updateBoardReqDTO, Long userId) {
        User user = null;
        try {
            user = userRepo.findById(userId).get();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400,  "No User"));
        }
        Category category = ctgRepo.findByCtgName(updateBoardReqDTO.getCtgName());

        Board board = null;
        try {
            board = boardRepo.findById(updateBoardReqDTO.getBoardId()).get();
        } catch(Exception e) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400,  "No Board"));
        }

        if(board.getUser() != user){
//        if(board.getUser() != user || user.getRoll().eqauls("ADMIN")){
            return ResponseEntity.status(405).body(BaseResponseBody.of(405,  "No Authorization"));
        }

        board.setCategory(category);
        board.setTitle(updateBoardReqDTO.getTitle());
        board.setContent(updateBoardReqDTO.getContent());
        boardRepo.save(board);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Update Board Success"));
    }

    public ResponseEntity deleteBoard(Long boardId, Long userId) {
        User user = null;
        try {
            user = userRepo.findById(userId).get();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400,  "No User"));
        }

        Board board = null;
        try {
            board = boardRepo.findById(boardId).get();
        } catch(Exception e) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400,  "No Board"));
        }
        if(board.getUser() != user){
//        if(board.getUser() != user || user.getRoll().eqauls("ADMIN")){
            return ResponseEntity.status(405).body(BaseResponseBody.of(405,  "No Authorization"));
        }

        boardRepo.delete(board);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Delete Board Success"));
    }

    public ResponseEntity getMatchBoard(Long matchId) {

        List<Board> boards =  boardRepo.findAllByMatchId(matchId);
        List<GetBoardResDTO> getBoards = new LinkedList<>();

        for(Board board : boards){
            getBoards.add(new GetBoardResDTO().builder()
                            .id(board.getId())
                            .title(board.getTitle())
                            // author 이름으로 나중에 바꾸기
//                  .author(board.getUser().getName())
                            .author(board.getUser().getNickname())
                            .build()
            );

            System.out.println(board);
        }
        return ResponseEntity.status(200).body(BoardRes.of(200, "Get Match Board Success", getBoards));
    }

    public List<GetBoardResDTO> getBoardList(int page) {
        PageRequest pageRequest = PageRequest.of(page -1, 10);
        List<Board> boards =  boardRepo.findAll(pageRequest).stream().collect(Collectors.toList());
//        List<Board> boards =  boardRepo.findByPage(pageRequest);
        List<GetBoardResDTO> getBoards = new LinkedList<>();
        for(Board board : boards){
            getBoards.add(new GetBoardResDTO().builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    // author 이름으로 나중에 바꾸기
                    .author(board.getUser().getNickname())
                    .build()
            );

//            System.out.println(board);
        }
        return getBoards;

    }
}
