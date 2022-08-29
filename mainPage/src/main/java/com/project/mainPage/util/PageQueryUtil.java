package com.project.mainPage.util;

import java.util.LinkedHashMap;
import java.util.Map;

// pagination
public class PageQueryUtil extends LinkedHashMap<String, Object> {
    // 현재 페이지 번호
    private int page;
    // 페이지 당 문서 수
    private int limit;

    public PageQueryUtil(Map<String, Object> params) {
        this.putAll(params);

        // 현재 페이지 할당
        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.put("start", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "PageUtil{" +
                "page=" + page +
                ", limit=" + limit +
                '}';
    }
}
