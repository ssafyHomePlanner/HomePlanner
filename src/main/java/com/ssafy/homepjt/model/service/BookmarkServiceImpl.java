package com.ssafy.homepjt.model.service;

import com.ssafy.homepjt.model.dto.HouseInfoDto;
import com.ssafy.homepjt.model.mapper.BookmarkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BookmarkServiceImpl implements BookmarkService{

    @Autowired
    private BookmarkMapper bookmarkMapper;

    @Override
    public void insertBookmarkApt(String memberId, long aptCode) {
        bookmarkMapper.insertBookmarkApt(memberId, aptCode);
    }

    @Override
    public List<HouseInfoDto> selectBookmarkApt(String memberId) throws SQLException {
        return bookmarkMapper.selectBookmarkApt(memberId);
    }

    @Override
    public void deleteBookmarkApt(int bookmarkAptId) throws SQLException {
        bookmarkMapper.deleteBookmarkApt(bookmarkAptId);
    }

    @Override
    public void insertBookmarkMember(int boardId, String memberId) {
        bookmarkMapper.insertBookmarkMember(boardId, memberId);
    }

    @Override
    public int checkBoardMemberLike(int boardId, String memberId) {
        return bookmarkMapper.checkBoardMemberLike(boardId, memberId);
    }

}
