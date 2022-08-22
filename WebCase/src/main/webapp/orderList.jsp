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
<body data-layout="horizontal">
<div id="wrapper">
    <jsp:include page="/WEB-INF/layout/top-nav.jsp"></jsp:include>
    <div class="content-page">
        <div class="content">
            <!-- Start Content-->
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12">
                        <div class="card-box">
                            <table class="table table-striped nowrap"
                                   style="border-collapse: collapse; border-spacing: 0; width: 100%;">
                                <thead>
                                <tr class="bg-primary text-white">
                                    <th>Checked</th>
                                    <th>Product Name</th>
                                    <th>Product IMG</th>
                                    <th>Product type</th>
                                    <th>Quantity</th>
                                    <th>Price</th>
                                    <th>Total</th>
                                    <c:if test="${type.equalsIgnoreCase('admin')}">
                                        <th>Status</th>
                                    </c:if>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="order" items="${orderList}">
                                    <tr>
                                        <c:forEach var="product" items="${listProduct}">
                                            <c:if test="${order.getProductID()==product.getProductID()}">
                                                <td><input type="checkbox" value="${order.getID()}" name="checked"></td>
                                                <td>${product.getProductName()}</td>
                                                <td style="height:80px "><img src="images/${product.getFileName()}"
                                                                              alt="" style="height: 100%"></td>
                                                <td>${product.getTypeName()}</td>
                                                <td>${order.getProductQuaility()}</td>
                                                <td>${product.getPrice()}</td>
                                                <td>${product.getPrice()*order.getProductQuaility()}</td>
                                                <c:if test="${type.equalsIgnoreCase('admin')}">
                                                    <td>
                                                        <select name="status" id="">
                                                            <option value="${order.getIDStatus()}">hello</option>
                                                            <c:forEach items="${typeOrderList}" var="typeOrder">
                                                                <option value="${typeOrder.getTypeOrderID()}">${typeOrder.getTypeOrderName()}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div>
                            <a class="btn btn-primary btn-lg waves-effect waves-light float-right text-white"
                               href="/product?action=confirmorder">Confirm Order</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
</script>
<jsp:include page="/WEB-INF/layout/footer-js.jsp"></jsp:include>
</body>
</html>
