package com.project.mainPage.service.impl;

import com.project.mainPage.common.ServiceResultEnum;
import com.project.mainPage.dto.tmp_AdminUser;
import com.project.mainPage.mapper.tmp_AdminUserMapper;
import com.project.mainPage.service.tmp_AdminUserService;
import com.project.mainPage.util.BeanUtil;
import com.project.mainPage.util.MD5Util;

import lombok.val;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class tmp_AdminUserServiceImpl implements tmp_AdminUserService {

    @Resource
    private tmp_AdminUserMapper adminUserMapper;

    @Override
    public String login(String loginName, String passwordMD5, HttpSession httpSession) {
        tmp_AdminUser adminUser = adminUserMapper.login(loginName, passwordMD5);
        if (adminUser != null && httpSession != null) {
            if (adminUser.getLocked() == 1) {
                return ServiceResultEnum.LOGIN_USER_LOCKED.getResult();
            }
            // 문자열이 너무 길 경우
            if (adminUser.getNickName() != null && adminUser.getNickName().length() > 7) {
                String tempNickName = adminUser.getNickName().substring(0, 7) + "..";
                adminUser.setNickName(tempNickName);
            }
            httpSession.setAttribute("loginUser", adminUser.getNickName());
            httpSession.setAttribute("loginUserId", adminUser.getAdminUserId());
            // session 만료 시간을 2시간으로 설정
            httpSession.setMaxInactiveInterval(60 * 60 * 2);
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }

    @Override
    public tmp_AdminUser getUserDetailById(Integer loginUserId) {
        return adminUserMapper.selectByPrimaryKey(loginUserId);
    }

    @Override
    public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
        tmp_AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        // 현재 사용자가 비어 있지 않으면 변경할 수 없습니다
        if (adminUser != null) {
            String originalPasswordMd5 = MD5Util.MD5Encode(originalPassword, "UTF-8");
            String newPasswordMd5 = MD5Util.MD5Encode(newPassword, "UTF-8");
            // 원본 암호가 정확한지 비교합니다.
            if (originalPasswordMd5.equals(adminUser.getLoginPassword())) {
                // 새 암호 설정 및 변경
                adminUser.setLoginPassword(newPasswordMd5);
                if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0) {
                    // 변경이 성공하면 true를 반환
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean updateName(Integer loginUserId, String loginUserName, String nickName) {
        tmp_AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        // 현재 사용자가 비어 있지 않으면 변경할 수 없습니다
        if (adminUser != null) {
            // 사용자 이름 설정 및 변경
            adminUser.setLoginUserName(loginUserName);
            adminUser.setNickName(nickName);
            if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0) {
                // 변경이 성공하면 true를 반환
                return true;
            }
        }
        return false;
    }
}
