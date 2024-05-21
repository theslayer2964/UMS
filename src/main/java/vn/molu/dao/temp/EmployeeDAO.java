package vn.molu.dao.temp;

import vn.molu.dto.admin.admin.EmployeeDTO;

import java.util.List;

public interface EmployeeDAO {
    EmployeeDTO findActiveEmp_codeByUsername(String username);
    List<EmployeeDTO> findActiveUserByUsername(String username);
    Integer changeShopCodeCSKK(String email, String newShopCode);
    Integer changeShopCodeResnum(String username, String newShopCode);
    String checkUserIsGDV(String username);
    String findEmp_nameByUser(String username);
}
