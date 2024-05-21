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
import vn.molu.domain.admin.Url;
import vn.molu.service.admin.UrlService;
import vn.molu.webapp.command.admin.UrlCommand;


@Component
public class UrlValidator extends ApplicationObjectSupport implements Validator {

    private transient final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private UrlService urlService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UrlCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UrlCommand command = (UrlCommand) o;
        checkRequiredFields(command, errors);
        checkUniqueCode(command, errors);
    }

    private void checkRequiredFields(UrlCommand command, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.code", "errors.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.name", "errors.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.path", "errors.required");
    }

    private void checkUniqueCode(UrlCommand command, Errors errors) {
        if (StringUtils.isNotBlank(command.getPojo().getCode())) {
            try {
                Url url = urlService.findByCode(command.getPojo().getCode());
                if (command.getPojo().getUrlId() != null) {
                    if (!url.getUrlId().equals(command.getPojo().getUrlId())) {
                        errors.rejectValue("pojo.code", "errors.code.duplicated");
                    }
                } else {
                    if (url != null) {
                        errors.rejectValue("pojo.code", "errors.code.duplicated");
                    }
                }
            } catch (RuntimeException oe) {
                log.error(oe.getMessage(), oe);
            }
        }

        if (StringUtils.isNotBlank(command.getPojo().getPath())) {
            try {
                Url url = urlService.findByPath(command.getPojo().getPath());
                if (command.getPojo().getUrlId() != null) {
                    if (!url.getUrlId().equals(command.getPojo().getUrlId())) {
                        errors.rejectValue("pojo.path", "errors.path.duplicated");
                    }
                } else {
                    if (url != null) {
                        errors.rejectValue("pojo.path", "errors.path.duplicated");
                    }
                }
            } catch (RuntimeException oe) {
                log.error(oe.getMessage(), oe);
            }
        }
    }
}
