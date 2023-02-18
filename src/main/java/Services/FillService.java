package Services;

import Response.FillResponse;

public class FillService {

    /**
     * Populates the server's database with generated data for the specified username. The required "username" parameter must be a user already registered with the server.
     * If there is any data in the database already associated with the given username, it is deleted.
     * The optional "generations" parameter lets the caller specify the number of generations of ancestors to be generated,
     * and must be a non-negative integer (the default is 4, which results in 31 new persons each with associated events).
     *
     * @param username a username
     * @param generations number of generations (optional, default=4)
     *
     * @return FillResponse
     * */

    FillResponse fill(String username, int generations){
        //verify the username is registered
        //generate
        return null;
    }
}
