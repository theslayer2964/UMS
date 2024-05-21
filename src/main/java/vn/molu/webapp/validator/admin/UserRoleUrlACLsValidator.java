package vn.molu.webapp.validator.admin;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.molu.webapp.command.admin.UserRoleUrlACLCommand;

@Component
public class UserRoleUrlACLsValidator extends ApplicationObjectSupport implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRoleUrlACLCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRoleUrlACLCommand command = (UserRoleUrlACLCommand) o;

        checkRequiredFields(command, errors);
    }

    private void checkRequiredFields(UserRoleUrlACLCommand command, Errors errors) {
        String checkList[] = command.getCheckList();
        if (checkList == null || checkList.length == 0) {
            errors.rejectValue(null, "errors.select.empty");
        }
    }
}
