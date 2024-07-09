package com.project.toy.vo;

import com.project.toy.vo.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	private int id;
	private String loginId;
	private String loginPw;
	private String regDate;
	private String updateDate;
	private String name;
	private String nickname;
	private String phoneNum;
	private String email;
}