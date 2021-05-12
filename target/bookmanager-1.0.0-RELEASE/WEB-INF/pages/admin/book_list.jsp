<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>图书列表</title>
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
            <p>图书列表</p>
        </div>
    </div>
</section>
<section class="main">
    <div class="container">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>名称</th>
                <th>分类</th>
                <th>创建时间</th>
                <th>最后修改时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pageInfo.list}" var="book">
                <tr>
                    <td>《 ${book.name} 》</td>
                    <td>${book.categoryId}</td>
                    <td><fmt:formatDate value="${book.createTime}" type="both"/></td>
                    <td><fmt:formatDate value="${book.updateTime}" type="both"/></td>
                    <td>
                        <a href="/admin/Book/toEdit.do?id=${book.id}">编辑</a>
                        <a href="/admin/Book/remove.do?id=${book.id}">删除</a>
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
            <a href="/admin/Book/toAdd.do?categoryId=${categoryId}"><button>新建</button></a>
        </div>
        <div  align="right">
            <!-- 分页部分可以参照MyBatis拦截器的例子-->
            <ul class="pagination">
                <li><a href="/admin/Book/list.do?pageNum=1">首页</a></li>
                <li><a href="/admin/Book/list.do?pageNum=${pageInfo.pageNum-1}">上一页</a></li>
                <li><a href="/admin/Book/list.do?pageNum=${pageInfo.pageNum}">共${pageInfo.total}条 ${pageInfo.pageNum}/${pageInfo.pages}</a></li>
                <li><a href="/admin/Book/list.do?pageNum=${pageInfo.pageNum+1}">下一页</a></li>
                <li><a href="/admin/Book/list.do?pageNum=${pageInfo.pages}">尾页</a></li>
            </ul>
        </div>
    </div>
</section>
<footer>
    copy@慕课网
</footer>
</body>
</html>
