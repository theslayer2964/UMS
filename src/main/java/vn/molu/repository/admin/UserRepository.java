package vn.molu.repository.admin;

import org.springframework.data.jpa.repository.Query;
import vn.molu.domain.admin.User;
import vn.molu.repository.common.MyRepository;

import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-09
 */
public interface UserRepository extends MyRepository<User, Long> {

    public User getUserByUserName(String userName);

    User findByEmail(String email);

    @Query(value = "select u from  User  u join u.userGroup g where (:displayName is null or u.displayName =:displayName) and (:accountType is null or u.accountType =:accountType) and (:userGroupId is null or g.userGroupId =:userGroupId)  ")
    List<User> findUser(String displayName,Integer accountType,Long userGroupId);

    @Query(value = "select u from User u where u.userName =:username")
    User findByName(String username);
}
