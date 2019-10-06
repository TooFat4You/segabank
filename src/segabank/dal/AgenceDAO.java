package segabank.dal;

import segabank.bo.*;

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
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(QUERY_ALL)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Agence agence = new Agence(rs.getInt("Id"), rs.getString("Code"), rs.getString("Adresse"));
                    agences.add(agence);
                }
            }
        }
        return agences;
    }

    public List<Agence> buildFull() throws SQLException, IOException, ClassNotFoundException {
        CompteDAO compteDAO = new CompteDAO();
        List<Agence> agences = findAll();
        List<Compte> comptes = compteDAO.buildFull();
        fillRelations(agences, comptes);
        return agences;
    }

    public void fillRelations(List<Agence> agences, List<Compte> comptes) {
        for (Agence agence : agences) {
            for (Compte compte : comptes) {
                if (compte.getIdAgence() == agence.getId()) {
                    compte.setAgence(agence);
                    switch (compte.getType()) {
                        case epargne:
                            agence.getCompteEpargnes().add((CompteEpargne) compte);
                            break;
                        case payant:
                            agence.getComptePayants().add((ComptePayant) compte);
                            break;
                        case simple:
                            agence.getCompteSimples().add((CompteSimple) compte);
                    }
                }
            }
        }
    }

    public void updateRelations(List<Agence> agences) {
        List<Compte> comptes = new ArrayList<>();
        for (Agence agence : agences) {
            comptes.addAll(agence.getComptes());
        }

        CompteDAO compteDAO = new CompteDAO();
        compteDAO.updateRelations(comptes);

        for (Agence agence : agences) {
            for (Compte compte : comptes) {
                if (compte.getAgence().getId() != compte.getIdAgence() && compte.getIdAgence() == agence.getId()) {
                    switch (compte.getType()) {
                        case epargne:
                            compte.getAgence().getCompteEpargnes().remove(compte);
                            agence.getCompteEpargnes().add((CompteEpargne) compte);
                            break;
                        case payant:
                            compte.getAgence().getComptePayants().remove(compte);
                            agence.getComptePayants().add((ComptePayant) compte);
                            break;
                        case simple:
                            compte.getAgence().getCompteSimples().remove(compte);
                            agence.getCompteSimples().add((CompteSimple) compte);
                            break;
                    }
                    compte.setAgence(agence);
                }
            }
        }
        Agence.setAgences(agences);
    }
}
