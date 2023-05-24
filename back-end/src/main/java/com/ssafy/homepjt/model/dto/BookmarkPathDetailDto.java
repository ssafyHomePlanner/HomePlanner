package com.ssafy.homepjt.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookmarkPathDetailDto {
	private int id;
	private int bookmarkPathId;
	private long aptCode;
	private String aptName;
	private String lat;
	private String lon;
}
