package com.ssafy.homepjt.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookmarkBoardDto {
    private int id;
    private String memberId;
    private int boardId;
}
