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
import vn.molu.domain.admin.UserGroup;
import vn.molu.service.admin.UserGroupService;
import vn.molu.webapp.command.admin.UserGroupCommand;


@Component
public class UserGroupValidator extends ApplicationObjectSupport implements Validator {
    private transient final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private UserGroupService userGroupService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserGroupCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserGroupCommand command = (UserGroupCommand) o;

        checkRequiredFields(command, errors);
        checkUniqueCode(command, errors);
    }

    private void checkRequiredFields(UserGroupCommand command, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.code", "errors.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.name", "errors.required");
    }

    private void checkUniqueCode(UserGroupCommand command, Errors errors) {
        if (StringUtils.isNotBlank(command.getPojo().getCode())) {
            try {
                UserGroup userGroup = userGroupService.findByCode(command.getPojo().getCode());
                if (command.getPojo().getUserGroupId() != null) {
                    if (!userGroup.getUserGroupId().equals(command.getPojo().getUserGroupId())) {
                        errors.rejectValue("pojo.code", "errors.code.duplicated");
                    }
                } else {
                    if (userGroup != null) {
                        errors.rejectValue("pojo.code", "errors.code.duplicated");
                    }
                }
            } catch (RuntimeException oe) {
                log.error(oe.getMessage(), oe);
            }
        }
    }
}
