package ua.com.kuchyn.tmm.phonebook.service;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verify;

import java.util.UUID;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ UUID.class, IdGeneratorService.class })
public class IdGeneratorServiceUnitTest {

    private IdGeneratorService idGeneratorService = new IdGeneratorService();

    @Test
    public void testGenerateId() {
	UUID expectedUuid = new UUID(3, 4);
	expectGenerateIdWithUUID(expectedUuid);
	String id = idGeneratorService.generateId();
	assertEquals(expectedUuid.toString(), id);
    }

    private void expectGenerateIdWithUUID(UUID expectedUuid) {
	mockStatic(UUID.class);
	expect(UUID.randomUUID()).andReturn(expectedUuid);
	replayAll();
    }

    @After
    public void verifyMocks() {
	verify(UUID.class);
    }
}
