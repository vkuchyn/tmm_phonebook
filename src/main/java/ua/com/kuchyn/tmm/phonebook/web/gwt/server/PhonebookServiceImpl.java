package ua.com.kuchyn.tmm.phonebook.web.gwt.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ua.com.kuchyn.tmm.phonebook.persistence.dao.UserAction;
import ua.com.kuchyn.tmm.phonebook.persistence.model.IUser;
import ua.com.kuchyn.tmm.phonebook.service.SpringContextHolder;
import ua.com.kuchyn.tmm.phonebook.web.gwt.client.PhonebookService;
import ua.com.kuchyn.tmm.phonebook.web.gwt.model.GwtUser;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class PhonebookServiceImpl extends RemoteServiceServlet implements
	PhonebookService {

    private UserAction userAction = SpringContextHolder.getUserAction();

    List<GwtUser> userList;
    private Logger logger = Logger.getLogger(getClass().getName());

    public PhonebookServiceImpl() {

    }

    @Override
    public List<GwtUser> getAllUsers() {
	return convertUserEntityListToGwtUserList(userAction.getAllUsers());
    }

    @Override
    public void deleteUser(String id) {
	userAction.deleteUser(id);
    }

    @Override
    public String createUser(String login, String phone) {
	String id = userAction.createUser(login, phone);
	logger.info("User " + login + " was created");
	return id;
    }

    @Override
    public void updateUser(String id, String login, String phone) {
	// TODO Auto-generated method stub

    }

    @Override
    public List<GwtUser> findByKey(String key) {
	return convertUserEntityListToGwtUserList(userAction.findByKey(key));
    }

    private List<GwtUser> convertUserEntityListToGwtUserList(
	    List<IUser> userEntityList) {
	List<GwtUser> res = new ArrayList<GwtUser>();
	for (IUser user : userEntityList) {
	    res.add(convertUserEntityToGwtUser(user));
	}

	return res;
    }

    private GwtUser convertUserEntityToGwtUser(IUser userEntity) {
	return new GwtUser(userEntity.getId(), userEntity.getLogin(),
		userEntity.getPhone());
    }

}
