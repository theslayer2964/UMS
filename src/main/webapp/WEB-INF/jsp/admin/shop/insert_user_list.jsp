<%@include file="/common/taglibs.jsp" %>
<div id="demo1" class="collapse">
    <form>
        <div class="form-group">
            <input type="hidden" class="form-control" id="idDemo01"/>
        </div>
        <div class="form-group">
            <label for="userDemo01"><spring:message code="label.c2user.username"/></label>
            <input type="text" class="form-control" id="userDemo01"/>
        </div>
        <div class="form-group">
            <label for="emailDemo01"><spring:message code="label.user.email"/></label>
            <input type="email" class="form-control" id="emailDemo01"/>
        </div>
        <div class="form-group">
            <label for="positionDemo01"><spring:message code="label.department.position"/></label>
            <input type="text" class="form-control" id="positionDemo01"/>
        </div>
        <button type="button" class="btn btn-success waves-effect" id="submitDemo01" onclick="addUser()"><spring:message code="button.add"/> </button>
        <button type="reset" class="btn btn-secondary waves-effect"  ><spring:message code="button.close"/> </button>
    </form>
</div>

