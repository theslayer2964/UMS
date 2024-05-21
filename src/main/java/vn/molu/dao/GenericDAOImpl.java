package vn.molu.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import vn.molu.common.utils.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

    private transient final Logger logger = Logger.getLogger(getClass().toString());

    private Class<T> persistentClass;

    public GenericDAOImpl(){
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected GenericDAOImpl(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    public String getPersistentClassName() {
        return persistentClass.getSimpleName();
    }

    @Autowired
    protected EntityManager entityManager;

    /**
     *
     * @param propertyName
     * @param value
     * @param rowStartIdxAndCount
     * @return
     */
    @Override
    public List<T> findByProperty(String propertyName, Object value, int... rowStartIdxAndCount) {
        logger.log(Level.INFO, "finding T instance with property: " + propertyName + ", value: " + value);

        try {
            final String queryString = "select model from " + getPersistentClassName() + " model where model." + propertyName + "= :propertyValue";

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

    /**
     * Perform an initial save of a previously unsaved T entity. All
     * subsequent persist actions of this entity should use the #update()
     * method.
     *
     * @param entity
     *            T entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public T save(T entity) throws DuplicateKeyException {
        logger.log(Level.INFO, "saving T instance");
        try {
            entityManager.persist(entity);
            entityManager.flush();
            logger.log(Level.INFO, "save successful");
            return entity;
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "save failed", re);
            throw re;
        }
    }

    @Override
    public void save(List<T> list, int batch) {
        logger.log(Level.INFO, "saving list of T instance");
        try {
            int count = 0;
            for (T entity: list) {
                entityManager.persist(entity);
                if (++count == batch) {
                    entityManager.flush();
                    count = 0;
                }
            }
            entityManager.flush();
            logger.log(Level.INFO, "save successful");
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "save failed", re);
            throw re;
        }
    }

    @Transactional
    @Override
    public T update(T entity) {
        logger.log(Level.INFO, "updating T instance");
        try {
            T result = entityManager.merge(entity);
            logger.log(Level.INFO, "update successful");
            return result;
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "update failed", re);
            throw re;
        }
    }

    @Override
    public void delete(ID id) {
        logger.log(Level.INFO, "deleting T instance");
        try {
            T entity = entityManager.getReference(getPersistentClass(), id);
            entityManager.remove(entity);
            logger.log(Level.INFO, "delete successful");
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "delete failed", re);
            throw re;
        }
    }

    @Override
    public void delete(T entity) {
        logger.log(Level.INFO, "deleting T instance");
        try {
            entityManager.remove(entity);
            logger.log(Level.INFO, "delete successful");
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "delete failed", re);
            throw re;
        }
    }

    @Override
    public Integer deleteAll(List<T> entities) {
        logger.log(Level.INFO, "deleting list of T instances");
        Integer res = 0;
        try {
            for (T entity : entities) {
                entityManager.remove(entity);
                res++;
            }
            return res;
        }catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public Integer delete(ID[] ids) {
        logger.log(Level.INFO, "deleting T instances");
        Integer res = 0;
        for (ID id : ids) {
            try {
                T entity = entityManager.getReference(getPersistentClass(), id);
                entityManager.remove(entity);
                res++;
            } catch (RuntimeException re) {
                logger.log(Level.SEVERE, "delete failed " + id, re);
                throw re;
            }
        }
        return res;
    }

    @Override
    public List<T> findAll(int... rowStartIdxAndCount) {
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
    public T findById(ID id) {
        logger.log(Level.INFO, "finding T instance with id: " + id);
        try {
            T instance = entityManager.find(getPersistentClass(), id);
            return instance;
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "find failed", re);
            throw re;
        }
    }

    @Override
    public T findEqualUnique(String propertyName, Object value) {
        logger.log(Level.INFO, "finding unique T instance with property: " + propertyName
                + ", value: " + value);
        try {
            final String queryString = "select model from " + getPersistentClassName() + " model where model."
                    + propertyName + "= :propertyValue";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("propertyValue", value);

            return (T)query.getSingleResult();
        } catch (NoResultException re) {
            logger.log(Level.SEVERE, "find by property name failed", re);
            throw re;
        }
    }

    @Override
    public T findEqualUniqueCaseSensitive(String propertyName, Object value) {
        logger.log(Level.INFO, "finding unique T instance with property: " + propertyName
                + ", value: " + value);
        try {
            final String queryString = "select model from " + getPersistentClassName() + " model where LOWER(model."
                    + propertyName + " ) = LOWER(:propertyValue)";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("propertyValue", value);

            return (T)query.getSingleResult();
        } catch (NoResultException re) {
            logger.log(Level.SEVERE, "find by property name failed", re);
            throw re;
        }
    }

    @Override
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection,
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
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection,
                                       Integer offset, Integer limit, String whereClause, Map<String, Object> mapFilter) {
        try {
            final Object[] nameQuery = HibernateUtil.buildNameQuery(getPersistentClass(), properties, null,
                    sortExpression, sortDirection, true, false, whereClause, true);

            String queryString = "select A " + nameQuery[0] + nameQuery[1];

            Query query = entityManager.createQuery(queryString);
            if (nameQuery.length == 4) {
                String[] params = (String[]) nameQuery[2];
                Object[] values = (Object[]) nameQuery[3];
                for (int i = 0; i < params.length; i++) {

                    query.setParameter(params[i], values[i]);
                }
            }
            Set<String> setKey = mapFilter.keySet();
            for(String key: setKey) {
                query.setParameter(key, mapFilter.get(key));
            }
            if (offset != null && offset >= 0) {
                query.setFirstResult(offset);
            }
            if (limit != null && limit > 0) {
                query.setMaxResults(limit);
            }
            @SuppressWarnings("unchecked")
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
            Set<String> setKey2 = mapFilter.keySet();
            for(String key: setKey2) {
                query2.setParameter(key, mapFilter.get(key));
            }
            totalItem = query2.getSingleResult();
            return new Object[] { totalItem, res };
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "find all failed", re);
            throw re;
        }
    }

    @Override
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limit) {
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
    public List<T> searchByProperties(Map<String, Object> properties, String whereClause, Map<String, Object> mapFilter) {
        try {
            final Object[] nameQuery = HibernateUtil.buildNameQuery(getPersistentClass(), properties, null,
                    null, null, true, false, whereClause, true);

            String queryString = "select A " + nameQuery[0] + nameQuery[1];

            Query query = entityManager.createQuery(queryString);
            if (nameQuery.length == 4) {
                String[] params = (String[]) nameQuery[2];
                Object[] values = (Object[]) nameQuery[3];
                for (int i = 0; i < params.length; i++) {

                    query.setParameter(params[i], values[i]);
                }
            }
            Set<String> setKey = mapFilter.keySet();
            for(String key: setKey) {
                query.setParameter(key, mapFilter.get(key));
            }

            @SuppressWarnings("unchecked")
            List<T> res = query.getResultList();
            return res ;
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
            return totalItem;
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "count by properties failed", re);
            throw re;
        }
    }

    @Override
    public List<T> findProperty(String propertyName, Object value) {
        logger.log(Level.INFO, "finding all T instance with property: " + propertyName
                + ", value: " + value);
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
    public List<T> findProperties(Map<String, Object> properties) {
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
    public List<T> findByProperties(Map<String, Object> properties, String whereClause) {
        try {
            final Object[] nameQuery = HibernateUtil.buildNameQuery(getPersistentClass(), properties, null,
                    null, null, true, false, whereClause, true);

            String queryString = "select A " + nameQuery[0] + nameQuery[1];
            Query query = entityManager.createQuery(queryString);
            if (nameQuery.length == 4) {
                String[] params = (String[]) nameQuery[2];
                Object[] values = (Object[]) nameQuery[3];
                for (int i = 0; i < params.length; i++) {

                    query.setParameter(params[i], values[i]);
                }
            }

            @SuppressWarnings("unchecked")
            List<T> res = query.getResultList();
            return res;
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "find all failed", re);
            throw re;
        }
    }
}
