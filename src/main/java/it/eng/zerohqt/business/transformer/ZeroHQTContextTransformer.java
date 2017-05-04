package it.eng.zerohqt.business.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eng.zerohqt.business.model.*;
import it.eng.zerohqt.config.Constants;
import it.eng.zerohqt.config.Utils;
import it.eng.zerohqt.dao.model.*;
import it.eng.zerohqt.orion.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ascatox on 17/02/17.
 */
public class ZeroHQTContextTransformer {
    private static Logger logger = Logger.getLogger(ZeroHQTContextTransformer.class);


    public static Optional<?> transformForWebSocket(ZeroHQTContext zeroHQTContext) {
        if (zeroHQTContext == null
                || zeroHQTContext.getContextResponses() == null
                || zeroHQTContext.getContextResponses().isEmpty())
            return Optional.empty();
        ContextElement contextElement = zeroHQTContext.getContextResponses().get(0).getContextElement();
        if (contextElement.getId().contains(Constants.ORION_CONTEXT_PREFIX_TESTSTATION))
            return transformToInformationBay(zeroHQTContext);
        else if (contextElement.getId().contains(Constants.ORION_CONTEXT_PREFIX_FEEDBACK))
            return transformToFeedback(zeroHQTContext);
        return Optional.empty();
    }

    private static Optional<InformationBay> transformToInformationBay(ZeroHQTContext zeroHQTContext) {
        InformationBay informationBay = new InformationBay();
        Acknowledge acknowledge = null;
        StateInfo stateInfo = new StateInfo();
        ArrayList<ContextResponses> contextResponses = zeroHQTContext.getContextResponses();
        if (null == contextResponses || contextResponses.isEmpty()) Optional.empty();
        for (ContextResponses contextResponse :
                contextResponses) {
            ContextElement contextElement = contextResponse.getContextElement();
            extractStationNameAndBayNumber(contextElement.getId(), informationBay);
            informationBay.setBayCode(contextElement.getId()); //TODO
            if (null == contextElement.getAttributes() || contextElement.getAttributes().isEmpty())
                return Optional.empty();
            for (Attributes attribute : contextElement.getAttributes()) {
                if (attribute.getName().equals(TestStationContextAttribute.state.name())) {
                    stateInfo.setStateCode(attribute.getValue());
                    ArrayList<Metadatas> metadatas = attribute.getMetadatas();
                    if (metadatas != null && !metadatas.isEmpty()) {
                        for (Metadatas metadata : metadatas) {
                            if (metadata.getName().equals("description")) {
                                stateInfo.setStateDescription(metadata.getValue());
                            }
                           //TODO Timestamp;
                        }
                    }
                } else if (attribute.getName().equals(TestStationContextAttribute.statePayload.name())) {
                    stateInfo.setStatePayload(attribute.getValue());
                } else if (attribute.getName().equals(TestStationContextAttribute.ipAddress.name())) {
                    informationBay.setIpAddress(attribute.getValue());
                } else if (attribute.getName().equals(TestStationContextAttribute.stationInfo.name())) {
                    informationBay.setStationDescription(attribute.getValue());
                } else if (attribute.getName().equals(TestStationContextAttribute.acknowledge.name())
                        && Utils.isStringNotBlankExt(attribute.getValue())) {
                    acknowledge = new Acknowledge();
                    extractStationNameAndBayNumber(contextElement.getId(), acknowledge);
                    acknowledge.setBayCode(contextElement.getId());
                    acknowledge.setAckType(AcknowledgeType.valueOf("ack" + attribute.getValue()));
                    acknowledge.setDescription(AcknowledgeType.valueOf("ack" + attribute.getValue()).getDescription());
                }
                stateInfo.setTimestamp(new Date());
                stateInfo.setType(resolveStateType(stateInfo.getStateCode(), acknowledge));
                informationBay.setTimestamp(new Date());
                informationBay.setStateInfo(stateInfo);
                informationBay.setAcknowledge(acknowledge);
            }
        }
        return Optional.of(informationBay);
    }

    public static Optional<FeedbackInfo> transformToFeedback(ZeroHQTContext zeroHQTContext) {
        FeedbackInfo feedbackInfo = new FeedbackInfo();
        ArrayList<ContextResponses> contextResponses = zeroHQTContext.getContextResponses();
        if (null == contextResponses || contextResponses.isEmpty()) Optional.empty();
        for (ContextResponses contextResponse : contextResponses) {
            ContextElement contextElement = contextResponse.getContextElement();
            feedbackInfo.setTimestamp(new Date());
            if (null == contextElement.getAttributes() || contextElement.getAttributes().isEmpty())
                return Optional.empty();
            for (Attributes attribute : contextElement.getAttributes()) {
                feedbackInfo.setMeasureId(attribute.getName());
                feedbackInfo.setValue(Double.parseDouble(attribute.getValue()));
            }
        }
        return Optional.of(feedbackInfo);
    }

    public static List<Acknowledge> transformToAcknowledges(List<TestStationData> testStationDatas) {
        if (null == testStationDatas || testStationDatas.isEmpty()) return null;
        return testStationDatas.stream()
                .map(td -> transformToAcknowledge(td)).collect(Collectors.toList());
    }


    public static Acknowledge transformToAcknowledge(TestStationData testStationData) {
        if (testStationData == null || StringUtils.isBlank(testStationData.getEntityId())) return null;
        Acknowledge acknowledge = new Acknowledge();
        acknowledge.setBayCode(testStationData.getEntityId());
        extractStationNameAndBayNumber(testStationData.getEntityId(), acknowledge);
        String attrName = testStationData.getAttrName();
        if (attrName.equals(TestStationContextAttribute.acknowledge.name())
                && Utils.isStringNotBlankExt(testStationData.getAttrValue())) {
            acknowledge.setAckType(AcknowledgeType.valueOf("ack" + testStationData.getAttrValue()));
            acknowledge.setDescription(AcknowledgeType.valueOf("ack" + testStationData.getAttrValue()).getDescription());
        }

        return acknowledge;
    }

    private static void extractStationNameAndBayNumber(String stationId, BaseBayInfo baseBayInfo) {
        String[] stationIds = stationId.split(":");
        if (null == stationIds || stationIds.length == 0) return;
        String part = stationIds[1];
        String[] parts = part.split("_");
        if (null == part || parts.length == 0) return;
        baseBayInfo.setStationName(parts[0]);
        baseBayInfo.setBayNumber(Integer.parseInt(parts[1]));
    }

    private static Metadatas[] parseJsonMetdatas(String metadatas) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(metadatas, Metadatas[].class);
    }


    private static StateType resolveStateType(String stateCode, Acknowledge acknowledge) {
        if (StringUtils.isBlank(stateCode)) return null;
        boolean isAckPresent = null != acknowledge && acknowledge.getAckType() != null;
        if (stateCode.equalsIgnoreCase("1000")
                || stateCode.equalsIgnoreCase("106")
                || stateCode.equalsIgnoreCase("700")) return StateType.error;
        else if (stateCode.equalsIgnoreCase("400") && !isAckPresent) return StateType.normal;
        else if (stateCode.equalsIgnoreCase("400") && isAckPresent) return StateType.warning;
        else return StateType.normal;
    }


}
