<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>分类列表</title>
    <link rel="stylesheet" href="/css/index.css">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>

<body>
<header>
    <div class="container">
        <c:forEach items="${categoryList}" var="category">
            <nav>
                <a href="/admin/Book/list.do?categoryId=${category.id}" >${category.name}</a>
            </nav>
        </c:forEach>
        <nav>
            <a href="/admin/Category/list.do" >分类</a>
        </nav>

    </div>
</header>
<section class="banner">
    <div class="container">
        <div>
            <h1>图书</h1>
            <p>图书分类列表</p>
            <p style="color:red">${msg}</p>
        </div>
    </div>
</section>
<section class="main">
    <div class="container">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>名称</th>
                <th>创建时间</th>
                <th>最后修改时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${categoryList}" var="category">
                <tr>
                    <td>${category.name}</td>
                    <td><fmt:formatDate value="${category.createTime}" type="both"/></td>
                    <td><fmt:formatDate value="${category.updateTime}" type="both"/></td>
                    <td>
                        <a href="/admin/Category/toEdit.do?id=${category.id}">编辑</a>
                        <a href="/admin/Category/remove.do?id=${category.id}">删除</a>
                        <a href="/admin/Category/descadeRemove.do?id=${category.id}">删除目录及书籍</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</section>
<section class="page">
    <div class="container">
        <div id="fatie">
            <a href="/admin/Category/toAdd.do"><button>新建</button></a>
        </div>
    </div>
</section>
<footer>
    copy@慕课网
</footer>
</body>
</html>