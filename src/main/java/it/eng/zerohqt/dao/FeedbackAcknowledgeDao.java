package it.eng.zerohqt.dao;

import it.eng.zerohqt.business.model.Acknowledge;
import it.eng.zerohqt.business.model.FeedbackAcknowledge;
import it.eng.zerohqt.dao.mapper.FeedbackAcknowledgeMapper;
import it.eng.zerohqt.dao.mapper.UserAccessMapper;
import it.eng.zerohqt.dao.model.UserAccess;
import it.eng.zerohqt.web.rest.RestServiceController;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by ascatox on 09/05/17.
 */
@Repository
public class FeedbackAcknowledgeDao {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    private final Logger logger = Logger.getLogger(RestServiceController.class);


    public void insertFeedbackAcknowledge(FeedbackAcknowledge acknowledge) {
        SqlSession sqlSession = null;
        try {
            if (acknowledge.getTimestamp() == null)
                acknowledge.setTimestamp(new Date());
            sqlSession = sqlSessionFactory.openSession(true);
            FeedbackAcknowledgeMapper mapper = sqlSession.getMapper(FeedbackAcknowledgeMapper.class);
            mapper.insertFeedbackAcknowledge(acknowledge);
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (null != sqlSession)
                sqlSession.close();
        }
    }
}
