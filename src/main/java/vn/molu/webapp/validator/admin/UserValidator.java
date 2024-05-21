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

import vn.molu.common.utils.CommonUtil;
import vn.molu.domain.admin.User;
import vn.molu.service.admin.UserService;
import vn.molu.webapp.command.admin.UserCommand;

@Component
public class UserValidator extends ApplicationObjectSupport implements Validator {

	private transient final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return UserCommand.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		UserCommand command = (UserCommand) o;

		checkRequiredFields(command, errors);
		validateEmail(command, errors);
		checkUniqueCode(command, errors);
		validateDisplayname(command, errors);
	}

	private void validateDisplayname(UserCommand command, Errors errors) {
	}

	private void checkRequiredFields(UserCommand command, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.userName", "errors.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.password", "errors.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.displayName", "errors.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.email", "errors.required");
	}

	private void checkUniqueCode(UserCommand command, Errors errors) {
		// check username
		if (StringUtils.isNotBlank(command.getPojo().getUserName())) {
			try {
				User users = userService.loadUserByUsername(command.getPojo().getUserName());
				if (command.getPojo().getUserId() != null) {
					if (!users.getUserId().equals(command.getPojo().getUserId())) {
						errors.rejectValue("pojo.userName", "error.user.duplicated");
					}
				} else {
					if (users != null) {
						errors.rejectValue("pojo.userName", "error.user.duplicated");
					}
				}
			} catch (RuntimeException oe) {
				log.error(oe.getMessage(), oe);
			}
		}

		if (StringUtils.isNotBlank(command.getPojo().getEmail())) {
			try {
				User user = userService.findByEmail(command.getPojo().getEmail());
				if (command.getPojo().getUserId() != null) { // update case
					if (!command.getPojo().getUserId().equals(user.getUserId())) {
						errors.rejectValue("pojo.email", "error.email.duplicated");
					}
				} else { // insert case
					if (user != null) {
						errors.rejectValue("pojo.email", "error.email.duplicated");
					}
				}
			} catch (RuntimeException oe) {
				log.error(oe.getMessage(), oe);
			}
		}
	}

	private void validateEmail(UserCommand command, Errors errors) {
		if (StringUtils.isNotBlank(command.getPojo().getEmail())) {
			if (!CommonUtil.isValidEmail(command.getPojo().getEmail())) {
				errors.rejectValue("pojo.email", "error.invalid_email");
			}
		}
	}
}
