package net.skhu.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lombok.Data;

@Data
public class Pagination {
    int pg = 1;        // 현재 페이지 번호
    int sz = 15;       // 페이지 당 레코드 수
    String st = "";    // 검색 문자열
    int recordCount;   // 전체 레코드 수

    public int getFirstRecordIndex() {
        return (pg - 1) * sz;
    }

    public String getQueryString() {
        try {
            String encoded = URLEncoder.encode(st, "UTF-8");
            return String.format("pg=%d&sz=%d&st=%s", pg, sz, encoded);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return String.format("pg=%d&sz=%d&st=%s", pg, sz, st);
    }
}

