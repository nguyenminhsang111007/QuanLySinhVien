package database;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        try {
            Connection conn = DBConnection.getConnection();

            System.out.println("KET NOI SQL SERVER THANH CONG!");

            conn.close();

        } catch (Exception e) {
            System.out.println("KET NOI THAT BAI!");
            e.printStackTrace();
        }
    }
}