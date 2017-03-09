package it.eng.zerohqt.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eng.zerohqt.business.model.Acknowledge;
import it.eng.zerohqt.business.model.BaseBayInfo;
import it.eng.zerohqt.business.model.InformationBay;
import it.eng.zerohqt.business.model.StateInfo;
import it.eng.zerohqt.dao.model.AcknowledgeTypes;
import it.eng.zerohqt.dao.model.ContextAttribute;
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
        StateInfo notification = new StateInfo();
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
                    notification.setStateCode(attribute.getValue());
                    ArrayList<Metadatas> metadatas = attribute.getMetadatas();
                    if (metadatas != null && !metadatas.isEmpty()) {
                        for (Metadatas metadata : metadatas) {
                            if (metadata.getName().equals("description")) {
                                notification.setStateDescription(metadata.getValue());
                            }
                        }
                    }
                } else if (attribute.getName().equals(ContextAttribute.statePayload.name())) {
                    notification.setStatePayload(attribute.getValue());
                } else if (attribute.getName().equals(ContextAttribute.ipAddress.name())) {
                    informationBay.setIpAddress(attribute.getValue());
                } else if (attribute.getName().equals(ContextAttribute.stationInfo.name())) {
                    informationBay.setStationDescription(attribute.getValue());
                }
                notification.setTimestamp(new Date());
                informationBay.setTimestamp(new Date());
                informationBay.setStateInfo(notification);
                informationBays.add(informationBay);
            }
        }
        return Optional.of(informationBays);
    }

    public static List<Acknowledge> transformToInformationBaies(List<TestStationData> testStationDatas) {
        if (null == testStationDatas || testStationDatas.isEmpty()) return null;
        return testStationDatas.stream()
                .map(td -> transformToInformationBay(td)).collect(Collectors.toList());
    }


    public static Acknowledge transformToInformationBay(TestStationData testStationData) {
        if (testStationData == null || StringUtils.isBlank(testStationData.getEntityId())) return null;
        Acknowledge acknowledge = new Acknowledge();
        acknowledge.setBayCode(testStationData.getEntityId());
        extractStationNameAndBayNumber(testStationData.getEntityId(), acknowledge);
        String attrName = testStationData.getAttrName();
        if (attrName.equals(ContextAttribute.acknowledge.name())) {
            acknowledge.setAckType(AcknowledgeTypes.valueOf("ack" + testStationData.getAttrValue()));
            acknowledge.setDescription(AcknowledgeTypes.valueOf("ack" + testStationData.getAttrValue()).getDescription());
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


}
