$(function () {
    var configType = $("#configType").val();

    $("#jqGrid").jqGrid({
        url: "/admin/indexConfigs/list?configType=" + configType,
        datatype: "json",
        colModel: [
            { label: "id", name: "configId", index: "configId", width: 50, key: true, hidden: true },
            { label: "인덱스 구성명", name: "configName", index: "configName", width: 180 },
            { label: "리디렉션URL", name: "redirectUrl", index: "redirectUrl", width: 120 },
            { label: "정렬값", name: "configRank", index: "configRank", width: 120 },
            { label: "상품 Id", name: "productId", index: "productId", width: 120 },
            { label: "생성날짜", name: "createTime", index: "createTime", width: 120 },
        ],
        height: 560,
        rowNum: 10,
        rowList: [10, 20, 50],
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
});

// jqGrid reload(페이지를 받고 그리드 다시 호출)
function reload() {
    var page = $("#jqGrid").jqGrid("getGridParam", "page");
    $("#jqGrid")
        .jqGrid("setGridParam", {
            page: page,
        })
        .trigger("reloadGrid");
}

function configAdd() {
    reset();
    $(".modal-title").html("인덱스 구성 설정 추가");
    $("#indexConfigModal").modal("show");
}

// 모달에 저장버튼 추가
$("#saveButton").click(function () {
    var configName = $("#configName").val();
    var configType = $("#configType").val();
    var redirectUrl = $("#redirectUrl").val();
    var productId = $("#productId").val();
    var configRank = $("#configRank").val();
    if (!validKR_ENString2_18(configName)) {
        $("#edit-error-msg").css("display", "block");
        $("#edit-error-msg").html("유효하지 않은 문자열 표현이나 값이 지정되었습니다");
    } else {
        var data = {
            configName: configName,
            configType: configType,
            redirectUrl: redirectUrl,
            productId: productId,
            configRank: configRank,
        };
        var url = "/admin/indexConfigs/save";
        var id = getSelectedRowWithoutAlert();
        if (id != null) {
            url = "/admin/indexConfigs/update";
            data = {
                configId: id,
                configName: configName,
                configType: configType,
                redirectUrl: redirectUrl,
                productId: productId,
                configRank: configRank,
            };
        }
        $.ajax({
            type: "POST", //Metod 유형
            url: url,
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (result) {
                if (result.resultCode == 200) {
                    $("#indexConfigModal").modal("hide");
                    Swal.fire("작업성공", {
                        icon: "success",
                    });
                    reload();
                } else {
                    $("#indexConfigModal").modal("hide");
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
});

function configEdit() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    var rowData = $("#jqGrid").jqGrid("getRowData", id);
    $(".modal-title").html("인덱스 구성 설정 변경");
    $("#indexConfigModal").modal("show");
    $("#configId").val(id);
    $("#configName").val(rowData.configName);
    $("#redirectUrl").val(rowData.redirectUrl);
    $("#productId").val(rowData.productId);
    $("#configRank").val(rowData.configRank);
}

function deleteConfig() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    Swal.fire({
        title: "인덱스 구성 설정 삭제",
        text: "인덱스 구성 설정을 정말 삭제하시겠습니까?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.ajax({
                type: "POST",
                url: "/admin/indexConfigs/delete",
                contentType: "application/json",
                data: JSON.stringify(ids),
                success: function (r) {
                    if (r.resultCode == 200) {
                        Swal.fire("삭제성공", {
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
    $("#configName").val("");
    $("#redirectUrl").val("##");
    $("#configRank").val(0);
    $("#productId").val(0);
    $("#edit-error-msg").css("display", "none");
}
