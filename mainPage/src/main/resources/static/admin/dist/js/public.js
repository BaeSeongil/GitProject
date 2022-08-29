// 정규표현식
// NULL
function isNull(obj) {
    if (obj == null || obj == undefined || obj.trim() == "") {
        return true;
    }
    return false;
}

// 유효 길이
function validLength(obj, length) {
    if (obj.trim().length < length) {
        return true;
    }
    return false;
}

// url 표현식
function isURL(str_url) {
    var strRegex = "^((https|http|ftp|rtsp|mms)?://)" + "(([0-9]{1,3}.){3}[0-9]{1,3}" + "|" + "([0-9a-zA-Z_!~*'()-]+.)*" + "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]." + "[a-zA-Z]{2,6})" + "(:[0-9]{1,4})?" + "((/?)|" + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";
    var re = new RegExp(strRegex);
    if (re.test(str_url)) {
        return true;
    } else {
        return false;
    }
}

// 사용자 이름(영어, 숫자, 4~16글자)
function validUserName(userName) {
    var pattern = /^[a-zA-Z0-9_-]{4,16}$/;
    if (pattern.test(userName.trim())) {
        return true;
    } else {
        return false;
    }
}

// 전화번호
function validPhoneNumber(phone) {
    if (/^1(3|4|5|6|7|8|9)\d{9}$/.test(phone)) {
        return true;
    }
    return false;
}

// 한글 영어 문자열 2~18글자
function validKR_ENString2_18(str) {
    var pattern = /^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣-_,. ]{2,18}$/;
    if (pattern.test(str.trim())) {
        return true;
    } else {
        return false;
    }
}

// 한글 영어 문자열 2~100글자
function validKR_ENString2_100(str) {
    var pattern = /^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣-_,. ]{2,100}$/;
    if (pattern.test(str.trim())) {
        return true;
    } else {
        return false;
    }
}

// 유효한 패스워드(영어 숫자 6~20글자)
function validPassword(password) {
    var pattern = /^[a-zA-Z0-9]{6,20}$/;
    if (pattern.test(password.trim())) {
        return true;
    } else {
        return false;
    }
}

// jqGrid로 선택한 데이터 호출
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        Swal.fire("데이터를 선택해주세요", {
            icon: "warning",
        });
        return;
    }
    var selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        Swal.fire("하나 이상의 데이터를 선택할 수 없습니다", {
            icon: "warning",
        });
        return;
    }
    return selectedIDs[0];
}

// jqGrid로 선택한 데이터 호출(경고창 없음)
function getSelectedRowWithoutAlert() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        return;
    }
    var selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        return;
    }
    return selectedIDs[0];
}

// jqGrid로 선택한 여러개의 데이터 호출
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        Swal.fire("데이터를 선택해주세요", {
            icon: "warning",
        });
        return;
    }
    return grid.getGridParam("selarrrow");
}
