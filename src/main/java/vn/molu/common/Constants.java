
package vn.molu.common;

public class Constants {
    
    /*
     * page action
     */
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_INACTIVITY = "inactivity";
    public static final String ACTION_UNLOCK = "unlock";
    public static final String ACTION_REGISTER = "register";
    public static final String ACTION_VIEWLOG = "viewlog";
    public static final Integer STATUS_DELETE_USER = 0;
    public static final Integer STATUS_USING = 1;
    public static final Integer STATUS_TEMPORARY_LOG = 2;
    public static final Integer STATUS_LOG = 4;

    public static final String ACTION_DELETE_ALL = "deleteAll";
    public static final String ACTION_DISABLE = "disable";
    public static final String ACTION_EXPORT = "export";
    public static final String ACTION_FIND = "find";
    public static final String ACTION_SEARCH = "search";
    public static final String ACTION_INSERT_OR_UPDATE = "insert-update";
    public static final String ACTION_CHANGE_SHOP = "change-shop";
    public static final String ACTION_CHANGE_IP_MSALE= "change-mSale";
    public static final String ACTION_INSERT_OR_UPDATE_ALL = "insert-update-all";
    public static final String ACTION_INSERT_PERCEN_ALL = "insert-percen-all";
    public static final String ACTION_INSERT_OR_UPDATE_PROFILE = "insert-update-profile";
    
    public static final String ACTION_AUDIT = "audit";
    public static final String ACTION_PREVIEW = "preview";
    public static final String ACTION_PRINT = "print";
    public static final String ACTION_IMPORT = "import";
    public static final String ACTION_UPLOAD = "upload";

    /*
     * Message model key
     */
    public static final String IS_MESSAGE_MODEL_KEY = "isMessage";
    public static final String MESSAGE_RESPONSE_MODEL_KEY = "messageResponse";
    public static final String MESSAGE_TYPE_MODEL_KEY = "messageType";
    public static final String MESSAGE_TYPE_MODEL_KEY_DIFFICULT = "isMessageDifficult";
    public static final String MESSAGE_TYPE_MODEL_KEY_CONTENT = "messageContent";

    public static final String MESSAGE_TYPE_MODEL_KEY_INCORRECT_MAIL = "isMessageIncorrectMail";
    public static final String MESSAGE_RESPONSE_MODEL_KEY_INCORRECT = "incorrectMail_messageResponse";
    public static final String IS_WARNING = "isWarning"; 
    public static final String ALERT_TYPE = "alertType";
    public static final String MESSAGE_VALIDATOR_MODEL_KEY = "messageValidator";
    /*
     * Modal dialog initizaliation
     */
    public static final String IS_MODAL_SHOW = "isModal";
    
    /*
     * Message model value
     */
    public static final String MESSAGE_TYPE_SUCCESS = "success";
    public static final String MESSAGE_TYPE_ERROR = "error";
    public static final String MESSAGE_TYPE_INFO = "info";
    public static final String MESSAGE_TYPE_WARN = "warning";
    
    /* *
     * Bcc recipients delimiter
     */
    public static final String DELIMITER_COMMA = ",";
    public static final String EMAIL_DELIMITER = ";";
    public static final String SEPERATE = ":";
    
    public static final String DASH = " - ";
    public static final String DELIMITER_SPACE = " ";
    
    /**
     * Sort direction constants
     */
    public static final String SORT_ASC = "2";
    public static final String SORT_DESC = "1";
    
    /**
     * Query
     */
    public static final String QUERY_EXISTS = " exists ";
    public static final String QUERY_NOT_EXISTS = " not exists ";

    public static final int MAXPAGEITEMS = 20;

    public static final int MAXHEAPITEMS = 350000;

    public static final int REPORT_MAXPAGEITEMS = 100;


    //~ Static fields/initializers =============================================

    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_FORMAT_2 = "dd-MM-yyyy";
    public static final String MONTH_FORMAT = "MM/yyyy";
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String VI_DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    public static final String DB_DATE_FORMAT = "YYYY-MM-DD";
    public static final String JAVA_DATE_FORMAT = "yyyy-MM-dd";
    
    /**
     * The Alphabet number for search query
     */
    public static final String ALPHABET_SEARCH_PREFIX = "^$^";
    
    /**
     * The name of the ResourceBundle used in this application
     */
    public static final String BUNDLE_KEY = "ApplicationResources";

    /**
     * File separator from System properties
     */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /**
     * User home from System properties
     */
    public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;

    /**
     * The name of the configuration hashmap stored in application scope.
     */
    public static final String CONFIG = "appConfig";

    /**
     * Session scope attribute that holds the locale set by the user. By setting this key
     * to the same one that Struts uses, we get synchronization in Struts w/o having
     * to do extra work or have two session-level variables.
     */
    public static final String PREFERRED_LOCALE_KEY = "org.apache.struts2.action.LOCALE";

    /**
     * The request scope attribute that holds the list form
     */
    public static final String LIST_MODEL_KEY = "items";

    /**
     * The request scope attribute that holds the form
     */
    public static final String FORM_MODEL_KEY = "item";

    /**
     * The name of the available roles list, common.a request-scoped attribute
     * when adding/editing common.a user.
     */
    public static final String AVAILABLE_ROLES = "availableRoles";

    /**
     * The name of the CSS Theme setting.
     */
    public static final String CSS_THEME = "csstheme";


    /**
     * Acegi security constants
     */

    public static final String ACEGI_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String ACEGI_SECURITY_FORM_PASSWORD_KEY = "password";
    public static final String ACEGI_SECURITY_LAST_USERNAME_KEY = "ACEGI_SECURITY_LAST_USERNAME";


    /**
     * Cookie for web and content security
     */
    public static final String LOGIN_USER_ID_COOKIE = "j_loggined_userid";
    public static final String LOGIN_CHECKSUM = "j_loginned_checksum";
    public static final String LOGIN_ROLE_COOKIE = "j_role";

    /**
     * Spring Credential delimiter.
     */
    public static final String SECURITY_CREDENTIAL_DELIMITER = "${SEC_CRED}";

    public static final String FLG_DELETE_ON = "1";
    public static final String FLG_DELETE_OFF = "0";

    public static final String GLOBAL_META_ROLE_PREFIX = "ROLE_";
    public static final String LOGON_ROLE = "LOGON";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String FAD_USER = "FAD_USER";
    public static final String FAD_USERGROUP = "FAD_USERGROUP";
    public static final String FAD_USERROLE = "FAD_USERROLE";
    public static final String FAD_URLGROUP = "FAD_URLGROUP";
    public static final String FAD_URL = "FAD_URL";
    public static final String FAD_USERROLEURLACL = "FAD_USERROLEURLACL";
    public static final String FAD_USERGROUPROLEACL = "FAD_USERGROUPROLEACL";
    public static final String FAD_USERROLEACL = "FAD_USERROLEACL";
    public static final String FAD_STATISTICLOCKUSER = "FAD_STATISLOCKUSER";
    public static final String FAD_STATISTICUSER = "FAD_STATISTICUSER";
    public static final String FAD_STATISTICSHOP = "FAD_STATISTICSHOP";
    public static final String FAD_C2_ADMIN_USER = "FAD_C2_ADMIN_USER";
    public static final String FAD_AUTO_USER = "FAD_AUTO_USER";
    public static final String FAD_ADD_USER_AUTO = "FAD_ADD_USER_AUTO";
    public static final String FAD_ADD_USER_AUTO_FILE = "FAD_ADD_USER_AUTO_FILE";
    public static final String FAD_MANAGER_USER_FILE = "FAD_MANAGER_USER_FILE";
    public static final String FAD_DEPARTMENT = "FAD_DEPARTMENT";
    public static final String FAD_PROGRAM = "FAD_PROGRAM";
    public static final String FAD_EXPLANATION_FORM = "FAD_EXPLANATIONFORM";
    public static final String FAD_GUEST_INFO = "FAD_GUESTINFO";
    public static final String FG_ADMIN = "FG_ADMIN";
    public static final String FG_UTILITY = "FG_UTILITY";
    public static final String FG_LIST = "FG_LIST";
    public static final String FG_GUEST = "FG_GUEST";
    //url
    public static final String FAD_USERGROUP_LINK ="/admin/usergroup-list.html";
    public static final String FAD_USER_LINK = "/admin/user-list.html";
    public static final String FAD_USERROLE_LINK ="/admin/userrole-list.html";
    public static final String FAD_URLGROUP_LINK = "/admin/urlgroup-list.html";
    public static final String FAD_URL_LINK = "/admin/url-list.html";
    public static final String FAD_USERROLEURLACL_LINK = "/admin/user_role_url_acl.html";
    public static final String FAD_USERGROUPROLEACL_LINK = "/admin/group_role_acl.html";
    public static final String FAD_USERROLEACL_LINK = "/admin/user_role_acl.html";
    public static final String FAD_STATISTIC_LOCK_USER_LINK = "/utilities/user_c2_statistical.html";
    public static final String FAD_STATISTIC_USER_LINK = "/utilities/user_c2_log.html";
    public static final String FAD_STATISTIC_SHOP_LINK = "/utilities/user_c2_log_by_shop.html";
    public static final String FAD_C2_ADMIN_USER_LINK = "/list/user_c2_ss.html";
    public static final String FAD_AUTO_USER_LINK = "/list/user_c2_auto.html";
    public static final String FAD_ADD_USER_LINK = "/list/add_user_c2.html";
    public static final String FAD_ADD_USER_FILE_LINK = "/list/add_user_c2_file.html";
    public static final String FAD_MANAGER_USER_FILE_LINK = "/list/manager_user_c2_file.html";
    public static final String FAD_DEPARTMENT_LINK = "/list/department.html";
    public static final String FAD_PROGRAM_LINK = "/list/program.html";
    public static final String FAD_EXPLANATION_FORM_LINK = "/guest/explanation-form.html";
    public static final String FAD_GUEST_INFO_LINK = "/guest/info.html";
    //    month
    public static final Integer JAN = 1;
    public static final Integer FEB = 2;
    public static final Integer MAR = 3;
    public static final Integer APR = 4;
    public static final Integer MAY = 5;
    public static final Integer JUN = 6;
    public static final Integer JUL = 7;
    public static final Integer AUG = 8;
    public static final Integer SEP = 9;
    public static final Integer OTC = 10;
    public static final Integer NOV = 11;
    public static final Integer DEC = 12;

    // Setting SMTP
    public static final String MAIL_HOST = "mail.smtp.host";
    public static final String MAIL_PORT = "mail.smtp.port";
    public static final String MAIL_AUTH = "mail.smtp.auth";
    public static final String MAIL_STARTTLS = "mail.smtp.starttls.enable";

    // Program
    public static final String PROGRAM_CSKH = "Chăm sóc khách hàng";
    public static final String PROGRAM_TTCP = "Thanh toán cước phí";
    public static final String PROGRAM_BHTT = "Bán hàng tập trung";
    public static final String PROGRAM_DTHGD = "Điện tử hóa giao dịch";
    public static final String PROGRAM_CSKK = "Cấp số khuyến khích";
    public static final String PROGRAM_RESNUM = "Kho số Resnum";

    // ProgramId
    public static final String PROGRAM_ID_CSKH = "3";
    public static final String PROGRAM_ID_TTCP = "2";
    public static final String PROGRAM_ID_BHTT = "6";
    public static final String PROGRAM_ID_DTHGD = "1";
    public static final String PROGRAM_ID_CSKK = "4";
    public static final String PROGRAM_ID_RESNUM = "5";

    public static final String CITY_DEFAULT = "Thành phố Hồ Chí Minh";
    public static final String DISTRICT_DEFAULT = "Quận 11";
    public static final String ACTION_ADD = "ADD";
    public static final String SAVE_MANY_USER_BY_FILE = "SAVE_USER_FILE";
    public static final String PASSWORD_DEFAULT = "tt2";
    public static final String RETIRE_SCHEDULER = "C8_NGHIVIEC";
    public static final String MOBIFONE_EMAIL = "@mobifone.vn";

    public static final String FILE_TYPE_PDF= "application/pdf";
    public static final String FILE_TYPE_IMG= "image/png";
    // CSKK:
    public static final Integer CSKK_Status_0= 0;
    public static final Integer CSKK_Status_Success = 1;
    public static final Integer CSKK_Status_Error = -1;
    // Explanation Form:
    public static final String EXPLANATION_FORM_UNLOCK= "0";
    public static final String EXPLANATION_FORM_ADD_USER= "1";
    public static final String EXPLANATION_FORM_LOCK_USER= "2";
    public static final Integer EXPLANATION_FORM_STATUS_SUBMIT = 0;
    public static final Integer EXPLANATION_FORM_LOCK_ACCEPTED = 1;
    public static final Integer EXPLANATION_FORM_LOCK_REJECTED = 2;
    public static final String EXPLANATION_FORM_NOTE_USER_SUBMIT = "99";

    // manager user:
    public static final String MANAGER_USER_POSITION_ADMIN = "Administrator";
    public static final String PROGRAM_SYSTEM_UMS = "UMS";

    // DTHGD - IP
    public static final String IP_DEFAULT = "1.1.1.1";
    // SHOP-CODE:
    public static final String CT2 = "CT2";
}
