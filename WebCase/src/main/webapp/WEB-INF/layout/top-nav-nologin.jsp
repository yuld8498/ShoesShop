<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="navbar-custom">
  <ul class="list-unstyled topnav-menu float-right mb-0">

    <li class="d-none d-sm-block">
        <a href="/product?action=orderList" class="nav-link waves-effect waves-light"
           data-toggle="tooltip" data-placement="top" title="Show Order">
            <span class="fe-shopping-bag"></span>
        </a>
<%--      <form class="app-search">--%>
<%--        <div class="app-search-box">--%>
<%--          <div class="input-group" disabled>--%>
<%--            <input type="text" class="form-control" placeholder="Search...">--%>
<%--            <div class="input-group-append">--%>
<%--              <button class="btn" type="submit">--%>
<%--                <i class="fe-search"></i>--%>
<%--              </button>--%>
<%--            </div>--%>
<%--          </div>--%>
<%--        </div>--%>
<%--      </form>--%>
    </li>
    <li class="dropdown notification-list">
      <a class="nav-link dropdown-toggle nav-user mr-0 waves-effect waves-light" data-toggle="dropdown"
         href="#" role="button" aria-haspopup="false" aria-expanded="false">
        <img src="logo.png" alt="user-image" class="rounded-circle">
        <span class="pro-user-name ml-1">
                            Setting<i class="mdi mdi-chevron-down"></i>
                        </span>
      </a>
      <div class="dropdown-menu dropdown-menu-right profile-dropdown ">
        <!-- item-->
        <div class="dropdown-item noti-title">
          <h5 class="m-0 text-white">
            Welcome !
          </h5>
        </div>
        <a href="/users?action=manager" class="dropdown-item noti-title text-white">
          <span class="fe-log-out"> My Account</span>
          <%--                    <a href="/users?action=logout"></a>--%>
        </a>
<%--        <div class="dropdown-item noti-title text-white">--%>
<%--          <span class="fe-settings">  Setting</span>--%>
<%--          <input type="submit" value="" class="m-0 text-white border-0 badge-light-dark">--%>
<%--        </div>--%>
        <a href="/users?action=logout" class="dropdown-item noti-title text-white">
          <span class="fe-log-out">  Log Out</span>
          <%--                    <a href="/users?action=logout"></a>--%>
        </a>
      </div>
    </li>

<%--    <li class="dropdown notification-list">--%>
<%--      <a href="javascript:void(0);" class="nav-link right-bar-toggle waves-effect waves-light">--%>
<%--        <i class="fe-settings noti-icon"></i>--%>
<%--      </a>--%>
<%--    </li>--%>
  </ul>
  <!-- LOGO -->
  <div class="logo-box">
    <a href="/product" class="logo text-center">
                    <span class="logo-lg">
                        <img src="logo.png" alt="" height="50">
                      <!-- <span class="logo-lg-text-light">Upvex</span> -->
                    </span>
      <span class="logo-sm">
                        <!-- <span class="logo-sm-text-dark">X</span> -->
                        <img src="logo.png" alt="" height="30">
                    </span>
    </a>
  </div>

  <ul class="list-unstyled topnav-menu topnav-menu-left m-0">
    <li>
      <button class="button-menu-mobile waves-effect waves-light">
        <span></span>
        <span></span>
        <span></span>
      </button>
    </li>

    <li class="dropdown d-none d-lg-block">
<%--      <div class="nav-link waves-effect waves-light">--%>
        <a href="/product?action=list" class="nav-link waves-effect waves-light">
          Product List
        </a>
<%--      </div>--%>
    </li>
    <li class="dropdown d-none d-lg-block">
<%--      <div class="nav-link waves-effect waves-light">--%>
        <a href="/users?action=login" class="nav-link waves-effect waves-light">
          Login User
        </a>
<%--      </div>--%>
    </li>
<%--    <li class="dropdown d-none d-lg-block">--%>
<%--      &lt;%&ndash;      <div class="nav-link waves-effect waves-light">&ndash;%&gt;--%>
<%--      <a href="/users?action=create" class="nav-link waves-effect waves-light">--%>
<%--        Register User--%>
<%--      </a>--%>
<%--      &lt;%&ndash;      </div>&ndash;%&gt;--%>
<%--    </li>--%>

  </ul>
</div>
<div class="left-side-menu">

  <div class="slimscroll-menu">

    <!--- Sidemenu -->
    <div id="sidebar-menu">

      <ul class="metismenu" id="side-menu">
        <li>
          <a href="javascript: void(0);">
            <i class="la la-bullseye"></i>
            <span> Menu </span>
            <span class="menu-arrow"></span>
          </a>
          <ul class="nav-second-level" aria-expanded="false">
            <li>
              <a href="/product">Product</a>
            </li>
            <li>
              <a href="/users">User Manager</a>
            </li>
          </ul>
        </li>
      </ul>

    </div>
    <!-- End Sidebar -->
    <div class="clearfix"></div>

  </div>
  <!-- Sidebar -left -->

</div>