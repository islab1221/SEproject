package dao.impl;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;

public class FHIRDaoTest {
    private FHIRDaoImpl fhirDao = new FHIRDaoImpl();


    @Test
    public void getFHIRidListTest(){
        try {
            assertEquals(0, fhirDao.getFHIRidList("manager").get(0).intValue());
        }catch (SQLException e){
            assertEquals("SQL connection failed","");
        }
    }
}
