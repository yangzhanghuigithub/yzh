<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>影核常用工具</title>
    <%@include file="/public/common.jsp" %>
    <Link rel="icon" href="${ctx}/image/bottom_logo.jpg">
    <link rel="stylesheet" href="${ctx}/common/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx}/common/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/common/bootstrap/css/bootstrap-theme.min.css">
    <script type="text/javascript" src="${ctx}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/common/bootstrap/js/bootstrap.min.js"></script>
    <script src="${ctx}/common/miniui/scripts/boot.js" type="text/javascript"></script>
    <link href="${ctx}/common/miniui/res/third-party/scrollbar/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/common/miniui/res/third-party/scrollbar/jquery.mCustomScrollbar.concat.min.js" type="text/javascript"></script>
    <link href="${ctx}/common/miniui/frame/frame1/res/menu/menu.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/common/miniui/frame/frame1/res/menu/menu.js" type="text/javascript"></script>
    <script src="${ctx}/common/miniui/frame/frame1/res/menutip.js" type="text/javascript"></script>
    <link href="${ctx}/common/miniui/frame/frame1/res/tabs.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/common/miniui/frame/frame1/res/frame.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/common/miniui/frame/frame1/res/index.css" rel="stylesheet" type="text/css" />
    <style>
        body{
            padding: 0px;
            margin: 0px;
        }
        .cotainer{
            box-sizing: border-box;
            color: rgb(57, 57, 57);
            font-family: 华文行楷;
            font-size: 14px;
            font-weight: 400;
            line-height: 21px;
            margin-bottom: 0px;
            margin-left: 0px;
            margin-right: 0px;
            margin-top: 0px;
            padding-bottom: 0px;
            padding-left: 0px;
            padding-right: 0px;
            padding-top: 0px;
            text-align: right;
        }
        input[type='text']{
            height: 32px;
            width: 220px;
        }
        select{
            height: 32px;
            width: 220px;
        }
        form{
            margin-top: 50px;
        }
        .rrr{
            color: red;
        }
        .ace{
            height: 13px;
        }
        button{
            height: 37px;
        }
    </style>
</head>
<body>
    <form class="form-horizontal bt-validate" id="validation-form" role="form" method="post">
        <div class="form-group">
            <label for="inputWarning" class="col-xs-12 col-sm-2 control-label no-padding-right"><i class="rrr">*</i>&nbsp;&nbsp;环境</label>
            <div class="col-md-8">
                <div class="radio">
                    <label>
                        <input name="platform" type="radio" value="1" class="ace" checked="" onclick="selectPlatform(1)">
                        <span class="lbl">147环境</span>
                    </label>
                    <label>
                        <input name="platform" type="radio" value="2" class="ace" onclick="selectPlatform(2)">
                        <span class="lbl">生产环境</span>
                    </label>
                    <label>
                        <input name="platform" type="radio" value="3" class="ace" onclick="selectPlatform(3)">
                        <span class="lbl">其他</span>
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="inputWarning" class="col-xs-12 col-sm-2 control-label no-padding-right"><i class="rrr">*</i>&nbsp;&nbsp;<span id="select_databases">选择数据库</span></label>
            <div class="col-md-10" id="select12Div">
                <select name="database" id="database" onchange="selectFre(this)" class="{required:true,messages:{required:'不能为空'}} valid"><option value="learn">只读库 learn</option><option value="0">--自定义--</option></select>
            </div>
            <div class="col-md-10" id="select3Div" style="display:none;"></div>
        </div>

        <script>
            function selectPlatform(value){
                if(value!=null && value=='1'){
                    $("#select12Div").show();
                    $("#select3Div").html("").hide();
                    //147环境
                    var html = '';
                    html += '<option value="dev_learn">开发环境 dev_learn</option>';
                    html += '<option value="test_learn">测试环境 test_learn</option>';
                    html += '<option value="testwo_learn">测试2环境 testwo_learn</option>';
                    html += '<option value="spare_learn">Spare环境 spare_learn</option>';
                    if($("#inputDatabase").length>0){
                        html += '<option value="0" selected>--自定义--</option>';
                    }else{
                        html += '<option value="0">--自定义--</option>';
                    }
                    $("#database").html(html);
                }else if(value!=null && value=='2'){
                    $("#select12Div").show();
                    $("#select3Div").hide().html("");
                    //线上环境
                    var html = '';
                    html += '<option value="learn">只读库 learn</option>';
                    if($("#inputDatabase").length>0){
                        html += '<option value="0" selected>--自定义--</option>';
                    }else{
                        html += '<option value="0">--自定义--</option>';
                    }
                    $("#database").html(html);
                }else if(value!=null && value=='3'){
                    //其他环境
                    $("#select12Div").hide();
                    $("#select3Div").show();
                    var dhost = "127.0.0.1";
                    if(getCookie("dhost")!=null && getCookie("dhost")!=''){
                        dhost = getCookie("dhost");
                    }
                    var dname = "learn";
                    if(getCookie("dname")!=null && getCookie("dname")!=''){
                        dname = getCookie("dname");
                    }
                    var duser = "root";
                    if(getCookie("duser")!=null && getCookie("duser")!=''){
                        duser = getCookie("duser");
                    }
                    var dpwd = "root";
                    if(getCookie("dpwd")!=null && getCookie("dpwd")!=''){
                        dpwd = getCookie("dpwd");
                    }
                    var html = '';
                    html += '<input type="text" style="margin-right:10px" value="'+dhost+'" name="dhost" class="{required:true,maxlength:30,messages:{required:\'请填写host\',maxlength:\'最多允许填写30个字\'}}" placeholder="host"/>';
                    html += '<input type="text" style="margin-right:10px" value="'+dname+'" name="dname" class="{required:true,maxlength:30,messages:{required:\'请填写数据库名\',maxlength:\'最多允许填写30个字\'}}" placeholder="数据库名"/>';
                    html += '<input type="text" style="margin-right:10px" value="'+duser+'" name="duser" class="{required:true,maxlength:60,messages:{required:\'请填写用户名\',maxlength:\'最多允许填写60个字\'}}" placeholder="用户名"/>';
                    html += '<input type="text" style="margin-right:10px" value="'+dpwd+'" name="dpwd" class="{required:true,maxlength:60,messages:{required:\'请填写密码\',maxlength:\'最多允许填写60个字\'}}" placeholder="密码"/>';
                    html += '<p class="rrr">请授予当前系统所在服务器IP访问权限，比如授权允许 192.168.0.123 访问你所指定的数据库 或 你的电脑IP</p>';
                    $("#select3Div").html(html);
                }
            }
            function getCookie(name){
                var strcookie = document.cookie;//获取cookie字符串
                var arrcookie = strcookie.split("; ");//分割
                //遍历匹配
                for ( var i = 0; i < arrcookie.length; i++) {
                    var arr = arrcookie[i].split("=");
                    if (arr[0] == name){
                        return arr[1];
                    }
                }
                return "";
            }
            function selectFre(th){
                var cc = $(th).val();
                if(cc!=null && cc!='' && cc=='0'){
                    //自定义
                    var html = "<input style='margin-right:10px' type='text' name='inputDatabase' id='inputDatabase' value='' class='{required:true,maxlength:30,messages:{required:\"请填写数据库名称\",maxlength:\"最多允许填写30个字\"}}'/>";
                    $("#database").before(html);
                    $('span label[for=database]').css('display', "none");//取消validate的报错信息
                    $("#select_databases").html("填写数据库");
                }else{
                    $('span label[for=inputDatabase]').css('display', "none");//取消validate的报错信息
                    $("#inputDatabase").remove();
                    $("#select_databases").html("选择数据库");
                }
            }
        </script>
        <div class="form-group">
            <label for="inputWarning" class="col-xs-12 col-sm-2 control-label no-padding-right"></label>
            <div class="col-md-8 rrr">
                影城编码、company_code、手机号、member_code 和 影城名称至少保持一个不为空
            </div>
        </div>
        <div class="form-group">
            <label for="inputWarning" class="col-xs-12 col-sm-2 control-label no-padding-right">影城编码</label>
            <div class="col-md-8">
                <input class="input-xlarge" type="text" name="cinemaCode" placeholder="影城编码">
                <input class="input-xlarge" type="text" name="companyCode" placeholder="company_code">
            </div>
        </div>
        <div class="form-group">
            <label for="inputWarning" class="col-xs-12 col-sm-2 control-label no-padding-right">手机号</label>
            <div class="col-md-8">
                <input class="input-xlarge valid" type="text" name="memberPhone" placeholder="手机号">
                <input class="input-xlarge" type="text" name="memberCode" placeholder="member_code">
            </div>
        </div>
        <div class="form-group">
            <label for="inputWarning" class="col-xs-12 col-sm-2 control-label no-padding-right">影城名称</label>
            <div class="col-md-8">
                <input class="input-xlarge" type="text" name="cinemaName" placeholder="影院名称模糊查询">
            </div>
        </div>
    </form>
    <!-- 表单项结束 -->
    <hr>
    <div class="wizard-actions">
        <div class="col-md-offset-2 col-md-8">
            <button class="btn btn-primary btn-xs col-md-2 btn-round" onclick="search()" id="submit_btn" data-loading-text="<i class='fa fa-spinner fa-spin fa-1x fa-fw margin-bottom'></i>  处理中...">
                <span class="bigger-110"><i></i> 查 询 </span>
            </button>
        </div>
    </div>


    <div class="panel panel-primary" style="margin-top: 90px;display: none">
        <div class="panel-body">
            Console数据
        </div>
        <div class="panel-footer">

        </div>
    </div>
    <table>
        <tr>
            <td>
                <table cellpadding="0" cellspacing="0" class="lv_unfoldTable">
                    <tr class="lv_unfoldTr">
                        <td class="perWin20"><b>卡片名称</b></td>
                        <td class="perWin10"><b>卡类型</b></td>
                        <td class="perWin10"><b>基本概要</b></td>
                        <td class="perWin10"><b></b></td>
                        <td class="perWin10"><b>卡售价</b></td>
                        <td class="perWin10"><b>购卡数量</b></td>
                        <td class="perWin10"><b>绑卡数量</b></td>
                        <td class="perWin10"><b>购卡总金额</b></td>
                        <td class="perWin10"><b></b></td>
                    </tr>
                    <tr class="lv_unfoldTr">
                        <td>美嘉三里屯折扣储值卡</td>
                        <td>储值卡</td>
                        <td>抵值额：100.00元<br>工本费：11.00元</td>
                        <td></td>
                        <td>100.00元</td>
                        <td>20</td>
                        <td>18</td>
                        <td>45,465.00元</td>
                        <td></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
<script>
    function backSubmit(){
        $("#result").hide().html("");
        $.ajax({
            type: "POST",
            url:'',
            data:$('#validation-form').serialize(),// 你的formid
            async: true,
            success: function(data) {
                $("#result").show().html(data.msg);
                $("#submit_btn").button('reset');
            }
        });
    }

    function search() {
        $(".bt-validate").validate({
            onsubmit:true,
            submitHandler: function(form) {
                // 防止表单重复提交，前端js限制
                var attr = $(form).find(":submit").attr("attr");
                if(attr!=null && attr.length>0 && attr=="no"){
                }else{
                    $(form).find(":submit").button('loading');
                }
                //form.submit();
                backSubmit();
            }
        });
    }
</script>
</body>
</html>
