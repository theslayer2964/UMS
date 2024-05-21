package vn.molu.dao.admin.impl;

import org.springframework.stereotype.Repository;

import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.admin.UserRoleACLDAO;
import vn.molu.domain.admin.UserRoleACL;

@Repository
public class UserRoleACLDAOImpl extends GenericDAOImpl<UserRoleACL, Long> implements UserRoleACLDAO {

}
