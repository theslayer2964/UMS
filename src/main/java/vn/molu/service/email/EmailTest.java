package vn.molu.service.email;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class EmailTest {
    public static void main(String[] args) throws IOException, ParseException {
//        LocalDate calendar = LocalDate.now();
//        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat outputFormat = new SimpleDateFormat("ddMMyyyy");
//        String birth = outputFormat.format(inputFormat.parse(calendar.minusDays(10).toString()));
//        String ngaycap = outputFormat.format(inputFormat.parse(calendar.minusDays(5).toString()));
//        System.out.println(birth + "|" + ngaycap);

        try {
            boolean rsA = functionA(0);
            System.out.println("RS_A:" + rsA);
        } catch (Exception e) {
            System.out.println("LOI");
        }
    }

    private static boolean functionA(int i) {
        try {
//            boolean rsB = functionB(i);
//            System.out.println("RS B:" + rsB);
            return functionB(i);
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean functionB(int i) {
        try {
            System.out.println(i / 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
