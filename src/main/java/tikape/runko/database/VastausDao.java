package tikape.runko.database;

import java.util.*;
import java.sql.*;
import tikape.runko.domain.Vastaus;

public class VastausDao implements Dao<Vastaus, Integer>{
    private Database database;

    public VastausDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Vastaus findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        Vastaus t = new Vastaus(rs.getInt("id"), rs.getString("vastausteksti"), rs.getBoolean("oikein"));
        stmt.close();
        rs.close();

        conn.close();

        return t;
    }
    @Override
    public List<Vastaus> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus");
        ResultSet rs = stmt.executeQuery();
        
        List<Vastaus> vastaukset = new ArrayList<>();
        while(rs.next()) {
            Vastaus t = new Vastaus(rs.getInt("id"), rs.getString("vastausteksti"), rs.getBoolean("oikein"));
  
            vastaukset.add(t);
        }
        stmt.close();
        rs.close();

        conn.close();
        
        
        return vastaukset;
    }
    @Override
    public Vastaus saveOrUpdate(Vastaus vastaus) throws SQLException {
        if (vastaus.getId() == null) {
            return save(vastaus);
        } else {
            return update(vastaus);
        }
    }
    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vastaus WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    private Vastaus save(Vastaus vastaus) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vastaus"
                + " (vastausteksti, oikein)"
                + " VALUES (?, 0 )");
        stmt.setString(1, vastaus.getVastausteksti());
        
              
        stmt.executeUpdate();
        stmt.close();
        
        return null;
    }
    private Vastaus update(Vastaus vastaus) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE Vastaus SET"
                + " vastausteksti = ?, oikein = ? WHERE id = ?");
        stmt.setString(1, vastaus.getVastausteksti());
        stmt.setBoolean(2, vastaus.getOikein());
        stmt.setInt(3, vastaus.getId());
        

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return vastaus;
    }
    public List<Vastaus> findAllVastaukset(Integer kysymysId) throws SQLException {
        String query = "SELECT Vastaus.id, Vastaus.vastausteksti FROM Vastaus, Kysymys\n"
        + "              WHERE Vastaus.id = Vastaus.kysymys_id "
        + "                  AND Vastaus.kysymys_id = ?\n";

        List<Vastaus> vastaukset = new ArrayList<>();

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, kysymysId);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                vastaukset.add(new Vastaus(result.getInt("id"), result.getString("vastausteksti"), result.getBoolean(0)));
            }
        }

        return vastaukset;
    }
}
