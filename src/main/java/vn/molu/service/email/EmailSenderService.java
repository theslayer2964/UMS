package vn.molu.service.email;

import vn.molu.domain.admin.ExplanationForm;
import vn.molu.domain.admin.ManagerUser;

import java.util.List;

public interface EmailSenderService {
    void sendMailToManager_LockUserOverAccess();
    void warnAdministratorWhenUserSubmitForm(ExplanationForm explanationForm, List<ManagerUser> managerUserList, String message);
    void sendMailToTTCNS_General();
}
