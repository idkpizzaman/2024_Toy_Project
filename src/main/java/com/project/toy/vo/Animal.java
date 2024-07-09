package com.project.toy.vo;

import com.project.toy.vo.Animal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {
	private int id;
	private String name;
	private String classification;
	private String sex;
	private String birth;
	private String sickness;
}