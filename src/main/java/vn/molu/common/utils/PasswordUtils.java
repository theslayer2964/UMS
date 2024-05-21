package vn.molu.common.utils;

import java.security.MessageDigest;
// Class PasswordUtils dùng để mã hóa password
public class PasswordUtils {

	 /**
	  * SHA-0
	  * @param btValue
	  * @param strAlgorithm
	  * @return
	  * @throws Exception
	  */
	 public static String encryptSHA(String password)  {
         try {
              Base64Encoder enc = new Base64Encoder();
              MessageDigest md = MessageDigest.getInstance("SHA");
              return enc.encodeBuffer(md.digest(password.getBytes()));
         } catch (Exception e) {

         }
         return null;
	 }
	 
	 public static void main(String[] args) throws Exception{
		 System.out.println(encryptSHA("123456")); // fEqNCco3Yq9h5ZUglD3CZJT4lBs=
//		 System.out.println(encryptSHA("vms@kpi"));
	 }
}
