package ua.com.kuchyn.tmm.phonebook.web.gwt.model;

import java.io.Serializable;

public class GwtUser implements Serializable {

    private String id;
    private String login;
    private String phone;

    public GwtUser() {
    }

    public GwtUser(String id, String login, String phone) {
	super();
	this.id = id;
	this.login = login;
	this.phone = phone;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

}
