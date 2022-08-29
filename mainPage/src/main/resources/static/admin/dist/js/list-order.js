$(function () {
    $("#jqGrid").jqGrid({
        url: "/admin/orders/list",
        datatype: "json",
        colModel: [
            { label: "id", name: "orderId", index: "orderId", width: 50, key: true, hidden: true },
            { label: "주문번호", name: "orderNo", index: "orderNo", width: 120 },
            { label: "총 주문금액", name: "totalPrice", index: "totalPrice", width: 60 },
            { label: "주문상태", name: "orderStatus", index: "orderStatus", width: 80, formatter: orderStatusFormatter },
            { label: "결제방법", name: "payType", index: "payType", width: 80, formatter: payTypeFormatter },
            { label: "배송지", name: "userAddress", index: "userAddress", width: 10, hidden: true },
            { label: "생성날짜", name: "createTime", index: "createTime", width: 120 },
            { label: "작업", name: "createTime", index: "createTime", width: 120, formatter: operateFormatter },
        ],
        height: 760,
        rowNum: 20,
        rowList: [20, 50, 80],
        styleUI: "Bootstrap",
        loadtext: "DB 불러오는 중",
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount",
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //하단 가로 스크롤바
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
        },
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    function operateFormatter(cellvalue, rowObject) {
        return "<a href='##' onclick=openOrderItems(" + rowObject.rowId + ")>주문 상세정보</a>" + "<br>" + "<a href='##' onclick=openExpressInfo(" + rowObject.rowId + ")>구매자 상세정보</a>";
    }

    function orderStatusFormatter(cellvalue) {
        // 주문상태(0: 미입금 1: 결제완료 2: 포장완료 3: 배송완료 4: 수령완료 -1: 수동취소 -2: 시간초과 -3: 판매종료)
        if (cellvalue == 0) {
            return "미입금";
        }
        if (cellvalue == 1) {
            return "결제완료";
        }
        if (cellvalue == 2) {
            return "포장완료";
        }
        if (cellvalue == 3) {
            return "배송완료";
        }
        if (cellvalue == 4) {
            return "수령완료";
        }
        if (cellvalue == -1) {
            return "수동취소";
        }
        if (cellvalue == -2) {
            return "시간초과";
        }
        if (cellvalue == -3) {
            return "판매종료";
        }
    }

    function payTypeFormatter(cellvalue) {
        // 결제 방법 0: 없음
        if (cellvalue == 0) {
            return "없음";
        }
        if (cellvalue == 1) {
            return "";
        }
        if (cellvalue == 2) {
            return "";
        }
    }

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
});

// jqGrid reload(페이지를 받고 그리드 다시 호출)
function reload() {
    initFlatPickr();
    var page = $("#jqGrid").jqGrid("getGridParam", "page");
    $("#jqGrid")
        .jqGrid("setGridParam", {
            page: page,
        })
        .trigger("reloadGrid");
}

// 주문 상세정보
function openOrderItems(orderId) {
    $(".modal-title").html("주문 상세정보");
    $.ajax({
        type: "GET", //Metod 유형
        url: "/admin/order-items/" + orderId,
        contentType: "application/json",
        success: function (result) {
            if (result.resultCode == 200) {
                $("#orderItemModal").modal("show");
                var itemString = "";
                for (i = 0; i < result.data.length; i++) {
                    itemString += result.data[i].productName + " x " + result.data[i].productCount + " 상품인덱스 " + result.data[i].productId + ";<br>";
                }
                $("#orderItemString").html(itemString);
            } else {
                Swal.fire(result.message, {
                    icon: "error",
                });
            }
        },
        error: function () {
            Swal.fire("작업실패", {
                icon: "error",
            });
        },
    });
}

// 구매자 상세정보
function openExpressInfo(orderId) {
    var rowData = $("#jqGrid").jqGrid("getRowData", orderId);
    $(".modal-title").html("구매자 상세정보");
    $("#expressInfoModal").modal("show");
    $("#userAddressInfo").html(rowData.userAddress);
}

// 주문 변경
function orderEdit() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    var rowData = $("#jqGrid").jqGrid("getRowData", id);
    $(".modal-title").html("주문 변경");
    $("#orderInfoModal").modal("show");
    $("#orderId").val(id);
    $("#userAddress").val(rowData.userAddress);
    $("#totalPrice").val(rowData.totalPrice);
}

// 모달에 저장버튼 추가
$("#saveButton").click(function () {
    var totalPrice = $("#totalPrice").val();
    var userName = $("#userName").val();
    var userPhone = $("#userPhone").val();
    var userAddress = $("#userAddress").val();
    var id = getSelectedRowWithoutAlert();
    var url = "/admin/orders/update";
    var data = {
        orderId: id,
        totalPrice: totalPrice,
        userName: userName,
        userPhone: userPhone,
        userAddress: userAddress,
    };
    $.ajax({
        type: "POST", //Metod 유형
        url: url,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                $("#orderInfoModal").modal("hide");
                Swal.fire("작업성공", {
                    icon: "success",
                });
                reload();
            } else {
                $("#orderInfoModal").modal("hide");
                Swal.fire(result.message, {
                    icon: "error",
                });
            }
        },
        error: function () {
            Swal.fire("작업실패", {
                icon: "error",
            });
        },
    });
});

// 포장 완료
function orderCheckDone() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    var orderNos = "";
    for (i = 0; i < ids.length; i++) {
        var rowData = $("#jqGrid").jqGrid("getRowData", ids[i]);
        if (rowData.orderStatus != "결제완료") {
            orderNos += rowData.orderNo + " ";
        }
    }
    if ((orderNos.length > 0) & (orderNos.length < 100)) {
        Swal.fire(orderNos + "결제완료 상태가 아닙니다 성공적으로 포장완료 작업을 수행할 수 없습니다.", {
            icon: "error",
        });
        return;
    }
    if (orderNos.length >= 100) {
        Swal.fire("너무 많은 결제완료 상태가 아닌 주문들을 선택했습니다. 포장완료 작업을 수행할 수 없습니다.", {
            icon: "error",
        });
        return;
    }
    Swal.fire({
        title: "포장완료",
        text: "포장완료 작업을 정말 수행하시겠습니까?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.ajax({
                type: "POST",
                url: "/admin/orders/checkDone",
                contentType: "application/json",
                data: JSON.stringify(ids),
                success: function (r) {
                    if (r.resultCode == 200) {
                        Swal.fire("포장완료", {
                            icon: "success",
                        });
                        $("#jqGrid").trigger("reloadGrid");
                    } else {
                        Swal.fire(r.message, {
                            icon: "error",
                        });
                    }
                },
            });
        }
    });
}

// 배송완료
function orderCheckOut() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    var orderNos = "";
    for (i = 0; i < ids.length; i++) {
        var rowData = $("#jqGrid").jqGrid("getRowData", ids[i]);
        if (rowData.orderStatus != "결제완료" && rowData.orderStatus != "포장완료") {
            orderNos += rowData.orderNo + " ";
        }
    }
    if ((orderNos.length > 0) & (orderNos.length < 100)) {
        Swal.fire(orderNos + "포장완료 상태가 아닙니다 성공적으로 배송완료 작업을 수행할 수 없습니다.", {
            icon: "error",
        });
        return;
    }
    if (orderNos.length >= 100) {
        Swal.fire("너무 많은 포장완료 상태가 아닌 주문들을 선택했습니다. 배송완료 작업을 수행할 수 없습니다.", {
            icon: "error",
        });
        return;
    }
    Swal.fire({
        title: "포장완료",
        text: "포장완료 작업을 정말 수행하시겠습니까?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.ajax({
                type: "POST",
                url: "/admin/orders/checkOut",
                contentType: "application/json",
                data: JSON.stringify(ids),
                success: function (r) {
                    if (r.resultCode == 200) {
                        Swal.fire("배송완료", {
                            icon: "success",
                        });
                        $("#jqGrid").trigger("reloadGrid");
                    } else {
                        Swal.fire(r.message, {
                            icon: "error",
                        });
                    }
                },
            });
        }
    });
}

function closeOrder() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    Swal.fire({
        title: "주문 마감",
        text: "정말 주문을 마감하시겠습니까?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.ajax({
                type: "POST",
                url: "/admin/orders/close",
                contentType: "application/json",
                data: JSON.stringify(ids),
                success: function (r) {
                    if (r.resultCode == 200) {
                        Swal.fire("주문 마감", {
                            icon: "success",
                        });
                        $("#jqGrid").trigger("reloadGrid");
                    } else {
                        Swal.fire(r.message, {
                            icon: "error",
                        });
                    }
                },
            });
        }
    });
}

function reset() {
    $("#totalPrice").val(0);
    $("#userAddress").val("");
    $("#edit-error-msg").css("display", "none");
}
