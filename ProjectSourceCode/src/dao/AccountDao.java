package dao;

import model.Account;

import java.sql.SQLException;

public interface AccountDao {
    Account getAccountByRegNumber(String regNumber) throws SQLException;

}
