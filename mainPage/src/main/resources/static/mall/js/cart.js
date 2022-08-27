// 팁
function tip() {
    Swal.fire("장바구니에 상품이 없어 결제가 불가능합니다", {
        icon: "error",
    });
}

// 결제 페이지로 이동
function settle() {
    window.location.href = "/mall/cart/settle";
}

// 장바구니 항목 업데이트
function updateItem(id) {
    var domId = "productCount" + id;
    var productCount = $("#" + domId).val();
    if (productCount > 5) {
        Swal.fire("한 상품을 5개 이상 구매할 수 없습니다", {
            icon: "error",
        });
        return;
    }
    if (productCount < 1) {
        Swal.fire("수량 이상", {
            icon: "error",
        });
        return;
    }
    var data = {
        cartItemId: id,
        productCount: productCount,
    };
    $.ajax({
        type: "PUT",
        url: "/mall/cart",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                window.location.reload();
            } else {
                Swal.fire("작업 실패", {
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

// 장바구니 항목 삭제
function deleteItem(id) {
    Swal.fire({
        title: "확인",
        text: "상품을 삭제하시겠습니까?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.ajax({
                type: "DELETE",
                url: "/mall/cart/" + id,
                success: function (result) {
                    if (result.resultCode == 200) {
                        window.location.reload();
                    } else {
                        Swal.fire("작업 실패", {
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
