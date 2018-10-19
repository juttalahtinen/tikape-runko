package tikape.runko.database;

import java.util.*;
import java.sql.*;
import tikape.runko.domain.Kysymys;

public class KysymysDao implements Dao<Kysymys, Integer>{
    private Database database;

    public KysymysDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Kysymys findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        Kysymys t = new Kysymys(rs.getInt("id"), rs.getString("kurssi"), rs.getString("aihe"), rs.getString("kysymysteksti"));
        stmt.close();
        rs.close();

        conn.close();

        return t;
    }
    @Override
    public List<Kysymys> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys");
        ResultSet rs = stmt.executeQuery();
        
        List<Kysymys> kysymykset = new ArrayList<>();
        while(rs.next()) {
            Kysymys t = new Kysymys(rs.getInt("id"), rs.getString("kurssi"), rs.getString("aihe"), rs.getString("kysymysteksti"));
  
            kysymykset.add(t);
        }
        stmt.close();
        rs.close();

        conn.close();
        
        
        return kysymykset;
    }
    @Override
    public Kysymys saveOrUpdate(Kysymys kysymys) throws SQLException {
        if (kysymys.getId() == null) {
            return save(kysymys);
        } else {
            // muulloin päivitetään asiakas
            return update(kysymys);
        }
    }
    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kysymys WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    private Kysymys save(Kysymys kysymys) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kysymys"
                + " (kurssi, aihe, kysymysteksti)"
                + " VALUES (?, ? , ?)");
        stmt.setString(1, kysymys.getKurssi());
        stmt.setString(2, kysymys.getAihe());
        stmt.setString(3, kysymys.getKysymysteksti());
        
        

        stmt.executeUpdate();
        stmt.close();
        
        return null;
    }
    private Kysymys update(Kysymys kysymys) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE Kysymys SET"
                + " kurssi = ?, aihe = ?, kysymysteksti = ? WHERE id = ?");
        stmt.setString(1, kysymys.getKurssi());
        stmt.setString(2, kysymys.getAihe());
        stmt.setString(3, kysymys.getKysymysteksti());
        stmt.setInt(4, kysymys.getId());

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return kysymys;
    }
    
}

