package tsp.pro11.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Classe d'accès pour une base de données sqlite.
 * @author Eric Lallet.
 * 
 */
public class DataBase {
	/**
	 * Connect to a sample database
	 */
	private String url = "pas initialisé"; 

	/**
	 * Constructeur qui associe la base à une URL.
	 * @param url 
	 *           url de la base de données.         
	 */
	public DataBase(String url)  {
		this.url = url;

	}

	
	/**
	 * Ouvre une connexion sur la base de donnée. Dans le cas se sqlite, on ne doit ouvrir qu'une
	 * seule connexion en même temps.
	 * @return
	 *         La connexion pour pouvoir faire les requêtes.
	 * @throws SQLException
	 *         Exceptions levées en cas d'erreur lors de la connexion.
	 */
	public Connection connect() throws SQLException {
		return DriverManager.getConnection(url);

	}


	/**
	 * getter sur l'url de la base de données.
	 * @return
	 *        l'url de la base de données.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Réalise une requête d'insertion. A la fin de la méthode la connexion à la base et la requête
	 * sont fermées: la base sqlite n'est donc pas bloqué pour d'autre requêtes.
	 * @param insertSql
	 *        la requête d'insertion.
	 * @throws SQLException
	 *          Exceptions levées en cas d'erreur lors de la connexion ou l'insertion.
	 */
	public void insert(String insertSql) throws SQLException {
		try (Connection conn = this.connect();
				PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {	// utilisation du autocloseable pour fermer la connexion automatiquement.

			insertStmt.executeUpdate();
		} 
	}
	
	
	/**
	 * Réalise une requête de suppression. A la fin de la méthode la connexion à la base et la requête
	 * sont fermées: la base sqlite n'est donc pas bloqué pour d'autre requêtes.
	 * @param deleteSql
	 *        la requête de supression.
	 * @throws SQLException
	 *          Exceptions levées en cas d'erreur lors de la connexion ou la supression.
	 */
	public void delete(String deleteSql) throws SQLException {
		this.insert(deleteSql); // même code qu'insert
	}
	

	/**
	 * Calcule le résultat d'une requête SQL. Le résultat de la méthode peut être parcourru une fois.
	 * Cette méthode ne ferme pas la connexion à la base. Il faudra appeler le méthode close() sur le
	 * résultat pour que la base de données ne soit pas bloquée.
	 * @param querySql
	 *        la requête à exécuter. 
	 * @return
	 *        le résultat de la requête. Il faut appelet sa méthode close() pour débloquer l'accès la base de donnée.
	 * @throws SQLException
	 *          Exceptions levées en cas d'erreur lors de la connexion ou l'exécution de la requête.
	 */
	public ResultSet query(String querySql) throws SQLException {
		ResultSet result ;
		Connection conn = this.connect();
		Statement stmt  = conn.createStatement();
		result   = stmt.executeQuery(querySql);
		
		// on quitte la méthode sans avoir fermé la connexion. Tant que le résultat n'est pas fermé
		// la base est lockée. Aucune modification ne fonctionnera.
		return result;
	}


}
