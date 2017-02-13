package it.eng.zerohqt.orion;

import it.eng.zerohqt.orion.client.OrionClient;
import it.eng.zerohqt.orion.model.ContextElementList;
import it.eng.zerohqt.orion.model.OrionContextElementToOrionEntityTransformer;
import it.eng.zerohqt.orion.model.OrionContextElementWrapper;
import it.eng.zerohqt.orion.model.subscribe.SubscriptionResponse;
import it.eng.zerohqt.rest.web.RestServiceController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by ascatox on 08/02/17.
 */
@Component
public class OrionContextConsumerExecutor implements OrionContextConsumer {

    /**
     * <p> Initialize connection with OCB RESTful API</p>
     */

    private final Logger logger = Logger.getLogger(RestServiceController.class);
    private OrionClient client = null;
    private String reference = null;


    public OrionContextConsumerExecutor(String orionServerUrl, String orionToken, String orionService,
                                        String orionServicePath, String orionReference) {
        client = new OrionClient(orionServerUrl, orionToken, orionService, orionServicePath);
        this.reference = orionReference;
    }

    public OrionContextConsumerExecutor() {
    }

    private List<OrionContextElementWrapper> getAllContextsToSubscribe(Optional<String> contextFilter) throws Exception {
        ContextElementList contextElementList = client.listContextEntities();
        if (null == contextElementList
                || null == contextElementList.getContextResponses()
                || contextElementList.getContextResponses().size() == 0)
            throw new IllegalStateException("No  Contexts available");
        if (!contextFilter.isPresent()) {
            return contextElementList.getContextResponses();
        } else
            return contextElementList.getContextResponses().stream().
                    filter(ctx -> ctx.getContextElement().getId()
                            .contains(contextFilter.get())).collect(Collectors.toList());

    }


    //TODO Cancellare una subscription se gia presente
    @Override
    public List<SubscriptionResponse> subscribeContexts(Optional<String> contextFilter) throws Exception {
        List<OrionContextElementWrapper> allContextsToSubscribe = getAllContextsToSubscribe(contextFilter);
        List<SubscriptionResponse> subscriptionResponses = new ArrayList<>();
        Consumer<OrionContextElementWrapper> orionContextElementWrapperConsumer = (OrionContextElementWrapper ctx) -> {
            try {
                SubscriptionResponse subscriptionResponse = client.subscribeChange(
                        OrionContextElementToOrionEntityTransformer
                                .transform(ctx.getContextElement()).get(), null,
                        reference);
                subscriptionResponses.add(subscriptionResponse);
            } catch (IOException e) {
                logger.error(e);
            }
        };
        allContextsToSubscribe.stream().forEach(orionContextElementWrapperConsumer);
        return subscriptionResponses;
    }

    @Override
    public void subscribeContextAttributes(String contextId, Optional<String> attributeFilter) {

    }

    @Override
    public void subscribeContextAttribute(String contextAttributeName) {

    }
}
