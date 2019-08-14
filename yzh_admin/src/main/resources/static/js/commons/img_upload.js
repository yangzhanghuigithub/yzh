/**
 *依赖于${ctx}/js/lib/jquery.fileupload.js
 * @param fileEleId file控件id
 * @param doneFun   加载完成时的处理方法，会将服务器回传的数据作为参数传入
 * @param params 可选参数对象，可以控制上传文件类型和文件大小,例如
 * {
        acceptFileTypes:/^image\/(gif|jpe?g|png)$/i,
        maxFileSize:1*1024*1024//文件最大上传大小
    }
 */
function uploadUtils(fileEleId,doneFun){
    var control={
        acceptFileTypes:/^image\/(gif|jpe?g|png)$/i,
        maxFileSize:1*1024*1024//文件最大上传大小
    }
    if(arguments.length>2){
        var properties=arguments[2];
        if(properties["acceptFileTypes"]){
            control.acceptFileTypes=properties["acceptFileTypes"];
        }
        if(properties["maxFileSize"]&& typeof parseInt(control.maxFileSize)=="number"){
            control.maxFileSize=properties["maxFileSize"];
        }
    }
    $(fileEleId).fileupload({
                                url:ctx+"nearby/upload.do",
                                dataType: 'json',
                                autoUpload:false,//是否自动上传
                                sequentialUploads: true//是否队列上传,
                            })
        .bind('fileuploadadd', function (e, data) {
            //有图片添加进来时触发的事件
            console.log(data);
            var uploadErrors = [];
            var acceptFileTypes = control.acceptFileTypes;
            var fileSize=control.maxFileSize;//文件限制大小
            var file=data.originalFiles[0];
            //文件类型判断
            if(data.originalFiles[0]['type'] && !acceptFileTypes.test(data.originalFiles[0]['type'])) {
                var suffix = acceptFileTypes.toString();
                suffix = suffix.substring(suffix.indexOf("(")+1,suffix.indexOf(")")).toLocaleUpperCase();
                uploadErrors.push('只接受' + suffix + '格式的图像文件');
            }
            //文件大小判断
            if(data.originalFiles[0]['size'] && data.originalFiles[0]['size'] > fileSize) {
                uploadErrors.push('文件大小不能超过'+new Number(fileSize/1024/1024).toFixed(2)+"MB");
            }
            if(uploadErrors.length > 0) {
                layer.msg(uploadErrors.join("\n"));
            } else {
                data.submit();
            }
        })
        .bind("fileuploaddone",function (e,data) {
            if(typeof doneFun !="function"){
                throw new Error("传入的应当是一个函数");
            }
            doneFun.call(this,data.result);
        })
        .bind("fileuploadfail",function (e,data) {
            //上传失败
            layer.msg("未知异常，上传图片失败，建议刷新页面重试。");
        });
};
function delImg(filePath){
    $.ajax({
       url:ctx+"nearby/delImg.do",
       type:"POST",
       data:{
           filePath:filePath
       },
       dataType:"json",
       success:function (data) {
           var resultCode=parseInt(data.resultCode);
           if(resultCode!==0){
               layer.msg("未知错误，删除失败！");
           }
       }/*,
       error:function (data) {
           layer.msg("服务器内部发生错误，请刷新页面重试！");
       }*/
    });
}