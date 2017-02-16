package it.eng.zerohqt;

import it.eng.zerohqt.dao.OrionSubscriptionDao;
import it.eng.zerohqt.dao.TablesMetaDataDao;
import it.eng.zerohqt.dao.TestStationDataDao;
import it.eng.zerohqt.dao.domain.NotificationState;
import it.eng.zerohqt.dao.domain.OrionSubscription;
import it.eng.zerohqt.dao.domain.TestStationData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ZeroHqTestApplicationTests {
    @Autowired
    private TablesMetaDataDao tablesMetaDataDao;
    @Autowired
    private TestStationDataDao testStationDataDao;
    @Autowired
    private OrionSubscriptionDao orionSubscriptionDao;

    private static final String DEFAULT_SERVICE = "whrtestservice";

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetTablesMetadata() {
        List<String> tablesMetaData = tablesMetaDataDao.getTablesMetaData(DEFAULT_SERVICE);
        assertNotNull(tablesMetaData);
        assertTrue(!tablesMetaData.isEmpty());
    }

    @Test
    public void testGetAllNotificationsForStates() {
        List<String> states = new ArrayList<>();
        states.add(NotificationState.state400.getValue());
        List<TestStationData> notifications = testStationDataDao.findAllNotificationsForStationBayByStates(
                DEFAULT_SERVICE,
                "whrTestsubservice_teststation_TestStation1_1_teststation",
                states);
        assertNotNull(notifications);
        assertTrue(!notifications.isEmpty());
    }


    @Test
    public void testGetAllNotificationsFor400And600() {
        List<TestStationData> notifications = testStationDataDao.findAllNotificationsForStationBay(DEFAULT_SERVICE,
                "whrTestsubservice_teststation_TestStation1_1_teststation");
        assertNotNull(notifications);
        assertTrue(!notifications.isEmpty());
    }

    @Test
    public void testDisableOrionSubscriptions() {
        orionSubscriptionDao.disableOrionSubscriptions();
    }

    @Test
    public void testInsertOrionSubscription(){
        try {
            OrionSubscription orionSubscription = new OrionSubscription();
            orionSubscription.setSubscriptionId("5050505");
            orionSubscription.setCreationDate(new Date());
            orionSubscription.setBoolEnabled(true);
            orionSubscriptionDao.insertOrionSubscription(orionSubscription);
            orionSubscriptionDao.deleteOrionSubscription(orionSubscription.getSubscriptionId());
            assertTrue(true);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

    }


}

