package model;

import java.sql.SQLException;

public class Account implements Authenticate{
    private boolean exist;

    private String id_account;
    private String password;
    private String permission;
    private int FHIRid;

    public Account(String id_account,String password,String permission,int FHIRid) throws SQLException {

        this.id_account = id_account;
        this.password = password;
        this.permission = permission;
        this.FHIRid = FHIRid;
        this.exist = true;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public boolean isExist() {
        return exist;
    }

    public String getPermission(){return permission;}

    public int getFHIRid() {
        return FHIRid;
    }

    public String getId_account() {
        return id_account;
    }
}
