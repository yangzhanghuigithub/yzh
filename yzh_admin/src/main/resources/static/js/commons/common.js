var chooseNameArr = [];
var   cinemaArr = [];
var cityCinemaMap = {};
var chooseArr=[];
$(function () {
   // getCinemaList(null);
    loadCity();
})
/****加载城市*************/
function loadCity() {
    $.ajax({
               url: ctx + "cinema/getcitycinema.do",
               type: "GET",
               cache: false,
               async: false,
               dataType: 'json',
               success: function (data) {

                   if (data && data.resultCode === '0') {
                       cityCinemaMap = data.resultData.citycodeCinemaMap;
                       var cityArr = data.resultData.cityList;
                       // 城市列表
                       $("#citySel").select2({placeholder: '请选择城市'});
                       $("#citySel").append("<option value='-1'>请选择城市</option>");
                       $(cityArr).each(function (idx, city) {

                           $("#citySel").append("<option value='" + city.cityCode + "'>" +city.cityName + "</option>");
                       });

                       $("#citySel").change(function () {
                           $("#cinemaSel").select2({placeholder: '请选择影院, 可多选', multiple: true});
                           $("#cinemaSel").empty();
                           if($("#citySel").val() == -1) {
                               $(_.values(cityCinemaMap)).each(function (idx, cinemas) {
                                   $(cinemas).each(function (idxtmp, cinema) {
                                       $("#cinemaSel").append("<option value='" + cinema.cinemaCode + "'>" + cinema.cinemaShortName + "</option>");
                                   });
                               });
                           } else {
                               $(cityCinemaMap[$("#citySel").val()]).each(function (idx, cinema) {
                                   $("#cinemaSel").append("<option value='" + cinema.cinemaCode + "'>" + cinema.cinemaShortName + "</option>");
                               });
                           }
                       });

                       $("#citySel").trigger('change');

                   } else {
                       if (data.resultDesc) {
                           layer.msg(data.resultDesc);
                       } else {
                           layer.msg('查询失败 !');
                       }
                   }
               },
               error: function () {
                   layer.msg('查询失败 !');
               }
           });
}


$("#pricinemanamesearch").on('keyup',function () {
    var searchStr = $.trim($(this).val());

    if(searchStr) {

        // 渲染匹配的影院(选中已选择的影院)
        var searchResult = _.filter(cinemaArr, function (elem) {
            return elem.cinemaShortName.indexOf(searchStr) != -1;
        });

        showCinemaList(searchResult);

    } else {
        showCinemaList(cinemaArr,chooseArr);

    }
});

function getCinemaListRadio(chooseCinema) {
    $.ajax({
               url: ctx + "cinema/getCinema.do",
               type: "GET",
               cache: false,
               // async: false,
               dataType: 'json',
               success: function (data) {
                   if (data && data.resultCode === '0') {
                       var cinemaCodeArr = [];
                       cinemaArr=data.resultData;
                       showCinemaListRadio(cinemaArr,chooseCinema)
                   }
               }
           });
}

function showCinemaListRadio(cinemaArr,chooseCinemaCode) {
    $("#cinemaRadioDiv").empty();
    $(cinemaArr).each(function (idx, elem) {
        var cur='';
        if( chooseCinemaCode!=null &&chooseCinemaCode!=undefined&&elem.cinemaCode==chooseCinemaCode){
            cur='on';
        }
        $("#cinemaRadioDiv").append(
            '<p><span class="pricinema checkBtn radio '+cur+'" ccode="'
            + elem.cinemaCode
            + '">' + elem.cinemaShortName + '</span></p>'
        );

    });
}

$("#cinemaRadioDiv").on('click', '.radio', function () {
    $(this).parent().parent().find(".radio").removeClass("on");
    $(this).addClass("on");
});

$("#pricinemanamesearchRadio").on('keyup',function () {
    var searchStr = $.trim($(this).val());
    if(searchStr) {

        // 渲染匹配的影院(选中已选择的影院)
        var searchResult = _.filter(cinemaArr, function (elem) {
            return elem.cinemaShortName.indexOf(searchStr) != -1;
        });

        showCinemaListRadio(searchResult);

    } else {
        showCinemaListRadio(cinemaArr);

    }
});

function getCinemaList(chooseCinema) {
    $.ajax({
               url: ctx + "cinema/getCinema.do",
               type: "GET",
               cache: false,
               // async: false,
               dataType: 'json',
               success: function (data) {
                   if (data && data.resultCode === '0') {
                       var cinemaCodeArr = [];
                       cinemaArr=data.resultData;
                      showCinemaList(cinemaArr,chooseCinema)
                   }
               }
           });
}
function getCheckCinemaList(chooseCinema) {
    $.ajax({
        url: ctx + "cinema/getCinema.do",
        type: "GET",
        cache: false,
        // async: false,
        dataType: 'json',
        success: function (data) {
            if (data && data.resultCode === '0') {
                cinemaArr=data.resultData;
                showCinemaList(cinemaArr,chooseCinema);
            }
        }
    });
}
function showCinemaList(cinemaArr,chooseCinemaCode) {
    $("#cinemaDiv").empty();
    $(cinemaArr).each(function (idx, elem) {
        var cur='';
        if( chooseCinemaCode!=null &&chooseCinemaCode!=undefined && $.inArray(elem.cinemaCode,chooseCinemaCode)!=-1){
            cur='cur'
            if(chooseArr){
                chooseArr.push(elem.cinemaCode);
                chooseNameArr.push(elem.cinemaShortName);
            }

        }
        $("#cinemaDiv").append(
            '<p><span class="pricinema checkBtn check '+cur+'" ccode="'
            + elem.cinemaCode
            + '">' + elem.cinemaShortName + '</span></p>'
        );


    });
    //复选框
    $("#cinemaDiv").find('.check').off("click").on('click', function () {
        var _this = $(this);
        _this.toggleClass('cur');
        if(_this.hasClass('cur') &&$.inArray(_this.attr('ccode'),chooseArr )==-1){
           // chooseNameArr = [];
            chooseNameArr.push($(this).html());
            chooseArr.push($(this).attr('ccode'));
        }else {
            _.pull(chooseNameArr,$(this).html());
            _.pull(chooseArr,$(this).attr('ccode'));
        }
        try {
            loadGroup();
        } catch (e) {
        }
        try {
            loadVoucher();
        }catch (e){

        }
        loadCard();
    });
}


//选择影院
$('.pricinema, .cinema_box').on('click','.radio',function(){
    var _this = $(this);
    _this.addClass('on').siblings('.radio').removeClass('on');
    if(_this.attr('cinemaradio')==1){
        $('.p_selCimema').addClass('none');
        clearSearch();
        $('.pricinema').removeClass('cur');
        try {
            loadGroup();
        } catch (e) {
        }
        try {
            loadVoucher();
        }catch (e){

        }
        try {
            loadFilm();
        } catch (e) {
        }
    } else {
        $('.p_selCimema').removeClass('none');
    }

});
// $("#cinemaDiv").on('click', '.check', function () {
//     var _this = $(this);
//     var ccode = _this.attr("ccode");
//     _this.toggleClass('cur');
//
// });
function clearSearch() {
    $("#pricinemanamesearch").val("");

}

/*********校验参数****************/

function  checkParmIsNull(param) {
    if(param==null || param==""||param==''
       ||param==undefined||param=='undefined'
       ||param=='null'){
        return true;
    }
    return false
}


/**
 * 判断多组时间[hh:mm]段[{"beginTime":"00:00","endTime":"23:00"}]有误重复交叉
 * @param times
 * @returns {boolean}
 */
function checkDates(times){
    var begin = [];
    var end = [];
    $(times).each(function (index,time) {
        begin.push("2000-01-01 "+time.beginTime + ":00");
        end.push("2000-01-01 "+time.endTime + ":00");
    });
    begin = begin.sort();
    end  = end.sort();
    for(var i=1;i<begin.length;i++){
        if (begin[i] < end[i-1]){
            return false;
        }
    }
    return true;
}





