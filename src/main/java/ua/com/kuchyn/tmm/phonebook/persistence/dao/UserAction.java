package ua.com.kuchyn.tmm.phonebook.persistence.dao;

import java.util.List;

import ua.com.kuchyn.tmm.phonebook.persistence.model.IUser;

public interface UserAction {

    public long createUser(String login, String phone);

    public void updateUser(String id, String login, String phone);

    public void deleteUser(String id);

    public List<IUser> getAllUsers();

    public IUser getUsersByLogin(String login);

    public IUser getUsersById(String id);

    /**
     * This method returns list of ${@link model.IUser} by appropriate search
     * key. The method should search by login, phone and partial matches.
     * 
     * @param key
     *            search key
     * @return list of ${@link model.IUser}
     */
    public List<IUser> findByKey(String key);
}
