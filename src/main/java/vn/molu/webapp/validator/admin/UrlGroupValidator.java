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

import vn.molu.domain.admin.UrlGroup;
import vn.molu.service.admin.UrlGroupService;
import vn.molu.webapp.command.admin.UrlGroupCommand;

@Component
public class UrlGroupValidator extends ApplicationObjectSupport implements Validator{

    private transient final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private UrlGroupService urlGroupService;
    
    @Override
    public boolean supports(Class<?> aClass) {
		return UrlGroupCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
		UrlGroupCommand command = (UrlGroupCommand) o;

		checkRequiredFields(command, errors);
		checkUniqueCode(command, errors);
    }

    private void checkRequiredFields(UrlGroupCommand command, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.code", "errors.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.name", "errors.required");
    }

    private void checkUniqueCode(UrlGroupCommand command, Errors errors) {

		// check username
		if (StringUtils.isNotBlank(command.getPojo().getCode())) {
			try {
				UrlGroup urlGroup = urlGroupService.findByCode(command.getPojo().getCode());
				if (command.getPojo().getUrlGroupId() != null) {
					if (!urlGroup.getUrlGroupId().equals(command.getPojo().getUrlGroupId())) {
					errors.rejectValue("pojo.code", "errors.code.duplicated");
					}
				} else {
					if (urlGroup != null) {
					errors.rejectValue("pojo.code", "errors.code.duplicated");
					}
				}
			} catch (RuntimeException oe) {
			log.error(oe.getMessage(), oe);
			}
		}
    }
}
