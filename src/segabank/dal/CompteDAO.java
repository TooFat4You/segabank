package segabank.dal;

import segabank.bo.Agence;
import segabank.bo.Compte;
import segabank.bo.CompteEpargne;
import segabank.bo.CompteSimple;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class CompteDAO implements IDAO<Integer, Compte> {
    private static final String INSERT = "INSERT INTO compte (IdAgence, Type, Solde, Decouvert, TauxInteret, DateCreation) VALUES (?, ?, ?, ?, ?, current_timestamp());";

    private PreparedStatement buildPreparedStatement(PreparedStatement ps, Compte compte) throws SQLException {
        Compte.Etat type = compte.getType();
        ps.setInt(1, compte.getAgence().getId());
        ps.setString(2, type.getLabel());
        ps.setDouble(3, compte.getSolde());
        ps.setString(4, "NULL");
        ps.setString(5, "NULL");
        switch (type) {
            case simple:
                ps.setDouble(4, ((CompteSimple)compte).getDecouvert());
                break;
            case epargne:
                ps.setDouble(5, ((CompteEpargne)compte).getTauxInteret());
        }
        return ps;
    }

    @Override
    public void create(Compte compte) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        if (connection != null) {
            try {
                PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                Compte.Etat type = compte.getType();
                ps.setInt(1, compte.getAgence().getId());
                ps.setString(2, type.getLabel());
                ps.setDouble(3, compte.getSolde());
                ps.setString(4, null);
                ps.setString(5, null);
                switch (type) {
                    case simple:
                        ps.setDouble(4, ((CompteSimple)compte).getDecouvert());
                        break;
                    case epargne:
                        ps.setDouble(5, ((CompteEpargne)compte).getTauxInteret());
                }
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        compte.setId(rs.getInt(1));
                    }
                }
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Compte object) throws SQLException, IOException, ClassNotFoundException {

    }

    @Override
    public void delete(Compte object) throws SQLException, IOException, ClassNotFoundException {

    }

    @Override
    public Compte findById(Integer integer) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Compte> findAll() throws SQLException, IOException, ClassNotFoundException {
        return null;
    }
}
