<%--
  Created by IntelliJ IDEA.
  User: phaolo
  Date: 2020-03-15
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<div class="navbar-custom">
    <div class="container-fluid">
        <ul class="list-unstyled topnav-menu float-right mb-0">
            <li class="dropdown notification-list">
                <a class="nav-link dropdown-toggle nav-user mr-0 waves-effect waves-light" data-toggle="dropdown"
                   href="#"
                   role="button" aria-haspopup="false" aria-expanded="false">
                    <img id="profileImg" src="<c:url value='/assets/images/users/profile.png'/>" alt="user-image"
                         class="rounded-circle">
                </a>
                <div class="dropdown-menu dropdown-menu-right profile-dropdown ">
                    <!-- item-->
                    <div class="dropdown-header noti-title">
<%--                                    <h6 class="text-overflow m-0">Xin chào!</h6>--%>
                        <h6 class="text-overflow m-0">
                            <spring:message code="system.welcome"/>
                            <security:authorize access="isAuthenticated()">
                                <security:authentication property="principal.username"/>
                            </security:authorize>
                        </h6>
                    </div>

                    <!-- item-->
                    <a href="javascript:void(0);" class="dropdown-item notify-item" onclick="toggleTheme()">
                        <i class="fas fa-sun" style="color: grey" id="theme"></i>
                        <spring:message code="system.dark_mode"/>
                    </a>

                    <!-- item-->
                    <a href="javascript:void(0);" class="dropdown-item notify-item" onclick="change_password()">
                        <i class="fas fa-user" style="color: grey"></i>
                        <spring:message code="system.profile"/>
                        <%--            <span>Profile</span>--%>
                    </a>
                    <!-- item-->
                    <a href="/logout.html" class="dropdown-item notify-item">
                        <i class="fas fa-sign-out-alt" style="color: grey"></i>
                        <spring:message code="system.sign_out"/>
                        <%--            <span>Logout</span>--%>
                    </a>

                </div>
            </li>
        </ul>

        <!-- LOGO -->
        <div class="logo-box">
            <div class="row">
                <a href="<c:url value='/'/>" class="logo text-center logo-light">
                <span class="logo-lg"><img src="<c:url value='/assets/images/MBF_QuyChuanLogo_white_red.png'/>" alt=""
                                           height="50"></span>
                </a>
                <a href="<c:url value='/'/>" class="logo text-center logo-light">
                <span class="logo-lg"><img src="<c:url value='/assets/images/Title_mPortal.png'/>" alt=""
                                           height="95"></span>
                </a>
            </div>
        </div>

    </div>
</div>
<script>
    const LOCAL_STORAGE_KEY = "toggle-bootstrap-theme";
    const LOCAL_META_DATA = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY));
    let isDark = LOCAL_META_DATA && LOCAL_META_DATA.isDark;
    function toggleTheme() {
        isDark = !isDark;
        const META = {isDark};
        localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(META));
        window.location.reload();
    }

    if (localStorage.getItem(LOCAL_STORAGE_KEY) !== null) {
        let theme = document.getElementById('theme');
        let profile = document.getElementById('profileImg');
        if (isDark) {
            theme.setAttribute("class", "fas fa-moon");
            profile.setAttribute("src", "<c:url value='/assets/images/users/profile-dark.png'/>");
        } else {
            theme.setAttribute("class", "fas fa-sun");
            profile.setAttribute("src", "<c:url value='/assets/images/users/profile.png'/>");
        }
    }

    function change_password() {
        $('#change_pass_modal').modal('show')
    }
</script>
