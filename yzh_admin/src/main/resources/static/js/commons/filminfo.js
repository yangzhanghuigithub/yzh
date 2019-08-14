/**
 * 跟影票相关的公共接口
 **/

/**
 * 指定影片公共接口
 */
function FilmInfoModel(){
    this.url=ctx+"filminfo/onShowFilmInfoList";
    this.filmInfoList=[];
    this.initFilmInfoList();//初始化影片库
}
FilmInfoModel.getDataFormServer=function(data){
    var resultData=[];
    $.ajax({
               url:ctx+"filminfo/onShowFilmInfoList.do",
               async:false,
               type:"GET",
                data:data,
               dataType:"json",
               success:function(data){
                   var resultCode=data.resultCode;
                   if(parseInt(resultCode)===0){
                       resultData=data.resultData || [];
                   }else {
                       layer.msg("服务器消息:"+data.resultDesc);
                   }
               },
               error:function () {
                   layer.msg("网络异常，请刷新页面重试");
               }
           });
    resultData.forEach(function (value,index) {
        var filmShowDate = value.filmShowDate || "";
        var split = filmShowDate.split(" ");
        value.filmShowDate=split[0] || "";
        resultData[index]=value;
    });
    return resultData;
};
/**
 *初始化服务器数据
 */
FilmInfoModel.prototype.initFilmInfoList=function () {
    this.filmInfoList=FilmInfoModel.getDataFormServer();
};
/**
 * 根据名字进行模糊匹配
 * @param name
 */
FilmInfoModel.prototype.findByName=function (name) {
    var result=[];
    var filmLis=this.filmInfoList;
    if(filmLis.length==0)
        return result;
    $(filmLis).each(function (index, ele) {
        var filmKey=ele.filmKey;
        var filmName=ele.filmName || "";
        if(filmName.indexOf(name)!=-1){
            result.push(ele);
        }
    });
    return result;
};
/**
 * 根据FilmKey来查找其对应的FilmInfo信息，如果在上映的列表中查找不到，那么通过服务器接口查询。
 * @param filmKey  多个之间用逗号分隔
 * @return 返回一个Object对象，key为filmKey,value为影片信息
 */
FilmInfoModel.prototype.findByFilmKey=function (filmKeys) {
    var buffer={};
    var filmLis=this.filmInfoList;
    $(filmLis).each(function (index, ele) {
        var filmKey=ele.filmKey;
        buffer[filmKey]=ele;
    });
    var result={};
    var keysList=filmKeys ? filmKeys.split(","):[];
    var noFindList=[];
    for(var i=0;i<keysList.length;i++){
        if(buffer[keysList[i]]){
            result[keysList[i]]=buffer[keysList[i]];
        }else{
            noFindList.push(keysList[i]);
        }
    }
    //在缓存中没有找到该影片，那么调取服务器接口查询.
    if(noFindList.length>0){
        var dataFormServer = FilmInfoModel.getDataFormServer({filmKey:noFindList.join(",")});
        for(var i=0;i<noFindList.length;i++){
            $(dataFormServer).each(function (index, ele) {
                if(noFindList[i]==ele.filmKey){
                    result[noFindList[i]]=ele;
                    return false;
                }
            });
        }
    }
    return result;
};

/**
 * 初始化页面的选择影片组件。（Global组件，修改请注意影响范围）
 * @return  FilmInfoModel 对象
 */
function initFilmChooseComponent(){
    var viewFlag=false;//是否查看
    if(arguments.length>0){
        viewFlag=arguments[0] ||false;
    }
    window.globalFilmInfoListObj=new FilmInfoModel();//从服务器查上映的影片库列表
    $(".filmtype").on('click',function (e) {
        var choose=$(this).data("i");
        $(this).addClass("on");
        if(choose==0){
            //选择不限影片
            $(this).next().removeClass("on");
            $(".appoint-cinema").addClass("none");
        }else{
            $(this).prev().removeClass("on");
            $(".appoint-cinema").removeClass("none");
        }
    });
    // 模糊搜索
    $('.i-search').on('input propertychange', function () {
        var inputVal=$(this).val();
        var $this=$(this);
        if (inputVal.length > 0) {
            $this.prev().show();//显示清除符号
            $this.next().empty();//清空结果列表
            //从影片库中模糊查询影片列表
            var resultList = globalFilmInfoListObj.findByName(inputVal);//查询结果
            $(resultList).each(function (index, ele) {
                var item="<li data-film-key='"+ele.filmKey+"' data-film-name='"+ele.filmName+"' data-time='"+(ele.filmShowDate || "未知")+"'>"+ele.filmName+"</li>";
                $this.next().append(item);
            });
            $this.next().show();//显示结果列表
        } else {
            $(this).next().hide();
            $(this).prev().hide();
        }
    });

    // 清除内容
    $('.clear').on('click', function () {
        $(this).hide();
        $(this).next().val($('.i-search').defaultValue);
        $(this).next().next().hide();
    });

    // 动态创建列表
    $('.m-search').on('click', 'li', function () {
        // 获取当前点击的内容
        var filmKey=$(this).data("filmKey") || "";
        if(hasContain(filmKey)){
            layer.msg("你已选择该影片，请勿重复选择");
            return;
        }
        var curText = $(this).data("filmName") || "";
        // 自定义时间 后台开发的时候传入动态变量
        var curTime = $(this).data("time");
        // 动态创建dom结构
        var liDom = $("<li class='flex-item' data-film-key='"+filmKey+"'><div class='c-name'></div><div class='c-time'></div><div class='del'>删除</div></li>");
        // 添加到父元素中
        $('.list-wrap').append(liDom);
        // 赋值
        liDom.find('.c-name').text(curText);
        liDom.find('.c-time').text(curTime);
        // 隐藏匹配列表
        $(this).parent().hide();
        // 清空搜索框的内容
        $('.clear').hide();
        $('.i-search').val($('.i-search').defaultValue);
    });
    if(!viewFlag){
        // 删除
        $('.list-wrap').on('click', '.del', function () {
            $(this).parent().remove();
        });
    }
    return globalFilmInfoListObj;
}

/**
 * 数据回显使用。
 * @param filmKey
 */
function appendFilmItem(filmKey) {
    var findResult=globalFilmInfoListObj.findByFilmKey(filmKey);
    var filmList=filmKey ? filmKey.split(",") :[];
    filmList.forEach(function (value) {
        var item = findResult[value];
        if(item){
            var liDom = $("<li class='flex-item' data-film-key='"+value+"'><div class='c-name'></div><div class='c-time'></div><div class='del'>删除</div></li>");
            // 添加到父元素中
            $('.list-wrap').append(liDom);
            // 赋值
            liDom.find('.c-name').text(item.filmName);
            liDom.find('.c-time').text(item.filmShowDate);
        }
    });
}

function reViewData(filmChooseWay,filmKey){
    if(filmChooseWay==0||filmChooseWay==1){
        $(".filmtype[data-i='0']").trigger('click');
    }else{
        $(".filmtype[data-i='2']").trigger('click');
        appendFilmItem(filmKey);
    }
}

/**
 * fhisou
 * @param filmKey
 * @return {boolean}
 */
function hasContain(filmKey){
    var flag=false;
    $(".list-wrap").find("li").each(function () {
        if($(this).data("filmKey")==filmKey){
            flag=true;
            return false;
        }
    });
    return flag;
}

/**
 * 获取选择的结果，该函数返回一个js对象，包含了三个字段
 * resultCode 0,成功,1，失败（校验未通过或者数据异常）
 * resultDesc 结果描述
 * resultData 如果处理成功，返回一个js对象，包含三个字段，filmType(影片类型值，0，不限影片，2，指定影片)，filmKey(选择的影片filmCode用逗号分隔),filmList(数组形式的filmCode)
 */
function checkAndGetChoose(){
    var result={
        resultCode:1,
        resultDesc:"未知错误",
        resultData:{}
    };
    var $filmTypeEle = $(".filmtype.on");
    if($filmTypeEle){
        var filmChoose=$filmTypeEle.data("i");
        if(filmChoose==2){
            //选择指定影片
            if($(".list-wrap").find("li").length<=0){
                result.resultDesc="请至少选择一部影片";
                return result;
            }
            var filmKeyList=[];
            $(".list-wrap").find("li").each(function () {
                var filmKey = $(this).data("filmKey");
                if(filmKey)
                    filmKeyList.push(filmKey);
            });
            result.resultCode=0;
            result.resultDesc="校验成功，指定影片";
            result.resultData.filmType=2;
            result.resultData.filmKey=filmKeyList.join(",");
            result.resultData.filmList=filmKeyList;
        }else if (filmChoose == 0) {
            //不限定影片
            result.resultCode=0;
            result.resultDesc="校验成功，不限影片";
            result.resultData.filmType=0;
        }else{
            result.resultDesc="未知影片类型选择，请检查组件结构";
        }
    }else{
        result.resultDesc="请选择影片方式";
    }
    return result;
}