package it.eng.zerohqt.dao;

import it.eng.zerohqt.dao.model.TestStationData;
import it.eng.zerohqt.dao.mapper.TablesMetaDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ascatox on 14/02/17.
 */
@Repository
public class TestStationDao {
    @Autowired
    private TablesMetaDataMapper tablesMetaDataMapper;
    @Autowired
    private TestStationDataDao testStationDataDao;
    private static final String TEST_STATION = "teststation";

    public List<TestStationData> findAllNotifications(String service) throws Exception {
        List<String> tablesMetaData = tablesMetaDataMapper.getTablesMetaData(service);
        if (null == tablesMetaData || tablesMetaData.size() == 0)
            throw new Exception("No tables metadata found on database :-(");
        List<TestStationData> notificationsList = new ArrayList<>();
        tablesMetaData = findTestStationMetadata(tablesMetaData);
        for (String tableMetadata :
                tablesMetaData) {
            List<TestStationData> allNotificationsForStation = testStationDataDao
                    .findAllNotificationsForStationBayByAck(service, tableMetadata);
            notificationsList.addAll(allNotificationsForStation);
        }
        return notificationsList.stream().sorted().collect(Collectors.toList());
    }

    public List<TestStationData> findNextNotifications(String service, int x0, int delta) throws Exception {
        List<String> tablesMetaData = tablesMetaDataMapper.getTablesMetaData(service);
        if (null == tablesMetaData || tablesMetaData.size() == 0)
            throw new Exception("No tables metadata found on database :-(");
        List<TestStationData> notificationsList = new ArrayList<>();
        tablesMetaData = findTestStationMetadata(tablesMetaData);
        for (String tableMetadata :
                tablesMetaData) {
            List<TestStationData> allNotificationsForStation = testStationDataDao
                    .findAllNotificationsForStationBayByAck(service, tableMetadata);
            // notificationsList.addAll(allNotificationsForStation);
            for (int i = x0; i < (x0 + delta); i++) {
                notificationsList.add(allNotificationsForStation.get(i));
            }
        }
        return notificationsList.stream().sorted().collect(Collectors.toList());
    }

    /**
     * Find only table name containing TEST_STATION
     *
     * @param tableMetadatas
     * @return
     */
    private List<String> findTestStationMetadata(List<String> tableMetadatas) {
        return tableMetadatas.stream().filter(mtd -> mtd.toLowerCase().contains(TEST_STATION))
                .collect(Collectors.toList());
    }


}
