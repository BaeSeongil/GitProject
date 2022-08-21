$(function () {
    $("#jqGrid").jqGrid({
        url: "/admin/product/list",
        datatype: "json",
        colModel: [
            { label: "상품인덱스", name: "productId", index: "productId", width: 60, key: true },
            { label: "상품명", name: "productName", index: "productName", width: 120 },
            { label: "상품설명", name: "productIntro", index: "productIntro", width: 120 },
            { label: "상품 대표 이미지", name: "productCoverImg", index: "productCoverImg", width: 120, formatter: coverImageFormatter },
            { label: "재고량", name: "stockNum", index: "stockNum", width: 60 },
            { label: "판매가", name: "sellingPrice", index: "sellingPrice", width: 60 },
            {
                label: "진열상태",
                name: "productSellStatus",
                index: "productSellStatus",
                width: 80,
                formatter: productSellStatusFormatter,
            },
            { label: "생성날짜", name: "createTime", index: "createTime", width: 60 },
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

    function productSellStatusFormatter(cellvalue) {
        // 상품 진열 상태(0: 판매안함 1: 진열)
        if (cellvalue == 0) {
            return '<button type="button" class="btn btn-block btn-success btn-sm" style="width: 80%;">판매중</button>';
        }
        if (cellvalue == 1) {
            return '<button type="button" class="btn btn-block btn-secondary btn-sm" style="width: 80%;">판매안함</button>';
        }
    }

    function coverImageFormatter(cellvalue) {
        return "<img src='" + cellvalue + '\' height="80" width="80" alt=\'CoverImg\'/>';
    }
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

// 상품 추가
function addProduct() {
    window.location.href = "/admin/product/edit";
}

// 상품 변경
function editProduct() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    window.location.href = "/admin/product/edit/" + id;
}

// 진열
function putUpProduct() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "판매시작",
        text: "정말 상품을 진열하시겠습니까?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.ajax({
                type: "PUT",
                url: "/admin/product/status/0",
                contentType: "application/json",
                data: JSON.stringify(ids),
                success: function (r) {
                    if (r.resultCode == 200) {
                        swal("진열 성공", {
                            icon: "success",
                        });
                        $("#jqGrid").trigger("reloadGrid");
                    } else {
                        swal(r.message, {
                            icon: "error",
                        });
                    }
                },
            });
        }
    });
}

// 판매 중지
function putDownProduct() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "판매중지",
        text: "정말 판매를 중단하시겠습니까?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.ajax({
                type: "PUT",
                url: "/admin/product/status/1",
                contentType: "application/json",
                data: JSON.stringify(ids),
                success: function (r) {
                    if (r.resultCode == 200) {
                        swal("판매 중지", {
                            icon: "success",
                        });
                        $("#jqGrid").trigger("reloadGrid");
                    } else {
                        swal(r.message, {
                            icon: "error",
                        });
                    }
                },
            });
        }
    });
}
