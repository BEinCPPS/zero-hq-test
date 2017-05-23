package it.eng.zerohqt.dao;

import it.eng.zerohqt.business.model.FeedbackAcknowledge;
import it.eng.zerohqt.dao.mapper.FeedbackAcknowledgeMapper;
import it.eng.zerohqt.web.rest.RestServiceController;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by ascatox on 09/05/17.
 */
@Repository
public class FeedbackAcknowledgeDao {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private FeedbackAcknowledgeMapper feedbackAcknowledgeMapper;
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


    public List<FeedbackAcknowledge> findFeedbackAcknowledges() {
        return feedbackAcknowledgeMapper.findFeedbackAcknowledges();
    }
}
