package vn.molu.dao.temp.impl;

import org.springframework.stereotype.Repository;
import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.temp.Resum_UserDAO;
import vn.molu.dto.admin.admin.Resum_UserDTO;

import javax.transaction.Transactional;

@Repository
public class Resum_UserDAOImpl extends GenericDAOImpl<Resum_UserDTO, String> implements Resum_UserDAO {
    @Override
    @Transactional
    public Integer insert(Resum_UserDTO resumUserDTO) {
        String sqlClause = "{ call sonbn.pr_add_user (?1) }";
        return entityManager.createNativeQuery(sqlClause)
                .setParameter(1, resumUserDTO.getUser_tracuu())
                .executeUpdate();
    }

    @Override
    @Transactional
    public Integer lockUser(String userName) {
        try {
            String sqlClause = "{ call LOCK_USER_RESNUM (?1) }";
            return entityManager.createNativeQuery(sqlClause)
                    .setParameter(1, userName)
                    .executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }
}
