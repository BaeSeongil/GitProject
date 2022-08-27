function login() {
    var loginName = $("#loginName").val();
    if (!validUserName(loginName)) {
        Swal.fire("올바른 로그인 ID를 입력하십시오", {
            icon: "error",
        });
        return false;
    }
    var password = $("#password").val();
    if (!validPassword(password)) {
        Swal.fire("올바른 암호 형식을 입력하십시오 (6-20자리와 숫자 조합)", {
            icon: "error",
        });
        return false;
    }
    //인증
    var params = $("#loginForm").serialize();
    var url = "/mall/login";
    $.ajax({
        type: "POST", //메소드 유형
        url: url,
        data: params,
        success: function (result) {
            if (result.resultCode == 200) {
                window.location.href = "/mall/index";
            } else {
                Swal.fire(result.message, {
                    icon: "error",
                });
            }
        },
        error: function () {
            Swal.fire("작업 실패", {
                icon: "error",
            });
        },
    });
}
