package vn.molu.dao.temp.impl;

import org.springframework.stereotype.Repository;
import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.temp.C2UserAdminDAO;
import vn.molu.dto.admin.admin.C2UserAdminDTO;
import vn.molu.service.request.ServerSideRequest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class C2UserAdminDAOImpl extends GenericDAOImpl<C2UserAdminDTO, Long> implements C2UserAdminDAO {

    @Override
    public List<C2UserAdminDTO> findAll() {
        String sqlClause = "select user_id, user_name, full_name, description, status, shop_code, center_code, granted_ip, account " +
                "from c2_admin_user where center_code = '2' ";

        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)

                .getResultList());
    }

    @Override
    public List<C2UserAdminDTO> layDS_ViPham_Homnay() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String sqlClause = "select user_id, user_name, full_name, description, status, shop_code, center_code, granted_ip, account " +
                "from c2_admin_user where status = '4' and center_code = '2' " +
                "and description like '%5814%' and description like '%" + formatter.format(today) + "%'";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .getResultList());
    }

    @Override
    public List<C2UserAdminDTO> layDS_ViPham_TanSuat_TraCuu_All() {
        String sqlClause = "select user_id, user_name, full_name, substr(description, 13 , 10), status, shop_code, center_code, granted_ip, account  " +
                "from c2_admin_user where status = '4' and center_code = '2' and description like '%5814%' ";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .getResultList());
    }

    @Override
    public List<C2UserAdminDTO> layDS_ViPham_TanSuat_TraCuu_10Ngay_DeGuiMail() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String sqlClause = "select user_id, user_name, full_name, substr(description, 13 , 10), status, shop_code, center_code, granted_ip, account  " +
                "from c2_admin_user where status = '4' and center_code = '2' " +
                "and description like '%5814%' and ( ";
        for (int i = 0; i < 10; i++) {
            sqlClause += " description like '%" + formatter.format(today.minusDays(i)) + "%' or ";
        }
        sqlClause += " description like '%" + formatter.format(today.minusDays(10)) + "%' ) and user_name not in (select user_name from explanation_form where form_type = '0' and status in (1) and update_date between SYSDATE - INTERVAL '10' day and SYSDATE) " +
                " order by shop_code, to_date(substr(description, 13 , 10), 'DD/MM/YYYY') ";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .getResultList());
    }

    @Override
    public C2UserAdminDTO findByUsername(String username) {
        String sqlClause = "select user_id, user_name, full_name, substr(description, 13 , 10), status, shop_code, center_code, granted_ip, account  "
                + " from c2_admin_user where user_name =  ?1";
        List<C2UserAdminDTO> rs = DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .setParameter(1, username)
                .getResultList());
        if (rs.size() > 0) {
            return rs.get(0);
        } else
            return new C2UserAdminDTO();
    }

    @Override
    public C2UserAdminDTO findByUserId(Long userId) {
        String sqlClause = "select user_id, user_name, full_name, description, status, shop_code, center_code, granted_ip, account  "
                + " from c2_admin_user where user_id = ?1";
        List<C2UserAdminDTO> rs = DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .setParameter(1, userId)
                .getResultList());
        if (rs.size() > 0) {
            return rs.get(0);
        } else
            return new C2UserAdminDTO();
    }

    @Override
    public List<C2UserAdminDTO> findByProperty(Map<String, Object> props) {
        String sqlClause = "select user_id, user_name, full_name, description, status, shop_code, center_code, granted_ip, account " +
                "from c2_admin_user where ";
        Set<String> set = props.keySet();
        for (String key : set) {
            System.out.println(key + " " + props.get(key));
            String condition = key + " " + " = '" + props.get(key).toString() + "' and ";
            sqlClause += condition;
        }
        sqlClause += " center_code = '2'";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .getResultList());
    }

    @Override
    @Transactional
    public Integer updateStatusC2UserAdmin(Long userId, String newStatus) {
        try {
            String sqlCause = "update c2_admin_user set status = ?1 " +
                    "    where user_id = ?2";
            Integer rs = entityManager.createNativeQuery(sqlCause)
                    .setParameter(1, newStatus)
                    .setParameter(2, userId)
                    .executeUpdate();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public Integer update(Long userId, String newStatus, String newGrantedIp) {
        try {
            String sqlCause = "update c2_admin_user set status = ?1, granted_ip = ?2 " +
                    "    where user_id = " + userId;
            Integer rs = entityManager.createNativeQuery(sqlCause)
                    .setParameter(1, newStatus)
                    .setParameter(2, newGrantedIp)
                    .executeUpdate();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public Integer updateIP(String username, String newGrantedIp) {
        try {
            String sqlCause = "update c2_admin_user set granted_ip = ?1 " +
                    "    where user_name = ?2";
            Integer rs = entityManager.createNativeQuery(sqlCause)
                    .setParameter(1, newGrantedIp)
                    .setParameter(2, username)
                    .executeUpdate();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<C2UserAdminDTO> findUserByShop(String shop_code) {
        String sqlClause = "select user_id, user_name, full_name, description, status, shop_code, center_code, granted_ip, account " +
                " from c2_admin_user where shop_code = ?1";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .setParameter(1, shop_code)
                .getResultList());
    }

    @Override
    public Integer count() {
        String sqlClause = "select count(*) from c2_admin_user";
        BigDecimal rs = (BigDecimal) entityManager.createNativeQuery(sqlClause)
                .getSingleResult();
        return rs.intValue();
    }

    @Override
    public List<C2UserAdminDTO> findU2Limit(ServerSideRequest ssr) {
        long length = ssr.getLength();
        long begin = ssr.getStart() + 1;
        long end = begin + length - 1;
        String sqlClause = "SELECT user_id, user_name, full_name, description, status, shop_code, center_code, granted_ip, account \n" +
                "FROM (\n" +
                "    SELECT user_id, user_name, full_name, description, status, shop_code, center_code, granted_ip, account, ROWNUM AS rn\n" +
                "    FROM c2_admin_user\n" +
                "    where center_code = '2'\n" +
                "    ORDER BY user_id \n" +
                ")\n" +
                "WHERE rn BETWEEN " + begin + " AND " + end;

        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)

                .getResultList());
    }

    @Override
    public List<C2UserAdminDTO> findByPropertySS(Map<String, Object> props, ServerSideRequest ssr) {
        long length = ssr.getLength();
        long begin = ssr.getStart() + 1;
        long end = begin + length - 1;
        String sqlClause = "select user_id, user_name, full_name, description, status, shop_code, center_code, granted_ip, account " +
                " from ( " +
                " select user_id, user_name, full_name, description, status, shop_code, center_code, granted_ip, account, ROWNUM AS rn " +
                " from c2_admin_user where ";
        Set<String> set = props.keySet();
        for (String key : set) {
            System.out.println(key + " " + props.get(key));
            String condition = key + " " + " = '" + props.get(key).toString() + "' and ";
            sqlClause += condition;
        }
        sqlClause += " center_code = '2' order by user_id ) " +
                " WHERE rn BETWEEN " + begin + " AND " + end;
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .getResultList());
    }

    @Override
    public int countFindByPropertySS(Map<String, Object> props) {
        String sqlClause = "select count(*) " +
                "from c2_admin_user where ";
        Set<String> set = props.keySet();
        for (String key : set) {
            System.out.println(key + " " + props.get(key));
            String condition = key + " " + " = '" + props.get(key).toString() + "' and ";
            sqlClause += condition;
        }
        sqlClause += " center_code = '2'";
        BigDecimal rs = (BigDecimal) entityManager.createNativeQuery(sqlClause)
                .getSingleResult();
        return rs.intValue();
    }

    @Override
    public List<C2UserAdminDTO> findBannedUserNNotSendExplanation() {
        return null;
    }

    private List<C2UserAdminDTO> DTOMapper(List<Object[]> objects) {

        List<C2UserAdminDTO> resultList = new ArrayList<>();

        for (Object[] o : objects) {
            C2UserAdminDTO dto = new C2UserAdminDTO();
            dto.setUserId(o[0] != null ? Long.parseLong(o[0].toString().trim()) : 0);
            dto.setUsername(o[1] != null ? o[1].toString().trim() : "");
            dto.setFullName(o[2] != null ? o[2].toString().trim() : "");
            dto.setDescription(o[3] != null ? o[3].toString().trim() : "");
            dto.setStatus(o[4] != null ? o[4].toString().trim() : "");
            dto.setShopCode(o[5] != null ? o[5].toString().trim() : "");
            dto.setCenter_code(o[6] != null ? o[6].toString().trim() : "");
            dto.setGranted_ip(o[7] != null ? o[7].toString().trim() : null);
            dto.setAccount(o[8] != null ? o[8].toString().trim() : null);
            resultList.add(dto);
        }

        return resultList;
    }
}
