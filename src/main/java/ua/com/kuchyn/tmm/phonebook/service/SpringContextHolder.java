package ua.com.kuchyn.tmm.phonebook.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ua.com.kuchyn.tmm.phonebook.persistence.dao.UserAction;

public class SpringContextHolder {

    private static ApplicationContext ctx;

    static {
	ctx = new ClassPathXmlApplicationContext("classpath:context.xml");
    }

    public static UserAction getUserAction() {
	return ctx.getBean(UserAction.class);
    }

}
