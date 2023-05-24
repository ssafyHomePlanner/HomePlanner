package com.ssafy.homepjt.model.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.homepjt.model.dto.BookmarkPathDetailDto;
import com.ssafy.homepjt.model.dto.BookmarkPathDto;
import com.ssafy.homepjt.model.dto.HouseInfoDto;
import com.ssafy.homepjt.model.mapper.BookmarkMapper;
import com.ssafy.homepjt.model.request.BookmarkPathRequestDto;
import com.ssafy.homepjt.model.response.BookmarkPathResponseDto;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

@Service
public class BookmarkServiceImpl implements BookmarkService {

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
    public void deleteBookmarkApt(String memberId, long aptCode) throws SQLException {
        bookmarkMapper.deleteBookmarkApt(memberId, aptCode);
    }

    @Override
    public void insertBookmarkMember(int boardId, String memberId) {
        bookmarkMapper.insertBookmarkMember(boardId, memberId);
    }

    @Override
    public int checkBoardMemberLike(int boardId, String memberId) {
        return bookmarkMapper.checkBoardMemberLike(boardId, memberId);
    }

    @Override
    public Map<String, Object> searchPath(List<BookmarkPathRequestDto> bookmarkPathRequestDtoList) {
        Map<String, Object> resultMap = new HashMap<>();

        int size = bookmarkPathRequestDtoList.size();
        cards = new int[size];
        result = new int[size];
        used = new boolean[size];
        timeArr = new int[size][size];
        distArr = new double[size][size];
        list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            cards[i] = i;
        }

        perm(0);
        pointArr = new Point[list.size()];

        // 거리, 시간 계산
        for (int start = 0; start < size; start++) {

            String startLat = bookmarkPathRequestDtoList.get(start).getLat();
            String startLon = bookmarkPathRequestDtoList.get(start).getLon();

            for (int end = 0; end < size; end++) {
                String endLat = bookmarkPathRequestDtoList.get(end).getLat();
                String endLon = bookmarkPathRequestDtoList.get(end).getLon();
                if (start != end) {
                    // start -> end 계산(시간, 거리)
                    int[] result = calculate(startLat, startLon, endLat, endLon);
                    int calcTime = result[0];
                    double calcDist = result[1];

                    timeArr[start][end] = calcTime;
                    distArr[start][end] = calcDist;
                }
            }
        }


        System.out.println("list size = " + list.size());
        for (int i = 0; i < list.size(); i++) {
            int t = 0;
            double d = 0;
            int start = 0, end;
            List<BookmarkPathResponseDto> temp = new ArrayList<>();

            for (int j = 1; j < list.get(i).length; j++) {
                end = list.get(i)[j];
                t += timeArr[start][end];
                d += distArr[start][end];
                start = end;
            }

            System.out.println("list.get = " + list.get(i).toString());
            for (int j = 0; j < list.get(i).length; j++) {
                int idx = list.get(i)[j];
                long aptCode = bookmarkPathRequestDtoList.get(idx).getAptCode();
                String aptName = bookmarkPathRequestDtoList.get(idx).getAptName();
                String lat = bookmarkPathRequestDtoList.get(idx).getLat();
                String lon = bookmarkPathRequestDtoList.get(idx).getLon();
                temp.add(new BookmarkPathResponseDto(aptCode, aptName, lat, lon));
            }

            pointArr[i] = new Point(t, d, temp);
        }

        Point[] timePointArr = pointArr;
        Point[] distPointArr = pointArr.clone();
        List<Point> timePointList = Arrays.asList(timePointArr);
        List<Point> distPointList = Arrays.asList(distPointArr);
        timePointList.sort(Comparator.comparing(Point::getTime).thenComparing(Point::getDist));
        distPointList.sort(Comparator.comparing(Point::getDist).thenComparing(Point::getTime));

        System.out.println("@@@@@@");
        System.out.println("time point list size = " + timePointList.size());
        System.out.println("dist point list size = " + distPointList.size());
        System.out.println("@@@@@");

        resultMap.put("timeArr", timeArr);
        resultMap.put("distArr", distArr);
        resultMap.put("pointArr", pointArr);
        resultMap.put("timePointList", timePointList);
        resultMap.put("distPointList", distPointList);
        return resultMap;
    }

    @Override
    public List<BookmarkPathDto> searchBookmarkPath(String memberId) {
        return bookmarkMapper.searchBookmarkPath(memberId);
    }

    @Override
    public List<BookmarkPathResponseDto> searchBookmarkPathDetail(int bookmarkPathId) {
        return bookmarkMapper.searchBookmarkPathDetail(bookmarkPathId);
    }

    @Override
    public void insertBookmarkPath(List<BookmarkPathRequestDto> bookmarkPathRequestDtoList, String memberId, String pathName) {
        int size = bookmarkPathRequestDtoList.size();
        BookmarkPathRequestDto startApt = bookmarkPathRequestDtoList.get(0);
        BookmarkPathRequestDto endApt = bookmarkPathRequestDtoList.get(size - 1);
        BookmarkPathDto bookmarkPathDto = new BookmarkPathDto(0,
                startApt.getAptCode(), startApt.getAptName(), startApt.getLat(), startApt.getLon(),
                endApt.getAptCode(), endApt.getAptName(), endApt.getLat(), endApt.getLon(),
                memberId, pathName
        );

        bookmarkMapper.insertBookmarkPath(bookmarkPathDto);

        int bookmarkPathId = bookmarkPathDto.getId();
        for (int i = 1; i < size - 1; i++) {
            BookmarkPathRequestDto pathDto = bookmarkPathRequestDtoList.get(i);
            bookmarkMapper.insertBookmarkPathDetail(new BookmarkPathDetailDto(0, bookmarkPathId, pathDto.getAptCode(), pathDto.getAptName(), pathDto.getLat(), pathDto.getLon()));
        }
    }

    @Override
    public void deleteBookmarkPath(int bookmarkPathId) {
        bookmarkMapper.deleteBookmarkPath(bookmarkPathId);
    }

    @Getter
    static class Point {
        @JsonProperty
        int time;
        @JsonProperty
        double dist;
        @JsonProperty
        List<BookmarkPathResponseDto> pathList;

        Point(int time, double dist, List<BookmarkPathResponseDto> pathList) {
            this.time = time;
            this.dist = dist;
            this.pathList = pathList;
        }
    }

    static boolean[] used;
    static int[] cards, result;
    static Point[] pointArr;
    static int[][] timeArr;
    static double[][] distArr;
    static ArrayList<int[]> list;

    static void perm(int idx) {
        if (idx == result.length) {
            if (result[0] == 0 && result[result.length - 1] == result.length - 1) {
                list.add(Arrays.copyOf(result, result.length));
            }
            return;
        }

        for (int i = 0; i < cards.length; i++) {
            if (used[i]) continue;

            result[idx] = cards[i];
            used[i] = true;
            perm(idx + 1);
            used[i] = false;
        }
    }

    static int[] calculate(String startLat, String startLon, String endLat, String endLon) {
        int[] result = new int[2]; //0: time, 1: dist
        String restApiKey = "a6487a3962ec0e331101397a266dbe98";
        String requestUrl = "https://apis-navi.kakaomobility.com/v1/directions?origin=" + startLon + "," + startLat + "&destination=" + endLon + "," + endLat;

        try {

            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "KakaoAK " + restApiKey);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            JSONObject obj = new JSONObject(sb.toString());
            System.out.println("TRANS_ID");
            JSONArray jsonArray = obj.getJSONArray("routes");
            System.out.println(jsonArray);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject element = (JSONObject) jsonArray.opt(i);
                JSONObject summary = element.getJSONObject("summary");
                int duration = summary.getInt("duration");
                int distance = summary.getInt("distance");
                result[0] = duration;
                result[1] = distance;
            }

        } catch (Exception e) {
            System.out.println("error 발생");
            e.printStackTrace();
        }

        return result;
    }


}
