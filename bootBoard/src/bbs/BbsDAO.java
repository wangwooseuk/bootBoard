package bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BbsDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private int widthBlock = 5; //한블럭의 크기
	private int pageRows = 10; //한페이지에 노출되는 행의 수
	
	public BbsDAO() {
		try {
			Context init = new InitialContext();
			DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/bbsDB");
			conn = ds.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void connClose() {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDate() {
		String sql = "select now()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ""; //데이터베이스 오류 코드
	}
	
	public int getNext() {
		String sql = "select bbsId from bbs order by bbsId desc";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; //첫 번째 게시물
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류 코드
	}
	
	public int write(String bbsTitle, String userId, String bbsContent) {
		String sql = "insert into bbs values (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userId);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6, 1);
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류 코드
	}
	
	public Vector<Bbs> getList(int pageNumber) {
		String sql = "select * from bbs where bbsId < ? and bbsAvailable = 1 order by bbsId desc limit " + getPageRows();
		Vector<Bbs> list = new Vector<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * getPageRows());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsId(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserId(rs.getString(3)); 
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5)); 
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean nextPage(int pageNumber) { //리턴값의 true, false를 변경
		String sql = "select * from bbs where bbsId < ? and bbsAvailable = 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * getPageRows());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public Bbs getBbs(int bbsId) {
		String sql = "select * from bbs where bbsId = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bbsId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsId(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserId(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				return bbs;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(int bbsId, String bbsTitle, String bbsContent) {
		String sql = "update bbs set bbsTitle = ?, bbsContent = ? where bbsId = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bbsTitle);
			pstmt.setString(2, bbsContent);
			pstmt.setInt(3, bbsId);
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류 코드
	}
	
	public int delete(int bbsId) {
		String sql = "update bbs set bbsAvailable = 0 where bbsId = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bbsId);
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류 코드
	}
	
	public int getWidthBlock() {
		return widthBlock;
	}

	public int getPageRows() {
		return pageRows;
	}
	
	public int getViewList() {
		String sql = "select count(*) from bbs where bbsAvailable = 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
			return 0; // row가 없으면 0을 리턴
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int totalBlock() { //전체 블록의 수
		if(getViewList() % (widthBlock * pageRows) > 0) {
			return getViewList() / (widthBlock * pageRows) + 1;
		}
		return getViewList() / (widthBlock * pageRows);
	}
	
	public int currentBlock(int pageNumber) { //현재 블록의 수
		if(pageNumber % widthBlock > 0) {
			return pageNumber / widthBlock + 1;
		}
		return pageNumber / widthBlock;
	}
	
	public int totalPage() { //전체 페이지 수를 계산하는 메소드
		if(getViewList() % pageRows > 0) {
			return getViewList() / pageRows + 1;
		}
		return getViewList() / pageRows;
	}
}
