package segabank.dal;

import segabank.bo.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteDAO implements IDAO<Integer, Compte> {
    private static final String INSERT = "INSERT INTO compte (IdAgence, Type, Solde, Decouvert, TauxInteret, DateCreation) VALUES (?, ?, ?, ?, ?, current_timestamp());";
    private static final String UPDATE = "UPDATE compte SET IdAgence = ?, Type = ?, Solde = ?, Decouvert = ?, TauxInteret = ? WHERE compte.Id = ?";
    private static final String DELETE = "DELETE FROM compte WHERE compte.Id = ?";
    private static final String QUERY_ALl = "SELECT Id, IdAgence, Type, Solde, Decouvert, TauxInteret, DateCreation FROM compte;";
    private static final String QUERY_ID_AGENCE = "SELECT Id, Type, Solde, Decouvert, TauxInteret, DateCreation FROM compte WHERE IdAgence = ?";

    private void buildPreparedStatement(PreparedStatement ps, Compte compte) throws SQLException {
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
    }

    private Compte buildCompte(ResultSet rs, Agence agence) throws SQLException {
        Compte compte = null;
        Compte.Etat etat = Compte.Etat.valueOf(rs.getString("Type"));
        Integer id = rs.getInt("Id");
        Double solde = rs.getDouble("Solde");
        Date dateCreation = rs.getDate("DateCreation");

        switch (etat) {
            case epargne:
                CompteEpargne cptE = new CompteEpargne(id, solde, dateCreation, agence, rs.getDouble("TauxInteret"));
                agence.getCompteEpargnes().add(cptE);
                compte = cptE;
                break;
            case simple:
                CompteSimple cptS = new CompteSimple(id, solde, dateCreation, agence, rs.getDouble("Decouvert"));
                agence.getCompteSimples().add(cptS);
                compte = cptS;
                break;
            case payant:
                ComptePayant cptP = new ComptePayant(id, solde, dateCreation, agence);
                agence.getComptePayants().add(cptP);
                compte = cptP;
        }
        return compte;
    }

    private Compte buildFullCompte(ResultSet rs, List<Agence> agences, AgenceDAO agenceDAO) throws SQLException, IOException, ClassNotFoundException {
        Compte compte = null;
        if (rs.next()) {
            Integer idAgence = rs.getInt("IdAgence"); // récuperation l'objet Agence correspondant à l'id d'agence
            Agence agence = null;
            for (Agence agc : agences) {
                if (agc.getId() == idAgence) { // si présent dans la collection existante, alors
                    agence = agc; // agence est égale à l'existente
                    break;
                }
            }
            if (agence == null) { // sinon: interrogation
                agence = agenceDAO.findById(idAgence);
                agences.add(agence); // puis ajout dans la collection existante
            }
            compte = buildCompte(rs, agence);
        }
        return compte;
    }

    @Override
    public void create(Compte compte) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                buildPreparedStatement(ps, compte);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        compte.setId(rs.getInt(1));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Compte compte) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
                buildPreparedStatement(ps, compte);
                ps.setInt(6, compte.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void delete(Compte compte) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(DELETE)) {
                ps.setInt(1, compte.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public Compte findById(Integer integer) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    public void getForAgence(Agence agence) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        List<Compte> comptes = new ArrayList<>();
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(QUERY_ID_AGENCE)) {
                ps.setInt(1, agence.getId());
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    buildCompte(rs, agence);
                }
            }
        }
    }

    @Override
    public List<Compte> findAll() throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        List<Compte> comptes = new ArrayList<>();
        if (connection != null) {
            List<Agence> agences = new ArrayList<>();
            try (PreparedStatement ps = connection.prepareStatement(QUERY_ALl)) {
                ResultSet rs = ps.executeQuery();
                AgenceDAO agenceDAO = new AgenceDAO();

                while (rs.next()) {
                    comptes.add(buildFullCompte(rs, agences, agenceDAO));
                }
                rs.close();
            }
        }
        return comptes;
    }
}
