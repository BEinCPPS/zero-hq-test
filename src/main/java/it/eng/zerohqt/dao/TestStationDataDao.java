package it.eng.zerohqt.dao;

import it.eng.zerohqt.orion.model.TestStationContextAttribute;
import it.eng.zerohqt.dao.model.TestStationData;
import it.eng.zerohqt.dao.mapper.TestStationDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ascatox on 13/02/17.
 */
@Repository
public class TestStationDataDao {

    @Autowired
    private TestStationDataMapper testStationDataMapper;


    public List<TestStationData> findAllNotificationsForStationBay(String service, String contextName) {
        return testStationDataMapper.findAllAcknowledgesByStationBay(service, contextName,  TestStationContextAttribute.acknowledge.name());
    }

    public List<TestStationData> findAllNotificationsForStationBayByAck(String service, String contextName, String ackType) {
        return testStationDataMapper.findAcknowledgesByStationBayByAck(service, contextName,  TestStationContextAttribute.acknowledge.name(), ackType);
    }


    public List<TestStationData> findAllNotificationsForStationBayByStatesAndAck(String service, String contextName, List<String> states) {
        return testStationDataMapper.finAllNotificationsByStates(service, contextName, TestStationContextAttribute.state.name(),
                TestStationContextAttribute.statePayload.name(), TestStationContextAttribute.acknowledge.name(), states);
    }
}