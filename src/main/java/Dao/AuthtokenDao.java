package Dao;

import Model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthtokenDao {

    private final Connection conn;
    public AuthtokenDao(Connection conn){
        this.conn = conn;
    }

    /**
     * inserts an authToken into the database
     * @param authtoken an authtoken
     * */
    public void insertAuthToken(AuthToken authtoken) throws DataAccessException {
        String sql = "INSERT INTO Authtokens (authtoken, username) VALUES(?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken.getAuthtoken());
            stmt.setString(2, authtoken.getUsername());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("error inserting authToken into database");
        }
    }

    /**
     * gets an authtoken based on a username
     * @param username a username
     * @return AuthToken an authtoken
     * */
    AuthToken findAuthToken(String username) throws DataAccessException {
        AuthToken authToken;
        ResultSet rs;
        String sql = "SELECT * FROM Authtokens WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken(
                        rs.getString("authToken"),
                        rs.getString("username")
                        );
                return authToken;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("error while finding authtoken from username");
        }
    }

    /**
     * gets a username based on an authtoken
     * @param authToken a authtoken
     * @return AuthToken an authtoken that contains a username
     * */
    AuthToken findUserName(String authToken) throws DataAccessException {
        AuthToken myAuthToken;
        ResultSet rs;
        String sql = "SELECT * FROM Authtokens WHERE authtoken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, authToken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                myAuthToken = new AuthToken(
                        rs.getString("authToken"),
                        rs.getString("username")
                );
                return myAuthToken;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("error while finding authtoken from username");
        }
    }

    /**
     * clears the authToken table
     * */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Authtokens";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("error clearing all authtokens");
        }
    }



}
