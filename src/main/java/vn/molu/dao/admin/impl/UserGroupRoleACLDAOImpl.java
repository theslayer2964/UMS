package vn.molu.dao.admin.impl;

import org.springframework.stereotype.Repository;
import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.admin.UserGroupRoleACLDAO;
import vn.molu.domain.admin.UserGroupRoleACL;

@Repository
public class UserGroupRoleACLDAOImpl extends GenericDAOImpl<UserGroupRoleACL, Long>	implements UserGroupRoleACLDAO {

}
