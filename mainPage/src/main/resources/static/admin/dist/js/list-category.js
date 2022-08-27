$(function () {
    var categoryLevel = $("#categoryLevel").val();
    var parentId = $("#parentId").val();

    $("#jqGrid").jqGrid({
        url: "/admin/categories/list?categoryLevel=" + categoryLevel + "&parentId=" + parentId,
        datatype: "json",
        colModel: [
            { label: "id", name: "categoryId", index: "categoryId", width: 50, key: true, hidden: true },
            { label: "분류명", name: "categoryName", index: "categoryName", width: 240 },
            { label: "정렬값", name: "categoryRank", index: "categoryRank", width: 120 },
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

// 카테고리 추가
function categoryAdd() {
    reset();
    $(".modal-title").html("카테고리 추가");
    $("#categoryModal").modal("show");
}

// 하위 카테고리 관리
function categoryManage() {
    var categoryLevel = parseInt($("#categoryLevel").val());
    var parentId = $("#parentId").val();
    var id = getSelectedRow();
    if (id == undefined || id < 0) {
        return false;
    }
    if (categoryLevel == 1 || categoryLevel == 2) {
        categoryLevel = categoryLevel + 1;
        window.location.href = "/admin/categories?categoryLevel=" + categoryLevel + "&parentId=" + id + "&backParentId=" + parentId;
    } else {
        Swal.fire("하위 카테고리 없음", {
            icon: "warning",
        });
    }
}

// 이전(상위 카테고리)으로 이동
function categoryBack() {
    var categoryLevel = parseInt($("#categoryLevel").val());
    var backParentId = $("#backParentId").val();
    if (categoryLevel == 2 || categoryLevel == 3) {
        categoryLevel = categoryLevel - 1;
        window.location.href = "/admin/categories?categoryLevel=" + categoryLevel + "&parentId=" + backParentId + "&backParentId=0";
    } else {
        Swal.fire("상위 카테고리 없음", {
            icon: "warning",
        });
    }
}

// 모달에 저장버튼 추가
$("#saveButton").click(function () {
    var categoryName = $("#categoryName").val();
    var categoryLevel = $("#categoryLevel").val();
    var parentId = $("#parentId").val();
    var categoryRank = $("#categoryRank").val();
    if (!validKR_ENString2_18(categoryName)) {
        $("#edit-error-msg").css("display", "block");
        $("#edit-error-msg").html("유효하지 않은 문자열 표현이나 값이 지정되었습니다");
    } else {
        var data = {
            categoryName: categoryName,
            categoryLevel: categoryLevel,
            parentId: parentId,
            categoryRank: categoryRank,
        };
        var url = "/admin/categories/save";
        var id = getSelectedRowWithoutAlert();
        if (id != null) {
            url = "/admin/categories/update";
            data = {
                categoryId: id,
                categoryName: categoryName,
                categoryLevel: categoryLevel,
                parentId: parentId,
                categoryRank: categoryRank,
            };
        }
        $.ajax({
            type: "POST", //Method 유형
            url: url,
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (result) {
                if (result.resultCode == 200) {
                    $("#categoryModal").modal("hide");
                    Swal.fire("작업성공", {
                        icon: "success",
                    });
                    reload();
                } else {
                    $("#categoryModal").modal("hide");
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

function categoryEdit() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    var rowData = $("#jqGrid").jqGrid("getRowData", id);
    $(".modal-title").html("카테고리 변경");
    $("#categoryModal").modal("show");
    $("#categoryId").val(id);
    $("#categoryName").val(rowData.categoryName);
    $("#categoryRank").val(rowData.categoryRank);
}

function deleteCagegory() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    Swal.fire({
        title: "카테고리 삭제",
        text: "카테고리를 정말 삭제하시겠습니까?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.ajax({
                type: "POST",
                url: "/admin/categories/delete",
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
    $("#categoryName").val("");
    $("#categoryRank").val(0);
    $("#edit-error-msg").css("display", "none");
}
