package vn.molu.webapp.validator.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vn.molu.webapp.command.admin.C2UserLockedCommand;
@Component
public class C2StatisticalValidator extends ApplicationObjectSupport implements Validator {

    private transient final Log log = LogFactory.getLog(this.getClass());

    @Override
    public boolean supports(Class<?> clazz) {
        return C2UserLockedCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        C2UserLockedCommand command = (C2UserLockedCommand) target;

    }
}
