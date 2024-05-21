package vn.molu.dao.admin.impl;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Repository;

import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.admin.UserRoleDAO;
import vn.molu.domain.admin.UserRole;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class UserRoleDAOImpl extends GenericDAOImpl<UserRole, Long> implements UserRoleDAO {

    private final Logger logger = Logger.getLogger(getClass().toString());

    @Override
    public List<UserRole> findAllByUserId(Long userId, String queryNotExists) {
        logger.log(Level.INFO, "finding all UserRole instance with userId: " + userId);
        try {
            final StringBuffer sqlClause = new StringBuffer();
            sqlClause.append("select model from UserRole model where " + queryNotExists +
                    "(select 1 from UserRoleACL where userRole.userRoleId = model.userRoleId and user.userId  = :userId) ");
            Query queryClause = entityManager.createNativeQuery(sqlClause.toString());
            queryClause.setParameter("userId", userId);
            List<UserRole> list = queryClause.getResultList();
            return list;
        } catch (NoResultException re) {
            logger.log(Level.INFO, "finding all UserRole instance with userId and userGroupId failed", re);
            throw new ObjectNotFoundException(null, "Not found object userId: " + userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
