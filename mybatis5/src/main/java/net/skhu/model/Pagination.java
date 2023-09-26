package net.skhu.model;

import lombok.Data;

@Data
public class Pagination {
	int pg = 1; // 현재 페이지 번호
	int sz = 15; // 페이지 당 레코드 수
	int recordCount; // 전체 레코드 수

	public int getFirstRecordIndex() { // pg 번째 페이지의 첫 레코드의 인덱스를 계산하는 메소드
		return (pg - 1) * sz;
	}

	public String getQueryString() {
		return String.format("pg=%d&sz=%d", pg, sz);
	}
}