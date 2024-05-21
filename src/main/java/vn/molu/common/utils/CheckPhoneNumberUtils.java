package vn.molu.common.utils;

import java.util.regex.Pattern;
// Phương thức để kiểm tra số điện thoại có thuộc mạng Mobifone hay không.
public class CheckPhoneNumberUtils {
    /*Input: Một chuỗi bất kì
    * Output: true hoặc fasle*/
    public static boolean checkMobifone(String phoneNumber){
        // 3 chữ số đầu bắt đầu bằng: 089|090|093|070|077|076|078|079 và 7 chữ số tiếp theo ngãu nhiên từ 0 -> 9
        String patternText = "(089|090|093|070|077|076|078|079)+([0-9]{7})\\b";
        Pattern pattern = Pattern.compile(patternText);
        return pattern.matcher(phoneNumber).matches();
    }
    /*Input: Một chuỗi bất kì
     * Output: true hoặc fasle*/
    public static boolean checkMobifoneWithoutFirstNumber(String phoneNumber){
        // 2 chữ số đầu bắt đầu bằng: 89|90|93|70|77|76|78|79 và 7 chữ số tiếp theo ngãu nhiên từ 0 -> 9
        String patternText = "^(89|90|93|70|77|76|78|79)+([0-9]{7})\\b";
        Pattern pattern = Pattern.compile(patternText);
        return pattern.matcher(phoneNumber).matches();

    }
}
