package de.marcey.serverapi.mysql;

import de.marcey.serverapi.mysql.impl.SQLData;

public class DataProvider {

    private MySQL mySQL;
    private SQLData data;

    public void setSQL(MySQL mySQL) {
        this.mySQL = mySQL;
    }

    public MySQL getSQL() {
        return mySQL;
    }

    public void setData(SQLData data) {
        this.data = data;
    }

    public SQLData getData() {
        return data;
    }
}
