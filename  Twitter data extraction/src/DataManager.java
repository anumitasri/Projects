import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataManager {

	public void insertData(ArrayList<JobVo> lst) {

		try {
			Connection miConexion;
			miConexion = ConexionDB.GetConnection();
			
			for(JobVo j:lst){

			String sql = "INSERT INTO job(title,hashtag,description,twitter_user,twitter_link) values(?,?,?,?,?)";

			PreparedStatement preparedStatement = miConexion.prepareStatement(sql);
			preparedStatement.setString(1, j.getTitle());
			preparedStatement.setString(2, j.getHashtag());
			preparedStatement.setString(3, j.getDescription());
			preparedStatement.setString(4, j.getTwitterUser());
			preparedStatement.setString(5, j.getTwiterLink());

			preparedStatement.executeUpdate();

			
			}
			
			miConexion.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void upDate() {
		// TODO Auto-generated method stub
		Connection miConexion;
		miConexion = ConexionDB.GetConnection();
		
		

		String sql = "delete from job where title like '%error%' or title like '%cookies%' or title like '%index%' or title='' or title like '%Please%' ";
		
		Statement st;
		try {
			st = miConexion.prepareStatement(sql);
			st.executeUpdate(sql);
			System.out.println("UPDATING....");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

}
	
}