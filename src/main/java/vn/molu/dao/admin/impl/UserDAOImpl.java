package vn.molu.dao.admin.impl;

import org.springframework.stereotype.Repository;
import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.admin.UserDAO;
import vn.molu.domain.admin.User;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author phaolo
 * @since 2020-03-05
 */
@Repository
public class UserDAOImpl extends GenericDAOImpl<User, Long> implements UserDAO {

    private final Logger logger = Logger.getLogger(getClass().toString());


    @SuppressWarnings("unchecked")
    @Override
    public List<String> findUrlsByUserId(Long userId) {
        logger.log(Level.INFO, "finding all code of URL instances with userId: " + userId);
        try {
            final StringBuffer sqlClause = new StringBuffer();
            sqlClause.append(" select distinct u.code ");
            sqlClause.append(" from ad_user_role_acl ura, ad_user_role_url_acl urua, ad_url u ");
            sqlClause.append(" where     ura.user_id = :userId ");
            sqlClause.append("       and ura.user_role_id = urua.user_role_id ");
            sqlClause.append("       and urua.url_id = u.url_id ");
            sqlClause.append(" union ");
            sqlClause.append(" select distinct u.code ");
            sqlClause.append(" from ad_user us,ad_user_group_role_acl ugra, ad_user_role_url_acl urua, ad_url u ");
            sqlClause.append(" where     us.user_id = :userId ");
            sqlClause.append("       and us.user_group_id = ugra.user_group_id ");
            sqlClause.append("       and ugra.user_role_id = urua.user_role_id ");
            sqlClause.append("       and urua.url_id = u.url_id ");

            Query queryClause = entityManager.createNativeQuery(sqlClause.toString());
            queryClause.setParameter("userId", userId);

            return queryClause.getResultList();
        } catch (NoResultException re) {
            logger.log(Level.SEVERE, "finding all code of URL instances failed", re);
            throw re;
        } catch (Exception e) {
            throw e;
        }
    }


}
