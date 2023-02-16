package Dao;

import java.sql.Connection;

public class Dao {
    private final Connection conn;
    public Dao(Connection conn) {
        this.conn = conn;
    }

}
