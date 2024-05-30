<%--
  Created by IntelliJ IDEA.
  User: phaolo
  Date: 2020-03-04
  Time: 10:04
  To change this template use File | Settings | File Templates.
--%>
<ul class="navigation-menu">

    <li class="has-submenu">
        <a href="/">
            <i class="fas fa-home"></i><spring:message code="menu.home"/> </a>
    </li>

    <%--Quan tri he thong--%>
    <security:authorize access="hasAnyRole('ADMIN')">
        <li class="has-submenu">
            <a href="#">
                <i class="fas fa-cog"></i><spring:message code="menu.system"/>
                <div class="arrow-down"></div>
            </a>
            <ul class="submenu">
                <security:authorize access="hasAnyRole('ADMIN')">
                    <li><a href="<c:url value='/admin/usergroup-list.html'/>"><fmt:message
                            key="menu.system.user_group"/> </a></li>
                </security:authorize>
                <security:authorize access="hasAnyRole('ADMIN')">  <%--xx--%>
                    <li><a href="<c:url value='/admin/user-list.html'/>"><fmt:message key="menu.system.user"/></a></li>
                </security:authorize>
                <security:authorize access="hasAnyRole('ADMIN')">
                    <li><a href="<c:url value='/admin/userrole-list.html'/>"><fmt:message
                            key="menu.system.user_role"/></a></li>
                </security:authorize>
                <security:authorize access="hasAnyRole('ADMIN')">
                    <li><a href="<c:url value='/admin/urlgroup-list.html'/>"><fmt:message
                            key="menu.system.url_group"/></a></li>
                </security:authorize>
                <security:authorize access="hasAnyRole('ADMIN')">
                    <li><a href="<c:url value='/admin/url-list.html'/>"><fmt:message key="menu.system.url"/></a></li>
                </security:authorize>
            </ul>
        </li>
    </security:authorize>
    <security:authorize
            access="hasAnyRole('ADMIN','FAD_C2_ADMIN_USER','FAD_AUTO_USER','FAD_ADD_USER_AUTO','FAD_ADD_USER_AUTO_FILE','FAD_MANAGER_USER_FILE','FAD_DEPARTMENT','FAD_PROGRAM')">
        <li class="has-submenu" id="danhMuc">
            <a href="#">
                <i class="fas fa-list"></i><spring:message code="menu.list"/>
                <div class="arrow-down"></div>
            </a>
            <ul class="submenu">
                <security:authorize access="hasAnyRole('ADMIN','FAD_C2_ADMIN_USER')">
                    <li>
                        <a href="<c:url value='/list/user_c2_ss.html'/>"><fmt:message
                                key="menu.system.useragency"/></a>
                    </li>
                </security:authorize>
                <security:authorize access="hasAnyRole('ADMIN','FAD_AUTO_USER')">
                    <li>
                        <a href="<c:url value='/list/user_c2_auto.html'/>"><fmt:message
                                key="menu.system.c2_user_auto"/></a>
                    </li>
                </security:authorize>
                <security:authorize access="hasAnyRole('ADMIN','FAD_ADD_USER_AUTO')">
                    <li><a href="<c:url value='/list/add_user_c2.html'/>"><fmt:message key="menu.system.addUser"/></a>
                    </li>
                </security:authorize>
                <security:authorize access="hasAnyRole('ADMIN','FAD_ADD_USER_AUTO_FILE')">
                    <li><a href="<c:url value='/list/add_user_c2_file.html'/>"><fmt:message
                            key="menu.system.addUserFile"/></a>
                    </li>
                </security:authorize>
                <security:authorize access="hasAnyRole('ADMIN','FAD_MANAGER_USER_FILE')">
                    <li><a href="<c:url value='/list/manager_user_c2_file.html'/>"><fmt:message
                            key="menu.system.inactivityUser"/></a>
                    </li>
                </security:authorize>
                <security:authorize access="hasAnyRole('ADMIN','FAD_DEPARTMENT')">
                    <li><a href="<c:url value='/list/department.html'/>"><fmt:message
                            key="menu.system.department"/></a>
                    </li>
                </security:authorize>
                <security:authorize access="hasAnyRole('ADMIN','FAD_PROGRAM')">
                    <li><a href="<c:url value='/list/program.html'/>"><fmt:message key="menu.system.program"/></a>
                    </li>
                </security:authorize>
            </ul>
        </li>
    </security:authorize>
    <security:authorize access="hasAnyRole('ADMIN','FAD_STATISLOCKUSER','FAD_STATISTICUSER','FAD_STATISTICSHOP')">
        <li class="has-submenu">
            <a href="#">
                <i class="fa fa-puzzle-piece"></i><spring:message code="menu.utilities"/>
                <div class="arrow-down"></div>
            </a>
            <ul class="submenu">
                <security:authorize access="hasAnyRole('ADMIN','FAD_STATISLOCKUSER')">
                    <li><a href="<c:url value='/utilities/user_c2_statistical.html'/>"><fmt:message
                            key="menu.system.statisticalUser"/></a></li>
                </security:authorize>
                <security:authorize access="hasAnyRole('ADMIN','FAD_STATISTICUSER')">
                    <li><a href="<c:url value='/utilities/user_c2_log.html'/>"><fmt:message
                            key="menu.system.statisticalUserByLog"/></a></li>
                </security:authorize>
                <security:authorize access="hasAnyRole('ADMIN','FAD_STATISTICSHOP')">
                    <li><a href="<c:url value='/utilities/user_c2_log_by_shop.html'/>"><fmt:message
                            key="menu.system.statisticalShopByLog"/></a></li>
                </security:authorize>
            </ul>
        </li>
    </security:authorize>
    <security:authorize access="hasAnyRole('ADMIN','FAD_EXPLANATIONFORM','FAD_GUESTINFO','FAD_GUESTREQUEST')">
        <li class="has-submenu" id="guest">
            <a href="#">
                <i class="fas fa-atom"></i><spring:message code="menu.guest"/>
                <div class="arrow-down"></div>
            </a>
            <ul class="submenu">
                <security:authorize access="hasAnyRole('ADMIN','FAD_EXPLANATIONFORM')">
                    <li><a href="<c:url value='/guest/explanation-form.html'/>"><fmt:message
                            key="menu.guest.explannation-form"/></a></li>
                </security:authorize>
                <security:authorize access="hasAnyRole('ADMIN','FAD_GUESTREQUEST')">
                    <li><a href="<c:url value='/guest/request-user.html'/>"><fmt:message
                            key="menu.guest.request"/></a></li>
                </security:authorize>
                <security:authorize access="hasAnyRole('ADMIN','FAD_GUESTINFO')">
                    <li><a href="<c:url value='/guest/info.html'/>"><fmt:message
                            key="menu.guest.info"/></a></li>
                </security:authorize>
            </ul>
        </li>
    </security:authorize>
</ul>