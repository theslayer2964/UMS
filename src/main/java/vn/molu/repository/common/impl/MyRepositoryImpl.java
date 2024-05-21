package vn.molu.repository.common.impl;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import vn.molu.common.utils.HibernateUtil;
import vn.molu.repository.common.MyRepository;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements MyRepository<T, ID> {

    private final Logger logger = Logger.getLogger(getClass().toString());

    private final EntityManager entityManager;
    private final JpaEntityInformation<T, ID> entityInformation;

    public MyRepositoryImpl(JpaEntityInformation<T,ID> entityInformation, EntityManager entityManager) {

        super(entityInformation, entityManager);

        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
    }

    public Class<T> getPersistentClass() {
        return entityInformation.getJavaType();
    }

    public String getPersistentClassName() {
        return entityInformation.getEntityName();
    }


    @Override
    @Transactional
    public void refresh(T t) {
        entityManager.refresh(t);
    }

    @Override
    public List<T> findByStartAndCount(int... rowStartIdxAndCount) {
        logger.log(Level.INFO, "finding all T instances " + getPersistentClassName());
        try {
            final String queryString = "select model from " + getPersistentClassName() + " model";
            Query query = entityManager.createQuery(queryString);

            if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
                int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
                if (rowStartIdx > 0) {
                    query.setFirstResult(rowStartIdx);
                }
                if (rowStartIdxAndCount.length > 1) {
                    int rowCount = Math.max(0, rowStartIdxAndCount[1]);
                    if (rowCount > 0) {
                        query.setMaxResults(rowCount);
                    }
                }
            }
            return query.getResultList();
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "find all failed", re);
            throw re;
        }
    }

    @Override
    public T findEqualUnique(String propertyName, Object propertyValue) throws NoResultException {
        logger.log(Level.INFO, "finding unique T instance with property: " + propertyName + ", value: " + propertyValue);
        try {
            final String queryString = "select model from " + getPersistentClassName() + " model where model."
                    + propertyName + "= :propertyValue";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("propertyValue", propertyValue);

            return (T)query.getSingleResult();
        } catch (NoResultException re) {
            logger.log(Level.SEVERE, "find by property name failed", re);
            throw new NoResultException("Not found object " + propertyName + " with value " + propertyValue);
        }
    }

    @Override
    public T findEqualUniqueCaseSensitive(String propertyName, Object propertyValue) throws NoResultException {
        logger.log(Level.INFO, "finding unique T instance with property: " + propertyName + ", value: " + propertyValue);
        try {
            final String queryString = "select model from " + getPersistentClassName() + " model where LOWER(model."
                    + propertyName + " ) = LOWER(:propertyValue)";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("propertyValue", propertyValue);

            return (T)query.getSingleResult();
        } catch (NoResultException re) {
            logger.log(Level.SEVERE, "find by property name failed", re);
            throw new NoResultException("Not found object " + propertyName + " with value " + propertyValue);
        }
    }

    @Override
    public List<T> findByProperty(String propertyName, Object value) {
        logger.log(Level.INFO, "finding all T instance with property: " + propertyName + ", value: " + value);
        try {
            final String queryString = "select model from " + getPersistentClassName() + " model where model."
                    + propertyName + "= :propertyValue";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("propertyValue", value);

            return query.getResultList();
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "find by property name failed", re);
            throw re;
        }
    }

    @Override
    public List<T> findByProperty(String propertyName, Object value, int... rowStartIdxAndCount) {
        logger.log(Level.INFO, "finding T instance with property: " + propertyName + ", value: " + value);
        try {
            final String queryString = "select model from " + getPersistentClassName() + " model where model."
                    + propertyName + "= :propertyValue";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("propertyValue", value);

            if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
                int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
                if (rowStartIdx > 0) {
                    query.setFirstResult(rowStartIdx);
                }
                if (rowStartIdxAndCount.length > 1) {
                    int rowCount = Math.max(0, rowStartIdxAndCount[1]);
                    if (rowCount > 0) {
                        query.setMaxResults(rowCount);
                    }
                }
            }
            return query.getResultList();
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "find by property name failed", re);
            throw re;
        }
    }

    @Override
    public List<T> findByProperties(Map<String, Object> properties) {
        try {
            final Object[] nameQuery = HibernateUtil.buildNameQuery(getPersistentClass(), properties, null, null,
                    null, true, true, null, false);

            String queryString = "select A " + nameQuery[0] + nameQuery[1];
            Query query = entityManager.createQuery(queryString);

            if (nameQuery.length == 4) {
                String[] params = (String[]) nameQuery[2];
                Object[] values = (Object[]) nameQuery[3];
                for (int i = 0; i < params.length; i++) {

                    query.setParameter(params[i], values[i]);
                }
            }

            List<T> res = query.getResultList();
            return  res;
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "find all failed", re);
            throw re;
        }
    }

    @Override
    public List<T> findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection,
                                    Integer offset, Integer limit){
        try {
            final Object[] nameQuery = HibernateUtil.buildNameQuery(getPersistentClass(), properties, null, sortExpression,
                    sortDirection, true, true, null, false);

            String queryString = "select A " + nameQuery[0] + nameQuery[1];
            Query query = entityManager.createQuery(queryString);

            if (nameQuery.length == 4) {
                String[] params = (String[]) nameQuery[2];
                Object[] values = (Object[]) nameQuery[3];
                for (int i = 0; i < params.length; i++) {

                    query.setParameter(params[i], values[i]);
                }
            }
            if (offset != null && offset >= 0) {
                query.setFirstResult(offset);
            }
            if (limit != null && limit > 0) {
                query.setMaxResults(limit);
            }
            return query.getResultList();
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "find all failed", re);
            throw re;
        }
    }

    @Override
    public Object[] findByPropertiesAndPaging(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        try {
            final Object[] nameQuery = HibernateUtil.buildNameQuery(getPersistentClass(), properties, null, sortExpression,
                    sortDirection, true, false, null, true);

            String queryString = "select A " + nameQuery[0] + nameQuery[1];
            Query query = entityManager.createQuery(queryString);

            if (nameQuery.length == 4) {
                String[] params = (String[]) nameQuery[2];
                Object[] values = (Object[]) nameQuery[3];
                for (int i = 0; i < params.length; i++) {

                    query.setParameter(params[i], values[i]);
                }
            }
            if (offset != null && offset >= 0) {
                query.setFirstResult(offset);
            }
            if (limit != null && limit > 0) {
                query.setMaxResults(limit);
            }
            List<T> res = query.getResultList();

            Object totalItem = 0;
            String queryTotal = "SELECT COUNT(*) " + nameQuery[0];

            Query query2 = entityManager.createQuery(queryTotal);
            if (nameQuery.length == 4) {
                String[] params = (String[]) nameQuery[2];
                Object[] values = (Object[]) nameQuery[3];
                for (int i = 0; i < params.length; i++) {

                    query2.setParameter(params[i], values[i]);
                }
            }
            totalItem = query2.getSingleResult();
            return new Object[]{totalItem, res};
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "find all failed", re);
            throw re;
        }
    }

    @Override
    public Object[] findByPropertiesAndPaging(Map<String, Object> properties, String sortExpression, String sortDirection,
                                              Integer offset, Integer limit, String whereClause) {
        try {
            final Object[] nameQuery = HibernateUtil.buildNameQuery(getPersistentClass(), properties, null, sortExpression,
                    sortDirection, true, false, whereClause, true);

            String queryString = "select A " + nameQuery[0] + nameQuery[1];
            Query query = entityManager.createQuery(queryString);

            if (nameQuery.length == 4) {
                String[] params = (String[]) nameQuery[2];
                Object[] values = (Object[]) nameQuery[3];
                for (int i = 0; i < params.length; i++) {

                    query.setParameter(params[i], values[i]);
                }
            }
            if (offset != null && offset >= 0) {
                query.setFirstResult(offset);
            }
            if (limit != null && limit > 0) {
                query.setMaxResults(limit);
            }
            List<T> res = query.getResultList();
            Object totalItem = 0;
            String queryTotal = "SELECT COUNT(*) " + nameQuery[0];

            Query query2 = entityManager.createQuery(queryTotal);
            if (nameQuery.length == 4) {
                String[] params = (String[]) nameQuery[2];
                Object[] values = (Object[]) nameQuery[3];
                for (int i = 0; i < params.length; i++) {

                    query2.setParameter(params[i], values[i]);
                }
            }
            totalItem = query2.getSingleResult();
            return new Object[]{totalItem, res};
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "find all failed", re);
            throw re;
        }
    }

    @Override
    public Long countByProperties(Map<String, Object> properties) {
        Long totalItem = 0L;
        try {
            final Object[] nameQuery = HibernateUtil.buildNameQuery(getPersistentClass(), properties, null, null,
                    null, true, false, null, true);
            String queryTotal = "SELECT COUNT(*) " + nameQuery[0] + nameQuery[1];
            Query query = entityManager.createQuery(queryTotal);
            if (nameQuery.length == 4) {
                String[] params = (String[]) nameQuery[2];
                Object[] values = (Object[]) nameQuery[3];
                for (int i = 0; i < params.length; i++) {

                    query.setParameter(params[i], values[i]);
                }
            }
            totalItem = (Long)query.getSingleResult();
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "count by properties failed", re);
        }
        return totalItem;
    }
}
