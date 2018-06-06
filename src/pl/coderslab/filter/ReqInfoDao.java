package pl.coderslab.filter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pl.coderslab.utils.DbUtil;

public class ReqInfoDao {

	public static void save(ReqInfo reqinfo) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement1 = null;
		ResultSet rs = null;

		if (reqinfo.getId() == 0) {
			String sql1 = "INSERT INTO ReqInfo(browser, dataczas, reqtime, ipaddress) VALUES (?, ?, ?, ?)";
			String generatedColumns1[] = { "ID" };
			preparedStatement1 = conn.prepareStatement(sql1, generatedColumns1);
			preparedStatement1.setString(1, reqinfo.getBrowser());
			preparedStatement1.setString(2, reqinfo.getdataCzas());
			preparedStatement1.setInt(3, reqinfo.getReqTime());
			preparedStatement1.setString(4, reqinfo.getIpAddress());
			preparedStatement1.executeUpdate();
			rs = preparedStatement1.getGeneratedKeys();
			if (rs.next()) {
				reqinfo.setId(rs.getInt(1));
			}
		}
		rs.close();
		rs = null;
		preparedStatement1.close();
		preparedStatement1 = null;
		conn.close();
		conn = null;
	}
}
