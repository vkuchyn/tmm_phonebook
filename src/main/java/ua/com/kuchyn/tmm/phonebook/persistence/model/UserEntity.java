package ua.com.kuchyn.tmm.phonebook.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity implements IUser {

    @Id
    private String id;

    private String login;
    private String phone;

    public UserEntity() {
    }

    public UserEntity(String login, String phone) {
	super();
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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((login == null) ? 0 : login.hashCode());
	result = prime * result + ((phone == null) ? 0 : phone.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	UserEntity other = (UserEntity) obj;
	if (login == null) {
	    if (other.login != null)
		return false;
	} else if (!login.equals(other.login))
	    return false;
	if (phone == null) {
	    if (other.phone != null)
		return false;
	} else if (!phone.equals(other.phone))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "UserEntity [id=" + id + ", login=" + login + ", phone=" + phone
		+ "]";
    }

}
