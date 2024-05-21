package vn.molu.common.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vn.molu.common.Constants;

public class WebCommonUtil {

    public static String getBaseFolder() {
        return "/files/";
    }

    public static String getTempFolderName() {
        return "temp";
    }

    /*
      * get extension of file
      */
    public static String getExtension(String fileName) {
        return (fileName.indexOf(".") < fileName.length()) ? fileName
                .substring(fileName.lastIndexOf(".") + 1) : "";
    }

    /*
      * get fileName without extension
      */
    public static String getNameWithoutExtension(String fileName) {
        return (fileName.indexOf(".") > 0) ? fileName.substring(0, fileName
                .lastIndexOf(".")) : fileName;
    }

    private static final Pattern EMAIL_P = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    private static final Pattern ZIP_P = Pattern.compile("\\d{5}(-\\d{4})?");
    private static final Pattern USERNAME_P = Pattern.compile("^[A-Za-z0-9_-]{3,25}");


    public static boolean isValidEmail(String email) {
        Matcher m = EMAIL_P.matcher(email);
        return m.matches();
    }

    public static boolean isValidZip(String email) {
        Matcher m = ZIP_P.matcher(email);
        return m.matches();
    }

    public static boolean isValidUsername(String username) {
        Matcher m = USERNAME_P.matcher(username);
        return m.matches();
    }



    public static String getSessionWithoutSuffix(String jSession) {
        int index = jSession.indexOf(".");
        return index > 0 ? jSession.substring(0, index) : jSession;
    }



    public static Integer compare2Double(Double value, Double compareValue){
        return new Integer(value.compareTo(compareValue));
    }

    public static String[] splitUsernameAndPassword(String input) {
        String username = "";
        String password = "";
        String accountType = "";
        String[] tmp = input.split(Pattern.quote(Constants.SECURITY_CREDENTIAL_DELIMITER));
        username = tmp[0];
        if (tmp.length > 1) {
            password = tmp[1];
        }
        if (tmp.length > 2) {
        	accountType = tmp[2];
        }

        return new String[] {username, password, accountType};
    }
    
    public static String buildPdfFilePath(String filePrefix, String pdfLocation) {
        StringBuilder sb = new StringBuilder(pdfLocation);
        sb.append(File.separator).append(filePrefix);

        sb.append("_").append(SecurityUtils.getPrincipal().getUserName())
                .append("_").append(new SimpleDateFormat("yyyyMMdd_HH24mmss").format(Calendar.getInstance().getTime())).append(".pdf");
        return sb.toString();
    }
}
