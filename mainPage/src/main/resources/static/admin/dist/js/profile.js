$(function () {
    // 개인정보 수정
    $("#updateUserNameButton").click(function () {
        $("#updateUserNameButton").attr("disabled", true);
        var userName = $("#loginUserName").val();
        var nickName = $("#nickName").val();
        if (validUserNameForUpdate(userName, nickName)) {
            var params = $("#userNameForm").serialize();
            $.ajax({
                type: "POST",
                url: "/admin/profile/name",
                data: params,
                success: function (r) {
                    $("#updateUserNameButton").attr("disabled", false);
                    console.log(r);
                    if (r == "success") {
                        alert("업데이트 성공");
                    } else {
                        alert("업데이트 실패");
                    }
                },
            });
        } else {
            $("#updateUserNameButton").attr("disabled", false);
        }
    });

    // 비밀번호 변경
    $("#updatePasswordButton").click(function () {
        $("#updatePasswordButton").attr("disabled", true);
        var originalPassword = $("#originalPassword").val();
        var newPassword = $("#newPassword").val();
        if (validPasswordForUpdate(originalPassword, newPassword)) {
            var params = $("#userPasswordForm").serialize();
            $.ajax({
                type: "POST",
                url: "/admin/profile/password",
                data: params,
                success: function (r) {
                    $("#updatePasswordButton").attr("disabled", false);
                    console.log(r);
                    if (r == "success") {
                        alert("변경 성공");
                        window.location.href = "/admin/login";
                    } else {
                        alert("변경 실패");
                    }
                },
            });
        } else {
            $("#updatePasswordButton").attr("disabled", false);
        }
    });
});

// 사용가능한 ID, 닉네임인지 확인
function validUserNameForUpdate(userName, nickName) {
    if (isNull(userName) || userName.trim().length < 1) {
        $("#updateUserName-info").css("display", "block");
        $("#updateUserName-info").html("아이디를 입력하세요");
        return false;
    }
    if (isNull(nickName) || nickName.trim().length < 1) {
        $("#updateUserName-info").css("display", "block");
        $("#updateUserName-info").html("닉네임은 비워둘 수 없습니다");
        return false;
    }
    if (!validUserName(userName)) {
        $("#updateUserName-info").css("display", "block");
        $("#updateUserName-info").html("유효하지 않은 문자열 표현이나 값이 지정되었습니다");
        return false;
    }
    if (!validKR_ENString2_18(nickName)) {
        $("#updateUserName-info").css("display", "block");
        $("#updateUserName-info").html("유효하지 않은 문자열 표현이나 값이 지정되었습니다");
        return false;
    }
    return true;
}

// 유효한 비밀번호인지 확인
function validPasswordForUpdate(originalPassword, newPassword) {
    if (isNull(originalPassword) || originalPassword.trim().length < 1) {
        $("#updatePassword-info").css("display", "block");
        $("#updatePassword-info").html("현재 비밀번호를 입력해주세요");
        return false;
    }
    if (isNull(newPassword) || newPassword.trim().length < 1) {
        $("#updatePassword-info").css("display", "block");
        $("#updatePassword-info").html("새 비밀번호는 비워둘 수 없습니다");
        return false;
    }
    if (!validPassword(newPassword)) {
        $("#updatePassword-info").css("display", "block");
        $("#updatePassword-info").html("유효하지 않은 문자열 표현이나 값이 지정되었습니다");
        return false;
    }
    return true;
}
