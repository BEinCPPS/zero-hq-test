package it.eng.zerohqt.business;

import it.eng.zerohqt.business.model.InformationBay;
import it.eng.zerohqt.business.model.Notification;
import it.eng.zerohqt.dao.model.AcknowledgeTypes;
import it.eng.zerohqt.dao.model.ContextAttribute;
import it.eng.zerohqt.orion.model.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by ascatox on 17/02/17.
 */
public class TestStationBayContextTransformer {


    public static Optional<List<InformationBay>> transformToInformationBay(TestStationBayContext testStationBayContext) {
        InformationBay informationBay = new InformationBay();
        Notification notification = new Notification();
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
                } else if (attribute.getName().equals(ContextAttribute.acknowledge.name())) {
                    if (StringUtils.isNotBlank(attribute.getValue())) {
                        notification.setAcknowledge(AcknowledgeTypes.valueOf("ack" + attribute.getValue()).getDescription());
                     }
                } else if (attribute.getName().equals(ContextAttribute.ipAddress.name())) {
                    informationBay.setIpAddress(attribute.getValue());
                } else if (attribute.getName().equals(ContextAttribute.stationInfo.name())) {
                    informationBay.setStationDescription(attribute.getValue());
                }
                notification.setTimestamp(new Date());
                informationBay.setTimestamp(new Date());
                informationBay.setNotification(notification);
                informationBays.add(informationBay);
            }
        }
        return Optional.of(informationBays);
    }


    private static void extractStationNameAndBayNumber(String stationId, InformationBay informationBay) {
        if (StringUtils.isBlank(stationId)) return;
        String[] stationIds = stationId.split(":");
        if (null == stationIds || stationIds.length == 0) return;
        String part = stationIds[1];
        String[] parts = part.split("_");
        if (null == part || parts.length == 0) return;
        informationBay.setStationName(parts[0]);
        informationBay.setBayNumber(Integer.parseInt(parts[1]));
    }


}
