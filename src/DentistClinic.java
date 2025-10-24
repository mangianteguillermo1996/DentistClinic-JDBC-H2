import org.apache.log4j.Logger;
import org.h2.command.Prepared;

import java.sql.*;

public class DentistClinic {

    private static final Logger LOGGER = Logger.getLogger(DentistClinic.class);

    private static final String SQL_CREATE = """
            DROP TABLE IF EXISTS DentistClinic;
            CREATE TABLE DentistClinic (
                ID INT PRIMARY KEY,
                REGISTRATION INT NOT NULL,
                FIRST_NAME VARCHAR(100) NOT NULL ,
                LAST_NAME VARCHAR(100) NOT NULL)
            """;

    private static final String SQL_INSERT_VALUE= """
            INSERT INTO DentistClinic (ID, REGISTRATION, FIRST_NAME, LAST_NAME) 
            VALUES (?,?,?,?)
            """;

    private static final String SQL_SELECT_VALUE= "SELECT * FROM DentistClinic";

    private static final String SQL_UPDATE= "UPDATE DentistClinic SET FIRST_NAME = ? WHERE ID = ? ";

    private static final String SQL_SELECT_ID= "SELECT * FROM DentistClinic WHERE ID = ?";

    private static final String SQL_DELETE= "DELETE FROM DentistClinic WHERE ID = ? ";


    static void main(String[] args) {

        Dentist destist1 = new Dentist(1,10001, "Juan", "Perez");
        Dentist destist2 = new Dentist(2,10002, "Alberto", "Juarez");

        Connection connection = null;

        try {
            connection = getConnection();

            //CREAR TABLA
            Statement statement = connection.createStatement();
            statement.execute(SQL_CREATE);

            //INSERTAR VALORES
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_VALUE);
            preparedStatement.setInt(1, destist1.getId());
            preparedStatement.setInt(2, destist1.getRegistration());
            preparedStatement.setString(3, destist1.getFirstName());
            preparedStatement.setString(4, destist1.getLastName());
            preparedStatement.execute();

            preparedStatement.setInt(1, destist2.getId());
            preparedStatement.setInt(2, destist2.getRegistration());
            preparedStatement.setString(3, destist2.getFirstName());
            preparedStatement.setString(4, destist2.getLastName());
            preparedStatement.execute();


            //CONSULTAR CARGA DE LOS DATOS
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_VALUE);
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + ", " + resultSet.getString(2) + ", " + resultSet.getString(3));
                LOGGER.info(resultSet.getInt(1) + ", " + resultSet.getString(2) + ", " + resultSet.getString(3));
            }

            //ACTUALIZAR DATOS
            connection.setAutoCommit(false);

            PreparedStatement psUpdate = connection.prepareStatement(SQL_UPDATE);
            String nameUpdate = "Matias";
            psUpdate.setString(1, nameUpdate);
            psUpdate.setInt(2, destist2.getId());
            psUpdate.execute();

            connection.commit();
            LOGGER.warn("Se actualizó el ID " + destist2.getId() + " en la base de datos");
            connection.setAutoCommit(true);

            //BORRAR UN REGISTRO
            connection.setAutoCommit(false);

            PreparedStatement psDelete = connection.prepareStatement(SQL_DELETE);
            Integer idDelete = destist1.getId();
            psDelete.setInt(1, idDelete);
            psDelete.execute();

            connection.commit();
            LOGGER.warn("Se borró el registró " + destist1.getId() + " en la base de datos");
            connection.setAutoCommit(true);



        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
                LOGGER.error(ex.getMessage(), ex);
            }
            e.printStackTrace();

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        //CONSULTA CAMBIOS
        try {
            connection = getConnection();
            PreparedStatement ps2 = connection.prepareStatement(SQL_SELECT_ID);
            ps2.setInt(1, destist2.getId());


            //MOSTRAR CAMBIOS
            ResultSet resultSet2 = ps2.executeQuery();
            while (resultSet2.next()) {
                System.out.println(resultSet2.getInt(1) + ", " + resultSet2.getString(2) + ", " + resultSet2.getString(3));
                LOGGER.info(resultSet2.getInt(1) + ", " + resultSet2.getString(2) + ", " + resultSet2.getString(3));
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //CONSULTAR REGISTRO BORRADO

        try {
            connection = getConnection();

            Statement s = connection.createStatement();
            ResultSet resultSet2 = s.executeQuery(SQL_SELECT_VALUE);
            while (resultSet2.next()) {
                System.out.println(resultSet2.getInt(1) + ", " + resultSet2.getString(2) + ", " + resultSet2.getString(3));
                LOGGER.info(resultSet2.getInt(1) + ", " + resultSet2.getString(2) + ", " + resultSet2.getString(3));
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



    }



    private static Connection getConnection() throws Exception {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/dentistClinic", "sa", "");
    }

}
