package vn.molu.webapp.validator.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import vn.molu.domain.admin.UserRole;
import vn.molu.service.admin.UserRoleService;
import vn.molu.webapp.command.admin.UserRoleCommand;

@Component
public class UserRoleValidator extends ApplicationObjectSupport implements Validator {
    private transient final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRoleCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRoleCommand command = (UserRoleCommand) o;

        checkRequiredFields(command, errors);
        checkUniqueCode(command, errors);
    }

    private void checkRequiredFields(UserRoleCommand command, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.code", "errors.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.name", "errors.required");
    }

    private void checkUniqueCode(UserRoleCommand command, Errors errors) {
        if (StringUtils.isNotBlank(command.getPojo().getCode())) {
            try {
                UserRole userRole = userRoleService.findByCode(command.getPojo().getCode());
                if (command.getPojo().getUserRoleId() != null) {
                    if (!userRole.getUserRoleId().equals(command.getPojo().getUserRoleId())) {
                        errors.rejectValue("pojo.code", "errors.code.duplicated");
                    }
                } else {
                    if (userRole != null) {
                        errors.rejectValue("pojo.code", "errors.code.duplicated");
                    }
                }
            } catch (RuntimeException oe) {
                log.error(oe.getMessage(), oe);
            }
        }
    }
}
