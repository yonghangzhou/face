<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改数据</title>

    <!--  Bootstrap 库 -->

    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container">

    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>修改数据</small>
                </h1>
            </div>

        </div>
    </div>


    <form action="${pageContext.request.contextPath}/face/updateFace" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label >人脸ID:</label>
            <input type="text" name="id" class="form-control" value="${QFace.faceID}" required>
        </div>

        <div class="form-group">
            <label >人脸性别:</label>
            <input type="text" name="sex" class="form-control" value="${QFace.faceSex}" required>
        </div>

        <div class="form-group">
            <label >人脸年龄:</label>
            <input type="text" name="age" class="form-control" value="${QFace.faceAge}" required>
        </div>

        <div class="form-group">
            <label >人脸图片:</label>
            <input type="file" name="pic" class="form-control">
        </div>

        <div class="form-group">
            <input type="submit" class="form-control" value="修改">
        </div>


    </form>




</div>
</body>
</html>
