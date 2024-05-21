<%@include file="/common/taglibs.jsp" %>
<html>
<head>
    <title><spring:message code="system.home"/></title>
</head>
<body>
<div class="container-fluid">

    <!-- start page title -->
    <div class="row">
        <div class="col-12">
            <div class="page-title-box">
                <div class="page-title-right">
                    <ol class="breadcrumb m-0">
                        <li class="breadcrumb-item active"><spring:message code="system.home"/></li>
                    </ol>
                </div>
                <%--				<h4 class="page-title">Welcome page</h4>--%>
                <h4 class="page-title"><spring:message code="system.welcome"/>
                    <security:authorize access="isAuthenticated()">
                        <security:authentication property="principal.username"/>
                    </security:authorize>
                </h4>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <section class="ftco-section">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="elegant-calencar d-md-flex">
                                        <div class="wrap-header d-flex align-items-center img" id="calendarBg">
                                            <p id="reset"><spring:message code="calendar.today"/></p>
                                            <div id="header" class="p-0">
                                                <div class="head-info">
                                                    <div class="head-month"></div>
                                                    <div class="head-day"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="calendar-wrap">
                                            <div class="w-100 button-wrap">
                                                <div class="pre-button d-flex align-items-center justify-content-center">
                                                    <i class="fa fa-chevron-left"></i></div>
                                                <div class="next-button d-flex align-items-center justify-content-center">
                                                    <i class="fa fa-chevron-right"></i></div>
                                            </div>
                                            <table id="calendar">
                                                <thead>
                                                <tr>
                                                    <th><spring:message code="calendar.Sun"/></th>
                                                    <th><spring:message code="calendar.Mon"/></th>
                                                    <th><spring:message code="calendar.Tue"/></th>
                                                    <th><spring:message code="calendar.Wed"/></th>
                                                    <th><spring:message code="calendar.Thu"/></th>
                                                    <th><spring:message code="calendar.Fri"/></th>
                                                    <th><spring:message code="calendar.Sat"/></th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div> <!-- end card-body -->
            </div> <!-- end card -->
        </div> <!-- end col -->
    </div> <!-- end row -->


    <link href="<c:url value='/assets/vendors/colorlib/calendar-07/css/style-dark.css' />" rel="stylesheet"
          type="text/css" id="stylesheet-calendar"/>
    <link href="<c:url value='/assets/css/bootstrap-dark.min.css' />" rel="stylesheet" type="text/css"
          id="bootstrap-stylesheet-calendar"/>
    <link href="<c:url value='/assets/css/app-dark.min.css' />" rel="stylesheet" type="text/css"
          id="app-stylesheet-calendar"/>
    <script src="<c:url value="/assets/vendors/colorlib/calendar-07/js/popper.js" />"></script>
    <script src="<c:url value="/assets/vendors/colorlib/calendar-07/js/main.js" />"></script>

</div>

<script>
    $(document).ready(function () {
        const LOCAL_STORAGE_KEY = "toggle-bootstrap-theme";
        if (localStorage.getItem(LOCAL_STORAGE_KEY) !== null) {
            const LOCAL_META_DATA = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY));
            let isDark = LOCAL_META_DATA && LOCAL_META_DATA.isDark;
            let stylesheet = document.getElementById('stylesheet-calendar');
            let bootstrap = document.getElementById('bootstrap-stylesheet-calendar');
            let app = document.getElementById('app-stylesheet-calendar');
            if (isDark) {
                stylesheet.setAttribute("href", "<c:url value='/assets/vendors/colorlib/calendar-07/css/style-dark.css' />");
                bootstrap.setAttribute("href", "<c:url value='/assets/css/bootstrap-dark.min.css' />");
                app.setAttribute("href", "<c:url value='/assets/css/app-dark.min.css' />");
            } else {
                stylesheet.setAttribute("href", "<c:url value='/assets/vendors/colorlib/calendar-07/css/style.css' />");
                bootstrap.setAttribute("href", "<c:url value='/assets/css/bootstrap.min.css' />");
                app.setAttribute("href", "<c:url value='/assets/css/app.min.css' />");
            }
        } else {
            let stylesheet = document.getElementById('stylesheet-calendar');
            let bootstrap = document.getElementById('bootstrap-stylesheet-calendar');
            let app = document.getElementById('app-stylesheet-calendar');
            stylesheet.setAttribute("href", "<c:url value='/assets/vendors/colorlib/calendar-07/css/style.css' />");
            bootstrap.setAttribute("href", "<c:url value='/assets/css/bootstrap.min.css' />");
            app.setAttribute("href", "<c:url value='/assets/css/app.min.css' />");
        }
        bgGenerator();

        /// Warning over access list
        $('#myModal').modal('show');

    });

    function bgGenerator() {
        let bg = document.getElementById('calendarBg');
        let min = 0
        let max = 1;
        let rand = Math.floor(Math.random() * (max - min + 1)) + min;
        if (rand === 0) {
            bg.setAttribute("style", "background-image: url(https://source.unsplash.com/random/?nature); background-repeat: no-repeat; background-size: cover;");
        } else {
            bg.setAttribute("style", "background-image: url(https://source.unsplash.com/random/?animals); background-repeat: no-repeat; background-size: cover;");
        }
    }
</script>
<security:authorize access="hasAnyRole('ADMIN','FAD_STATISLOCKUSER','FAD_STATISTICUSER','FAD_STATISTICSHOP')">
    <%@include file="warning_over_access_user.jsp" %>
    <%@include file="change_password.jsp" %>
</security:authorize>
</body>
</html>
