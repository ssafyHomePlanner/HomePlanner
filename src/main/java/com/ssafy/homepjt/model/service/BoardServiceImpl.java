package com.ssafy.homepjt.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.homepjt.model.dto.BoardCommentDto;
import com.ssafy.homepjt.model.dto.BoardDto;
import com.ssafy.homepjt.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

    private static final int COUNT_PER_PAGE = 10;

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public Map<String, Object> listBoard(int page) throws SQLException {
        int totalCount = boardMapper.selectTotalCount();
        int totalPage = totalCount / COUNT_PER_PAGE;
        if (totalPage % COUNT_PER_PAGE > 0) {
            totalPage++;
        }

        int startPage = (page - 1) / 10 * 10 + 1;
        int endPage = startPage + 9;
        if (endPage > totalPage) {
            endPage = totalPage;
        }

        int startRow = (page - 1) * COUNT_PER_PAGE;
        List<BoardDto> boardDtoList = boardMapper.listBoard(startRow, COUNT_PER_PAGE);

        Map<String, Object> result = new HashMap<>();
        result.put("startPage", startPage);
        result.put("endPage", endPage);
        result.put("currPage", page);
        result.put("totalPage", totalPage);
        result.put("boardList", boardDtoList);
        return result;
    }

    @Override
    public void writeBoard(BoardDto boardDto) throws SQLException {
        boardMapper.writeBoard(boardDto);
    }

    @Override
    public void updateReadCount(int boardId) throws SQLException {
        boardMapper.updateReadCount(boardId);
    }

    @Override
    public void updateLikeCount(int boardId, String memberId, boolean flag) throws SQLException {
        if (!flag) {
            boardMapper.insertBoardLike(boardId, memberId);
        } else {
            boardMapper.deleteBoardLike(boardId, memberId);
        }
        boardMapper.updateLikeCount(boardId);
    }

    @Override
    public boolean checkBoardLike(int boardId, String memberId) throws SQLException {
        if (boardMapper.checkBoardLike(boardId, memberId) == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateBoard(BoardDto boardDto) throws SQLException {
        boardMapper.updateBoard(boardDto);
    }

    @Override
    public void deleteBoard(int boardID) throws SQLException {
        boardMapper.deleteBoard(boardID);
    }

    @Override
    public List<BoardCommentDto> listBoardComment(int boardId) throws SQLException {
        return boardMapper.listBoardComment(boardId);
    }

    @Override
    public void writeBoardComment(BoardCommentDto boardCommentDto) throws SQLException {
        boardMapper.writeBoardComment(boardCommentDto);
    }

    @Override
    public void updateBoardComment(BoardCommentDto boardCommentDto) throws SQLException {
        boardMapper.updateBoardComment(boardCommentDto);
    }

    @Override
    public void deleteBoardCommentDto(int boardCommentId) throws SQLException {
        boardMapper.deleteBoardComment(boardCommentId);
    }

}
