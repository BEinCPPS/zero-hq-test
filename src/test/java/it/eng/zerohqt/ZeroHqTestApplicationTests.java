package it.eng.zerohqt;

import it.eng.zerohqt.dao.domain.NotificationState;
import it.eng.zerohqt.dao.domain.TestStationData;
import it.eng.zerohqt.dao.mapper.TablesMetaDataMapper;
import it.eng.zerohqt.dao.mapper.TestStationDataMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ZeroHqTestApplicationTests {
    @Autowired
    private TablesMetaDataMapper tablesMetaDataMapper;
    @Autowired
    private TestStationDataMapper testStationDataMapper;

    private static final String DEFAULT_SERVICE = "whrtestservice";

    @Test
    public void contextLoads() {
    }

    @Test
    public void getTablesMetadata() {
        List<String> tablesMetaData = tablesMetaDataMapper.getTablesMetaData(DEFAULT_SERVICE);
        assertNotNull(tablesMetaData);
        assertTrue(!tablesMetaData.isEmpty());
    }

    @Test
    public void getAllNotificationsForStates() {
        List<TestStationData> notifications = testStationDataMapper.finAllNotificationsByStates(DEFAULT_SERVICE,
                "whrTestsubservice_teststation_TestStation1_1_teststation",
                "state",
                "statePayload",
                "acknowledge",
                NotificationState.getValueStates());
        assertNotNull(notifications);
        assertTrue(!notifications.isEmpty());
    }


    @Test
    public void getAllNotificationsFor400And600() {
        List<TestStationData> notifications = testStationDataMapper.findAllNotifications(DEFAULT_SERVICE,
                "whrTestsubservice_teststation_TestStation1_1_teststation",
                "state",
                "statePayload",
                "acknowledge");
        assertNotNull(notifications);
        assertTrue(!notifications.isEmpty());

    }

}
