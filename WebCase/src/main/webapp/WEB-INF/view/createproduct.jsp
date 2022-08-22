<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product Management</title>
    <jsp:include page="/WEB-INF/layout/head-meta.jsp"></jsp:include>
</head>
<body  data-layout="horizontal">
    <div id="wrapper">
        <c:choose>
            <c:when test="${logincheck!=null}">
                <c:if test="${typeUser.equalsIgnoreCase('admin')}">
                    <jsp:include page="/WEB-INF/layout/top-nav.jsp"></jsp:include>
                </c:if>
                <c:if test="${typeUser.equalsIgnoreCase('user')}">
                    <jsp:include page="/WEB-INF/layout/top-nav-user.jsp"></jsp:include>
                </c:if>
            </c:when>
            <c:otherwise>
                <jsp:include page="/WEB-INF/layout/top-nav-nologin.jsp"></jsp:include>
            </c:otherwise>
        </c:choose>
        <div class="content-page">
            <div class="content">
                <!-- Start Content-->
                <div class="container-fluid">
                    <div class="row">
                        <c:choose>
                            <c:when test="${action.equals('create')}">
                                <div><h3 class="w-100 text-center m-2"> Create Product</h3></div>
                            </c:when>
                            <c:otherwise>
                                <div><h3 class="w-100 text-center m-2"> Edit Product</h3></div>
                            </c:otherwise>
                        </c:choose>
                        <div class="col-lg-12">
                            <form method="post" enctype="multipart/form-data">
                                <div class="form-group">
                                    <label>product Name: </label>
                                    <input type="text" class="form-control" name="productName" id="productName" size="45" value="${product.getProductName()}"/>
                                </div>
                                <div class="form-group">
                                    <label >Description: </label>
                                    <input type="text" class="form-control" name="productDescription" id="productDescription" size="45" value="${product.getProductDescription()}"/>
                                </div>
                                <div class="form-group">
                                    <label>Price: </label>
                                    <input type="text" class="form-control" name="price" id="price" size="45" value="${product.getPrice()}"/>
                                </div>
                                <div class="form-group">
                                    <label>Quaility: </label>
                                    <input type="number" class="form-control" name="quaility" id="quaility" size="45" value="${product.getQuaility()}"/>
                                </div>
                                <div class="form-group">
                                    <label>Image Product: </label>
                                    <input type="file" class="form-control" name="img" id="img" size="45" value="${product.getFileName()}"/>
                                </div>
                                <div class="form-group">
                                    <label >Type of product: </label>
                                    <select name="typeID">
                                        <c:forEach items="${applicationScope.listType}" var="type">
                                        <option value="${type.getTypeID()}">${type.getTypeName()}</option>
                                        </c:forEach>
                                </div>
                                <div class="form-group">
                                    <input type="submit" value="Submit" class="form-control w-25 bg-primary text-white">
                                </div>
                            </form>
                        </div>
                        <div class="text-danger mt-2 w-100">
                            <c:if test="${requestScope.errors!=null}">
                                <div class="alert alert-danger alert-dismissible bg-danger text-white border-0 fade show" role="alert">
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                        <span aria-hidden="true">×</span>
                                    </button>
                                    <h3 class="text-center">Warning</h3>
                                    <c:forEach items="${errors}" var="String">
                                        <ul><h5>${(String.key).toUpperCase()}</h5></ul>
                                        <c:forEach items="${String.value}" var="message">
                                            <li>${message}</li>
                                        </c:forEach>
                                    </c:forEach>
                                </div>
                            </c:if>
                            <c:if test="${errorsprice!=null}">
                                <<div class="alert alert-danger alert-dismissible bg-danger text-white border-0 fade show" role="alert">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>
                                    <ul>
                                        <li>${errorsprice}</li>
                                    </ul>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<jsp:include page="/WEB-INF/layout/footer-js.jsp"></jsp:include>
</body>
</html>
