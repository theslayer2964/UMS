package vn.molu.dao.admin;

import vn.molu.dao.GenericDAO;
import vn.molu.domain.admin.UserRole;

import java.util.List;

public interface UserRoleDAO extends GenericDAO<UserRole, Long> {

    List<UserRole> findAllByUserId(Long userId, String queryNotExists);

}
