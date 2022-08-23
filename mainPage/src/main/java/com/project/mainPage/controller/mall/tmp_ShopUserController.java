package com.project.mainPage.controller.mall;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.mainPage.common.Constants;
import com.project.mainPage.common.ServiceResultEnum;
import com.project.mainPage.controller.vo.UserVO;
import com.project.mainPage.dto.tmp_User;
import com.project.mainPage.service.tmp_UserService;
import com.project.mainPage.util.MD5Util;
import com.project.mainPage.util.Result;
import com.project.mainPage.util.ResultGenerator;

@Controller
@RequestMapping("/mall")
public class tmp_ShopUserController {

    @Resource
    private tmp_UserService UserService;

    @GetMapping({"/myhome"})
    public String myhomePage(HttpServletRequest request,
                               HttpSession httpSession) {
        request.setAttribute("path", "myhome");
        return "mall/myhome";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute(Constants.MALL_USER_SESSION_KEY);
        return "mall/login";
    }

    @GetMapping({"/login", "/login.html"})
    public String loginPage() {
        return "mall/login";
    }

    @GetMapping({"/register", "/register.html"})
    public String registerPage() {
        return "mall/register";
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        return "mall/index";
    }

    @GetMapping("/myhome/addresses")
    public String addressesPage() {
        return "mall/addresses";
    }

    @PostMapping("/login")
    public @ResponseBody Result login(@RequestParam("loginName") String loginName,
                        @RequestParam("password") String password,
                        HttpSession httpSession) {
        if (!StringUtils.hasText(loginName)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (!StringUtils.hasText(password)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }

        String loginResult = UserService.login(loginName, MD5Util.MD5Encode(password, "UTF-8"), httpSession);
        // 로그인 성공
        if (ServiceResultEnum.SUCCESS.getResult().equals(loginResult)) {
            return ResultGenerator.genSuccessResult();
        }
        // 로그인 실패
        return ResultGenerator.genFailResult(loginResult);
    }

    @PostMapping("/register")
    public @ResponseBody Result register(@RequestParam("loginName") String loginName,
                           @RequestParam("password") String password,
                           HttpSession httpSession) {
        if (!StringUtils.hasText(loginName)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (!StringUtils.hasText(password)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }

        String registerResult = UserService.register(loginName, password);
        // 가입 성공
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            return ResultGenerator.genSuccessResult();
        }
        // 가입 실패
        return ResultGenerator.genFailResult(registerResult);
    }

    @PostMapping("/myhome/updateInfo")
    @ResponseBody
    public Result updateInfo(@RequestBody tmp_User User, HttpSession httpSession) {
        UserVO UserTemp = UserService.updateUserInfo(User, httpSession);
        if (UserTemp == null) {
            Result result = ResultGenerator.genFailResult("업데이트 실패");
            return result;
        } else {
            // 반환 성공
            Result result = ResultGenerator.genSuccessResult();
            return result;
        }
    }
}
