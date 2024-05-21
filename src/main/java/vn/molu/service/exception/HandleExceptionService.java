package vn.molu.service.exception;

import java.util.List;
import java.util.Map;

public interface HandleExceptionService {
    String convertResultOneUserForAllProgram(Map<String, Integer> resultOneUserForAllProgram);
    String convertIncorrectDataInput(List<String> listIncorrectMail);
}
