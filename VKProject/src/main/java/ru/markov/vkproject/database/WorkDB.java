package ru.markov.vkproject.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import ru.markov.vkproject.entity.UserF;

public class WorkDB {

    public int setEmployFriends(List<UserF> users, String groupId, int i) throws ClassNotFoundException, SQLException {

        Connection conn = new ConnectionDB().getConnection();
        String SQL = "INSERT INTO db_work.friends_outbreak VALUES (?,?)";
        PreparedStatement pstmt = null;

        pstmt = conn.prepareStatement(SQL);

        for (UserF user : users) {
            if (user.getUsers()== null) {
                pstmt.setString(1, user.getId());
                pstmt.setString(2, groupId);
                i++;
                pstmt.execute();
            } else {
                for (Integer user1 : user.getUsers()) {
                    if (user.getUsers().size() != 0) {
                        pstmt.setString(1, user.getId());
                        pstmt.setString(2, String.valueOf(user1));
                        i++;
                        pstmt.execute();
                    }
                }
            }
        }

        pstmt.close();
        conn.close();
        return i;
    }

//    private void deleteNotNull(List<User> users) throws SQLException, ClassNotFoundException {
//        Connection conn = new ConnectionDB().getConnection();
//        String SQL = "DELETE FROM db_work.friends_outbreak WHERE user = ? ";
//        PreparedStatement pstmt = null;
//        for (User user : users) {
//            pstmt = conn.prepareStatement(SQL);
//            pstmt.setInt(1, user.getId());
//            pstmt.executeUpdate();
//        }
//        
//        pstmt.close();
//        conn.close();
//// get a connection and then in your try catch for executing your delete...
//    }
}
