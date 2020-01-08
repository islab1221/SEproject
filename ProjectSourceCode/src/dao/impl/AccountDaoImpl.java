package dao.impl;

import dao.AccountDao;
import dbconn.DbConn;
import model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {

    private final Connection pcon = DbConn.getConnection(DbConn.MEDICALRECORD);


    public AccountDaoImpl(){

    }

    @Override
    public Account getAccountByRegNumber(String regNumber) throws SQLException {

        String sql = "SELECT * FROM medical_record.account WHERE id_account = ?";
        return getAccountByOneParameter(sql, regNumber);
    }



    private Account getAccountByOneParameter(String sql, String parameter) throws SQLException {
        if(pcon == null){
            System.out.println("isNull!!!!!!!!!!!!!!!!!");
        }
        Account account = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = pcon.prepareStatement(sql);
            preparedStatement.setString(1, parameter);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                account = new Account(
                        resultSet.getString("id_account"),
                        resultSet.getString("password"),
                        resultSet.getString("permission"),
                        Integer.parseInt(resultSet.getString("FHIRid"))
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return account;
    }


}
