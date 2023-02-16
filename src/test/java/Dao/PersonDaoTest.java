package Dao;

import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class PersonDaoTest {
    private Database db;
    private Person person1;
    private Person person2;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        person1 = new Person("person1ID", "user", "jonny",
                "Cache", "M", "fatherID", "motherID", "spouseID");
        person2 = new Person("person2ID", "user", "King of ",
                "Rock", "M", "fatherID", "motherID", "spouseID");

        Connection conn = db.getConnection();
        pDao = new PersonDao(conn);
        pDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    void insertPersonPass() throws DataAccessException {
        pDao.insertPerson(person1);
        Person compareTest = pDao.findPerson(person1.getPersonID());
        assertNotNull(compareTest);
        assertEquals(person1, compareTest);
    }

    @Test
    void insertPersonFail() throws DataAccessException {
        pDao.insertPerson(person1);
        assertThrows(DataAccessException.class, () -> pDao.insertPerson(person1));
    }

    @Test
    void findPersonPass() throws DataAccessException {
        pDao.insertPerson(person1);
        Person compareTest = pDao.findPerson("person1ID");
        assertEquals(person1, compareTest);

        pDao.insertPerson(person2);
        compareTest = pDao.findPerson("person1ID");
        assertEquals(person1, compareTest);

        compareTest = pDao.findPerson("person2ID");
        assertEquals(person2, compareTest);
    }

    @Test
    void findPersonFail() throws DataAccessException {
        Person compareTest = pDao.findPerson("fakePersonID");
        assertNull(compareTest);
    }

    @Test
    void clear() throws DataAccessException {
        pDao.clear();
        Person compareTest = pDao.findPerson("fakePersonID");
        assertNull(compareTest);

        pDao.insertPerson(person1);
        pDao.insertPerson(person2);
        pDao.clear();

        compareTest = pDao.findPerson("person1ID");
        assertNull(compareTest);

        compareTest = pDao.findPerson("person2ID");
        assertNull(compareTest);
    }


}