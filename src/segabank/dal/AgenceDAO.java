package segabank.dal;

import segabank.bo.Agence;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgenceDAO implements IDAO<Integer, Agence> {
    private static final String INSERT_QUERY = "INSERT INTO agence (Code, Adresse) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE agence SET Code = ?, Adresse = ? WHERE agence.Id = ?";
    private static final String DELETE_QUERY = "DELETE FROM agence WHERE agence.Id = ?";
    private static final String QUERY_ALL = "SELECT Id, Code, Adresse FROM agence;";
    private static final String QUERY_ID = "SELECT Id, Code, Adresse FROM agence WHERE Id = ?";

    @Override
    public void create(Agence agence) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1, agence.getCode());
                ps.setString(2, agence.getAdresse());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        agence.setId(rs.getInt(1));
                    }
                }
            }
        }
    }

    @Override
    public void update(Agence agence) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY)) {
                ps.setString(1, agence.getCode());
                ps.setString(2, agence.getAdresse());
                ps.setInt(3, agence.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void delete(Agence agence) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();

        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(DELETE_QUERY)) {
                ps.setInt(1, agence.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public Agence findById(Integer id) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        Agence agence = null;

        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(QUERY_ID)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    agence = new Agence(rs.getInt("Id"), rs.getString("Code"), rs.getString("Adresse"));
                }
            }
        }
        return agence;
    }

    @Override
    public List<Agence> findAll() throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        List<Agence> agences = new ArrayList<>();
        CompteDAO compteDAO = new CompteDAO();
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(QUERY_ALL)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Agence agence = new Agence(rs.getInt("Id"), rs.getString("Code"), rs.getString("Adresse"));
                    compteDAO.getForAgence(agence);
                    agences.add(agence);
                }
            }
        }
        return agences;
    }
}
