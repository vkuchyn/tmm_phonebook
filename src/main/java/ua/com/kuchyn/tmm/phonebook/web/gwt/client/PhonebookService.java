package ua.com.kuchyn.tmm.phonebook.web.gwt.client;

import java.util.List;

import ua.com.kuchyn.tmm.phonebook.web.gwt.model.GwtUser;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("list")
public interface PhonebookService extends RemoteService {

    public String createUser(String login, String phone);

    public void updateUser(String id, String login, String phone);

    public void deleteUser(String id);

    public List<GwtUser> getAllUsers();

    /**
     * This method returns list of ${@link model.IUser} by appropriate search
     * key. The method should search by login, phone and partial matches.
     * 
     * @param key
     *            search key
     * @return list of ${@link model.IUser}
     */
    public List<GwtUser> findByKey(String key);
}
