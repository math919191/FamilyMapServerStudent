package Services;

import Dao.DataAccessException;
import Model.AuthToken;
import Model.Data.GenerateData;
import Model.User;
import Request.UserRegisterRequest;
import Response.Response;
import Response.ErrorResponse;
import Response.UserRegisterResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoffrequest.RegisterRequest;

import static org.junit.jupiter.api.Assertions.*;

class UserRegisterServiceTest {


    GenerateData generateData;
    UserRegisterService userRegisterService;
    UserRegisterRequest userRegisterRequest;
    User user;
    @BeforeEach
    void setUp() throws DataAccessException {

        //clear all the data for a fresh start
        new ClearService().clear();

        //generate data class to be able to get data
        generateData = new GenerateData();
        userRegisterService = new UserRegisterService();

        //create dummy data
        user = generateData.generateRandomUser();
    }

    @Test
    void userRegisterServicePass(){
        UserRegisterRequest request = new UserRegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getGender());
        Response response = userRegisterService.userRegister(request);
        assertEquals(UserRegisterResponse.class, response.getClass());

        UserRegisterResponse userRegisterResponse = (UserRegisterResponse) response;
        assertEquals(user.getUsername(), userRegisterResponse.getUsername());


    }

    @Test
    void userRegisterServiceFail(){
        UserRegisterRequest request = new UserRegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getGender());
        Response response = userRegisterService.userRegister(request);
        assertEquals(UserRegisterResponse.class, response.getClass());

        //duplicate usernames should fail
        response = userRegisterService.userRegister(request);
        assertEquals(ErrorResponse.class, response.getClass());


    }
}