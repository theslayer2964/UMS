package vn.molu.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
// Lớp DateUtil chứa các phương thức để làm việc với các thao tác liên quan đến ngày tháng.
public final class DateUtil {
	public static long HOUR = 3600000L;
	/*
		date2String: Chuyển đổi đối tượng Date thành chuỗi ký tự theo định dạng được chỉ định.
	 */
	public static String date2String(Date input, String format) throws IllegalArgumentException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(input);
	}
	/*
		string2Date(String input, String format): Chuyển đổi chuỗi ký tự thành đối tượng Date theo định dạng được chỉ định.
	 */
	public static Date string2Date(String input, String format) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.parse(input);
	}
	/*
		move2TheEndOfDay(Timestamp input): Di chuyển thời gian của đối tượng Timestamp đến cuối ngày.
	 */
	public static Timestamp move2TheEndOfDay(Timestamp input) {
		Timestamp res = null;
		if (input != null) {
			res = new Timestamp(input.getTime() + 24 * HOUR - 1000);
		}
		return res;
	}
	/*
		validateDateFormatMMDDYYYY(String input): Kiểm tra tính hợp lệ của định dạng ngày tháng MM/DD/YYYY.
	 */
	public static boolean validateDateFormatMMDDYYYY(String input) {
		boolean res = false;
		if (StringUtils.isNotBlank(input)) {
			String[] temp = input.split("/");
			if (temp.length == 3) {
				try {
					int month = Integer.parseInt(temp[0]);
					int day = Integer.parseInt(temp[1]);
					int year = Integer.parseInt(temp[2]);

					if (month >= 1 && month <= 12) {
						if (year >= 1900) {
							res = isValidDay(day, month, year);
						}
					}
				} catch (Exception e) {
					res = false;
				}
			}
		}
		return res;
	}
	/*
		isValidFormat(String format, String value): Kiểm tra tính hợp lệ của định dạng ngày tháng cho một giá trị đã cho.
	 */
	public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }
	/*
		isLeapYear(int year): Kiểm tra xem một năm có phải là năm nhuận hay không.
	 */
	public static boolean isLeapYear(int year) {
		if (year % 4 == 0 && year % 100 != 0) {
			return true;
		}
		return false;
	}
	/*
		isValidDay(int day, int month, int year): Kiểm tra các tham số input day, month, year có hợp lệ hay không
	 */
	private static boolean isValidDay(int day, int month, int year) {
		if (day < 1) {
			return false;
		}
		if (day > 31)
			return false;
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (day > 30) {
				return false;
			}
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				if (day > 29) {
					return false;
				}
			} else {
				if (day > 28) {
					return false;
				}
			}
		}
		return true;
	}
	/*
		truncateTime(Date date): Cắt bỏ phần thời gian trong đối tượng Date, chỉ giữ lại ngày tháng.
	 */
	public static long truncateTime(Date date) {
		try {
			return DateUtil.string2Date(date2String(date, "yyyyMMdd"), "yyyyMMdd").getTime();
		} catch (Exception e) {
			return System.currentTimeMillis();
		}
	}
	/*
		yesterday(): Trả về đối tượng Date đại diện cho ngày hôm qua.
	 */
	public static Date yesterday() {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE, -1); // minus number would decrement the days
		return cal.getTime();
	}
	/*
		Trả về đối tượng Date đại diện cho ngày hôm nay (không có phần giờ phút giây).
	 */
	public static Date today() {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	/*
		firstDayOfMonth(): Trả về đối tượng Date đại diện cho ngày đầu tiên của tháng hiện tại.
	 */
	public static Date firstDayOfMonth(){
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_MONTH, 1);
		return date.getTime();
	}
	/*
		firstDayOfLastMonth(): Trả về đối tượng Date đại diện cho ngày đầu tiên của tháng trước đó.
	 */
	public static Date firstDayOfLastMonth(){
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, -1);
		date.set(Calendar.DAY_OF_MONTH, date.getActualMinimum(Calendar.DAY_OF_MONTH));
		return date.getTime();
	}
	/*
		theMonthBeforeLastMonth(): Trả về đối tượng Date đại diện cho ngày đầu tiên của tháng hai tháng trước đó.
	 */
	public static Date theMonthBeforeLastMonth(){
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, -2);
		date.set(Calendar.DAY_OF_MONTH, date.getActualMinimum(Calendar.DAY_OF_MONTH));
		return date.getTime();
	}
	/*
		lastDayOfMonth(): Trả về đối tượng Date đại diện cho ngày cuối cùng của tháng hiện tại.
	 */
	public static Date lastDayOfMonth(){
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_MONTH, date.getActualMaximum(Calendar.DAY_OF_MONTH));
		return date.getTime();
	}
	/*
		formatTimestamp2String(Timestamp dateTime, String format):
		Chuyển đổi đối tượng Timestamp thành chuỗi ký tự theo định dạng đã cho.
	 */
	public static String formatTimestamp2String(Timestamp dateTime, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(dateTime.getTime()));
	}
	/*
		dateToTimestamp(Date dateInput, String format):
		Chuyển đổi đối tượng Date thành đối tượng Timestamp theo định dạng đã cho.
	 */
	public static Timestamp dateToTimestamp(Date dateInput, String format) {
		return new Timestamp(dateInput.getTime());
	}
	/*
		differenceInMonths(Date beginningDate, Date endingDate): Tính số tháng chênh lệch giữa hai ngày
	 */
	public static Integer differenceInMonths(Date beginningDate, Date endingDate) {
		if (beginningDate == null || endingDate == null) {
			return 0;
		}
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(beginningDate);
		Calendar cal2 = new GregorianCalendar();
		cal2.setTime(endingDate);
		return differenceInMonths(cal1, cal2);
	}

	private static Integer differenceInMonths(Calendar beginningDate, Calendar endingDate) {
		if (beginningDate == null || endingDate == null) {
			return 0;
		}
		int m1 = beginningDate.get(Calendar.YEAR) * 12 + beginningDate.get(Calendar.MONTH);
		int m2 = endingDate.get(Calendar.YEAR) * 12 + endingDate.get(Calendar.MONTH);
		return m2 - m1;
	}
	public static void main(String[] args) {
		try {
			System.out.println(DateUtil.date2String(today(),"yyyy-MM-dd"));
			System.out.println(DateUtil.today());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
