package ua.com.kuchyn.tmm.phonebook.web.gwt.client;

import java.util.List;

import ua.com.kuchyn.tmm.phonebook.web.gwt.model.GwtUser;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PhonebookServiceAsync {

    void updateUser(String id, String login, String phone,
	    AsyncCallback<Void> callback);

    public void createUser(String login, String phone,
	    AsyncCallback<String> callback);

    public void deleteUser(String id, AsyncCallback<Void> callback);

    public void getAllUsers(AsyncCallback<List<GwtUser>> callback);

    /**
     * This method returns list of ${@link model.IUser} by appropriate search
     * key. The method should search by login, phone and partial matches.
     * 
     * @param key
     *            search key
     * @return list of ${@link model.IUser}
     */
    public void findByKey(String key, AsyncCallback<List<GwtUser>> callback);

}
