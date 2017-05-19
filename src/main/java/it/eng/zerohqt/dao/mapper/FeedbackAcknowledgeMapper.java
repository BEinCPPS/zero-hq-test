package it.eng.zerohqt.dao.mapper;

import it.eng.zerohqt.business.model.Acknowledge;
import it.eng.zerohqt.dao.model.UserAccess;
import org.apache.ibatis.annotations.Insert;

/**
 * Created by ascatox on 09/05/17.
 */
public interface FeedbackAcknowledgeMapper {


    @Insert("INSERT INTO feedback_acknowledge(id, acknowledge, timestamp, description) " +
            "VALUES (#{id}, #{acknowledge}, #{timestamp}, #{description})")
    void insertFeedbackAcknowledge(Acknowledge acknowledge);
}
