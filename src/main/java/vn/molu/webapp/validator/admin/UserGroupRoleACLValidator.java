package vn.molu.webapp.validator.admin;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.molu.webapp.command.admin.UserGroupRoleACLCommand;

@Component
public class UserGroupRoleACLValidator extends ApplicationObjectSupport implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserGroupRoleACLCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserGroupRoleACLCommand command = (UserGroupRoleACLCommand) o;

        checkRequiredFields(command, errors);
    }

    private void checkRequiredFields(UserGroupRoleACLCommand command, Errors errors) {
        String checkList[] = command.getCheckList();
        if (checkList == null || checkList.length == 0) {
            errors.rejectValue(null, "errors.select.empty");
        }
    }
}
