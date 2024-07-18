package vn.molu.dao.temp.impl;

import org.springframework.stereotype.Repository;
import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.temp.EmployeeDAO;
import vn.molu.dto.admin.admin.EmployeeDTO;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDAOImpl extends GenericDAOImpl<EmployeeDTO, String> implements EmployeeDAO {

    @Override
    public EmployeeDTO findActiveEmp_codeByUsername(String username) {
        String sqlClause = "select emp_code, emp_name, shop_code, status, emp_type, user_name, email from employee where user_name = ?1 and status = 1";
        List<EmployeeDTO> rs = DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .setParameter(1, username)
                .getResultList());
        if (rs.size() > 0)
            return rs.get(0);
        return null;
    }

    @Override
    public List<EmployeeDTO> findActiveUserByUsername(String username) {
        String sqlClause = "select emp_code, emp_name, shop_code, status, emp_type, user_name, email from employee where status = 1 and (user_name = ?1 or emp_code = ?1) ";
        List<EmployeeDTO> rs = DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .setParameter(1, username)
                .getResultList());
        return rs.size() > 0 ? rs : null;
    }

    @Override
    @Transactional
    public Integer changeShopCodeCSKK(String email, String newShopCode) {
        String user = email.replace("@mobifone.vn", "");
        System.out.println("USER:" + user + "|newShopCode: " + newShopCode);
        try {
            String sqlClause = "{ call cs_nhansu_owner.pr_sonbn_change_shop@bcsxkd_sys_mgmt_owner_sms_dbl( ?1, ?2) }";
            Integer rs = entityManager.createNativeQuery(sqlClause)
                    .setParameter(1, user.toUpperCase())
                    .setParameter(2, newShopCode)
                    .executeUpdate();
            return rs;
        } catch (Exception e){
            throw e;
        }
//        } catch (PersistenceException e) {
//            Throwable cause = e.getCause();
//            if (cause instanceof GenericJDBCException) {
//                System.out.println("GENERIC JDBC - 01403");
//                return -1;
//            } else {
//                System.out.println("SQL EXECEPTION:" + e.getMessage());
//            }
//        }
//        return -1;
    }

    @Override
    @Transactional
    public Integer changeShopCodeResnum(String username, String newShopCode) {
        try {
//            String sqlClause = "UPDATE sonbn.user_login SET shop_code = ?1 WHERE user_name = ?2 ";
            String sqlClause = "{ call CHANGE_SHOPCODE_RESNUM(?1, ?2)}";
            return entityManager.createNativeQuery(sqlClause)
                    .setParameter(1, username)
                    .setParameter(2, newShopCode)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public String checkUserIsGDV(String username) {
        try {
            String sqlClause = "select * from employee where status = '1' and ( emp_code = ?1 or user_name = ?1)";
            List<EmployeeDTO> rs = DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                    .setParameter(1, username)
                    .getResultList());
            String kq = "YES";
            if (rs.size() > 0) {
                if (rs.get(0).getEmp_type().equals("2") || rs.get(0).getEmp_type().equals("3")) {
                    kq = "NO";
                }
            }
            return kq;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "UNKNOWN";
    }

    @Override
    public String findEmp_nameByUser(String username) {
        String sqlClause = "select emp_code, emp_name, shop_code, status, emp_type, user_name, email from employee where (user_name = ?1 or emp_code = ?1) ";
        List<EmployeeDTO> rs = DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .setParameter(1, username)
                .getResultList());
        return rs.size() > 0 ? rs.get(0).getEmp_name() : "Chưa có data";
    }

    private List<EmployeeDTO> DTOMapper(List<Object[]> objects) {
        List<EmployeeDTO> resultList = new ArrayList<>();
        for (Object[] o : objects) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setEmp_code(o[0] != null ? o[0].toString().trim() : null);
            employeeDTO.setEmp_name(o[1] != null ? o[1].toString().trim() : null);
            employeeDTO.setShop_code(o[2] != null ? o[2].toString().trim() : null);
            employeeDTO.setStatus(o[3] != null ? Integer.parseInt(o[3].toString().trim()) : null);
            employeeDTO.setEmp_type(o[4] != null ? Integer.parseInt(o[4].toString().trim()) : null);
            employeeDTO.setUser_name(o[5] != null ? o[5].toString().trim() : null);
            employeeDTO.setEmail(o[6] != null ? o[6].toString().trim() : null);
            resultList.add(employeeDTO);
        }
        return resultList;
    }
}
