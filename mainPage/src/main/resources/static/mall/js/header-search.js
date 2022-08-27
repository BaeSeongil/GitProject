$(function () {
    $("#keyword").keypress(function (e) {
        var key = e.which; //e.which: 키의 값
        if (key == 13) {
            var q = $(this).val();
            if (q && q != "") {
                var url = "/mall/search?keyword=" + q;
                $("#templatemo_search").on("hidden.bs.modal", function (e) {
                    window.location.href = url;
                });
                $("#templatemo_search").modal("hide");
            }
        }
    });
    $("#mobileKeyword").keypress(function (e) {
        var key = e.which; //e.which: 키의 값
        if (key == 13) {
            var q = $(this).val();
            if (q && q != "") {
                var url = "/mall/search?keyword=" + q;
                window.location.href = url;
            }
        }
    });
});

function search() {
    var q = $("#keyword").val();
    if (q && q != "") {
        var url = "/mall/search?keyword=" + q;
        $("#templatemo_search").on("hidden.bs.modal", function () {
            window.location.href = url;
        });
        $("#templatemo_search").modal("hide");
    }
}

function mobileSearch() {
    var q = $("#mobileKeyword").val();
    if (q && q != "") {
        var url = "/mall/search?keyword=" + q;
        window.location.href = url;
    }
}
