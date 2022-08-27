// 우편번호 찾기 찾기 화면을 넣을 element
var element_wrap = document.getElementById("daumAddressWrap");

// 개인정보 저장
$("#savePersonalButton").click(function () {
    var address = $("#address").val();
    if (address.trim().length < 2) {
        Swal.fire("정확한 배송지를 입력해주세요", {
            icon: "error",
        });
        return;
    }
    var introduceSign = $("#introduceSign").val();
    if (introduceSign.trim().length < 4) {
        Swal.fire("상태메세지는 4글자 이상입니다", {
            icon: "error",
        });
        return;
    }
    var nickName = $("#nickName").val();
    if (nickName.trim().length < 2) {
        Swal.fire("닉네임은 2글자 이상입니다", {
            icon: "error",
        });
        return;
    }
    var userId = $("#userId").val();
    var data = {
        userId: userId,
        address: address,
        introduceSign: introduceSign,
        nickName: nickName,
    };
    $.ajax({
        type: "POST",
        url: "/mall/myhome/updateInfo",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                Swal.fire({
                    title: "알림",
                    text: "계정정보가 성공적으로 변경되었습니다",
                    type: "success",
                }).then((flag) => {
                    window.location.reload();
                });
            } else {
                Swal.fire(result.message);
            }
        },
        error: function () {
            Swal.fire("작업 실패");
        },
    });
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
            url: "/mall/myhome/password",
            data: params,
            success: function (result) {
                $("#updatePasswordButton").attr("disabled", false);
                console.log(result);
                if (result == "success") {
                    Swal.fire({
                        title: "알림",
                        text: "비밀번호가 성공적으로 변경되었습니다",
                        type: "success",
                    }).then((flag) => {
                        window.location.reload();
                    });
                } else {
                    Swal.fire(result.message);
                }
            },
            error: function () {
                Swal.fire("작업 실패");
            },
        });
    } else {
        $("#updatePasswordButton").attr("disabled", false);
    }
});

// 유효한 비밀번호인지 확인
function validPasswordForUpdate(originalPassword, newPassword) {
    if (isNull(originalPassword) || originalPassword.trim().length < 1) {
        Swal.fire("현재 비밀번호를 입력해주세요");
        return false;
    }
    if (isNull(newPassword) || newPassword.trim().length < 1) {
        Swal.fire("새 비밀번호는 비워둘 수 없습니다");
        return false;
    }
    if (!validPassword(newPassword)) {
        Swal.fire("유효하지 않은 문자열 표현이나 값이 지정되었습니다");
        return false;
    }
    return true;
}

function foldDaumPostcode() {
    // iframe을 넣은 element를 안보이게 한다.
    element_wrap.style.display = "none";
}

function openDaumPostcode() {
    // 현재 scroll 위치를 저장해놓는다.
    var currentScroll = Math.max(document.body.scrollTop, document.documentElement.scrollTop);
    new daum.Postcode({
        oncomplete: function (data) {
            // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ""; // 주소 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === "R") {
                // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else {
                // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 주소 저장
            updateAddress(addr);
        },
        // 우편번호 찾기 화면 크기가 조정되었을때 실행할 코드를 작성하는 부분. iframe을 넣은 element의 높이값을 조정한다.

        onresize: function (size) {
            element_wrap.style.height = size.height + "px";
        },
        width: "100%",
        height: "100%",
    }).embed(element_wrap);

    // iframe을 넣은 element를 보이게 한다.
    element_wrap.style.display = "block";
}

// 주소 업데이트
function updateAddress(newAddress) {
    var address = newAddress;
    var userId = $("#loginName").text();
    var data = {
        userId: userId,
        address: address,
    };
    var url = "/mall/myhome/updateInfo";
    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                location.reload();
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
