package vn.molu.service.admin;

import vn.molu.domain.admin.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User loadUserByUsername(String username);
    List<User> findUser(Map<String, Object> props);
    List<String> findUrlByUserId(Long userId);
    User findByEmail(String email);
    void delete(Long userId);
    List<User> findAll();
    User save(User pojo);
    User findById(Long urlGroupId);
    User update(User pojo);
    User findByUsername();
    User getUserLogin_BHTT_System();
}
