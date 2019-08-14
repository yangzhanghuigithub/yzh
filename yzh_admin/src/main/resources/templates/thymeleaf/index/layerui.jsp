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
    <script type="text/javascript" src="${ctx}/js/lib/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/common/bootstrap/js/bootstrap.min.js"></script>
    <style>
        body{
            padding: 0px;
            margin: 0px;
        }
    </style>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">
            <img src="${ctx}/image/logo2.png"/>
        </div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a href="">控制台</a></li>
            <li class="layui-nav-item"><a href="">商品管理</a></li>
            <li class="layui-nav-item"><a href="">用户</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;">其它系统</a>
                <dl class="layui-nav-child">
                    <dd><a href="">邮件管理</a></dd>
                    <dd><a href="">消息管理</a></dd>
                    <dd><a href="">授权管理</a></dd>
                </dl>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
                    贤心
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="">基本资料</a></dd>
                    <dd><a href="">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="">退了</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree"  lay-filter="test">
                <li class="layui-nav-item layui-nav-itemed">
                    <a class="" href="javascript:;">常用工具</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">获取Console参数</a></dd>
                        <dd><a href="javascript:;">修改密码</a></dd>
                        <dd><a href="javascript:;">Pos接口工具</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">解决方案</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">列表一</a></dd>
                        <dd><a href="javascript:;">列表二</a></dd>
                        <dd><a href="">超链接</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item"><a href="">云市场</a></li>
                <li class="layui-nav-item"><a href="">发布商品</a></li>
            </ul>
        </div>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;">内容主体区域</div>
    </div>

    <hr style="height:5px;border:none;border-top:5px ridge green;margin-top: 612px;"/>

    <div class="layui-footer" style="text-align: center;background-color: #fff">
        <!-- 底部固定区域 -->
        <img src="${ctx}/image/bottom_logo.jpg" alt="影核" style="width: 20px;">Copyright © 2018 <a>影核</a>. All Rights Reserved.
    </div>
</div>
<script src="${ctx}/common/layui/layui.js"></script>
<script>
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;

    });
</script>
</body>
</html>
