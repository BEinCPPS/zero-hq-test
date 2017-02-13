package it.eng.zerohqt.dao;

import it.eng.zerohqt.dao.domain.TestStation;
import it.eng.zerohqt.dao.mapper.TestStationMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ascatox on 13/02/17.
 */
public class TestStationDao {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private TestStationMapper testStationMapper;


    public List<TestStation> findAllTestStations() {
        return null;
    }

}
