package vn.molu.dao.temp.impl;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.temp.CSKK_UserDAO;
import vn.molu.dto.admin.admin.CSKK_UserDTO;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CSKK_UserDAOImpl extends GenericDAOImpl<CSKK_UserDTO, String> implements CSKK_UserDAO {
    @Override
    @Transactional
    public Integer insert(CSKK_UserDTO cskkUserDTO) {
        String sqlClause = "insert into cs_nhansu_owner.tmp_sonbn@bcsxkd_sys_mgmt_owner_sms_dbl(user_name, email, full_name, shop_code, shop_name, phone, chi_nhanh, chuc_danh, ghi_chu, status, error_explain) " +
                "    values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, null, ?9, null)";
        entityManager.createNativeQuery(sqlClause)
                .setParameter(1, cskkUserDTO.getUsername())
                .setParameter(2, cskkUserDTO.getEmail())
                .setParameter(3, cskkUserDTO.getFullName())
                .setParameter(4, cskkUserDTO.getShopCode())
                .setParameter(5, cskkUserDTO.getShopName())
                .setParameter(6, cskkUserDTO.getPhone())
                .setParameter(7, cskkUserDTO.getBranch())
                .setParameter(8, cskkUserDTO.getPosition())
                .setParameter(9, cskkUserDTO.getStatus())
                .executeUpdate();

        String sqlClause2 = "{ CALL cs_nhansu_owner.pr_sonbn_create_user@bcsxkd_sys_mgmt_owner_sms_dbl }";
        return entityManager.createNativeQuery(sqlClause2)
                .executeUpdate();
    }

    @Override
    @Transactional
    public String executeProcedureInsert() {
        try {
            String sqlClause = "{ CALL cs_nhansu_owner.pr_sonbn_create_user@bcsxkd_sys_mgmt_owner_sms_dbl() }";
            Integer rs = entityManager.createNativeQuery(sqlClause)
                    .executeUpdate();
            return rs.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Modifying
    @Transactional
    public String deleteTemp() {
        try {
            String sqlClause = "{ call TEMP_PROCEDURE() }";
            Integer rs = entityManager.createNativeQuery(sqlClause)
                    .executeUpdate();
            return rs.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public Integer grand_DuyetSoPermission_ForCHT(String email) {
        try {
            String sqlClause = "{ call update_CHT (?1) }";
            Integer rs = entityManager.createNativeQuery(sqlClause)
                    .setParameter(1, email.toUpperCase())
                    .executeUpdate();

//            StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("update_CHT");
//            storedProcedureQuery.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
//            storedProcedureQuery.setParameter("p_email", email);
//            storedProcedureQuery.registerStoredProcedureParameter("error_code", Integer.class, ParameterMode.OUT);
//            storedProcedureQuery.execute();
//            Integer rs = (Integer) storedProcedureQuery.getOutputParameterValue("error_code");
            return rs;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<CSKK_UserDTO> getListCSKK_Result() {
        String sqlClause = "SELECT * FROM cs_nhansu_owner.tmp_sonbn@bcsxkd_sys_mgmt_owner_sms_dbl";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause).getResultList());
    }

    @Override
    public List<CSKK_UserDTO> getListCSKK_ResultUnsuccessful() {
        String sqlClause = "SELECT * FROM cs_nhansu_owner.tmp_sonbn@bcsxkd_sys_mgmt_owner_sms_dbl where status = -1 ";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause).getResultList());
    }

    @Override
    public CSKK_UserDTO findCSKK_ResultByUsername(String username) {
        String sqlClause = "SELECT * FROM cs_nhansu_owner.tmp_sonbn@bcsxkd_sys_mgmt_owner_sms_dbl where user_name = ?1 ";
        List<CSKK_UserDTO> list = DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .setParameter(1, username)
                .getResultList());
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public boolean findMailInHr_Nhanvien_Alldata(String mail) {
        try {
            String sqlClause = "select email from cs_nhansu_owner.hr_nhanvien_alldata@BCSXKD_SYS_MGMT_OWNER_SMS_DBL\n" +
                    "    where email = ?1 ";
            List<String> rs = DTOMapper2((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                    .setParameter(1, mail.toUpperCase())
                    .getResultList());
            return rs.size() > 0 ? true : false;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public Integer lockUser(String userName) {
        try {
            String sqlClause = "{ call LOCK_USER_CSKK(?1) }";
            return entityManager.createNativeQuery(sqlClause)
                    .setParameter(1, userName)
                    .executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    private List<String> DTOMapper2(List<Object[]> objects) {
        List<String> resultList = new ArrayList<>();
        for (Object o : objects) {
            resultList.add(o != null ? o.toString().trim() : null);
        }
        return resultList;
    }

    private List<CSKK_UserDTO> DTOMapper(List<Object[]> objects) {
        List<CSKK_UserDTO> resultList = new ArrayList<>();
        for (Object[] o : objects) {
            CSKK_UserDTO cskkUserDTO = new CSKK_UserDTO();
            cskkUserDTO.setUsername(o[0] != null ? o[0].toString().trim() : null);
            cskkUserDTO.setEmail(o[1] != null ? o[1].toString().trim() : null);
            cskkUserDTO.setFullName(o[2] != null ? o[2].toString().trim() : null);
            cskkUserDTO.setShopCode(o[3] != null ? o[3].toString().trim() : null);
            cskkUserDTO.setShopName(o[4] != null ? o[4].toString().trim() : null);
            cskkUserDTO.setPhone(o[5] != null ? o[5].toString().trim() : null);
            cskkUserDTO.setBranch(o[6] != null ? o[6].toString().trim() : null);
            cskkUserDTO.setPosition(o[7] != null ? o[7].toString().trim() : null);
            cskkUserDTO.setGhi_chu(o[8] != null ? o[8].toString().trim() : null);
            cskkUserDTO.setStatus(o[9] != null ? Integer.parseInt(o[9].toString().trim()) : null);
            cskkUserDTO.setError_explain(o[10] != null ? o[10].toString().trim() : null);

            resultList.add(cskkUserDTO);
        }

        return resultList;
    }
}
