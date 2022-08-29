package com.project.mainPage.util;

import java.io.Serializable;
import java.util.List;

// 페이지네이션 Util
public class PageResult implements Serializable {

    // 총 문서량
    private int totalCount;
    // 페이지 당 문서 개수
    private int pageSize;
    // 전체 페이지
    private int totalPage;
    // 현재 페이지
    private int currPage;
    // 리스트 데이터
    private List<?> list;

    /**
     * 페이지
     *
     * @param list       리스트 데이터
     * @param totalCount 총 문서 개수
     * @param pageSize   페이지 당 문서 수
     * @param currPage   현재 페이지
     */
    public PageResult(List<?> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

}
