// 우편번호 찾기 찾기 화면을 넣을 element
var element_wrap = document.getElementById("daumAddressWrap");

$("#saveOrder").click(function () {
    var userAddress = $("#userAddress").text();
    if (userAddress == "" || userAddress == "배송지 없음") {
        Swal.fire("배송지를 입력해주세요", {
            icon: "error",
        });
        return;
    }
    if (userAddress.trim().length < 2) {
        Swal.fire("정확한 배송지를 입력해주세요", {
            icon: "error",
        });
        return;
    }
    window.location.href = "/mall/saveOrder";
});

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
    var userId = $("#userId").text();
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
