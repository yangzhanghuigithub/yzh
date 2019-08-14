var globalTools=(function($,log){
    if(!$){
        throw new TypeError("依赖于jQuery环境");
    };
    var ajaxTools={
        ajaxGet:function(params,fn){
            var url=params.url;
            if(!url)
                throw  new Error("url不能为空");
            $.ajax({
                       url:url,
                       data:params.data ? params.data :null,
                       async:params.async==undefined ? true :params.async,
                       type:"GET",
                       dataType:"json",
                       success:function(data){
                           var resultCode=data.resultCode;
                           if(parseInt(resultCode)===0){
                               //处理成 功
                               fn.call(params,data.resultData);
                           }else {
                               layer.msg("服务器消息:"+data.resultDesc);
                           }
                       },
                       error:function () {
                           layer.msg("服务器内部错误，请刷新页面重试");
                       }
                   });

        },
        ajaxPost:function (params,fn) {
            var url=params.url;
            if(!url)
                throw  new Error("url不能为空");
            $.ajax({
                       url:url,
                       data:params.data ? params.data :null,
                       async:params.async==undefined ? true :params.async,
                       type:"POST",
                       dataType:"json",
                       success:function(data){
                           var resultCode=data.resultCode;
                           if(parseInt(resultCode)===0){
                               //处理成 功
                               fn.call(params,data.resultData);
                           }else {
                               layer.msg("服务器消息:"+data.resultDesc);
                           }
                       },
                       error:function () {
                           layer.msg("服务器内部错误，请刷新页面重试");
                       }
                   })
        },
        ajaxJson:function (params,fn) {
            var url=params.url;
            if(!url)
                throw  new Error("url不能为空");
            $.ajax({
                       url:url,
                       data:JSON.stringify(params.data ? params.data :null),
                       async:params.async==undefined ? true :params.async,
                       contentType:"application;json",
                       type:"POST",
                       dataType:"json",
                       success:function(data){
                           var resultCode=data.resultCode;
                           if(parseInt(resultCode)===0){
                               //处理成 功
                               fn.call(params,data.resultData);
                           }else {
                               layer.msg("服务器消息:"+data.resultDesc);
                           }
                       },
                       error:function () {
                           layer.msg("服务器内部错误，请刷新页面重试");
                       }
                   });
        }
    }
    var modules={
        ajaxTools:ajaxTools
    };
    return function (moduleName, funName) {
        if(arguments.length==0)
            throw new TypeError("缺少必要的参数，moduleName");
        if(!modules[moduleName]){
            throw new TypeError("不存在"+moduleName+"模块");
        }
        if(arguments.length==1){
            return modules[moduleName];
        }else if(arguments.length==2){
            return modules[moduleName][funName];
        }else if(arguments.length>2){
            var params=[];
            for(var i=2;i<arguments.length;i++){
                params.push(arguments[i]);
            }
            return modules[moduleName][funName].apply(modules[moduleName],params);
        }

    }
})(window.jQuery,{
    logCtx:layer ? layer :window.console,
    debug:function (msg) {
        if(this.logCtx==layer){
            layer.msg(msg);
        }else{
            this.logCtx.log(msg);
        }
    }
});