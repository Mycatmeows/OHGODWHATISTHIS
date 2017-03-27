package com.placeholder.trainingph;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Created by jmontez on 25-03-2017.
 */


public class DBHelper {

    Connection connection;


    public DBHelper() throws SQLException{
        connection = DriverManager.getConnection("localhost");
    }


    public void close() throws  SQLException{
        connection.close();
    }

    public void addReactionTime(int user, int run, Date extractionDate, double reactionTime) throws SQLException{
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO DIM_REACTION (info_dt, id_user, id_run, reaction_time) VALUES (");
        stringBuilder.append(extractionDate.toString());
        stringBuilder.append(", ");
        stringBuilder.append(user);
        stringBuilder.append(", ");
        stringBuilder.append(run);
        stringBuilder.append(", ");
        stringBuilder.append(reactionTime);
        stringBuilder.append(")");

        Statement statement = connection.prepareStatement(stringBuilder.toString());
    }

    public void addRunData(){

    }

}
