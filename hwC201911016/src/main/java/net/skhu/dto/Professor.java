package net.skhu.dto;

import lombok.Data;

@Data
public class Professor {
	int id;
	String name;
	int departmentId;
	String phone;
	String office;
	String email;
	String departmentName;
}