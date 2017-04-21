package it.eng.zerohqt.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eng.zerohqt.business.model.Acknowledge;
import it.eng.zerohqt.business.model.BaseBayInfo;
import it.eng.zerohqt.business.model.InformationBay;
import it.eng.zerohqt.business.model.StateInfo;
import it.eng.zerohqt.config.Utils;
import it.eng.zerohqt.dao.model.AcknowledgeType;
import it.eng.zerohqt.dao.model.ContextAttribute;
import it.eng.zerohqt.dao.model.StateType;
import it.eng.zerohqt.dao.model.TestStationData;
import it.eng.zerohqt.orion.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ascatox on 17/02/17.
 */
public class InformationBayContextTransformer {
    private static Logger logger = Logger.getLogger(InformationBayContextTransformer.class);

    public static Optional<List<InformationBay>> transformToInformationBay(TestStationBayContext testStationBayContext) {
        InformationBay informationBay = new InformationBay();
        Acknowledge acknowledge = null;
        StateInfo stateInfo = new StateInfo();
        ArrayList<ContextResponses> contextResponses = testStationBayContext.getContextResponses();
        List<InformationBay> informationBays = new ArrayList<>();
        if (null == contextResponses || contextResponses.isEmpty()) Optional.empty();
        for (ContextResponses contextResponse :
                contextResponses) {
            ContextElement contextElement = contextResponse.getContextElement();
            extractStationNameAndBayNumber(contextElement.getId(), informationBay);
            informationBay.setBayCode(contextElement.getId()); //TODO
            if (null == contextElement.getAttributes() || contextElement.getAttributes().isEmpty())
                return Optional.empty();
            for (Attributes attribute : contextElement.getAttributes()) {
                if (attribute.getName().equals(ContextAttribute.state.name())) {
                    stateInfo.setStateCode(attribute.getValue());
                    ArrayList<Metadatas> metadatas = attribute.getMetadatas();
                    if (metadatas != null && !metadatas.isEmpty()) {
                        for (Metadatas metadata : metadatas) {
                            if (metadata.getName().equals("description")) {
                                stateInfo.setStateDescription(metadata.getValue());
                            }
                        }
                    }
                } else if (attribute.getName().equals(ContextAttribute.statePayload.name())) {
                    stateInfo.setStatePayload(attribute.getValue());
                } else if (attribute.getName().equals(ContextAttribute.ipAddress.name())) {
                    informationBay.setIpAddress(attribute.getValue());
                } else if (attribute.getName().equals(ContextAttribute.stationInfo.name())) {
                    informationBay.setStationDescription(attribute.getValue());
                } else if (attribute.getName().equals(ContextAttribute.acknowledge.name())
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
                informationBays.add(informationBay);
            }
        }
        return Optional.of(informationBays);
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
        if (attrName.equals(ContextAttribute.acknowledge.name())
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
        boolean isAckPresent = null != acknowledge && acknowledge.getAckType() != null;
        if (stateCode.equalsIgnoreCase("1000")
                || stateCode.equalsIgnoreCase("106")
                || stateCode.equalsIgnoreCase("700")) return StateType.error;
        else if (stateCode.equalsIgnoreCase("400") && !isAckPresent) return StateType.normal;
        else if (stateCode.equalsIgnoreCase("400") && isAckPresent) return StateType.warning;
        else return StateType.normal;
    }

}
