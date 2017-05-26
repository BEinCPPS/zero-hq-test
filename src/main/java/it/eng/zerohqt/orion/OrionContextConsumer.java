package it.eng.zerohqt.orion;

import it.eng.zerohqt.business.model.FeedbackScale;
import it.eng.zerohqt.orion.client.model.OrionContextElement;
import it.eng.zerohqt.orion.client.model.subscribe.SubscriptionResponse;

import java.util.List;
import java.util.Optional;

/**
 * Created by ascatox on 08/02/17.
 */
public interface OrionContextConsumer {


    /**
     *
     * @param isMerge
     * @return
     * @throws Exception
     */
    List<SubscriptionResponse> subscribe(boolean isMerge) throws Exception;

    /**
     * @param contextId
     * @param attributeFilter
     */
    void subscribeContextAttributes(String contextId, java.util.Optional<String> attributeFilter);

    /**
     * @param contextAttributeName
     */
    void subscribeContextAttribute(String contextAttributeName);

    /**
     *
     */
    void cancelAndDeleteSubscriptions() throws Exception;

    /**
     * @return
     * @throws Exception
     */
    List<FeedbackScale> readFeedbackScaleContext() throws Exception;

    String createFeedbackScaleContext(OrionContextElement feedbackScale) throws Exception;
}
