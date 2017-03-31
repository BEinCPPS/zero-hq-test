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
    @Select("SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = #{service} AND table_name like '%teststation%'")
    List<String> getTablesMetaData(String service);


    /**
     * Retrieve table names from database
     *
     * @param service
     * @param entity
     * @return
     */
    @Select("SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = #{service} AND table_name like '%entity%'")
    List<String> getTableMetaData(String service, String entity);

}
