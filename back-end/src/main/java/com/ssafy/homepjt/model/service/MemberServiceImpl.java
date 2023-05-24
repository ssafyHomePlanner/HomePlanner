package com.ssafy.homepjt.model.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.homepjt.model.dto.RecentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.homepjt.model.dto.MemberDto;
import com.ssafy.homepjt.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public int idCheck(String id) throws SQLException {
        return memberMapper.idCheck(id);
    }

    @Override
    public void join(MemberDto memberDto) throws SQLException, NoSuchAlgorithmException {
        String raw = memberDto.getPw();
        String hex = "";

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[]bytes = new byte[16];
        random.nextBytes(bytes);

        //SALT 생성
        String salt = new String(Base64.getEncoder().encode(bytes));
        String rawAndSalt = raw + salt;

        MessageDigest md = MessageDigest.getInstance("SHA-256");
//        md.update(raw.getBytes());
//        hex = String.format("%064x", new BigInteger(1, md.digest()));

        md.update(rawAndSalt.getBytes());
        hex = String.format("%064x", new BigInteger(1, md.digest()));

        memberDto.setPw(hex.toString());
        System.out.println("hex = " + hex.toString());
        memberDto.setSalt(salt);
        System.out.println("memberDto = " + memberDto.toString());
        memberMapper.join(memberDto);
    }

    @Override
    public MemberDto login(String memberId, String memberPw) throws SQLException, NoSuchAlgorithmException {
        String salt = memberMapper.findSalt(memberId);
        if(salt == null){
            return null;
        }

        String raw = memberPw;
        String hex = "";
        String rawAndSalt = raw + salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(rawAndSalt.getBytes());
        hex = String.format("%064x", new BigInteger(1, md.digest()));

        return memberMapper.login(memberId, hex);
    }

    @Override
    public void saveRefreshToken(String memberId, String refreshToken) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("token", refreshToken);
        memberMapper.saveRefreshToken(map);
    }

    @Override
    public Object getRefreshToken(String memberId) throws Exception {
        return memberMapper.getRefreshToken(memberId);
    }

    @Override
    public void deleteRefreshToken(String memberId) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("token", null);
        memberMapper.deleteRefreshToken(map);
    }

    @Override
    public MemberDto detail(String id) throws SQLException {
        return memberMapper.detail(id);
    }

    @Override
    public void update(MemberDto memberDto) throws SQLException {
        memberMapper.update(memberDto);
    }

    @Override
    public void delete(String id) throws SQLException {
        memberMapper.delete(id);
    }

    @Override
    public String findId(String memberName, String memberPhone) {
        return memberMapper.findId(memberName, memberPhone);
    }

    @Override
    public String findPw(String memberId, String memberPhone) {
        return memberMapper.findPw(memberId, memberPhone);
    }

    @Override
    public void insertRecentData(String memberId, String data) throws SQLException {
        if(memberMapper.checkRecentData(memberId, data) == 0){
            memberMapper.insertRecentData(memberId, data);
        }
    }

    @Override
    public List<RecentDto> searchRecentData(String memberId) throws SQLException {
        return memberMapper.searchRecentData(memberId);
    }

    @Override
    public void deleteRecentData(String memberId, String recentId) throws SQLException {
        memberMapper.deleteRecentData(memberId, recentId);
    }

    @Override
    public void deleteRecentDataAll(String memberId) throws SQLException {
        memberMapper.deleteRecentDataAll(memberId);
    }

}
