package Services;

import Dao.AuthtokenDao;
import Dao.Database;
import Model.AuthToken;
import Model.Data.GenerateFamilyTree;
import Model.User;
import Response.ErrorResponse;
import Response.UserRegisterResponse;

public class ValidateAuthtokenService {

    public boolean validateAuthToken(String givenAuthtoken){
        Database db = new Database();
        try {
            db.openConnection();

            AuthtokenDao authtokenDao = new AuthtokenDao(db.getConnection());
            AuthToken authToken = authtokenDao.findUserName(givenAuthtoken);

            System.out.printf(authToken.getAuthtoken());
            db.closeConnection(false);

            if (authToken != null){
                return true;
            } else {
                return false;
            }

        }  catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            ErrorResponse result = new ErrorResponse("Couldn't validate auth token", false);
            return false;

        }
    }
}
