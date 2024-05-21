package vn.molu.service.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Service
public class HandleExceptionServiceImpl implements HandleExceptionService {
    @Autowired
    private Environment environment;

    @Override
    public String convertResultOneUserForAllProgram(Map<String, Integer> resultOneUserForAllProgram) {
        String listSuccess = "";
        String listFail = "";
        for (Map.Entry<String, Integer> user : resultOneUserForAllProgram.entrySet()) {
            if (user.getValue() == 1) { // chuong trinh thanh cong
                listSuccess += (user.getKey() + ",");
            } else { // chuong trinh that bai
                listFail += (user.getKey() + ",");
            }
        }
        if (listSuccess.endsWith(",")) {
            listSuccess = listSuccess.substring(0, listSuccess.length() - 1);
        }
        if (listFail.endsWith(",")) {
            listFail = listFail.substring(0, listFail.length() - 1);
        }
        return environment.getProperty("msg.result_message_OneUser_AllProgram")
                .replace("SUCCESS", listSuccess.equals("") ? "-1" : listSuccess)
                .replace("ERROR", listFail.equals("") ? "-1" : listFail);
    }

    @Override
    public String convertIncorrectDataInput(List<String> listIncorrectMail) {
        StringJoiner rs = new StringJoiner(",");
        listIncorrectMail.forEach(mail -> {
            rs.add(mail);
        });
        return rs.toString();
    }
}
