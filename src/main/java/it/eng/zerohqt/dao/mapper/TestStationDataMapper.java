package it.eng.zerohqt.dao.mapper;

import it.eng.zerohqt.dao.model.TestStationData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by ascatox on 13/02/17.
 */

public interface TestStationDataMapper {


    /**
     * Extract Test Station Data using default state 400
     *
     * @param service         //     * @param context
     *                        //     * @param stateName
     *                        //     * @param statePayloadName
     * @param acknowledgeName
     * @return
     */
    @Select("SELECT * FROM ${service}.${context} where " +
            "attrName=#{acknowledgeName} and " +
            "attrValue in ('1','2','3')  " +
            "order by recvTime DESC LIMIT 100"
    )
    List<TestStationData> findAllAcknowledgesByStationBay(@Param("service") String service,
                                                          @Param("context") String context,
//                                               @Param("stateName") String stateName,
//                                               @Param("statePayloadName") String statePayloadName,
                                                          @Param("acknowledgeName") String acknowledgeName);


    /**
     *
     * @param service
     * @param context
     * @param acknowledgeName
     * @param ackType
     * @return
     */
    @Select("SELECT * FROM ${service}.${context} where " +
            "attrName=#{acknowledgeName} and " +
            "attrValue = ${ackType} " +
            "order by recvTime DESC LIMIT 100"
    )
    List<TestStationData> findAcknowledgesByStationBayByAck(@Param("service") String service,
                                                            @Param("context") String context,
                                                            @Param("acknowledgeName") String acknowledgeName,
                                                            @Param("ackType") String ackType);



    /**
     * Extracts Test Station data passsing list of interesting states
     *
     * @param service
     * @param context //     * @param stateName
     *                //     * @param statePayloadName
     *                //     * @param acknowledgeName
     *                //     * @param states
     * @return
     */
    @Select({"<script>",
            "SELECT * ",
            "FROM ${service}.${context}",
            "where",
            "(attrName = #{stateName}",
            "and attrValue in ",
            "<foreach item='item' index='index' collection='states'",
            "open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            ") or attrName=#{statePayloadName}",
            "or ",
            "attrName=#{acknowledgeName}",
            "order by recvTime DESC",
            "</script>"}
    )
    List<TestStationData> finAllNotificationsByStates(@Param("service") String service,
                                                      @Param("context") String context,
                                                      @Param("stateName") String stateName,
                                                      @Param("statePayloadName") String statePayloadName,
                                                      @Param("acknowledgeName") String acknowledgeName,
                                                      @Param("states") List<String> states);
}
