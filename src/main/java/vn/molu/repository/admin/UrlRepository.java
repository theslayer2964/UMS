package vn.molu.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.molu.domain.admin.Url;
import vn.molu.domain.admin.UrlGroup;

import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-10
 */
public interface UrlRepository extends JpaRepository<Url, Long> {

    Url findByCode(String code);
    Url findByPath(String path);

    @Query(value = "select model from Url model, UrlGroup modelUrlGroup "
            + "where modelUrlGroup.urlGroupId = model.urlGroup.urlGroupId and modelUrlGroup.flgDelete = :propertyFlagDelete and model.flgDelete = :propertyFlagDelete and modelUrlGroup.urlGroupId = :propertyUrlGroupId and model.urlId not in (select modelUserRoleUrlACL.url.urlId "
            + "from UserRoleUrlACL modelUserRoleUrlACL, UserRole modelUserRole "
            + "where modelUserRole.userRoleId = modelUserRoleUrlACL.userRole.userRoleId and modelUserRole.flgDelete = :propertyFlagDelete and modelUserRoleUrlACL.userRole.userRoleId = :propertyUserRoleId) "
            + "order by model.urlId asc")
    List<Url> findAllByUserRoleId(@Param("propertyUserRoleId") Long userRoleId,@Param("propertyUrlGroupId") Long urlGroupId,@Param("propertyFlagDelete") String flagDelete);

    @Query(value = "select model from Url model"
            + "     where model.urlId not in (select modelUserRoleUrlACL.url.urlId "
            + "                               from UserRoleUrlACL modelUserRoleUrlACL "
            + "                               where modelUserRoleUrlACL.userRole.userRoleId = :propertyUserRoleId) "
            + "     order by model.urlId asc")
    List<Url> findAllNotAssignUserRoleId(@Param("propertyUserRoleId")Long userRoleId);
}
