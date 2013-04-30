package ua.com.kuchyn.tmm.phonebook.persistence.dao;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(value = { "hibernate-context.xml" })
public class UserDaoIntegrationTest extends
	AbstractTransactionalJUnit4SpringContextTests {

}
