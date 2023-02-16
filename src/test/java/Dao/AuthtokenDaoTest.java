package Dao;

import Model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class AuthtokenDaoTest {

        private Database db;
        private AuthToken authtoken1;
        private AuthToken Authtoken2;
        private AuthtokenDao authDao;

        @BeforeEach
        public void setUp() throws DataAccessException {
            db = new Database();
            authtoken1 = new AuthToken("Authtoken", "username");
            Connection conn = db.getConnection();
            authDao = new AuthtokenDao(conn);
            authDao.clear();
        }

        @AfterEach
        public void tearDown() {
            db.closeConnection(false);
        }

        @Test
        void insertAuthtokenPass() throws DataAccessException {
            authDao.insertAuthToken(authtoken1);
            AuthToken compareTest = authDao.findAuthToken(authtoken1.getUsername());
            assertNotNull(compareTest);
            assertEquals(authtoken1, compareTest);
        }

        @Test
        void insertAuthtokenFail() throws DataAccessException {
            authDao.insertAuthToken(authtoken1);
            assertThrows(DataAccessException.class, () -> authDao.insertAuthToken(authtoken1));
        }

        @Test
        void findAuthtokenPass() throws DataAccessException {
            authDao.insertAuthToken(authtoken1);
            AuthToken compareTest = authDao.findAuthToken(authtoken1.getUsername());
            assertEquals(authtoken1, compareTest);
        }

        @Test
        void findAuthtokenFail() throws DataAccessException {
            AuthToken compareTest = authDao.findAuthToken("fakeAuthtokenID");
            assertNull(compareTest);
        }

        @Test
        void clear() throws DataAccessException {
            authDao.insertAuthToken(authtoken1);

            authDao.clear();

            AuthToken compareTest = authDao.findAuthToken("Authtoken1ID");
            assertNull(compareTest);

            compareTest = authDao.findAuthToken(authtoken1.getUsername());
            assertNull(compareTest);


        }



}