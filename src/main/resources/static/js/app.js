/**
 * Created by changer on 2017/2/11.
 */
$(function () {
    $("#submit").on("click", function () {

        var reldata = {};
        reldata['type'] = $("#type").val();
        reldata['ip'] = $("#ip").val();
        reldata['port'] = $("#port").val();
        reldata['instance'] = $("#instance").val();
        reldata['user'] = $("#user").val();
        reldata['password'] = $("#password").val();
        reldata['query'] = $("#query").val();

        if (check(reldata)) {
            $("#submit").text("查询中...");
            $("#information").text("查询中...");
            $("#submit").addClass("disabled");
            $.ajax({
                url: '/query',
                type: 'post',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(reldata),
                crossDomain: true,
                dataType: 'json',
                success: function (data) {
                    showTable(data);
                    $("#submit").text("执行");
                    $("#submit").removeClass("disabled");
                }
            });
        }
    });

    function checkItem(parent,item) {
        if (parent[item] == undefined || parent[item] == null
                || parent[item] === "" ) {
            $("#" + item).parent().addClass("has-error");
            $("#information").addClass("text-danger");
            $("#information").text("错误:请设置" + item);
            return false;
        }
        return true;
    }

    function check(reldata) {
        $(".form-group .has-error").removeClass("has-error");
        for ( var item in reldata) {
            if ( checkItem(reldata,item) == false ) {
                return false;
            }
        }
        return true;
    }

    function merge(pre, cur) {
        return pre + cur;
    }

    function showTable(resultData) {

        var info = "错误的请求!";
        var table = "";
        if (resultData['error'] != undefined) {
            info = resultData['error'];
        }
        if (info == 'OK') {
            $("#information").removeClass("text-danger");
            $("#information").addClass("text-success");
            table += "<thead><tr><th>#</th>";
            table += resultData['meta'].map(function (value) {
                return '<th>' + value + '</th>';
            }).reduce(merge);
            table += '</tr></thead><tbody>';
            table += resultData['fields'].map(function (value, index) {
                return '<tr><th scope="row">' + (index + 1) + '</th>' +
                    value.map(function (val) {
                        return '<td>' + val + '</td>';
                    }).reduce(merge) + '</tr>';
            }).reduce(merge);
            table += '</tbody>'
            //console.log(table);
        } else {
            $("#information").removeClass("text-success");
            $("#information").addClass("text-danger");
        }
        $("#information").text(info);
        $("#rs").html(table);
    }
});