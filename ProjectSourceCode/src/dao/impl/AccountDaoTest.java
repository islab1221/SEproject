package dao.impl;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;

public class AccountDaoTest {

    private AccountDaoImpl accountDao = new AccountDaoImpl();

    @Test
    public void getAccountByRegNumberTest(){
        try {
            assertEquals("admin", accountDao.getAccountByRegNumber("admin").getId_account());
        }catch (SQLException e){
            assertEquals("SQL connection failed","");
        }
    }

}
