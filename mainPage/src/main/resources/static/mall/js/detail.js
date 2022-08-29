// 장바구니에 담기
function saveToCart(id) {
    var productCount = 1;
    var data = {
        productId: id,
        productCount: productCount,
    };
    $.ajax({
        type: "POST",
        url: "/mall/cart",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                Swal.fire({
                    title: "상품을 장바구니에 추가합니다",
                    icon: "success",
                    buttons: true,
                    dangerMode: true,
                }).then((flag) => {
                    window.location.reload();
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

// 장바구니에 추가하고 장바구니 페이지로 이동
function saveAndGoCart(id) {
    var productCount = 1;
    var data = {
        productId: id,
        productCount: productCount,
    };
    $.ajax({
        type: "POST",
        url: "/mall/cart",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                Swal.fire({
                    title: "상품을 장바구니에 추가합니다",
                    icon: "success",
                    showDenyButton: true,
                    confirmButtonText: "계속 쇼핑하기",
                    denyButtonText: `결제하기`,
                    dangerMode: false,
                }).then((flag) => {
                    if (flag.isConfirmed) {
                        window.location.reload();
                    } else {
                        window.location.href = "/mall/cart";
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
