$("#syncHall").click(function () {
    // 价格策略
    if($(".pricinematype.on").length > 0) {
        var cinemaCodeArrT = [];
        if($(".pricinematype.on").attr('pritype') == 1) {
            $("#cinemaDiv").find(".check").each(function (idx, elem) {
                cinemaCodeArrT.push($(elem).attr("ccode"));
            });
        }
        if($(".pricinematype.on").attr('pritype') == 2) {
            $("#cinemaDiv").find(".check.cur").each(function (idx, elem) {
                cinemaCodeArrT.push($(elem).attr("ccode"));
            });
        }
        syncHall(cinemaCodeArrT.join(","));
    }

    // 卡
    if($(".rulecinematype.on").length > 0) {
        var cinemaCodeArrT = [];
        if($(".rulecinematype.on").attr('rctype') == 1) {
            $("#cinemaDiv").find(".check").each(function (idx, elem) {
                cinemaCodeArrT.push($(elem).attr("ccode"));
            });
        }
        if($(".rulecinematype.on").attr('rctype') == 2) {
            $("#cinemaDiv").find(".check.cur").each(function (idx, elem) {
                cinemaCodeArrT.push($(elem).attr("ccode"));
            });
        }
        syncHall(cinemaCodeArrT.join(","));
    }

    /***
     * 活动同步影厅
     */
    var cinemaCodes=[];
   $('.cinemaClass').each(function () {
       cinemaCodes.push($(this).attr('id'))
   })
    if(cinemaCodes){
        syncHall(cinemaCodes.join(","))
    }


    // 券
    if($(".cinameType.on").length > 0) {
        var cinemaCodeArrT = [];
        if($(".cinameType.on").attr('cinametype') == 1) {
            $("#cinemaDiv").find(".check").each(function (idx, elem) {
                cinemaCodeArrT.push($(elem).attr("ccode"));
            });
        }
        if($(".cinameType.on").attr('cinametype') == 2) {
            $("#cinemaDiv").find(".check.cur").each(function (idx, elem) {
                cinemaCodeArrT.push($(elem).attr("ccode"));
            });
        }
        syncHall(cinemaCodeArrT.join(","));
    }
});

function syncGoods(cinemaCode) {
    if(!cinemaCode){
        layer.msg("请选择影院！");
        return;
    }

    $.ajax({
        url:ctx + "data/good.do",
        type:"POST",
        dataType:'json',
        data:{"cinemaCode":cinemaCode},
        success:function (data) {
            if(data && data.resultCode == '0'){
                layer.confirm("卖品同步成功,点击确定刷新页面查看！", function () {
                    location.reload();
                });
            }else{
                layer.msg("卖品同步失败！");
            }
        },
        error:function () {
            layer.msg("卖品同步失败！");
        }
    })
}
function syncHall(cinemaCode) {
    if(cinemaCode == null){
        layer.msg("请选择影院！");
        return;
    }
    $.ajax({
        url:ctx + "data/hall.do",
        type:"POST",
        dataType:'json',
        data:{"cinemaCode":cinemaCode},
        success:function (data) {
            if(data && data.resultCode == '0'){
                layer.confirm("影厅同步成功,点击确定刷新页面查看！", function () {
                    location.reload();
                });
            }else{
                layer.msg("影厅同步失败！");
            }
        },
        error:function () {
            layer.msg("影厅同步失败！");
            return;
        }
    })
}
function syncShow(cinemaCode) {
    if(cinemaCode == null){
        layer.msg("请选择影院！");
        return;
    }
    $.ajax({
        url:ctx + "data/session.do",
        type:"POST",
        dataType:'json',
        data:{"cinemaCode":cinemaCode},
        success:function (data) {
            if(data && data.resultCode == '0'){
                layer.confirm("场次同步成功,点击确定刷新页面查看！", function () {
                    location.reload();
                });
            }else{
                layer.msg("场次同步失败！");
            }
        },
        error:function () {
            alert("1");
            layer.msg("场次同步失败！");
            return;
        }
    })
}