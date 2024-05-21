package vn.molu.dao.admin.impl;

import org.springframework.stereotype.Repository;

import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.admin.UserRoleUrlACLDAO;
import vn.molu.domain.admin.UserRoleUrlACL;

@Repository
public class UserRoleUrlACLDAOImpl extends GenericDAOImpl<UserRoleUrlACL, Long> implements UserRoleUrlACLDAO {

}
