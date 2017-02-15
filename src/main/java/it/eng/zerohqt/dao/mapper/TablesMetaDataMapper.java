package it.eng.zerohqt.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by ascatox on 14/02/17.
 */
@Mapper
public interface TablesMetaDataMapper {

    /**
     * Retrieve table names from database
     *
     * @param service
     * @return
     */
    @Select("SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = #{service}")
    List<String> getTablesMetaData(String service);
}
