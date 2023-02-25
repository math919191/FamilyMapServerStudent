package Services;

import Dao.DataAccessException;
import Model.AuthToken;
import Model.Data.GenerateData;
import Model.User;

import Response.Response;
import Response.ErrorResponse;
import Response.UserLoginResponse;

import Request.UserLoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserLoginServiceTest extends TestHelperFunctions {
    GenerateData generateData;
    UserLoginService userLoginService;
    AuthToken authToken;
    User user;
    @BeforeEach
    void setUp() throws DataAccessException {

        //clear all the data for a fresh start
        new ClearService().clear();

        //generate data class to be able to get data
        generateData = new GenerateData();
        userLoginService = new UserLoginService();

        //create dummy data
        user = generateData.generateRandomUser();
        authToken = generateData.generateRandomAuthTokenWithUsername(user.getUsername());

        addUser(user);
        addAuthToken(authToken);
    }

    @Test
    void userLoginServicePass(){
        Response r = userLoginService.userLogin(new UserLoginRequest(user.getUsername(), user.getPassword()));
        assertEquals(UserLoginResponse.class, r.getClass());
        UserLoginResponse loginResponse = (UserLoginResponse) r;

        assertEquals(user.getPersonID(), loginResponse.getPersonID());

    }

    @Test
    void userloginServiceFail(){
        //bad username
        Response r = userLoginService.userLogin(new UserLoginRequest("bad username", user.getPassword()));
        assertEquals(ErrorResponse.class, r.getClass());
        ErrorResponse errorResponse = (ErrorResponse) r;

        assertTrue(errorResponse.getMessage().contains("User doesn't exist"));

        //bad password
        r = userLoginService.userLogin(new UserLoginRequest(user.getUsername(), "bad password"));
        assertEquals(ErrorResponse.class, r.getClass());
        errorResponse = (ErrorResponse) r;
        assertTrue(errorResponse.getMessage().contains("Invalid username or password"));





    }

}