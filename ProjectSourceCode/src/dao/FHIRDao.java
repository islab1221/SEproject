package dao;

import java.sql.SQLException;
import java.util.List;

public interface FHIRDao {

    public List<Integer> getFHIRidList(String userType) throws SQLException;
}
