package it.eng.zerohqt.dao.mapper;

import it.eng.zerohqt.dao.model.UserAccess;
import org.apache.ibatis.annotations.Insert;

/**
 * Created by ascatox on 09/05/17.
 */
public interface UserAccessMapper {


    @Insert("INSERT INTO user-access(timestamp,uid, email, fullName) " +
            "VALUES (#{timestamp}, #{uid}, #{email}, #{fullName})")
    void insertUserAccess(UserAccess userAccess);
}
