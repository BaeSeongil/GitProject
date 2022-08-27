package com.project.mainPage.controller.admin;

import java.util.Map;

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

import com.project.mainPage.service.tmp_UserService;
import com.project.mainPage.util.PageQueryUtil;
import com.project.mainPage.util.Result;
import com.project.mainPage.util.ResultGenerator;


@Controller
@RequestMapping("/admin")
public class tmp_UserController {

    @Resource
    private tmp_UserService UserService;

    @GetMapping("/users")
    public String usersPage(HttpServletRequest request) {
        request.setAttribute("path", "users");
        return "admin/list-user";
    }

    // 목록
    @RequestMapping(value = "/users/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (!StringUtils.hasText((CharSequence) params.get("page")) || !StringUtils.hasText((CharSequence) params.get("limit"))) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(UserService.getUsersPage(pageUtil));
    }

    // 사용자 계정 잠금 활성화 및 비활성화
    // 0: 잠금 해제
    // 1:  잠금
    @RequestMapping(value = "/users/lock/{lockStatus}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids, @PathVariable int lockStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        if (lockStatus != 0 && lockStatus != 1) {
            return ResultGenerator.genFailResult("계정 잠금 상태 이상");
        }
        if (UserService.lockUsers(ids, lockStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("잠금 실패");
        }
    }
}