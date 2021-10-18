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
                    <small>数据列表————显示所有数据</small>
                </h1>
            </div>

        </div>
    </div>

    <div class="row">
        <div class="col-md-4 column">
            <a href="${pageContext.request.contextPath}/face/toAdd">新增数据</a>

        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-hover table-striped">
                <thead>
                    <tr>
                        <th>人脸编号</th>
                        <th>性别</th>
                        <th>年龄</th>
                        <th>文件地址</th>
                        <th>操作</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="Faces" items="${list}">
                        <tr>
                            <td>${Faces.faceID}</td>
                            <td>${Faces.faceSex}</td>
                            <td>${Faces.faceAge}</td>
                            <td>${Faces.faceFile}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/face/toUpdate?id=${Faces.faceID}">修改</a>
                                &nbsp;|&nbsp;
                                <a href="${pageContext.request.contextPath}/face/deleteFace/${Faces.faceID}">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </div>
    </div>


</div>
</body>
</html>
