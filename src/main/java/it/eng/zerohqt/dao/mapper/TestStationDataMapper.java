package it.eng.zerohqt.dao.mapper;

import it.eng.zerohqt.dao.domain.TestStationData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by ascatox on 13/02/17.
 */

public interface TestStationDataMapper {


    @Select("SELECT * FROM ${service}.${context} where " +
            "attrName = #{stateName} and " +
            "attrValue in (\"400\", \"106\") or " +
            "attrName=#{statePayloadName} " +
            "or attrName=#{acknowledgeName} " +
            "order by recvTime DESC"
    )
    List<TestStationData> findAllNotifications(@Param("service") String service,
                                               @Param("context") String context,
                                               @Param("stateName") String stateName,
                                               @Param("statePayloadName") String statePayloadName,
                                               @Param("acknowledgeName") String acknowledgeName);


    @Select({"<script>",
            "SELECT * ",
            "FROM ${service}.${context}",
            "where",
            "attrName = #{stateName}",
            "and in ",
                "<foreach item='item' index='index' collection='states'",
                "open='(' separator=',' close=')'>",
                "#{item}",
                "</foreach>",
            "and attrName=#{statePayloadName} ",
            "or attrName=#{acknowledgeName}",
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
