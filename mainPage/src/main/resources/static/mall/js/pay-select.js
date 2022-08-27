// 안내
$(".bank").click(function () {
    Swal.fire({
        icon: 'error',
        text: '현재 지원하지 않는 결제수단입니다'
      });
});

function payOrder(payType) {
    var orderNo = $("#orderNoValue").val();
    window.location.href = "/mall/payPage?orderNo=" + orderNo + "&payType=" + payType;
}
