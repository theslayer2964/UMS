package vn.molu.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface MyRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    void refresh(T t);

    List<T> findByStartAndCount(int... rowStartIdxAndCount);

    T findEqualUnique(String property, Object value) throws NoResultException;

    T findEqualUniqueCaseSensitive(String property, Object value) throws NoResultException;

    List<T> findByProperty(String property, Object value);

    List<T> findByProperty(String propertyName, Object value, int... rowStartIdxAndCount);

    List<T> findByProperties(Map<String, Object> properties);

    List<T> findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection,
                             Integer offset, Integer limit);

    Object[] findByPropertiesAndPaging(Map<String, Object> properties, String sortExpression, String sortDirection,
                              Integer offset, Integer limit);

    Object[] findByPropertiesAndPaging(Map<String, Object> properties, String sortExpression, String sortDirection,
                              Integer offset, Integer limit, String whereClause);

    Long countByProperties(Map<String, Object> properties);
}
