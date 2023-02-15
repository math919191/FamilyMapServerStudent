package Dao;

import Model.AuthToken;

public class AuthtokenDao {

    /**
     * inserts an authToken into the database
     * @params authToken an authtoken
     * */
    void insertAuthToken(AuthToken authtoken){}

    /**
     * gets an authtoken based on a username
     * @params username a username
     * @return AuthToken an authtoken
     * */
    AuthToken getAuthToken(String username){
        return null;
    }

    /**
     * gets a username based on an authtoken
     * @params authToken a authtoken
     * @return AuthToken an authtoken that contains a username
     * */
    AuthToken getUserName(String authToken){
        return null;
    }

    /**
     * clears the authToken table
     * */
    void clear(){}



}
