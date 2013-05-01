package ua.com.kuchyn.tmm.phonebook.persistence.dao;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.com.kuchyn.tmm.phonebook.persistence.model.IUser;
import ua.com.kuchyn.tmm.phonebook.persistence.model.UserEntity;
import ua.com.kuchyn.tmm.phonebook.service.IdGeneratorService;

@Repository
public class UserActionDao implements UserAction {

    @Autowired
    protected SessionFactory sessionFactory;

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Transactional
    public String createUser(String login, String phone) {
	UserEntity toSave = new UserEntity(login, phone);
	toSave.setId(getIdAndCheckUnique());
	UserEntity savedEntity = (UserEntity) sessionFactory
		.getCurrentSession().merge(toSave);
	return savedEntity.getId();
    }

    private String getIdAndCheckUnique() {
	String id = idGeneratorService.generateId();
	try {
	    getUsersById(id);
	    return getIdAndCheckUnique();
	} catch (EntityNotFoundException e) {
	    return id;
	}
    }

    @Transactional
    public void updateUser(String id, String login, String phone) {
	IUser user = getUsersById(id);
	user.setLogin(login);
	user.setPhone(phone);
	sessionFactory.getCurrentSession().merge(user);
    }

    @Transactional
    public void deleteUser(String id) {
	sessionFactory.getCurrentSession()
		.createQuery("delete from UserEntity where id = ?")
		.setParameter(0, id).executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<IUser> getAllUsers() {
	return sessionFactory.getCurrentSession()
		.createCriteria(UserEntity.class).list();
    }

    @Transactional
    public IUser getUsersByLogin(String login) {
	IUser result = (IUser) sessionFactory.getCurrentSession()
		.createCriteria(UserEntity.class)
		.add(Restrictions.eq("login", login)).uniqueResult();
	if (result == null) {
	    throw new EntityNotFoundException("No user was found by login "
		    + login);
	}
	return result;
    }

    @Transactional
    public IUser getUsersById(String id) {
	IUser result = (IUser) sessionFactory.getCurrentSession()
		.createCriteria(UserEntity.class)
		.add(Restrictions.eq("id", id)).uniqueResult();
	if (result == null) {
	    throw new EntityNotFoundException("No user was found by id " + id);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<IUser> findByKey(String key) {
	return sessionFactory
		.getCurrentSession()
		.createCriteria(UserEntity.class)
		.add(Restrictions.disjunction()
			.add(Restrictions.like("login", "%" + key + "%"))
			.add(Restrictions.like("phone", "%" + key + "%")))
		.list();
    }
}
