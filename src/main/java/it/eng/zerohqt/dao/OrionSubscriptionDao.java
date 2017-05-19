package it.eng.zerohqt.dao;

import it.eng.zerohqt.dao.model.OrionSubscription;
import it.eng.zerohqt.dao.mapper.OrionSubscriptionMapper;
import it.eng.zerohqt.web.rest.RestServiceController;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by ascatox on 16/02/17.
 */
@Repository
public class OrionSubscriptionDao {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    OrionSubscriptionMapper orionSubscriptionMapper;

    private final Logger logger = Logger.getLogger(RestServiceController.class);

    public void insertOrionSubscription(OrionSubscription orionSubscription) {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession(true);
            OrionSubscriptionMapper mapper = sqlSession.getMapper(OrionSubscriptionMapper.class);
            mapper.insertOrionSubscription(orionSubscription);
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (null != sqlSession)
                sqlSession.close();
        }
    }

    public void disableOrionSubscriptions() {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession(true);
            OrionSubscriptionMapper mapper = sqlSession.getMapper(OrionSubscriptionMapper.class);
            mapper.disableOrionSubscriptions();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (null != sqlSession)
                sqlSession.close();
        }
    }


    public void deleteOrionSubscription(String subscriptionId) {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession(true);
            OrionSubscriptionMapper mapper = sqlSession.getMapper(OrionSubscriptionMapper.class);
            mapper.deleteOrionSubscription(subscriptionId);
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (null != sqlSession)
                sqlSession.close();
        }
    }


    public List<OrionSubscription> findEnabledSubscriptions() {
        return orionSubscriptionMapper.findEnabledSubscriptions();
    }


}
