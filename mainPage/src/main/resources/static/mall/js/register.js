// 등록 버튼 onclick 이벤트를 register()로 변경
function register() {
    var loginName = $("#loginName").val();
    if (!validPhoneNumber(loginName)) {
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
    var params = $("#registerForm").serialize();
    var url = "/mall/register";
    $.ajax({
        type: "POST", //메소드 유형
        url: url,
        data: params,
        success: function (result) {
            if (result.resultCode == 200) {
                Swal.fire({
                    title: "등록 성공",
                    text: "로그인 페이지로 이동합니까?",
                    icon: "success",
                    buttons: true,
                    dangerMode: true,
                }).then((flag) => {
                    if (flag) {
                        window.location.href = "/mall/login";
                    }
                });
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
