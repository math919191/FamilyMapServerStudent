package Dao;

import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private Database db;
    private User user1;
    private User user2;
    private UserDao uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        user1 = new User("user", "pass", "joe@gmail",
                "Joe", "Doe", "M", "user1ID");
        user2 = new User("user2", "pass2", "joe@gmail2",
                "Joe2", "Doe2", "F", "user2ID");

        Connection conn = db.getConnection();
        uDao = new UserDao(conn);
        uDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    void insertUserPass() throws DataAccessException {
        uDao.insertUser(user1);
        User compareTest = uDao.findUser(user1.getPersonID());
        assertNotNull(compareTest);
        assertEquals(user1, compareTest);
    }

    @Test
    void insertUserFail() throws DataAccessException {
        uDao.insertUser(user1);
        assertThrows(DataAccessException.class, () -> uDao.insertUser(user1));
    }

    @Test
    void findUserPass() throws DataAccessException {
        uDao.insertUser(user1);
        User compareTest = uDao.findUser("user1ID");
        assertEquals(user1, compareTest);

        uDao.insertUser(user2);
        compareTest = uDao.findUser("user1ID");
        assertEquals(user1, compareTest);

        compareTest = uDao.findUser("user2ID");
        assertEquals(user2, compareTest);
    }

    @Test
    void findUserFail() throws DataAccessException {
        User compareTest = uDao.findUser("fakeUserID");
        assertNull(compareTest);
    }

    @Test
    void deleteUserPass() throws DataAccessException {
        uDao.insertUser(user1);
        uDao.insertUser(user2);

        //make sure that it was inserted correctly
        User compareTest = uDao.findUser(user1.getPersonID());
        assertEquals(user1, compareTest);

        uDao.deleteUser(user1.getUsername());

        compareTest = uDao.findUser(user1.getPersonID());
        assertNull(compareTest);
    }

    @Test
    void deleteUserFail() throws DataAccessException {
        uDao.insertUser(user1);

        assertEquals(0, uDao.deleteUser(user2.getUsername()));

    }


    @Test
    void clear() throws DataAccessException {
        uDao.clear();
        User compareTest = uDao.findUser("fakeUserID");
        assertNull(compareTest);

        uDao.insertUser(user1);
        uDao.insertUser(user2);
        uDao.clear();

        compareTest = uDao.findUser("user1ID");
        assertNull(compareTest);

        compareTest = uDao.findUser("user2ID");
        assertNull(compareTest);
    }

}