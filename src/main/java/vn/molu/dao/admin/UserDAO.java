package vn.molu.dao.admin;

import vn.molu.dao.GenericDAO;
import vn.molu.domain.admin.User;

import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-05
 */
public interface UserDAO extends GenericDAO<User, Long> {

    List<String> findUrlsByUserId(Long userId);
}
