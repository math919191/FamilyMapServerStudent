package Services;

import Dao.EventDao;
import Model.AuthToken;
import Model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {

    private ClearService clearService;

    @BeforeEach
    void setUp(){
        clearService = new ClearService();
    }

    @Test
    void clearServicePass(){}

}