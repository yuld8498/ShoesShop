<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <jsp:include page="WEB-INF/layout/head-meta.jsp"></jsp:include>
</head>
<body style="background-image: linear-gradient(to right, #c9c5c5,#5d5757)">
<div id="wrapper">
    <div class="text-danger mt-2 w-100 text-center">
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
    </div>
    <div class="content-page ml-0">
        <div class="account-pages mt-0 mb-0">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-10 col-lg-6 col-xl-5">
                        <div class="card">

                            <div class="text-center account-logo-box">
                                <div class="mt-2 mb-2">
                                    <a href="/loginpage.jsp" class="text-success">
                                        <div><span><img src="logo.png" alt="" height="50"></span></div>
                                        <span class="text-white">Shoes for men's</span>
                                    </a>
                                </div>
                            </div>

                            <div class="card-body">

                                <form action="users?action=create" method="post">

                                    <div class="form-group">
                                        <input class="form-control" type="text" id="fullName" name="fullName" required="" placeholder="Full Name">
                                    </div>

                                    <div class="form-group">
                                        <input class="form-control" type="text" id="age" name="age" required="" placeholder="enter your age">
                                        <div>${errorsAge}</div>
                                    </div>

                                    <div class="form-group">
                                        <input class="form-control" type="text" id="username"  name="userName" required="" placeholder="Username">
                                        <div>${errorsUserName}</div>
                                    </div>

                                    <div class="form-group">
                                        <input class="form-control" type="password" name="password" required="" id="password" placeholder="Password">
                                    </div>

                                    <div class="form-group">
                                        <input class="form-control" type="text" id="email" name="email" required="" placeholder="enter your email">
                                        <div>${errorsEmail}</div>
                                    </div>

                                    <div class="form-group">
                                        <input class="form-control" type="text" id="phone" name="phone" required="" placeholder="enter your phone number">
                                        <div>${errorsPhone}</div>
                                    </div>

                                    <div class="form-group">
                                        <select name="country" id="">
                                            <c:forEach items="${applicationScope.countryList}" var="country">
                                                <option value="${country.getIdCountry()}">${country.getCountryName()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <input class="form-control text-center" type="submit" value="Register">
                                    </div>

                                </form>
                            </div>
                            <!-- end card-body -->
                        </div>
                        <!-- end card -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="WEB-INF/layout/footer-js.jsp"></jsp:include>
</body>
</html>
