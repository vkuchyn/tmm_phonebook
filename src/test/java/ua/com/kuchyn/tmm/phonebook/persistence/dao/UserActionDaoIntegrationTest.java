package ua.com.kuchyn.tmm.phonebook.persistence.dao;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import javax.persistence.EntityNotFoundException;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ua.com.kuchyn.tmm.phonebook.persistence.model.IUser;
import ua.com.kuchyn.tmm.phonebook.persistence.model.UserEntity;
import ua.com.kuchyn.tmm.phonebook.service.IdGeneratorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:test-service-context.xml",
	"classpath:hibernate-context.xml" })
public class UserActionDaoIntegrationTest extends
	AbstractTransactionalJUnit4SpringContextTests {

    private static final String UNIQUE_ID = "Unique password";
    private static final String MOCK_ID_GENERATED = "mockIdGenerated";
    private static final String TEST_PHONE = "testPhone";
    private static final String TEST_LOGIN = "testUser";

    @Autowired
    private UserAction userAction;
    @Autowired
    private SessionFactory sessFactory;
    @Autowired
    private IdGeneratorService idGeneratorServiceMock;

    @Before
    public void cleanUpDb() {
	sessFactory.getCurrentSession().createQuery("delete from UserEntity")
		.executeUpdate();
    }

    @Test
    @DirtiesContext
    public void testCreateUser() {
	String id = insertUser(UNIQUE_ID, TEST_LOGIN, TEST_PHONE);
	assertEntityWasSaved(id, TEST_LOGIN, TEST_PHONE);
    }

    @Test
    @DirtiesContext
    public void testCreateUserWhenAmbiguousIdGenerated() {
	expect(idGeneratorServiceMock.generateId())
		.andReturn(MOCK_ID_GENERATED).times(2);
	expect(idGeneratorServiceMock.generateId()).andReturn(UNIQUE_ID);
	replayAllMocks();
	String existingUserId = userAction.createUser(TEST_LOGIN, TEST_PHONE);
	String insertedUserId = userAction.createUser(TEST_LOGIN, TEST_PHONE);
	assertEntityWasSaved(existingUserId, TEST_LOGIN, TEST_PHONE);
	assertEntityWasSaved(insertedUserId, TEST_LOGIN, TEST_PHONE);
    }

    @Test
    @DirtiesContext
    public void testGetUserById() {
	insertUser(MOCK_ID_GENERATED, TEST_LOGIN, TEST_PHONE);
	IUser user = userAction.getUsersById(MOCK_ID_GENERATED);
	assertEquals(MOCK_ID_GENERATED, user.getId());
	assertEquals(new UserEntity(TEST_LOGIN, TEST_PHONE), user);
    }

    @Test(expected = EntityNotFoundException.class)
    @DirtiesContext
    public void testGetUserByIdAndGetEntityNotFoundException() {
	replayAllMocks();
	userAction.getUsersById(UNIQUE_ID);
    }

    private String insertUser(String id, String login, String phone) {
	expect(idGeneratorServiceMock.generateId()).andReturn(id);
	replayAllMocks();
	String savedId = userAction.createUser(login, phone);
	assertEquals(id, savedId);
	return savedId;
    }

    private void assertEntityWasSaved(String id, String login, String phone) {
	UserEntity user = (UserEntity) sessFactory.getCurrentSession()
		.createCriteria(UserEntity.class)
		.add(Restrictions.eq("id", id)).uniqueResult();
	assertEquals(login, user.getLogin());
	assertEquals(phone, user.getPhone());
    }

    private void replayAllMocks() {
	replay(idGeneratorServiceMock);
    }

    @After
    public void verifyAllMocks() {
	verify(idGeneratorServiceMock);
    }
}
