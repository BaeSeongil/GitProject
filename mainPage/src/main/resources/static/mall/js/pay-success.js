function payOrderSuccess() {
    var orderNo = $("#orderNoValue").val();
    $.ajax({
        type: "GET",
        url: "/mall/paySuccess?payType=1&orderNo=" + orderNo,
        success: function (result) {
            if (result.resultCode == 200) {
                window.location.href = "/mall/orders/" + orderNo;
            } else {
                alert(result.message);
            }
        },
        error: function () {
            alert("작업 실패");
        },
    });
}
