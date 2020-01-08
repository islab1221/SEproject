package dao.impl;

import dao.FHIRDao;
import dbconn.DbConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FHIRDaoImpl implements FHIRDao {
    private final Connection pcon = DbConn.getConnection(DbConn.MEDICALRECORD);

    public FHIRDaoImpl(){}

    @Override
    public List<Integer> getFHIRidList(String userType) throws SQLException {
        String sql = "SELECT FHIRid FROM medical_record.account WHERE permission = ?";
        List<Integer> FHIRList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = pcon.prepareStatement(sql);
            preparedStatement.setString(1, userType);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                FHIRList.add(resultSet.getInt("FHIRid"));

            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(preparedStatement != null)
                preparedStatement.close();
            if(resultSet != null)
                resultSet.close();
        }

        return FHIRList;

    }
}
