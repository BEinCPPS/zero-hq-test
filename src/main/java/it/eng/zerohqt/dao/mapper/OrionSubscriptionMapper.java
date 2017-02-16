package it.eng.zerohqt.dao.mapper;

import it.eng.zerohqt.dao.domain.OrionSubscription;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import javax.ws.rs.DELETE;
import java.util.List;

/**
 * Created by ascatox on 16/02/17.
 */
public interface OrionSubscriptionMapper {

    @Insert("INSERT INTO ocb_subscription(subscriptionId,creationDate, enabled, entity) " +
            "VALUES (#{subscriptionId}, #{creationDate}, #{enabled}, #{entity})")
    void insertOrionSubscription(OrionSubscription orionSubscription);

    @Update("UPDATE ocb_subscription SET enabled = 0")
    void disableOrionSubscriptions();

    @Delete("DELETE FROM ocb_subscription WHERE subscriptionId = #{subscriptionId}")
    void deleteOrionSubscription(String subscriptionId);

    @Select("SELECT * FROM ocb_subscription where enabled = 1")
    List<OrionSubscription> findEnabledSubscriptions();

}
