package vn.molu.dao;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.molu.dto.admin.admin.C2UserDTO;

@Repository
public class CommonDAOImpl implements CommonDAO {

    private transient static Logger logger = Logger.getLogger("CommonDAOImpl");

    @Autowired
    private EntityManager entityManager;

    @Override
    public C2UserDTO findActiveUserByUserName(String username) {
        logger.log(Level.INFO, "Find a C2_User_Admin with usermame " + username);

        try {
            final StringBuffer queryString = new StringBuffer();
            queryString.append(" select user_id, user_name, full_name, status, shop_code, account, password ");
            queryString.append(" from c2_admin_user@SMS_CRM_OWNER_BCSXKD_DBL c ");
            queryString.append(" where user_name = :userName and status = '1' ");

            Query query = entityManager.createNativeQuery(queryString.toString());
            query.setParameter("userName", username.toUpperCase().trim());

            List<Object[]> tmpCT2 = (List<Object[]>) query.getResultList();

            if (tmpCT2 != null&&tmpCT2.size()>0) {
                C2UserDTO userDTO = new C2UserDTO();
                Object[] tmpArr=tmpCT2.get(0);
                userDTO.setUserId(tmpArr[0] != null ? Long.parseLong(tmpArr[0].toString().trim()) : null);
                userDTO.setUserName(tmpArr[1] != null ? tmpArr[1].toString().trim() : null);
                userDTO.setFullName(tmpArr[2] != null ? tmpArr[2].toString().trim() : null);
                userDTO.setStatus(tmpArr[3] != null ? tmpArr[3].toString().trim() : null);
                userDTO.setShopCode(tmpArr[4] != null ? tmpArr[4].toString().trim() : null);
                userDTO.setAccount(tmpArr[5] != null ? tmpArr[5].toString().trim() : null);
                userDTO.setPassword(tmpArr[6] != null ? tmpArr[6].toString().trim() : null);

                return userDTO;
            }
        } catch (NoResultException re) {
            logger.log(Level.SEVERE, "find list of Employee failed", re);
            re.printStackTrace();
            throw new ObjectNotFoundException(null, "find list of Employee failed");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
