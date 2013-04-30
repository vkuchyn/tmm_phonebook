package ua.com.kuchyn.tmm.phonebook.persistence.model;

public interface IUser {

    public String getId();

    public String getLogin();

    public String getPhone();

    public void setId(String id);

    public void setLogin(String login);

    public void setPhone(String phone);

}
