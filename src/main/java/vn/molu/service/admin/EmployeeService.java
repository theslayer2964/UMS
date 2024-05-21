package vn.molu.service.admin;

import vn.molu.dto.admin.admin.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO findActiveEmp_codeByUsername(String emp_name);
    List<EmployeeDTO> findActiveUserByUsername(String username);
    Integer changeShopcodeInCSKK(String email, String shopCode) ;
    Integer changeShopCodeInResnum(String username, String shopCode);
    String checkUserIsGDV(String username);
    String findEmp_nameByUser(String username);
}
