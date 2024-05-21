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
//import org.springframework.web.util.UriComponentsBuilder;
//import vn.molu.common.module.LocalDateTimeModule;
//import vn.molu.common.utils.DateUtil;
//import vn.molu.domain.Collaborator;
//import vn.molu.repository.CollaboratorRepository;
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
//public class BachHoaSo_API {
//    private transient final Log log = LogFactory.getLog(this.getClass());
//    @Autowired
//    private CollaboratorRepository collaboratorRepository;
//
//    @Scheduled(fixedRate = 60000)
//    private void callApiAndGetData() {
//        log.info("Starting call Bach hoa so API  and get data");
//        String today = DateUtil.date2String(DateUtil.today(), "yyyy-MM-dd");
//        //Tắt xác thực chứng chỉ SSL
//        disableSSLVerification();
//        String apiUrl = "https://apibhs.k-media.vn/api/report/kpi";
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + getAuthorization());
//
//        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiUrl)
//                .queryParam("fromDate", today)
//                .queryParam("toDate", today);
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                uriBuilder.toUriString(), HttpMethod.GET, entity, String.class);
//
//        if (response.getStatusCode().is2xxSuccessful()) {
//            String responseBody = response.getBody();
//            // Sử dụng ObjectMapper để phân tích JSON response
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.registerModule(new LocalDateTimeModule());
//            try {
//                List<Collaborator> listFromDB = collaboratorRepository.findAll();
//                JsonNode jsonResponse = objectMapper.readTree(responseBody);
//                JsonNode dataNode = jsonResponse.get("data");
//                if (dataNode.isArray()) {
//                    ObjectReader objectReader = objectMapper.readerFor(Collaborator.class);
//                    for (JsonNode itemNode : dataNode) {
//                        Collaborator collaborator = objectReader.readValue(itemNode);
//                        List<Collaborator> list = listFromDB.stream()
//                                .filter(c -> Objects.equals(c.getSuccessDate(), collaborator.getSuccessDate()))
//                                .collect(Collectors.toList());
//                        if (list.isEmpty()) {
//                            collaboratorRepository.save(collaborator);
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//            }
//        } else {
//            log.error("API call was not successful. Status code: " + response.getStatusCode());
//        }
//        log.info("Ending call Bach hoa so API  and get data");
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
//        String apiUrl = "https://apibhs.k-media.vn/api/user/login";
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("username", "baocaoctkv2_kpi");
//        requestBody.put("password", "aA@123456");
//
//        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
//        if (response.getStatusCode().is2xxSuccessful()) {
//            String responseBody = response.getBody();
//            JSONParser parser = new JSONParser();
//            try {
//                JSONObject jsonResponse = (JSONObject) parser.parse(responseBody);
//                return (String) jsonResponse.get("token");
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//            }
//
//        } else {
//            log.error("API call was not successful. Status code: " + response.getStatusCode());
//        }
//        return null;
//    }
//}
