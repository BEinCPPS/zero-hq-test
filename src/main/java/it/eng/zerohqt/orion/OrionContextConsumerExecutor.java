package it.eng.zerohqt.orion;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.eng.zerohqt.business.model.FeedbackScale;
import it.eng.zerohqt.config.Constants;
import it.eng.zerohqt.dao.OrionSubscriptionDao;
import it.eng.zerohqt.dao.model.OrionSubscription;
import it.eng.zerohqt.orion.client.OrionClient;
import it.eng.zerohqt.orion.client.model.*;
import it.eng.zerohqt.orion.client.model.subscribe.SubscriptionResponse;
import it.eng.zerohqt.orion.model.FeedbackAcknowledgeContextAttribute;
import it.eng.zerohqt.orion.model.FeedbackContextAttribute;
import it.eng.zerohqt.orion.model.TestStationContextAttribute;
import it.eng.zerohqt.web.rest.RestServiceController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
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
    private OrionClient orionClient = null;
    private String reference = null;

    @Autowired
    private OrionSubscriptionDao orionSubscriptionDao;

    public OrionContextConsumerExecutor(String orionServerUrl, String orionToken, String orionService,
                                        String orionServicePath, String orionReference) throws Exception {
        orionClient = new OrionClient(orionServerUrl, orionToken, orionService, orionServicePath);
        this.reference = orionReference;
    }

    public OrionContextConsumerExecutor() throws Exception {
    }

    @Override
    public List<SubscriptionResponse> subscribe(boolean isMerge) throws Exception {
        List<SubscriptionResponse> subscriptionResponses = new ArrayList<>();
        if (!isMerge)
            cancelAndDeleteSubscriptions();
        Optional<String> contextFatherNamePrefix = Optional.of(TestStationContextAttribute.getContextFatherNamePrefix());
        Optional<String> contextFatherNamePrefix1 = Optional.of(FeedbackContextAttribute.getContextFatherNamePrefix());
        Optional<String> contextFatherNamePrefix2 = Optional.of(FeedbackAcknowledgeContextAttribute.getContextFatherNamePrefix());

        subscriptionResponses = subscribeAllContexts(TestStationContextAttribute.getValuesString(), isMerge,
                contextFatherNamePrefix, contextFatherNamePrefix1, contextFatherNamePrefix2);
        return subscriptionResponses;
    }

    private List<OrionContextElementWrapper> getAllContextsToSubscribe(Optional<String>... contextFilters) throws Exception {
        ContextElementList contextElementList = orionClient.listContextEntities();
        List<OrionContextElementWrapper> orionContextsToSubscribe = new ArrayList<>();
        if (null == contextElementList
                || null == contextElementList.getContextResponses()
                || contextElementList.getContextResponses().size() == 0)
            throw new IllegalStateException("No  Contexts available");
        if (contextFilters.length == 0) {
            return contextElementList.getContextResponses();
        } else
            contextElementList.getContextResponses().stream().forEach(ctx -> {
                boolean match = Arrays.asList(contextFilters).stream()
                        .anyMatch(f -> ctx.getContextElement().getId().contains(f.get()));
                if (match)
                    orionContextsToSubscribe.add(ctx);
            });
        return orionContextsToSubscribe;
    }

    private List<SubscriptionResponse> subscribeAllContexts(String[] attributes, boolean isMerge, Optional<String>... contextFilters)
            throws Exception {
        List<OrionContextElementWrapper> allContextsToSubscribe = getAllContextsToSubscribe(contextFilters);
        List<OrionSubscription> enabledSubscriptions = orionSubscriptionDao.findEnabledSubscriptions();
        List<SubscriptionResponse> subscriptionResponses = new ArrayList<>();
        Consumer<OrionContextElementWrapper> orionContextElementWrapperConsumer =
                (OrionContextElementWrapper ctx) -> {
                    try {
                        String[] conditions = {};
                        boolean match = enabledSubscriptions.stream().anyMatch(s -> s.getEntity().equals(ctx.getContextElement().getId()));
                        if (!match || !isMerge) {
                            SubscriptionResponse subscriptionResponse = orionClient.subscribeChange(
                                    OrionContextElementToOrionEntityTransformer
                                            .transform(ctx.getContextElement()).get(), attributes,
                                    reference, conditions);
                            subscriptionResponses.add(subscriptionResponse);
                            orionSubscriptionDao.insertOrionSubscription(new OrionSubscription
                                    (subscriptionResponse.getSubscribeResponse().getSubscriptionId(), new Date(), true,
                                            ctx.getContextElement().getId(), ctx.getContextElement().getType()));
                        }
                    } catch (IOException e) {
                        logger.error(e);
                    }
                };
        allContextsToSubscribe.stream().forEach(orionContextElementWrapperConsumer);
        Consumer<OrionSubscription> orionSubscriptionConsumer = (OrionSubscription or) -> {
            try {
                boolean match = allContextsToSubscribe.stream().anyMatch(ctx -> ctx.getContextElement().getId().equals(or.getEntity()));
                if (!match && isMerge) {
                    orionSubscriptionDao.deleteOrionSubscription(or.getSubscriptionId());
                }
            } catch (Exception e) {
                logger.error(e);
            }
        };
        enabledSubscriptions.stream().forEach(orionSubscriptionConsumer);
        return subscriptionResponses;
    }

    public void subscribeContextAttributes(String contextId, Optional<String> attributeFilter) {
        //TODO
    }

    @Override
    public void subscribeContextAttribute(String contextAttributeName) {
        //TODO
    }

    @Override
    public void cancelAndDeleteSubscriptions() throws Exception {
        List<OrionSubscription> enabledSubscriptions = orionSubscriptionDao.findEnabledSubscriptions();
        for (OrionSubscription subscription :
                enabledSubscriptions) {
            try {
                orionClient.unSubscribeChange(subscription.getSubscriptionId());
                orionSubscriptionDao.deleteOrionSubscription(subscription.getSubscriptionId());
            } catch (Exception e) {
                logger.error(e);
                throw new Exception(e);
            }
        }
    }

    @Override
    public List<FeedbackScale> readFeedbackScaleContext() throws Exception {
        final List<OrionContextElementWrapper> contextElementWrappers =
                getAllContextsToSubscribe(Optional.of(Constants.ORION_CONTEXT_PREFIX_FEEDBACK_SCALE)); //TODO
        if (null == contextElementWrappers || contextElementWrappers.isEmpty())
            throw new IllegalStateException("No " + Constants.ORION_CONTEXT_PREFIX_FEEDBACK_SCALE + " context available");
        OrionContextElementWrapper contextElementWrapper = contextElementWrappers.get(0);
        OrionContextElement contextElement = contextElementWrapper.getContextElement();
        List<Attribute> attributes = contextElement.getAttributes();
        if (attributes == null || attributes.isEmpty())
            throw new IllegalStateException("No attributes in " +
                    Constants.ORION_CONTEXT_PREFIX_FEEDBACK_SCALE + " context available");
        List<FeedbackScale> collect = attributes.stream().map(attr -> {
            FeedbackScale feedbackScale = new FeedbackScale(attr.getName(),
                    extractMinValuePartForScale(attr.getValue()), extractMaxValuePartForScale(attr.getValue()));
            return feedbackScale;
        }).collect(Collectors.toList());
        return collect;
    }


    @Override
    public String createFeedbackScaleContext(OrionContextElement feedbackScale) throws Exception {
        List<FeedbackScale> feedbackScaleRegistered = null;
        try {
            feedbackScaleRegistered = readFeedbackScaleContext();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        if (null == feedbackScaleRegistered || feedbackScaleRegistered.isEmpty()) {
            return orionClient.postContextEntity("", feedbackScale);
        }
        return null;
    }

    private Double extractMinValuePartForScale(String value) {
        JsonParser jsonParser = new JsonParser();
        JsonObject obj = (JsonObject) jsonParser.parse(value);
        JsonElement min = obj.get("min");
        return min.getAsDouble();

    }

    private Double extractMaxValuePartForScale(String value) {
        JsonParser jsonParser = new JsonParser();
        JsonObject obj = (JsonObject) jsonParser.parse(value);
        JsonElement max = obj.get("max");
        return max.getAsDouble();
    }

}
