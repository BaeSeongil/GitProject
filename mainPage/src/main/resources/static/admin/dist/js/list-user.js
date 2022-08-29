$(function () {
    $("#jqGrid").jqGrid({
        url: "/admin/users/list",
        datatype: "json",
        colModel: [
            { label: "id", name: "userId", index: "userId", width: 50, key: true, hidden: true },
            { label: "닉네임", name: "nickName", index: "nickName", width: 180 },
            { label: "ID", name: "loginName", index: "loginName", width: 120 },
            { label: "차단", name: "lockedFlag", index: "lockedFlag", width: 60, formatter: lockedFormatter },
            { label: "말소", name: "isDeleted", index: "isDeleted", width: 60, formatter: deletedFormatter },
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

    function lockedFormatter(cellvalue) {
        if (cellvalue == 1) {
            return '<button type="button" class="btn btn-block btn-secondary btn-sm" style="width: 50%;">차단</button>';
        } else if (cellvalue == 0) {
            return '<button type="button" class="btn btn-block btn-success btn-sm" style="width: 50%;">정상</button>';
        }
    }

    function deletedFormatter(cellvalue) {
        if (cellvalue == 1) {
            return '<button type="button" class="btn btn-block btn-secondary btn-sm" style="width: 50%;">말소</button>';
        } else if (cellvalue == 0) {
            return '<button type="button" class="btn btn-block btn-success btn-sm" style="width: 50%;">정상</button>';
        }
    }
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

function lockUser(lockStatus) {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    if (lockStatus != 0 && lockStatus != 1) {
        Swal.fire("비정상적인 상태", {
            icon: "error",
        });
    }
    Swal.fire({
        title: "확인창",
        text: "계정상태를 변경하시겠습니까?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.ajax({
                type: "POST",
                url: "/admin/users/lock/" + lockStatus,
                contentType: "application/json",
                data: JSON.stringify(ids),
                success: function (r) {
                    if (r.resultCode == 200) {
                        Swal.fire("변경성공", {
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
