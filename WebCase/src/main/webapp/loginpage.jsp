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
<div id="wrapper" class="h-100">
    <div class="content-page ml-0">
        <div class="account-pages mt-5 mb-5">
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

                                <form method="post" action="/users?action=login">
                                    <div>
                                        ${errors}
                                    </div>

                                    <div class="form-group">
                                        <input class="form-control" type="text" id="userName" name="userName" required="" placeholder="Username">
                                    </div>

                                    <div class="form-group">
                                        <input class="form-control" type="password" required="" id="password" name="password" placeholder="Password">
                                    </div>

                                    <div class="form-group">
                                        <input class="form-control text-center" type="submit" value="Log In">
                                    </div>

                                    <div class="form-group">
                                        <div class="form-control w-100 h-100 bg-secondary text-center">
                                            <span class="fa fa-lock mr-1 text-white"></span>
                                            <input type="submit" value="Forgot your password?" class="bg-secondary border-0 text-white">
                                        </div>
                                    </div>

                                    <div class="form-group bg-info">
                                        <div class="text-center form-control">
                                            <p>Don't have an account? <a href="/register.jsp" class="text-primary ml-1"><b>Sign Up</b></a></p>
                                        </div>
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
