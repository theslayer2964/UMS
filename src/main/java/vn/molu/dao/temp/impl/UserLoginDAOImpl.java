package vn.molu.dao.temp.impl;

import org.springframework.stereotype.Repository;
import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.temp.UserLoginDAO;
import vn.molu.dto.admin.admin.UserLoginDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserLoginDAOImpl extends GenericDAOImpl<UserLoginDTO, Long> implements UserLoginDAO {

    @Override
    public List<UserLoginDTO> findUsersByUsername(List<String> usernames) {
        String usernames_joinString = usernames.stream().map(name -> "\"" + name + "\"").collect(Collectors.joining(", "));
        String sqlClause = "select user_id, user_name, full_name, shop_code, create_date, emp_code from sonbn.user_login where user_name in (" + usernames_joinString + ")";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .getResultList());
    }

    @Override
    public UserLoginDTO findUserByname(String username) {
        System.out.println("USER: " + username);
        try {
            String sqlClause = "select user_id, user_name, full_name, shop_code, create_date, emp_code from sonbn.user_login where user_name = " + "'" + username + "'";
            List<UserLoginDTO> rs = DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                    .getResultList());
            return rs.size() > 0 ? rs.get(0) : null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private List<UserLoginDTO> DTOMapper(List<Object[]> objects) {
        List<UserLoginDTO> resultList = new ArrayList<>();
        for(Object[] o: objects){
            UserLoginDTO dto = new UserLoginDTO();
            dto.setUser_id(o[0] != null ? Long.parseLong(o[0].toString()) : 0);
            dto.setUser_name(o[1] != null ? o[1].toString().trim() : "");
            dto.setFull_name(o[2] != null ? o[2].toString().trim() : "");
            dto.setShop_code(o[3] != null ? o[3].toString().trim() : "");
            dto.setCreate_date(o[4] != null ? o[4].toString().trim() : "");
            dto.setEmp_code(o[5] != null ? o[5].toString().trim() : "");
        }
        return resultList;
    }
}
