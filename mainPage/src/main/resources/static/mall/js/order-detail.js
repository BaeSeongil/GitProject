// 주문 취소
function cancelOrder() {
    var orderNo = $("#orderNoValue").val();
    Swal.fire({
        title: "안내",
        text: "주문을 취소하시겠습니까?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.ajax({
                type: "PUT",
                url: "/mall/orders/" + orderNo + "/cancel",
                success: function (result) {
                    if (result.resultCode == 200) {
                        window.location.reload();
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
    });
}

// 주문 결제
function payOrder() {
    var orderNo = $("#orderNoValue").val();
    window.location.href = "/mall/selectPayType?orderNo=" + orderNo;
}

// 거래 성공
function finishOrder() {
    var orderNo = $("#orderNoValue").val();
    $.ajax({
        type: "PUT",
        url: "/mall/orders/" + orderNo + "/finish",
        success: function (result) {
            if (result.resultCode == 200) {
                window.location.reload();
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
