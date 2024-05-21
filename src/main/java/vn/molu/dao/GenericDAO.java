package vn.molu.dao;

import org.springframework.dao.DuplicateKeyException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface GenericDAO<T, ID extends Serializable> {

    /**
     * Perform an initial save of a previously unsaved T entity. All subsequent
     * persist actions of this entity should use the #update() method.
     *
     * @param entity
     *            T entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public T save(T entity);

    /**
     * Persist a previously saved T entity and return it or a copy of it to the
     * sender. A copy of the T entity parameter is returned when the JPA
     * persistence mechanism has not previously been tracking the updated
     * entity.
     *
     * @param entity
     *            T entity to update
     * @return T the persisted T entity instance, may not be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public T update(T entity);

    /**
     * Delete a persistent T entity.
     *
     * @param id
     *            the ID of T entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(ID id);

    /**
     * Delete a persistent T entity.
     *
     * @param entity
     *            the T entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(T entity);

    /**
     * Delete list of persitence entity.
     *
     * @param entities
     *            the list of entities to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public Integer deleteAll(List<T> entities);

    /**
     * Delete list of persitence entity.
     *
     * @param ids
     *            the list ID of T entities to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public Integer delete(ID[] ids);

    /**
     * Find all T entities.
     *
     * @param rowStartIdxAndCount
     *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
     *            row index in the query result-set to begin collecting the
     *            results. rowStartIdxAndCount[1] specifies the the maximum
     *            count of results to return.
     * @return List<T> all T entities
     */
    public List<T> findAll(int... rowStartIdxAndCount);

    /**
     * Find a persistence T entitiy
     *
     * @param Id
     *            the ID of T entity to find.
     * @return T found by query
     */
    public T findById(ID id);

    /**
     * Find a persistence T entitiy with a field unique
     *
     * @param property
     *            the field unique of T entity to find.
     * @param value
     * 		  the value of field unique to find
     * @return T found by query
     */
    public T findEqualUnique(String property, Object value);

    /**
     * Find a persistence T entitiy with a field unique
     *
     * @param property
     *            the field unique of T entity to find.
     * @param value
     * 		  the value of field unique to find
     * @return T found by query
     */
    public T findEqualUniqueCaseSensitive(String property, Object value);

    /**
     * Find all T entities with a specific property value.
     *
     * @param propertyName
     *            the name of the T property to query
     * @param value
     *            the property value to match
     * @param rowStartIdxAndCount
     *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
     *            row index in the query result-set to begin collecting the
     *            results. rowStartIdxAndCount[1] specifies the the maximum
     *            count of results to return.
     * @return List<T> found by query
     */
    public List<T> findByProperty(String propertyName, Object value, int... rowStartIdxAndCount);

    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection,
                                       Integer offset, Integer limit, String whereClause);

    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection,
                                       Integer offset, Integer limit, String whereClause, Map<String, Object> mapFilter);

    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection,
                                       Integer offset, Integer limit);

    public List<T> searchByProperties(Map<String, Object> properties, String whereClause, Map<String, Object> mapFilter);

    /**
     * Count all records in database
     *
     * @return number of records
     */
    public Long countByProperties(Map<String, Object> properties);

    public List<T> findProperty(String property, Object value);

    public List<T> findProperties(Map<String, Object> properties);

    public void save(List<T> list, int batch);

    public List<T> findByProperties(Map<String, Object> properties, String whereClause);
}
