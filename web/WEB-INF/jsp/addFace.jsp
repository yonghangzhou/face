<%@ page import="org.springframework.web.multipart.MultipartFile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>数据展示</title>

    <!--  Bootstrap 库 -->

    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container">

    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>新增数据</small>
                </h1>
            </div>

        </div>
    </div>


    <form action="${pageContext.request.contextPath}/face/addFace" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label >人脸性别:</label>
            <input type="text" name="sex" class="form-control" required>
        </div>

        <div class="form-group">
            <label >人脸年龄:</label>
            <input type="text" name="age" class="form-control" required>
        </div>

        <div class="form-group">
            <label >人脸图片:</label>
            <input type="file" name="pic" class="form-control">
        </div>

        <div class="form-group">
            <input type="submit" class="form-control" value="添加">
        </div>


    </form>




</div>
</body>
</html>
