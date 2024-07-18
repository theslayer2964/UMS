package vn.molu.service.email.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import vn.molu.common.Constants;
import vn.molu.dao.temp.ShopDAO;
import vn.molu.domain.admin.ExplanationForm;
import vn.molu.domain.admin.ManagerUser;
import vn.molu.dto.admin.admin.C2UserAdminDTO;
import vn.molu.dto.admin.admin.ShopDTO;
import vn.molu.service.admin.C2UserAdminService;
import vn.molu.service.admin.ManagerUserService;
import vn.molu.service.email.EmailSenderService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SendMailServiceImpl implements EmailSenderService {
    @Autowired
    private C2UserAdminService c2UserAdminService;
    @Autowired
    private ManagerUserService managerUserService;
    @Autowired
    private ShopDAO shopDAO;
    @Autowired
    private Environment environment;

    @Value("${spring.mail.username}")
    private String SENDMAIL_USERNAME;
    @Value("${spring.mail.password}")
    private String SENDMAIL_PASSWORD;
    @Value("${spring.mail.host}")
    private String MAIL_HOST;
    @Value("${spring.mail.port}")
    private String MAIL_PORT;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String MAIL_SMTP_AUTO;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String MAIL_STARTTLS;

    @Override
    public void sendMailToManager_LockUserOverAccess() {
        // data:
        List<C2UserAdminDTO> listUser = c2UserAdminService.layDS_ViPham_TanSuat_TraCuu_10Ngay_DeGuiMail();

        Map<String, List<C2UserAdminDTO>> groupUsersPerShop = new HashMap<>(); // CHECK 1 shop -> 2 user
        for (C2UserAdminDTO dto : listUser) {
            String shop_code = dto.getShopCode();
            if (groupUsersPerShop.containsKey(shop_code)) {
                groupUsersPerShop.get(shop_code).add(dto);
            } else {
                groupUsersPerShop.put(shop_code, new ArrayList<>(Arrays.asList(dto)));
            }
        }

        for (List<C2UserAdminDTO> group : groupUsersPerShop.values()) { // send
            // receiver
            List<ManagerUser> managerUserList = managerUserService.findByShopCode(group.get(0).getShopCode());
            if (!managerUserList.isEmpty()) {
                managerUserList.forEach(s -> {
                    Message message = new MimeMessage(createSession(SENDMAIL_USERNAME, SENDMAIL_PASSWORD));
                    try {
                        message.setFrom(new InternetAddress(SENDMAIL_USERNAME));
                        message.setSubject(MimeUtility.encodeText(createSubjectDefault(), "utf-8", "B"));
                        message.setContent(createContentDefault(group), "text/html; charset=utf-8");
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(s.getEmail()));
                        Transport.send(message);
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    @Override
    public void warnAdministratorWhenUserSubmitForm(ExplanationForm explanationForm, List<ManagerUser> managerUserList, String content) {
        C2UserAdminDTO c2UserAdminDTO = c2UserAdminService.findbyUsername(explanationForm.getUser_name());
        if (managerUserList.size() > 0) {
            managerUserList.forEach(manager -> {
                Message message = new MimeMessage(createSession(SENDMAIL_USERNAME, SENDMAIL_PASSWORD));
                try {
                    message.setFrom(new InternetAddress(SENDMAIL_USERNAME));
                    message.setSubject(MimeUtility.encodeText(createSubjectSubmitForm(), "utf-8", "B"));
                    message.setContent(createContentSubmitForm(explanationForm, c2UserAdminDTO.getShopCode()), "text/html; charset=utf-8");
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(manager.getEmail()));
                    Transport.send(message);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Override
    public void sendMailToTTCNS_General() {
        // data:
        List<C2UserAdminDTO> listUser = c2UserAdminService.layDS_ViPham_TanSuat_TraCuu_10Ngay_DeGuiMail();
        // receiver
        List<ManagerUser> managerUserList = managerUserService.findByShopCode(Constants.CT2);
        if(!managerUserList.isEmpty()){
            managerUserList.forEach(manager -> {
                Message message = new MimeMessage(createSession(SENDMAIL_USERNAME, SENDMAIL_PASSWORD));
                try {
                    message.setFrom(new InternetAddress(SENDMAIL_USERNAME));
                    message.setSubject(MimeUtility.encodeText(createSubjectDefault(), "utf-8", "B"));
                    message.setContent(listUser.isEmpty() ? createContentDefault_General_NoUser() : createContentDefault_General(listUser), "text/html; charset=utf-8");
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(manager.getEmail()));
                    Transport.send(message);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private String createContentDefault_General_NoUser() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String rs = "    <p>Chào anh/chị,</p>\n" +
                "\n" +
                "<p>\n" +
                "      Báo cáo danh sách tài khoản bị khóa của tất cả các đơn vị.</p>" +
                "    <p>Lý do: vi phạm tần suất tra cứu.</p>\n" +
                "\n" +
                "    <p>Từ ngày " + formatter.format(today.minusDays(10)) + " đến ngày "
                        + formatter.format(today) + " không có nguời dùng vi phạm.</p>\n" +
                "\n" +
                "    <p>Trân trọng!</p>\n" +
                "    <p>Phòng Công Nghệ - Kỹ Thuật</p>\n" +
                "    <p>Trung tâm kinh doanh công nghệ số - Cty DV MobiFone KV 2</p>";
        return rs;
    }

    private String createContentDefault_General(List<C2UserAdminDTO> group) {
        String listUser = "";
        for (C2UserAdminDTO dto : group) {
            listUser += "      <tr>\n" +
                    "        <td style=\"border: 1px solid black;\">" + dto.getShopCode() + "</td>\n" +
                    "        <td style=\"border: 1px solid black;\">" + dto.getUsername() + "</td>\n" +
                    "        <td style=\"border: 1px solid black;\">" + dto.getFullName() + "</td>\n" +
                    "        <td style=\"border: 1px solid black;\">" + dto.getDescription() + "</td>\n" +
                    "      </tr>\n";
        }
        String rs = "    <p>Chào anh/chị,</p>\n" +
                "\n" +
                "<p>\n" +
                "      Báo cáo danh sách tài khoản bị khóa của tất cả các đơn vị.</p>" +
                "    <p>Lý do: vi phạm tần suất tra cứu.</p>\n" +
                "\n" +
                "    <table style=\"width: 100%;border: 1px solid black;border-collapse: collapse;\">\n" +
                "      <tr>\n" +
                "        <th style=\"border: 1px solid black;\"><b>Shopcode CH</b></th>\n" +
                "        <th style=\"border: 1px solid black;\"></b>Tài khoản</b></th>\n" +
                "        <th style=\"border: 1px solid black;\"></b>Tên tài khoản</b></th>\n" +
                "        <th style=\"border: 1px solid black;\"></b>Ngày khóa</b></th>\n" +
                "      </tr>\n" + listUser +
                "    </table>" +
                "\n" +
                "    <p>Trân trọng!</p>\n" +
                "    <p>Phòng Công Nghệ - Kỹ Thuật</p>\n" +
                "    <p>Trung tâm kinh doanh công nghệ số - Cty DV MobiFone KV 2</p>";
        return rs;
    }

    private String createContentDefault(List<C2UserAdminDTO> group) {
        ShopDTO shopDTO = shopDAO.findById(group.get(0).getShopCode());
        String listUser = "";
        for (C2UserAdminDTO dto : group) {
            listUser += "      <tr>\n" +
                    "        <td style=\"border: 1px solid black;\">" + dto.getShopCode() + "</td>\n" +
                    "        <td style=\"border: 1px solid black;\">" + dto.getUsername() + "</td>\n" +
                    "        <td style=\"border: 1px solid black;\">" + dto.getFullName() + "</td>\n" +
                    "        <td style=\"border: 1px solid black;\">" + dto.getDescription() + "</td>\n" +
                    "      </tr>\n";
        }
        String rs = "    <p>Chào anh/chị,</p>\n" +
                "\n" +
                "<p>\n" +
                "      Trung tâm xin thông báo danh sách tài khoản bị khóa của đơn vị <b>" + shopDTO.getName() + "</b>\n" +
                "    </p>" +
                "    <p>Lý do: vi phạm tần suất tra cứu.</p>\n" +
                "\n" +
                "    <table style=\"width: 100%;border: 1px solid black;border-collapse: collapse;\">\n" +
                "      <tr>\n" +
                "        <th style=\"border: 1px solid black;\"><b>Shopcode CH</b></th>\n" +
                "        <th style=\"border: 1px solid black;\"></b>Tài khoản</b></th>\n" +
                "        <th style=\"border: 1px solid black;\"></b>Tên tài khoản</b></th>\n" +
                "        <th style=\"border: 1px solid black;\"></b>Ngày khóa</b></th>\n" +
                "      </tr>\n" + listUser +
                "    </table>" +
                "    <p>\n" +
                "      Để có thể mở khóa, anh/chị vui lòng nộp file giải trình lý do vi phạm đến\n" +
                "      cho chúng tôi. Sau khi nhận và xác nhận thông tin hợp lệ, tài khoản của\n" +
                "      anh/ chị sẽ được mở khóa sau 10 ngày kể từ ngày nộp file giải trình.\n" +
                "    </p>\n" +
                "\n" +
                "    <p>Trân trọng!</p>\n" +
                "    <p>Phòng Công Nghệ - Kỹ Thuật</p>\n" +
                "    <p>Trung tâm kinh doanh công nghệ số - Cty DV MobiFone KV 2</p>";
        return rs;
    }

    private Session createSession(String username, String password) {
        Properties props = new Properties();
        props.put(Constants.MAIL_AUTH, MAIL_SMTP_AUTO);
        props.put(Constants.MAIL_STARTTLS, MAIL_STARTTLS);
        props.put(Constants.MAIL_HOST, MAIL_HOST);
        props.put(Constants.MAIL_PORT, MAIL_PORT);
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        return session;
    }

    public String createSubjectDefault() {
        return environment.getProperty("menu.system.mail");
    }

    public String createSubjectSubmitForm() {
        return environment.getProperty("menu.system.explanationform.title");
    }

    public String createContentSubmitForm(ExplanationForm violator, String shopcode) {
        String formatterDate = new SimpleDateFormat("dd/MM/yyyy").format(violator.getUpdate_date());
        String rs = "<p>Chào quản trị viên,</p>\n" +
                "\n" +
                "<p>Hiện tại có tài khoản: " + violator.getUser_name() + " thuộc đơn vị: "
                + shopcode + ", đã nộp biên bản giải trình vào ngày "
                + formatterDate + " trên hệ thống UMS. Anh/chị vui lòng phê duyệt biên bản đã nộp.</p>\n" +
                "<p>Sau khi được phê duyệt, tài khoản bị khóa sẽ được mở khóa sau 10 ngày kể từ ngày nộp biên bản giải trình.</p>\n" +
                "    <p>Trân trọng!</p>\n" +
                "    <p>Phòng Công Nghệ - Kỹ Thuật</p>\n" +
                "    <p>Trung tâm kinh doanh công nghệ số - Cty DV MobiFone KV 2</p>";
        return rs;
    }
}
