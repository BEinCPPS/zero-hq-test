package it.eng.zerohqt.dao;

import it.eng.zerohqt.business.model.Acknowledge;
import it.eng.zerohqt.business.model.FeedbackAcknowledge;
import it.eng.zerohqt.business.transformer.ZeroHQTContextTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ascatox on 23/05/17.
 */
@Repository
public class HistoryDao {
    @Autowired
    TestStationDao testStationDao;
    @Autowired
    FeedbackAcknowledgeDao feedbackAcknowledgeDao;

    public List<Acknowledge> readHistory(String service) throws Exception {
        List<Acknowledge> acknowledges = ZeroHQTContextTransformer.
                transformToAcknowledges(testStationDao
                        .findAllNotifications(service));
        List<FeedbackAcknowledge> feedbackAcknowledges = feedbackAcknowledgeDao.findFeedbackAcknowledges();
        acknowledges.addAll(feedbackAcknowledges);
        return acknowledges;
    }


}
