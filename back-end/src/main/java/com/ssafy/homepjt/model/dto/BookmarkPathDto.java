package com.ssafy.homepjt.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookmarkPathDto {
    private int id;
    private long startAptCode;
    private String startAptName;
    private String startLat;
    private String startLon;
    private long endAptCode;
    private String endAptName;
    private String endLat;
    private String endLon;
    private String memberId;
    private String pathName;
}
