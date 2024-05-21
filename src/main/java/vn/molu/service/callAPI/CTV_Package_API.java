//package vn.molu.service.callAPI;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectReader;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import vn.molu.common.module.LocalDateTimeModule;
//import vn.molu.common.utils.DateUtil;
//import vn.molu.domain.CTV_Package;
//import vn.molu.repository.CTVPackageRepository;
//
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import java.security.cert.X509Certificate;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//@Service
//@EnableScheduling
//public class CTV_Package_API {
//    private transient final Log log = LogFactory.getLog(this.getClass());
//    @Autowired
//    private CTVPackageRepository ctvPackageRepository;
//
//    @Scheduled(fixedRate = 60000)
//    private void callApiAndGetData() {
//        log.info("Starting call API CTV_Package and get data");
//        String today = DateUtil.date2String(DateUtil.today(), "yyyy-MM-dd");
//        //Tắt xác thực chứng chỉ SSL
//        disableSSLVerification();
//        String apiUrl = "https://congtacvien.mobifone.vn/aff-api/api/transPackage/get-trans-admin";
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + getAuthorization());
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // Tạo body cho request
//        Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("startTime", DateUtil.date2String(DateUtil.today(), "dd/MM/yyyy") + " 00:00:00");
//        requestBody.put("endTime", DateUtil.date2String(DateUtil.today(), "dd/MM/yyyy") + " 12:00:00");
//        requestBody.put("type", "2");
//
//        // Thực hiện gọi API và nhận response
//        ResponseEntity<String> response = restTemplate.exchange(
//                apiUrl, HttpMethod.POST, new HttpEntity<>(requestBody, headers), String.class);
//
//        // Kiểm tra mã trạng thái HTTP, trong trường hợp thành công là 200
//        if (response.getStatusCode().is2xxSuccessful()) {
//            String responseBody = response.getBody();
//
//            // Sử dụng ObjectMapper để phân tích JSON response
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.registerModule(new LocalDateTimeModule());
//            try {
//                List<CTV_Package> listFromDB = ctvPackageRepository.findAll();
//                JsonNode jsonResponse = objectMapper.readTree(responseBody);
//                JsonNode dataNode = jsonResponse.get("data");
//                if (dataNode.isArray()) {
//                    // Tạo một ObjectReader được cấu hình để chuyển đổi từ JsonNode sang CTV_Package
//                    ObjectReader objectReader = objectMapper.readerFor(CTV_Package.class);
//                    for (JsonNode itemNode : dataNode) {
//                        CTV_Package ctvPackage = objectReader.readValue(itemNode);
//                        List<CTV_Package> list = listFromDB.stream()
//                                .filter(c -> Objects.equals(c.getTransId(), ctvPackage.getTransId()))
//                                .collect(Collectors.toList());
//                        if (list.isEmpty()) {
//                            ctvPackageRepository.save(ctvPackage);
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//            }
//        } else {
//            log.error("API call was not successful. Status code: " + response.getStatusCode());
//        }
//        log.info("Ending call API CTV_Package and get data");
//    }
//
//    private void disableSSLVerification() {
//        try {
//            TrustManager[] trustAllCertificates = new TrustManager[]{
//                    new X509TrustManager() {
//                        public X509Certificate[] getAcceptedIssuers() {
//                            return null;
//                        }
//
//                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                        }
//
//                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                        }
//                    }
//            };
//
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());
//
//            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//
//    private String getAuthorization() {
//        String apiUrl = "https://congtacvien.mobifone.vn/aff-api/api/authenticate";
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("username", "congty2_api");
//        requestBody.put("password", "Vms@cty2");
//
//        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);
//
//        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
//        if (response.getStatusCode().is2xxSuccessful()) {
//            String responseBody = response.getBody();
//            JSONParser parser = new JSONParser();
//            try {
//                JSONObject jsonResponse = (JSONObject) parser.parse(responseBody);
//                String token = (String) ((JSONObject) jsonResponse.get("data")).get("token");
//                return token;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            log.error("API call was not successful. Status code: " + response.getStatusCode());
//        }
//        return null;
//    }
//}
