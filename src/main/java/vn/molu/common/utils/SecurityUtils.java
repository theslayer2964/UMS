package vn.molu.common.utils;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.client.RestTemplate;
import vn.molu.webapp.security.MyUserDetail;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityUtils {
    /**
     * This method to retrieve the current online User Detail
     *
     * @return the current online MyUserDetail object
     */
    public static MyUserDetail getPrincipal() {
        return (MyUserDetail) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
    }

    /**
     * This getLoginUserId() is only used for doing jUnit test case only
     *
     * @return the current login name for online user
     */
    public static Long getLoginUserId() {
        return getPrincipal().getUserId();
    }

    @SuppressWarnings("unchecked")
    public static boolean userHasAuthority(String roleCode) {
        List<GrantedAuthority> list = (List<GrantedAuthority>) (SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities());
        List<GrantedAuthority> authories = list;
        for (GrantedAuthority authority : authories) {
            if (authority.getAuthority().equals(roleCode)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static List<String> getAuthorities() {
        List<String> results = new ArrayList<String>();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) (SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities());
        for (GrantedAuthority authority : authorities) {
            results.add(authority.getAuthority());
        }
        return results;
    }

    private static void disableSSLVerification() {
        try {
            TrustManager[] trustAllCertificates = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getAuthorization(){
        String apiUrl = "https://congtacvien.mobifone.vn/aff-api/api/authenticate";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("username","congty2_api");
        requestBody.put("password","Vms@cty2");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
        if (response.getStatusCode().is2xxSuccessful()){
            String responseBody = response.getBody();
            JSONParser parser = new JSONParser();
            try {
                JSONObject jsonResponse = (JSONObject) parser.parse(responseBody);
                String token = (String) ((JSONObject) jsonResponse.get("data")).get("token");
                return token;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("API call was not successful. Status code: " + response.getStatusCode());
        }
        return null;
    }

}