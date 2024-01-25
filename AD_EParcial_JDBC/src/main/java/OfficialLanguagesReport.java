
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OfficialLanguagesReport {

    private static final String url = "jdbc:mysql://localhost:3306/world";
    private static final String user = "world";
    private static final String password = "world";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
        	String nombreContinente = getNombreContinente();
        	
            System.out.println(nombreContinente);
            listaPantalla(conn,nombreContinente);

        }catch (SQLException e) {
        	e.printStackTrace();
        } 
    }
    

    private static String getNombreContinente() {
        System.out.println("Introduce el nombre del continente para ver sus idiomas oficiales: ");
        return Consola.readString();
        
    }

    
    private static void listaPantalla(Connection conn, String nombreContinente) throws SQLException {
        String sql2 = "SELECT u.name, c.Language, c.Percentage " 
                + "FROM countrylanguage c " 
                + "JOIN country u ON c.CountryCode = u.Code " 
                + "WHERE c.IsOfficial = 'T' && u.continent = ?" ;        
       
        try (PreparedStatement sentencia = conn.prepareStatement(sql2)){
        	sentencia.setString(1, nombreContinente);
        
            try(ResultSet rs = sentencia.executeQuery()) {
        	
        	System.out.println(" Pais                        | Idioma Oficial|  % Hablantes ");
            System.out.println("-----------------------------|---------------|--------------");
        	
            while (rs.next()) {
            	String pais =  rs.getString(1);
            	String idioma = rs.getString(2);
            	float hablantes = rs.getFloat(3);
            	
                
            	System.out.println(pais + "               "+ idioma + "          "+ hablantes);
            	
            } rs.close();
        }
        }
    } 
} 

    



