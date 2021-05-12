<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/add.css">
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/admin/Book/list.do?categoryId=${book.categoryId}">
                慕课网图书管理
            </a>
        </div>
    </div>
</nav>
<div class="container">
    <div class="jumbotron">
        <h1>Hello,Admin!</h1>
        <p>请小心的修改图书记录，要是修改错误了就不好了。。。</p>
    </div>
    <div class="page-header">
        <h3><small>修改</small></h3>
    </div>
    <form class="form-horizontal" action="/admin/Book/edit.do" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${book.id}"/>
        <div id="content">
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">名称 ：</label>
                <div class="col-sm-8">
                    <input name="name" class="form-control" id="name" value="${book.name}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">分类 ：</label>
                <select name="categoryId" class="col-sm-2 form-control" style="width: auto">
                    <!-- 此处数据需要从数据库中读取 -->
                    <c:forEach items="${categoryList}" var="category">
                        <c:if test="${book.categoryId == category.id}">
                            <option value="${category.id}" selected>${category.name}</option>
                        </c:if>
                        <c:if test="${book.categoryId != category.id}">
                            <option value="${category.id}"\>${category.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">星级 ：</label>
                <select name="level" class="col-sm-2 form-control" style="width: auto">
                    <c:forEach begin="1" end="5" var="i">
                        <c:if test="${book.level == i}">
                            <option id="${i}" value="${i}" selected="selected">${i}星</option>
                        </c:if>
                        <c:if test="${book.level != i}">
                            <option id="${i}" value="${i}">${i}星</option>
                        </c:if>

                    </c:forEach>

                </select>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">价格 ：</label>
                <div class="col-sm-8">
                    <input name="price" type="number" class="form-control" id="price" value="${book.price}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">图片 ：</label>
                <div class="col-sm-8">
                    <input type="hidden" name="imgPath" value="${book.imgPath}"/>
                    <input name="smallImg" class="file-loading"
                           type="file" multiple accept=".jpg,.jpeg,.png" data-min-file-count="1"
                           data-show-preview="true">
                    <input type="hidden" name="end_flag" value="true">
                </div>
            </div>

        </div>
        <div align="right">
            <button type="button" onclick="addContent()" class="btn btn-primary">增加</button>&nbsp;&nbsp;&nbsp;
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary">保存</button>&nbsp;&nbsp;&nbsp;
            </div>
        </div>
    </form>
</div>
<footer class="text-center" >
    copy@imooc
</footer>
<script type="text/javascript">
    var index = 1;
    function addContent() {
        var html =
            '           <h5 class="page-header"></h5>\n' +
            '           <div id="_div_"' + index + '>\n' +
            '               <div class="form-group">\n' +
            '                    <label for="name" class="col-sm-2 control-label">名称 ：</label>\n' +
            '                    <div class="col-sm-8">\n' +
            '                        <input name="name" class="form-control" id="name">\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '                <div class="form-group">\n' +
            '                    <label class="col-sm-2 control-label">分类 ：</label>\n' +
            '                    <select name="categoryId" class="col-sm-2 form-control" style="width: auto">\n' +
            '                        <c:forEach var="item" items="${categories}">\n' +
            '                            <option id="${item.id}" value="${item.id}">${item.name}</option>\n' +
            '                        </c:forEach>\n' +
            '                    </select>\n' +
            '                </div>\n' +
            '                <div class="form-group">\n' +
            '                    <label class="col-sm-2 control-label">星级 ：</label>\n' +
            '                    <select name="level" class="col-sm-2 form-control" style="width: auto">\n' +
            '                        <option id="1" value="1">1星</option>\n' +
            '                        <option id="2" value="2">2星</option>\n' +
            '                        <option id="3" value="3">3星</option>\n' +
            '                        <option id="4" value="4">4星</option>\n' +
            '                        <option id="5" value="5">5星</option>\n' +
            '                    </select>\n' +
            '                </div>\n' +
            '                <div class="form-group">\n' +
            '                    <label class="col-sm-2 control-label">价格 ：</label>\n' +
            '                    <div class="col-sm-8">\n' +
            '                        <input name="price" type="number" class="form-control" id="price">\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '                <div class="form-group">\n' +
            '                    <label class="col-sm-2 control-label">图片 ：</label>\n' +
            '                    <div class="col-sm-8">\n' +
            '                        <input name="smallImg" class="file-loading"\n' +
            '                               type="file" multiple accept=".jpg,.jpeg,.png" data-min-file-count="1"\n' +
            '                               data-show-preview="true">\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '             </div>\n';

        var newDiv = document.createElement('div');
        newDiv.id = '_' + index++;
        newDiv.innerHTML = html;
        document.getElementById('content').appendChild(newDiv);
    }
</script>
</body>
</html>

