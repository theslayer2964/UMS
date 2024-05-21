package vn.molu.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// Cung cấp các phương thức xử lý và làm sạch chuỗi
public class CommonUtil {
	//static: biến thuộc về class và các instance sẽ được sử dụng chung mà không cần tạo mới
	//final: giá trị của biến và cố định sau khi được gán giá trị ban đầu
	private static final Pattern EMAIL_P = Pattern
			.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
	private static final Pattern ZIP_P = Pattern.compile("\\d{5}(-\\d{4})?");
	private static final Pattern USERNAME_P = Pattern.compile("^[A-Za-z0-9_-]{3,25}");
	// private static final Pattern ALPHABET = Pattern.compile("[A-Za-z]+");

	/*
		Phương thức này kiểm tra xem một chuỗi email có đúng định dạng email hợp lệ không
		Ví dụ:
			Input: "test@example.com"
			Output: true
			Input: "invalid.email"
			Output: false
	*/
	public static boolean isValidEmail(String email) {
		Matcher m = EMAIL_P.matcher(email);
		return m.matches();
	}
	/**
	 * validate number
	 *
	 * @param number
	 * @return
	 */
	/*
		Phương thức này kiểm tra xem một chuỗi number có chỉ chứa các chữ số không
		Ví dụ:

			Input: "12345"
			Output: true
			Input: "abc123"
			Output: false
	 */
	public static boolean isDigit(String number) {
		Pattern pattern = Pattern.compile("[0-9]+");
		return pattern.matcher(number).matches();
	}
	/*
		Phương thức này kiểm tra xem một chuỗi email có đúng định dạng mã zip hợp lệ không.
		Ví dụ:

			Input: "12345"
			Output: true
			Input: "123456789"
			Output: false
	 */
	public static boolean isValidZip(String email) {
		Matcher m = ZIP_P.matcher(email);
		return m.matches();
	}
	/*
		 Phương thức này kiểm tra xem một chuỗi username có đúng định dạng tên người dùng hợp lệ không
		 Ví dụ:

			Input: "john_doe123"
			Output: true
			Input: "johndoe@"
			Output: false
	 */
	public static boolean isValidUsername(String username) {
		Matcher m = USERNAME_P.matcher(username);
		return m.matches();
	}

	/*
	 * get fileName without extension
	 */
	public static String getNameWithoutExtension(String fileName) {
		return (fileName.indexOf(".") > 0) ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName;
	}

	/*
	 * get extension of file
	 */
	public static String getExtension(String fileName) {
		return (fileName.indexOf(".") < fileName.length()) ? fileName.substring(fileName.lastIndexOf(".") + 1) : "";
	}
	/*
		Trả về một chuỗi ngẫu nhiên
	 */
	public static String generateUUID() {
		return UUID.randomUUID().toString();

	}
	/*
		Phương thức này làm tròn một số thập phân val với số chữ số thập phân xác định bởi decimalDigit
		Ví dụ:
			Input: val = 3.14159, decimalDigit = 2
			Output: "3.14"
	 */
	public static String roundUp(Double val, int decimalDigit) {
		if (val == null) {
			return "";
		}
		BigDecimal decimal = new BigDecimal(val);
		decimal = decimal.setScale(decimalDigit, RoundingMode.HALF_UP);
		return decimal.toString();
	}

	/**
	 * Trims, removes line breaks, multiple spaces and generally cleans text
	 * before processing.
	 * 
	 * @param input
	 *            Text to be transformed
	 */
	/*
		Phương thức này làm sạch các thẻ HTML và văn bản trong chuỗi input
		Ví dụ:
			Input: "<p>Hello <b>world</b>!</p>"
			Output: "Hello world!
	 */
	public static String cleanHtmlTags(String input) {
		try {
			// Remove math expression
			input = input.replaceAll("<span class=\"AM\">`", "");
			input = input.replaceAll("`</span>", " ");
			List<String> res = extractText(new StringReader(input));
			input = StringUtils.join(res, ' ');
		} catch (IOException ex) {

		}

		return input;
	}
	/*
		Xử lý text
	 */
	public static List<String> extractText(Reader reader) throws IOException {
		final ArrayList<String> list = new ArrayList<String>();

		ParserDelegator parserDelegator = new ParserDelegator();
		HTMLEditorKit.ParserCallback parserCallback = new HTMLEditorKit.ParserCallback() {
			public void handleText(final char[] data, final int pos) {
				list.add(new String(data));
			}

			public void handleStartTag(HTML.Tag tag, MutableAttributeSet attribute, int pos) {
			}

			public void handleEndTag(HTML.Tag t, final int pos) {
			}

			public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, final int pos) {
				if (t.toString().equalsIgnoreCase("img")) {
					list.add("###");
				}
			}

			public void handleComment(final char[] data, final int pos) {
			}

			public void handleError(final String errMsg, final int pos) {
			}
		};
		parserDelegator.parse(reader, parserCallback, true);
		return list;
	}
	/*
		String removeDiacritic(String str):
		Phương thức này loại bỏ các dấu thanh và dấu huyền khỏi một chuỗi str
		và chuyển đổi các ký tự có dấu thành ký tự không dấu tương ứng
		Ví dụ:
			Input: "Hà Nội"
			Output: "Ha Noi"
	 */
	public static String removeDiacritic(String str) {
		String result = Normalizer.normalize(str, Normalizer.Form.NFD);
		result = result.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		result = result.replace('đ', 'd');
		result = result.replace('Đ', 'D');
		result = result.replaceAll("[^a-z A-Z0-9-]", "");
		return result;
	}
	/*
		String seoURL(String input):
		Phương thức loại bỏ các dấu và ký tự không hợp lệ, thay thế khoảng trắng bằng dấu gạch ngang
		và chuyển đổi thành chữ thường. Phương thức trả về URL tối ưu hóa.
		Ví dụ:

			Input: "Hello World"
			Output: "hello-world"
	 */
	public static String seoURL(String input) {
		String result = Normalizer.normalize(input, Normalizer.Form.NFD);
		result = result.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		result = result.replace('đ', 'd');
		result = result.replace('Đ', 'D');
		result = result.replaceAll("[^a-z A-Z0-9-]", "");
		result = result.replaceAll(" ", "-");
		return result.toLowerCase();
	}

	@SuppressWarnings("unused")
	private static String unaccent(String input) {
		if (StringUtils.isEmpty(input)) {
			return "";
		} else {
			String result = Normalizer.normalize(input, Normalizer.Form.NFD);
			result = result.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
			result = result.replace('đ', 'd');
			result = result.replace('Đ', 'D');
			return result;
		}
	}
	/*
		String formatDate(Date input):
		Phương thức này định dạng một đối tượng Date thành một chuỗi ngày tháng có định dạng "MM/dd/yy".
		Ví dụ:
			Input: Thu Jul 11 00:00:00 GMT 2023
			Output: "07/11/23"
	 */
	public static String formatDate(Date input) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		return sdf.format(input);
	}
	/*
		File getConfigFile(String fileName):
		Phương thức này trả về một đối tượng File đại diện cho tệp cấu hình có tên fileName.
		Phương thức tìm kiếm tệp cấu hình trong các thư mục cấu hình của máy chủ (server) JBoss.
		Nếu tệp cấu hình tồn tại, phương thức trả về đối tượng File, ngược lại trả về null.
	 */
	public static File getConfigFile(String fileName) {
		String domainConfigDir = System.getProperty("jboss.domain.config.dir");
		File f;
		if (StringUtils.isNotBlank(domainConfigDir)) {
			f = new File(domainConfigDir + File.separator + fileName);
			if (f.exists()) {
				return f;
			}
		}
		String configDir = System.getProperty("jboss.server.config.dir");
		if (StringUtils.isNotBlank(configDir)) {
			f = new File(configDir + File.separator + fileName);
			if (f.exists()) {
				return f;
			}
		}
		return null;
	}
	/*
		long ipToLong(InetAddress ip):
		Phương thức này chuyển đổi địa chỉ IP (dưới dạng đối tượng InetAddress) thành một số nguyên dạng long.
		Phương thức trả về giá trị số nguyên đại diện cho địa chỉ IP.
	 */
	public static long ipToLong(InetAddress ip) {
        byte[] octets = ip.getAddress();
        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }
	/*
		boolean checkIPAddressInRange(String ipAddress, String ipAddressStart, String ipAddressEnd):
		Phương thức này kiểm tra xem một địa chỉ IP ipAddress có nằm trong khoảng địa chỉ IP từ ipAddressStart đến ipAddressEnd hay không.
		Phương thức trả về true nếu địa chỉ IP nằm trong khoảng, ngược lại trả về false.
	 */
	public static boolean checkIPAddressInRange(String ipAddress, String ipAddressStart, String ipAddressEnd) {
		try {
			long iPAddStart = ipToLong(InetAddress.getByName(ipAddressStart));
	        long iPAddEnd = ipToLong(InetAddress.getByName(ipAddressEnd));
	        long ipAdd = ipToLong(InetAddress.getByName(ipAddress));

	        return (ipAdd >= iPAddStart && ipAdd <= iPAddEnd);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        return false;
	}
	/*
		boolean isIPAddRange(String ipAddressStart, String ipAddressEnd):
		Phương thức này kiểm tra xem một khoảng địa chỉ IP từ ipAddressStart đến ipAddressEnd có hợp lệ hay không.
		Phương thức trả về true nếu khoảng địa chỉ IP hợp lệ (địa chỉ IP đầu nhỏ hơn hoặc bằng địa chỉ IP cuối),
		ngược lại trả về false.
	 */
	public static boolean isIPAddRange(String ipAddressStart, String ipAddressEnd) {
		try {
			long iPAddStart = ipToLong(InetAddress.getByName(ipAddressStart));
	        long iPAddEnd = ipToLong(InetAddress.getByName(ipAddressEnd));

	        return (iPAddStart <= iPAddEnd);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        return false;
	}
}
