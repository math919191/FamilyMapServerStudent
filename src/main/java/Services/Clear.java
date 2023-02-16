package Services;

import Dao.EventDao;
import Response.ClearResponse;

import java.sql.Connection;

public class Clear {

//spec    Deletes ALL data from the database, including user, authtoken, person, and event data

    /**
     * Deletes ALL data from the database
     *
     * @return ClearResponse response to clear
     * */
    ClearResponse clear(){
        /* would call
        eventDao.clear()
        userDao.clear()
        authTokenDao.clear()
        personDao.clear()
        */

        return null;

    }

}
