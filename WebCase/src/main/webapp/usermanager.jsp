<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <%--    <meta charset="UTF-8">--%>
    <title>Product Management</title>
    <jsp:include page="/WEB-INF/layout/head-meta.jsp"></jsp:include>
    <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<body>
<!-- Begin page -->
<div id="wrapper">
    <c:choose>
        <c:when test="${logincheck!=null}">
            <c:if test="${typeUser.equalsIgnoreCase('admin')}">
                <jsp:include page="/WEB-INF/layout/top-nav.jsp"></jsp:include>
            </c:if>
        </c:when>
        <c:otherwise>
            <jsp:include page="/WEB-INF/layout/top-nav-user.jsp"></jsp:include>
        </c:otherwise>
    </c:choose>
    <div class="content-page pt-3">
        <div class="content">
            <!-- Start Content-->
            <div class="container-fluid">
                <!-- end page title -->
                <div class="row">
                    <div class="col-lg-4 col-xl-4">
                        <div class="card-box text-center">
                            <img src="assets\images\users\user-1.jpg" class="rounded-circle avatar-lg img-thumbnail"
                                 alt="profile-image">
                            <h3 class="mb-0">${User.getFullName()}</h3>
                            <div class="text-left mt-3">
                                <h3 class="font-13 text-uppercase">Infomation</h3>
                                <p class="text-muted mb-2 font-13"><strong>User Name :</strong> <span
                                        class="ml-2">${User.getUserName()}</span>
                                </p>
                                <p class="text-muted mb-1 font-13"><strong>Password :</strong> <span
                                        class="ml-2">***********</span>
                                    <a href="/users?action=changepassword" class="float-right">change</a>
                                </p>
                                <p class="text-muted mb-2 font-13"><strong>Mobile :</strong>
                                    <span class="ml-2">${User.getPhoneNumber()}</span>
<%--                                    <a href="#" class="float-right">change</a>--%>
                                </p>
                                <p class="text-muted mb-2 font-13"><strong>Email :</strong> <span
                                        class="ml-2 ">${User.getEmail()}</span>
<%--                                    <a href="#" class="float-right">change</a>--%>
                                </p>
                                <p class="text-muted mb-1 font-13"><strong>Location :</strong> <span
                                        class="ml-2">${Address}</span>
                                </p>
                            </div>

                        </div> <!-- end card-box -->

                    </div> <!-- end col-->

                    <c:if test="${password!=null}">
                        <div class="col-lg-8 col-xl-8">
                            <div class="card-box">
                                <form class="mt-2 mb-3" method="post">
                                    <div class="input-icon">Password: </div>
                                    <div class="form-items">
                                        <input type="password" class="form-control" placeholder="input now password" name="password"></input>
                                        <div>${errorspw}</div>
                                    </div>
                                    <div class="input-icon">New Password: </div>
                                    <div class="form-items">
                                        <input type="password" class="form-control" placeholder="input now password" name="newpassword"></input>
                                        <div>${errorspw2}</div>
                                    </div>
                                    <div class="input-icon">Retype New Password: </div>
                                    <div class="form-items">
                                        <input type="password" class="form-control" placeholder="input now password" name="newpasswordreaplay"></input>
                                        <div>${errorspw2}</div>
                                    </div>
                                    <input type="submit" value="Change Password" class="btn btn-primary mt-2">
                                </form>
                                <div class="mt-2">
                                    <c:if test="${requestScope.errors!=null}">
                                        <div class="alert alert-icon alert-danger alert-dismissible fade show mb-0" role="alert">
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
                            </div> <!-- end card-box-->
                        </div>
                    </c:if>
                </div>
                <!-- end row-->
            </div> <!-- container -->
        </div> <!-- content -->
    </div>
</div>
<c:if test="${requestScope.success!=null}">
    <script>
        $(document).ready(function () {
            <% String message= (String) request.getAttribute("success"); %>
            var success = "<%= message %>";
            toastr.options = {
                "closeButton": true,
                "debug": false,
                "newestOnTop": false,
                "progressBar": false,
                "positionClass": "toast-top-right",
                "preventDuplicates": false,
                "onclick": null,
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "5000",
                "extendedTimeOut": "1000",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
            }
            toastr["success"](success)
        });
    </script>
</c:if>
<!-- Right bar overlay-->

<jsp:include page="/WEB-INF/layout/footer-js.jsp"></jsp:include>
</body>
</html>