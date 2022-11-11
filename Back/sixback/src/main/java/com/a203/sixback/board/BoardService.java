package com.a203.sixback.board;


import com.a203.sixback.auth.UserPrincipal;
import com.a203.sixback.board.req.PostBoardLikeReq;
import com.a203.sixback.board.req.PostBoardReq;
import com.a203.sixback.board.req.PostCommentReq;
import com.a203.sixback.board.req.UpdateBoardReq;
import com.a203.sixback.board.res.*;
import com.a203.sixback.db.entity.*;
import com.a203.sixback.db.mongo.BoardCommentRepo;
import com.a203.sixback.db.mongo.BoardLikeRepo;
import com.a203.sixback.db.repo.*;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepo boardRepo;
    private final BoardLikeRepo boardLikeRepo;
    private final BoardCommentRepo boardCommentRepo;
    private final CtgRepo ctgRepo;

    private final MatchesRepo matchRepo;
    private final TeamRepo teamRepo;

    public ResponseEntity createBoard(PostBoardReq postBoardReqDTO) {
        User user = null;
        Team team = null;
        Matches match = null;
        try {
            user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//            user = userRepo.findByEmail("pcm0720@gmail.com");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400,  "No User"));
        }

        Category category = ctgRepo.findByCtgName(postBoardReqDTO.getCtgName());

        if(postBoardReqDTO.getTeamId() != null){
            team = teamRepo.getReferenceById(postBoardReqDTO.getTeamId());
        }
        if(postBoardReqDTO.getMatchId() != null) {
            match = matchRepo.getReferenceById(postBoardReqDTO.getMatchId());
        }

        Board board = Board.builder()
                .title(postBoardReqDTO.getTitle())
                .content(postBoardReqDTO.getContent())
                .category(category)
                .match(match)
                .team(team)
                .user(user)
                .build();
        boardRepo.save(board);

        BoardComment boardComment = BoardComment
                .builder()
                .boardId(board.getId())
                .comments(new ArrayList<Comment>())
                .build();
        boardCommentRepo.save(boardComment);

        BoardLike boardLike = BoardLike.builder()
                .boardId(board.getId())
                .people(new ArrayList<Long>())
                .build();
        boardLikeRepo.save(boardLike);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Post Board Success"));
    }

    public GetBoardDetailRes getBoardDetail(long boardId) {
        Board board = boardRepo.findById(boardId).get();
        GetBoardDetailRes getDetail = new GetBoardDetailRes().builder()
                .id(boardId)
                .title(board.getTitle())
                .ctgName(board.getCategory().getCtgName())
                .content(board.getContent())
                .author(board.getUser().getNickname())
                .createDate(board.getCreateDate())
                .build();
        if(board.getTeam()!=null)
            getDetail.setTeam(board.getTeam().getId());
        if(board.getMatch()!=null)
            getDetail.setMatch(board.getMatch().getId());
        return getDetail;

    }

    public ResponseEntity updateBoard(UpdateBoardReq updateBoardReqDTO) {
        User user = null;
        try {
            user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
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

    public ResponseEntity deleteBoard(Long boardId) {
        User user = null;
        try {
            user = user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400,  "No User"));
        }

        Board board = null;
        try {
            board = boardRepo.findById(boardId).get();
        } catch(Exception e) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400,  "No Board"));
        }
        if(board.getUser().getId() != user.getId()){

            return ResponseEntity.status(405).body(BaseResponseBody.of(405,  "No Authorization"));
        }

        boardRepo.delete(board);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Delete Board Success"));
    }



    public List<GetBoardRes> getBoardList(int page) {
        PageRequest pageRequest = PageRequest.of(page -1, 10, Sort.by("id").descending());
        List<Board> boards =  boardRepo.findAll(pageRequest).stream().collect(Collectors.toList());
        List<GetBoardRes> getBoards = new LinkedList<>();
        for(Board board : boards){
            getBoards.add(new GetBoardRes().builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .author(board.getUser().getNickname())
                    .build()
            );
        }
        return getBoards;
    }

    public List<GetBoardRes> getBoardSearchList(Long page, String type, String keyword) {
        PageRequest pageRequest = PageRequest.of((int) (page-1), 10, Sort.by("id").descending());
        List<Board> boards = null;
        List<GetBoardRes> getBoards = new LinkedList<>();
        if(type.equals("Title")){
            boards = boardRepo.findByTitleContains(pageRequest, keyword);
        }
        else if(type.equals("Content")){
            boards = boardRepo.findByContentContains(pageRequest, keyword);
        }
        else if(type.equals("All")){
            boards = boardRepo.findByContentOrTitleContains(pageRequest,keyword, keyword);
        }

        if(boards.isEmpty()){
            return getBoards;
        }

        for(Board board : boards) {
            getBoards.add(new GetBoardRes().builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .author(board.getUser().getNickname())
                    .build()
            );
        }
        return getBoards;
    }

    public ResponseEntity getMatchBoard(Long matchId, int page) {
        PageRequest pageRequest = PageRequest.of(page -1, 10, Sort.by("id").descending());
        List<Board> boards =  boardRepo.findAllByMatchId(pageRequest, matchId);
        List<GetBoardRes> getBoards = new LinkedList<>();

        for(Board board : boards){
            getBoards.add(new GetBoardRes().builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .author(board.getUser().getNickname())
                    .build()
            );

            System.out.println(board);
        }
        return ResponseEntity.status(200).body(BoardRes.of(200, "Get Match Board Success", getBoards, getBoards.size() / 10 + 1));
    }

    public ResponseEntity getTeamBoard(Long teamId, int page) {
        PageRequest pageRequest = PageRequest.of(page -1, 10, Sort.by("id").descending());
        List<Board> boards =  boardRepo.findAllByTeamId(pageRequest, teamId);
        List<GetBoardRes> getBoards = new LinkedList<>();

        for(Board board : boards){
            getBoards.add(new GetBoardRes().builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .author(board.getUser().getNickname())
                    .build()
            );

            System.out.println(board);
        }
        return ResponseEntity.status(200).body(BoardRes.of(200, "Get Team Board Success", getBoards, getBoards.size() / 10 + 1));
    }

    public ResponseEntity getTeamTop4Board(int teamId) {
        List<Board> boards = boardRepo.findTop4ByTeamIdOrderByIdDesc(teamId);
        List<GetBoardDetailRes> getBoards = new LinkedList<>();

        for(Board board : boards) {
            getBoards.add(new GetBoardDetailRes().builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .author(board.getUser().getNickname())
                    .team(board.getTeam().getId())
                    .build()
            );
        }
        return ResponseEntity.status(200).body(BoardDetailListRes.of(200, "Get Top4 Team Board Success", getBoards));
    }

    public ResponseEntity getMatchTop4Board(long matchId) {
        List<Board> boards = boardRepo.findTop4ByMatchIdOrderByIdDesc(matchId);
        List<GetBoardDetailRes> getBoards = new LinkedList<>();

        for(Board board : boards) {
            getBoards.add(new GetBoardDetailRes().builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .author(board.getUser().getNickname())
                    .match(board.getMatch().getId())
                    .build()
            );
        }
        return ResponseEntity.status(200).body(BoardDetailListRes.of(200, "Get Top4 Match Board Success", getBoards));
    }

    public long getLastPage(){
        long boardSize = boardRepo.count();
        return boardSize / 10 + (boardSize %10 == 0 ? 0 : 1);
    }

    public long getSearchLastPage(Long page, String type, String keyword) {
        long boardSize = -1L;
        if(type.equals("Title")){
            boardSize =  boardRepo.countByTitleKeyword(keyword);
        }
        else if(type.equals("Content")){
            boardSize =  boardRepo.countByContentKeyword(keyword);
        }
        else if(type.equals("All")){
            boardSize =  boardRepo.countByContentOrTitleKeyword(keyword);
        }
        return boardSize / 10 + (boardSize %10 == 0 ? 0 : 1);
    }


    public ResponseEntity postComment(PostCommentReq postCommentDTO) {
        User user = null;

        try {
            user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "No User"));
        }

        BoardComment boardComment = null;
        try {
            boardComment = boardCommentRepo.findByBoardId(postCommentDTO.getBoardId());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400,  "No Board"));
        }

        Comment comment = Comment.builder()
                .id(boardComment.getComments().size() + 1)
                .comment(postCommentDTO.getComment())
                .author(user.getNickname())
                .authorId(user.getId())
                .createDate(LocalDateTime.now())
                .build();
        boardComment.getComments().add(comment);
        boardCommentRepo.save(boardComment);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Post Comment Success"));
    }

    public List<GetCommentRes> findComments(long boardId) {
        List<Comment> comments = boardCommentRepo.findByBoardId(boardId).getComments();
        List<GetCommentRes> getComments = new LinkedList<>();
        for(Comment comment : comments){
            getComments.add(new GetCommentRes().builder()
                    .commentId(comment.getId())
                    .author(comment.getAuthor())
                    .comment(comment.getComment())
                    .createDate(comment.getCreateDate())
                    .build());
        }
        return getComments;
    }

    public Boolean getLike(Long boardId) {
        long userId = -1L;
        try {
            userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        } catch (Exception e) {
            userId = -1L;
        }
        BoardLike boardLike = boardLikeRepo.findByBoardId(boardId);
        if(boardLike == null){
            boardLike = BoardLike.builder()
                    .boardId(boardId)
                    .people(new ArrayList<Long>())
                    .build();
            boardLikeRepo.save(boardLike);
        }
        return boardLike.getPeople().contains(userId);
    }

    public int getLikeCount(Long boardId) {
        long userId = -1L;
        try {
            userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        } catch (Exception e) {
            userId = -1L;
        }
        BoardLike boardLike = boardLikeRepo.findByBoardId(boardId);
        if(boardLike == null){
            boardLike = BoardLike.builder()
                    .boardId(boardId)
                    .people(new ArrayList<Long>())
                    .build();
            boardLikeRepo.save(boardLike);
        }
        return boardLike.getPeople().size();
    }

    public ResponseEntity postBoardLike(PostBoardLikeReq postBoardLikeReq) {
        User user = null;
        try {
            user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "No User"));
        }

        BoardLike boardLike = boardLikeRepo.findByBoardId(postBoardLikeReq.getBoardId());
        if(boardLike == null){
            boardLike = BoardLike.builder()
                    .boardId(postBoardLikeReq.getBoardId())
                    .people(new ArrayList<Long>())
                    .build();
            boardLikeRepo.save(boardLike);
        }
        HashSet<Long> hs = new HashSet<Long>(boardLike.getPeople());
        // 좋아요

        if(postBoardLikeReq.isCheckLiked()) {
            System.out.println("AAA");
            hs.add(user.getId());
        }

        // 좋아요 취소
        else {
            hs.remove(user.getId());
        }
        List<Long> list = new ArrayList<Long>();
        list.addAll(hs);

        boardLike.setPeople(list);
        boardLikeRepo.save(boardLike);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Post Board Like Success"));
    }


}
