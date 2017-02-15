package it.eng.zerohqt.dao;

import it.eng.zerohqt.dao.domain.ContextAttribute;
import it.eng.zerohqt.dao.domain.NotificationState;
import it.eng.zerohqt.dao.domain.TestStationData;
import it.eng.zerohqt.dao.mapper.TestStationDataMapper;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ascatox on 13/02/17.
 */
@Repository
public class TestStationDataDao {

    @Autowired
    private TestStationDataMapper testStationDataMapper;


    public List<TestStationData> findAllNotificationsForStation(String service, String contextName) {
        return testStationDataMapper.findAllNotifications(service, contextName, ContextAttribute.state.name(),
                ContextAttribute.statePayload.name(), ContextAttribute.acknowledge.name());
    }
}
