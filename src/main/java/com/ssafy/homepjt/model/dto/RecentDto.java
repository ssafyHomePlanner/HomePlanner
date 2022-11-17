package com.ssafy.homepjt.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecentDto {
    private int id;
    private String memberId;
    private String searchedName;
    private String searchedDate;
}
