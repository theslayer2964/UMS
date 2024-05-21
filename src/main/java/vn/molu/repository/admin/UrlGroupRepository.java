package vn.molu.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.molu.domain.admin.UrlGroup;

import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-10
 */
public interface UrlGroupRepository extends JpaRepository<UrlGroup, Long> {
    UrlGroup findByCode(String code);
    @Query(value = "select distinct modelUrlGroup from Url model, UrlGroup modelUrlGroup "
            + "where modelUrlGroup.urlGroupId = model.urlGroup.urlGroupId and modelUrlGroup.flgDelete = :propertyFlagDelete and model.flgDelete = :propertyFlagDelete and model.urlId not in (select modelUserRoleUrlACL.url.urlId "
            + "from UserRoleUrlACL modelUserRoleUrlACL, UserRole modelUserRole "
            + "where modelUserRole.userRoleId = modelUserRoleUrlACL.userRole.userRoleId and modelUserRole.flgDelete = :propertyFlagDelete and modelUserRoleUrlACL.userRole.userRoleId = :propertyUserRoleId) "
            + "order by modelUrlGroup.urlGroupId asc")
    List<UrlGroup> findAllByUserRoleId(@Param("propertyUserRoleId") Long userRoleId, @Param("propertyFlagDelete") String flgDelete );
}
