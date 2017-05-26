package it.eng.zerohqt.dao.mapper;

import it.eng.zerohqt.business.model.Acknowledge;
import it.eng.zerohqt.business.model.FeedbackAcknowledge;
import it.eng.zerohqt.config.Constants;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by ascatox on 09/05/17.
 */
public interface FeedbackAcknowledgeMapper {


    @Insert("INSERT INTO feedback_acknowledge(ackType, timestamp, description) " +
            "VALUES (#{ackType}, #{timestamp}, #{description})")
    void insertFeedbackAcknowledge(FeedbackAcknowledge acknowledge);



    @Select("SELECT * FROM whirlpool.feedback_acknowledge ORDER by timestamp DESC LIMIT "+ Constants.HISTORY_BLOCK_SIZE)
    List<FeedbackAcknowledge> findFeedbackAcknowledges();
}
