package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.dao.temp.EmployeeDAO;
import vn.molu.dto.admin.admin.EmployeeDTO;
import vn.molu.service.admin.EmployeeService;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Override
    public EmployeeDTO findActiveEmp_codeByUsername(String username) {
        return employeeDAO.findActiveEmp_codeByUsername(username);
    }

    @Override
    public List<EmployeeDTO> findActiveUserByUsername(String username) {
        return employeeDAO.findActiveUserByUsername(username);
    }

    @Override
    public Integer changeShopcodeInCSKK(String email, String shopCode) {
        try {
            return employeeDAO.changeShopCodeCSKK(email, shopCode);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Integer changeShopCodeInResnum(String username, String shopCode) {
        return employeeDAO.changeShopCodeResnum(username, shopCode);
    }

    @Override
    public String checkUserIsGDV(String username) {
        return employeeDAO.checkUserIsGDV(username);
    }

    @Override
    public String findEmp_nameByUser(String username) {
        return employeeDAO.findEmp_nameByUser(username);
    }

}
