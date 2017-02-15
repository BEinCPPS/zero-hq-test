package it.eng.zerohqt.dao;

import it.eng.zerohqt.dao.mapper.TablesMetaDataMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ascatox on 14/02/17.
 */
public class TablesMetaDataDao {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    public List<String> getTablesMetaData(String service) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TablesMetaDataMapper tablesMetaDataMapper = sqlSession.getMapper(TablesMetaDataMapper.class);
        return tablesMetaDataMapper.getTablesMetaData(service.toLowerCase());
    }


}
