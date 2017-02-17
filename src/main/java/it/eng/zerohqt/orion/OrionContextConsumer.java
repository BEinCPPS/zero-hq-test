package it.eng.zerohqt.orion;

import it.eng.zerohqt.orion.client.model.subscribe.SubscriptionResponse;

import java.util.List;
import java.util.Optional;

/**
 * Created by ascatox on 08/02/17.
 */
public interface OrionContextConsumer {


    /**
     * @param contextFilter
     * @throws Exception
     */
    List<SubscriptionResponse> subscribeContexts(Optional<String> contextFilter) throws Exception;

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
}
