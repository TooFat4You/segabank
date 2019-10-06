package segabank.dal;

import segabank.bo.Operation;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperationDAO implements IDAO<Integer, Operation> {
    private static final String CREATE = "INSERT INTO operation (Montant, Date, Type, IdCompte) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE operation SET Montant = ?, Date = ?, Type = ?, IdCompte = ? WHERE operation.Id = ?";
    private static final String DELETE = "DELETE FROM operation WHERE operation.Id = ?";
    private static final String QUERY_ID = "SELECT Id, Montant, Date, Type, IdCompte FROM operation WHERE Id = ?";
    private static final String QUERY_ALL = "SELECT Id, Montant, Date, Type, IdCompte FROM operation";

    private Operation buildOperation(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("Id");
        Double montant = rs.getDouble("Montant");
        Date date = rs.getDate("Date");
        Operation.TypeOperation typeOperation = Operation.TypeOperation.valueOf(rs.getString("Type"));
        Integer idCompte = rs.getInt("IdCompte");
        Operation operation = new Operation(id, montant, date, typeOperation, idCompte);
        return operation;
    }

    @Override
    public void create(Operation operation) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
                ps.setDouble(1, operation.getMontant());
                ps.setTimestamp(2, new Timestamp(operation.getDate().getTime()));
                ps.setString(3, operation.getTypeOperation().getLabel());
                ps.setInt(4, operation.getIdCompte());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        operation.setId(rs.getInt(1));
                    }
                }
            }
        }
    }

    @Override
    public void update(Operation operation) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
                ps.setDouble(1, operation.getMontant());
                ps.setTimestamp(2, new Timestamp(operation.getDate().getTime()));
                ps.setString(3, operation.getTypeOperation().getLabel());
                ps.setInt(4, operation.getIdCompte());
                ps.setInt(5, operation.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void delete(Operation operation) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        if (connection != null) {
            try (PreparedStatement ps = connection.prepareStatement(DELETE)) {
                ps.setInt(1, operation.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public Operation findById(Integer id) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        Operation operation = null;
        try (PreparedStatement ps = connection.prepareStatement(QUERY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.first()) {
                    operation = buildOperation(rs);
                }
            }
        }
        return operation;
    }

    @Override
    public List<Operation> findAll() throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        List<Operation> operations = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(QUERY_ALL)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    operations.add(buildOperation(rs));
                }
            }
        }
        Operation.setOperations(operations);
        return operations;
    }


}
