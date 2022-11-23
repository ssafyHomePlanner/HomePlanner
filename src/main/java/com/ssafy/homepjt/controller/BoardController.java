package com.ssafy.homepjt.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.homepjt.model.dto.BoardCommentDto;
import com.ssafy.homepjt.model.dto.BoardDto;
import com.ssafy.homepjt.model.service.BoardService;

@RestController
@RequestMapping("/board")
@Api("게시판 컨트롤러 API V1")
public class BoardController {

    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private BoardService boardService;

    // 전체 게시판 목록
    @ApiOperation(value = "전체 게시판 목록 확인")
    @ApiResponses({@ApiResponse(code = 200, message = "전체 게시판 목록 확인 성공!!"),
            @ApiResponse(code = 404, message = "잘못된 접근!!"), @ApiResponse(code = 500, message = "서버에러!!")})
    @GetMapping({"{page}", ""})
    public ResponseEntity<Map<String, Object>> listBoard(@PathVariable("page") Optional<Integer> page) {

        if (!page.isPresent()) {
            page = Optional.of(1);
        }

        logger.info("board list controller");
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            Map<String, Object> result = boardService.listBoard(page.get().intValue());
            int startPage = (int) result.get("startPage");
            int endPage = (int) result.get("endPage");
            int currPage = (int) result.get("currPage");
            int totalPage = (int) result.get("totalPage");
            List<BoardDto> boardList = (List<BoardDto>) result.get("boardList");

            resultMap.put("startPage", startPage);
            resultMap.put("endPage", endPage);
            resultMap.put("currPage", currPage);
            resultMap.put("totalPage", totalPage);
            resultMap.put("boardList", boardList);

            logger.debug("게시판 목록 조회 성공 : {}", boardList.toString());
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("게시판 목록 조회 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 게시판 글쓰기
    @ApiOperation(value = "게시판 글쓰기")
    @ApiResponses({@ApiResponse(code = 200, message = "게시판 글쓰기 성공!!"), @ApiResponse(code = 404, message = "잘못된 접근!!"),
            @ApiResponse(code = 500, message = "서버에러!!")})
    @PostMapping("/write")
    public ResponseEntity<Map<String, Object>> writeBoard(@RequestBody BoardDto boardDto) {
        logger.info("board write controller");
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            boardService.writeBoard(boardDto);
            logger.debug("게시판 글쓰기 성공 : {}", boardDto.toString());
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("게시판 글쓰기 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 게시글 조회수 증가
    @ApiOperation(value = "게시글 조회수 증가")
    @PutMapping("/update/view/{boardId}")
    public ResponseEntity<Map<String, Object>> updateReadCount(@PathVariable("boardId") int boardId) {
        logger.info("board update view count controller, boardId : {}", boardId);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            boardService.updateReadCount(boardId);
            logger.debug("조회수 증가 성공");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("게시글 조회수 증가 실패");
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 좋아요 수 증가
    @ApiOperation(value = "게시판 글 좋아요 수 갱신")
    @ApiResponses({@ApiResponse(code = 200, message = "게시판 글 좋아요 수 갱신 성공!!"),
            @ApiResponse(code = 404, message = "잘못된 접근!!"), @ApiResponse(code = 500, message = "서버에러!!")})
    @PutMapping("/update/{boardId}/{memberId}/{flag}")
    public ResponseEntity<Map<String, Object>> updateLikeCount(@PathVariable("boardId") int boardId, @PathVariable("memberId") String memberId, @PathVariable("flag") boolean flag) {
        logger.info("board update like count controller");
        Map<String, Object> resultMap = new HashMap<>();

        try {
            boardService.updateLikeCount(boardId, memberId, flag);
            logger.debug("좋아요 수 갱신 성공");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("좋아요 수 갱신 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 좋아요 확인
    @ApiOperation(value = "게시판 글 좋아요 여부 확인")
    @GetMapping("/check/like/{boardId}/{memberId}")
    public ResponseEntity<Map<String, Object>> checkLikeBoard(@PathVariable("boardId") int boardId, @PathVariable("memberId") String memberId){
        logger.info("board check like controller");
        Map<String, Object> resultMap = new HashMap<>();

        try {
            boolean check = boardService.checkBoardLike(boardId, memberId);
            logger.info("좋아요 클릭 여부 확인 : {}", check);
            resultMap.put("likeFlag", check);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        }catch (Exception e){
            logger.info("좋아요 여부 확인 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 게시판 글 수정
    @ApiOperation(value = "게시판 글 수정")
    @ApiResponses({@ApiResponse(code = 200, message = "게시판 글 수정 성공!!"), @ApiResponse(code = 404, message = "잘못된 접근!!"),
            @ApiResponse(code = 500, message = "서버에러!!")})
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateBoard(@RequestBody BoardDto boardDto) {
        logger.info("board update controller");
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            boardService.updateBoard(boardDto);
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("게시판 수정 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 게시판 글 삭제
    @ApiOperation(value = "게시판 글 삭제")
    @ApiResponses({@ApiResponse(code = 200, message = "게시판 글 삭제 성공!!"), @ApiResponse(code = 404, message = "잘못된 접근!!"),
            @ApiResponse(code = 500, message = "서버에러!!")})
    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<Map<String, Object>> deleteBoard(@PathVariable("boardId") int boardId) {
        logger.info("board delete controller");
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            boardService.deleteBoard(boardId);
            logger.debug("게시판 삭제 성공 ");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("게시판 삭제 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 게시판 댓글 목록
    @ApiOperation(value = "게시판 댓글 목록")
    @ApiResponses({@ApiResponse(code = 200, message = "게시판 댓글 목록 조회 성공!!"),
            @ApiResponse(code = 404, message = "잘못된 접근!!"), @ApiResponse(code = 500, message = "서버에러!!")})
    @GetMapping("/comment/{boardId}")
    public ResponseEntity<Map<String, Object>> listBoardComment(@PathVariable("boardId") int boardId) {
        logger.info("board comment list controller");
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            List<BoardCommentDto> boardCommentList = boardService.listBoardComment(boardId);
            logger.debug("게시판 댓글 목록 조회 성공 : {}", boardCommentList.toString());
            resultMap.put("message", SUCCESS);
            resultMap.put("boardCommentList", boardCommentList);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("게시판 댓글 목록 조회 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 게시판 댓글 쓰기
    @ApiOperation(value = "게시판 댓글 쓰기")
    @ApiResponses({@ApiResponse(code = 200, message = "게시판 댓글 쓰기 성공!!"),
            @ApiResponse(code = 404, message = "잘못된 접근!!"), @ApiResponse(code = 500, message = "서버에러!!")})
    @PostMapping("/comment/write")
    public ResponseEntity<Map<String, Object>> writeBoardComment(@RequestBody BoardCommentDto boardCommentDto) {
        logger.info("board comment write controller");
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            boardService.writeBoardComment(boardCommentDto);
            logger.debug("게시판 댓글 쓰기 성공 : {}", boardCommentDto.toString());
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("게시판 댓글 쓰기 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 게시판 댓글 수정
    @ApiOperation(value = "게시판 댓글 수정")
    @ApiResponses({@ApiResponse(code = 200, message = "게시판 댓글 수정 성공!!"),
            @ApiResponse(code = 404, message = "잘못된 접근!!"), @ApiResponse(code = 500, message = "서버에러!!")})
    @PutMapping("/comment/update")
    public ResponseEntity<Map<String, Object>> updateBoardComment(@RequestBody BoardCommentDto boardCommentDto) {
        logger.info("board comment update controller");
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            boardService.updateBoardComment(boardCommentDto);
            logger.debug("게시판 댓글 수정 성공 ");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("게시판 댓글 수정 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 게시판 댓글 삭제
    @ApiOperation(value = "게시판 댓글 삭제")
    @ApiResponses({@ApiResponse(code = 200, message = "게시판 댓글 삭제 성공!!"),
            @ApiResponse(code = 404, message = "잘못된 접근!!"), @ApiResponse(code = 500, message = "서버에러!!")})
    @DeleteMapping("/comment/delete/{boardCommentId}")
    public ResponseEntity<Map<String, Object>> deleteBoardComment(@PathVariable("boardCommentId") int boardCommentId) {
        logger.info("board comment delete controller");
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            boardService.deleteBoardCommentDto(boardCommentId);
            logger.debug("게시판 댓글 삭제 성공 ");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("게시판 댓글 삭제 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
