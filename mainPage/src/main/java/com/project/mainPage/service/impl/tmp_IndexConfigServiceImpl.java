package com.project.mainPage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.project.mainPage.common.ServiceResultEnum;
import com.project.mainPage.controller.vo.IndexConfigProductVO;
import com.project.mainPage.dto.tmp_IndexConfig;
import com.project.mainPage.dto.tmp_Product;
import com.project.mainPage.mapper.tmp_IndexConfigMapper;
import com.project.mainPage.mapper.tmp_ProductMapper;
import com.project.mainPage.service.tmp_IndexConfigService;
import com.project.mainPage.util.BeanUtil;
import com.project.mainPage.util.PageQueryUtil;
import com.project.mainPage.util.PageResult;

@Service
public class tmp_IndexConfigServiceImpl implements tmp_IndexConfigService {

    @Autowired
    private tmp_IndexConfigMapper indexConfigMapper;

    @Autowired
    private tmp_ProductMapper productMapper;

    @Override
    public PageResult getConfigsPage(PageQueryUtil pageUtil) {
        List<tmp_IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigList(pageUtil);
        int total = indexConfigMapper.getTotalndexConfigs(pageUtil);
        PageResult pageResult = new PageResult(indexConfigs, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveIndexConfig(tmp_IndexConfig indexConfig) {
        if (productMapper.selectByPrimaryKey(indexConfig.getProductId()) == null) {
            return ServiceResultEnum.PRODUCT_NOT_EXIST.getResult();
        }
        if (indexConfigMapper.selectByTypeAndProductId(indexConfig.getConfigType(), indexConfig.getProductId()) != null) {
            return ServiceResultEnum.SAME_INDEX_CONFIG_EXIST.getResult();
        }
        if (indexConfigMapper.insertSelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateIndexConfig(tmp_IndexConfig indexConfig) {
        if (productMapper.selectByPrimaryKey(indexConfig.getProductId()) == null) {
            return ServiceResultEnum.PRODUCT_NOT_EXIST.getResult();
        }
        tmp_IndexConfig temp = indexConfigMapper.selectByPrimaryKey(indexConfig.getConfigId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        tmp_IndexConfig temp2 = indexConfigMapper.selectByTypeAndProductId(indexConfig.getConfigType(), indexConfig.getProductId());
        if (temp2 != null && !temp2.getConfigId().equals(indexConfig.getConfigId())) {
            // productId가 같고 다른 id는 계속 수정할 수 없습니다
            return ServiceResultEnum.SAME_INDEX_CONFIG_EXIST.getResult();
        }
        indexConfig.setUpdateTime(new Date());
        if (indexConfigMapper.updateByPrimaryKeySelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public tmp_IndexConfig getIndexConfigById(Long id) {
        return null;
    }

    @Override
    public List<IndexConfigProductVO> getConfigProductesForIndex(int configType, int number) {
        List<IndexConfigProductVO> IndexConfigProductVOS = new ArrayList<>(number);
        List<tmp_IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigsByTypeAndNum(configType, number);
        if (!CollectionUtils.isEmpty(indexConfigs)) {
            // 전체 productId 불러오기
            List<Long> productIds = indexConfigs.stream().map(tmp_IndexConfig::getProductId).collect(Collectors.toList());
            List<tmp_Product> Product = productMapper.selectByPrimaryKeys(productIds);
            IndexConfigProductVOS = BeanUtil.copyList(Product, IndexConfigProductVO.class);
            for (IndexConfigProductVO IndexConfigProductVO : IndexConfigProductVOS) {
                String productName = IndexConfigProductVO.getProductName();
                String productIntro = IndexConfigProductVO.getProductIntro();
                // 문자열이 너무 길 경우
                if (productName.length() > 30) {
                    productName = productName.substring(0, 30) + "...";
                    IndexConfigProductVO.setProductName(productName);
                }
                if (productIntro.length() > 22) {
                    productIntro = productIntro.substring(0, 22) + "...";
                    IndexConfigProductVO.setProductIntro(productIntro);
                }
            }
        }
        return IndexConfigProductVOS;
    }

    @Override
    public Boolean deleteBatch(Long[] ids) {
        if (ids.length < 1) {
            return false;
        }
        // 데이터 삭제
        return indexConfigMapper.deleteBatch(ids) > 0;
    }
}
