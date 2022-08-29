package com.project.mainPage.service;

import com.project.mainPage.controller.vo.IndexConfigProductVO;
import com.project.mainPage.dto.tmp_IndexConfig;
import com.project.mainPage.util.PageQueryUtil;
import com.project.mainPage.util.PageResult;

import java.util.List;

public interface tmp_IndexConfigService {
    // 백엔드
    // 인덱스 구성 가져오기
    PageResult getConfigsPage(PageQueryUtil pageUtil);
    // 인덱스 구성 업로드
    String saveIndexConfig(tmp_IndexConfig indexConfig);
    // 인덱스 구성 업데이트
    String updateIndexConfig(tmp_IndexConfig indexConfig);
    // Id로 인덱스 구성 호출
    tmp_IndexConfig getIndexConfigById(Long id);

    // 고정된 수량의 첫 페이지 상품 개체 설정 (홈페이지 호출)
    List<IndexConfigProductVO> getConfigProductesForIndex(int configType, int number);
    // 일괄삭제
    Boolean deleteBatch(Long[] ids);
}
