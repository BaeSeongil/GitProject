package com.project.mainPage.controller.admin;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.mainPage.common.IndexConfigTypeEnum;
import com.project.mainPage.common.ServiceResultEnum;
import com.project.mainPage.common.ShopException;
import com.project.mainPage.dto.tmp_IndexConfig;
import com.project.mainPage.service.tmp_IndexConfigService;
import com.project.mainPage.util.PageQueryUtil;
import com.project.mainPage.util.Result;
import com.project.mainPage.util.ResultGenerator;


@Controller
@RequestMapping("/admin")
public class tmp_IndexConfigController {

    @Resource
    private tmp_IndexConfigService IndexConfigService;

    @GetMapping("/indexConfigs")
    public String indexConfigsPage(HttpServletRequest request, @RequestParam("configType") int configType) {
        IndexConfigTypeEnum indexConfigTypeEnum = IndexConfigTypeEnum.getIndexConfigTypeEnumByType(configType);
        if (indexConfigTypeEnum.equals(IndexConfigTypeEnum.DEFAULT)) {
            ShopException.fail("파라미터가 비정상입니다");
        }

        request.setAttribute("path", indexConfigTypeEnum.getName());
        request.setAttribute("configType", configType);
        return "admin/list_index_config";
    }

    // 목록
    @RequestMapping(value = "/indexConfigs/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (!StringUtils.hasText((CharSequence) params.get("page")) || !StringUtils.hasText((CharSequence) params.get("limit"))) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(IndexConfigService.getConfigsPage(pageUtil));
    }

    // 추가
    @RequestMapping(value = "/indexConfigs/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody tmp_IndexConfig indexConfig) {
        if (Objects.isNull(indexConfig.getConfigType())
                || !StringUtils.hasText(indexConfig.getConfigName())
                || Objects.isNull(indexConfig.getConfigRank())) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        String result = IndexConfigService.saveIndexConfig(indexConfig);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


 // 업데이트
    @RequestMapping(value = "/indexConfigs/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody tmp_IndexConfig indexConfig) {
        if (Objects.isNull(indexConfig.getConfigType())
                || Objects.isNull(indexConfig.getConfigId())
                || !StringUtils.hasText(indexConfig.getConfigName())
                || Objects.isNull(indexConfig.getConfigRank())) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        String result = IndexConfigService.updateIndexConfig(indexConfig);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

   // 상세 정보
    @GetMapping("/indexConfigs/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        tmp_IndexConfig config = IndexConfigService.getIndexConfigById(id);
        if (config == null) {
            return ResultGenerator.genFailResult("데이터를 찾을 수 없음");
        }
        return ResultGenerator.genSuccessResult(config);
    }

   // 삭제
    @RequestMapping(value = "/indexConfigs/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        if (IndexConfigService.deleteBatch(ids)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("삭제하지 못했습니다");
        }
    }


}