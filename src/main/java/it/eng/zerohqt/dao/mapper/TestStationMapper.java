package it.eng.zerohqt.dao.mapper;

import it.eng.zerohqt.dao.domain.TestStation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by ascatox on 13/02/17.
 */

//TODO Cambiare in base alla tabella e mettere qui le query
@Mapper
public interface TestStationMapper {

    @Select("select id, name, email from users") //TODO Example
    List<TestStation> findAllTestStations();

}
