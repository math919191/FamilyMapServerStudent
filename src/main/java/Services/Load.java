package Services;

import Request.LoadRequest;
import Response.LoadResponse;

public class Load {

    /**
     * Clears all data from the database (just like the /clear API)
     * Loads the user, person, and event data from the request body into the database.
     *
     * @param loadRequest a loadRequest
     * @return LoadResponse a loadResponse
     * */

    LoadResponse load(LoadRequest loadRequest){
        //calls clear service
        //loops through persons and adds them to the database
        //loops through users and adds them to the database
        //loops through events and adds them to the database
        //creates a LoadResponse object and returns
        return null;
    }
}
