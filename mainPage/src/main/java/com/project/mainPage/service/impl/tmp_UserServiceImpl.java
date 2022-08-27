package com.project.mainPage.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.project.mainPage.common.Constants;
import com.project.mainPage.common.ServiceResultEnum;
import com.project.mainPage.controller.vo.UserVO;
import com.project.mainPage.dto.tmp_User;
import com.project.mainPage.mapper.tmp_UserMapper;
import com.project.mainPage.service.tmp_UserService;
import com.project.mainPage.util.BeanUtil;
import com.project.mainPage.util.MD5Util;
import com.project.mainPage.util.MallUtils;
import com.project.mainPage.util.PageQueryUtil;
import com.project.mainPage.util.PageResult;

@Service
public class tmp_UserServiceImpl implements tmp_UserService {

    @Autowired
    private tmp_UserMapper UserMapper;

    @Override
    public PageResult getUsersPage(PageQueryUtil pageUtil) {
        List<tmp_User> Users = UserMapper.findUserList(pageUtil);
        int total = UserMapper.getTotalUsers(pageUtil);
        PageResult pageResult = new PageResult(Users, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String register(String loginName, String password) {
        if (UserMapper.selectByLoginName(loginName) != null) {
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        tmp_User registerUser = new tmp_User();
        registerUser.setLoginName(loginName);
        registerUser.setNickName(loginName);
        String passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        registerUser.setPassword(passwordMD5);
        if (UserMapper.insertSelective(registerUser) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String login(String loginName, String passwordMD5, HttpSession httpSession) {
        tmp_User user = UserMapper.selectByLoginNameAndPasswd(loginName, passwordMD5);
        if (user != null && httpSession != null) {
            if (user.getLockedFlag() == 1) {
                return ServiceResultEnum.LOGIN_USER_LOCKED.getResult();
            }
            // 문자열이 너무 길 경우
            if (user.getNickName() != null && user.getNickName().length() > 7) {
                String tempNickName = user.getNickName().substring(0, 7) + "..";
                user.setNickName(tempNickName);
            }
            // 유저 세션
            UserVO UserVO = new UserVO();
            BeanUtil.copyProperties(user, UserVO);
            // 장바구니 수량 설정
            httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, UserVO);
            // session 만료 시간을 2시간으로 설정
            httpSession.setMaxInactiveInterval(60 * 60 * 2);
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }

    @Override
    public Boolean updatePassword(String originalPassword, String newPassword, HttpSession httpSession) {
        UserVO userTemp = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        tmp_User userFromDB = UserMapper.selectByPrimaryKey(userTemp.getUserId());
        // 현재 사용자가 비어 있지 않으면 변경할 수 없습니다
        if (userFromDB != null) {
            String originalPasswordMd5 = MD5Util.MD5Encode(originalPassword, "UTF-8");
            String newPasswordMd5 = MD5Util.MD5Encode(newPassword, "UTF-8");
            // 원본 암호가 정확한지 비교합니다.
            if (originalPasswordMd5.equals(userFromDB.getPassword())) {
                // 새 암호 설정 및 변경
                userFromDB.setPassword(newPasswordMd5);
                if (UserMapper.updateByPrimaryKeySelective(userFromDB) > 0) {
                    // 변경이 성공하면 true를 반환
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public UserVO updateUserInfo(tmp_User User, HttpSession httpSession) {
        UserVO userTemp = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        tmp_User userFromDB = UserMapper.selectByPrimaryKey(userTemp.getUserId());
        if (userFromDB != null) {
            if (!StringUtils.isEmpty(User.getNickName())) {
                userFromDB.setNickName(MallUtils.cleanString(User.getNickName()));
            }
            if (!StringUtils.isEmpty(User.getAddress())) {
                userFromDB.setAddress(MallUtils.cleanString(User.getAddress()));
            }
            if (!StringUtils.isEmpty(User.getIntroduceSign())) {
                userFromDB.setIntroduceSign(MallUtils.cleanString(User.getIntroduceSign()));
            }
            if (UserMapper.updateByPrimaryKeySelective(userFromDB) > 0) {
                UserVO UserVO = new UserVO();
                userFromDB = UserMapper.selectByPrimaryKey(userTemp.getUserId());
                BeanUtil.copyProperties(userFromDB, UserVO);
                httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, UserVO);
                return UserVO;
            }
        }
        return null;
    }

    @Override
    public Boolean lockUsers(Integer[] ids, int lockStatus) {
        if (ids.length < 1) {
            return false;
        }
        return UserMapper.lockUserBatch(ids, lockStatus) > 0;
    }
}
