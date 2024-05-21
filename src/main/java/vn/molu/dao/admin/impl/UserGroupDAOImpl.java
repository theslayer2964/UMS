package vn.molu.dao.admin.impl;

import org.springframework.stereotype.Repository;

import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.admin.UserGroupDAO;
import vn.molu.domain.admin.UserGroup;

@Repository
public class UserGroupDAOImpl extends GenericDAOImpl<UserGroup, Long> implements UserGroupDAO {

}
