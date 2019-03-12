package autoMeter;

/*database  of  AutoMeter */
import java.sql.SQLException;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;
class AutoMeterData {
	private int appNo;
	private String appName="";
	private long appData;
	private long currentTime;
	private long endTime;
	
	public void setAppNo(int appNo)
	{
		this.appNo=appNo;	
	}
	public int getAppNo()
	{
		return this.appNo;
	}
	
	public void setAppName(String appName)
	{
		this.appName=appName;	
	}
	public String getAppName()
	{
		return this.appName;
	}
	
	public void setAppData(long appData)
	{
		this.appData=appData;
	}
	public long getAppData()
	{
		return appData;
	}
	
	public void setCurrentTime(long currentTime)
	{
		this.currentTime=currentTime;
	}
	public long getCurrentTime()
	{
		return currentTime;
	}
	public void setEndTime(long endTime)
	{
		this.endTime=endTime;
	}
	public long getEndTime()
	{
		return endTime;
	}
	
}



 class DBConnection
{
	private final static String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=AutoMeter";
    private static final String USER="sa";
    private static final String PASSWORD="123456";
    String sql;
    public  static Connection conn=null;
    //静态代码块（将加载驱动、连接数据库放入静态块中）
    public static void initConn ()
    {
        try {
            //1.加载驱动程序
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //2.获得数据库的连接
            conn=(Connection)DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
    	if ( conn == null )
    		initConn () ;
        return conn;
    }
    
}


 




public class DataBaseOperation {
    //测试用例
	public static void initializeDB(){
        Connection conn = null;
        Statement st = null;
        
        String sql="use master ;if not exists (select 8 from sysdatabases where name='AutoMeter') create database AutoMeter;";
        					

		try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433", "sa", "123456");
            st = conn.createStatement();
            st.execute(sql);
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                if(conn!=null)conn.close();
                if(st!=null)st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	    
	    public static void initializeTABLE(){
	        Connection conn = null;
	        Statement st = null;
	        
	        String sql=			"use AutoMeter "
	        					+"if OBJECT_ID ( N'data' , N'U' ) is null"
	        					+" create table data("
	        					+" appNo char(9),"
	        					+" appName char(48)primary key,"
	        					+" appData char(65),"
	        					+" currentTime bigint,"
	        					+" endTime bigint,"
	        					+" appLocation char(48),"
                 				+" )"	;	
			try {
	            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433", "sa", "123456");
	            st = conn.createStatement();
	            st.execute(sql);
	        } catch(ClassNotFoundException e){
	            e.printStackTrace();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally{
	            try {
	                if(conn!=null)conn.close();
	                if(st!=null)st.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    
	   
	    
	    public static void dropTABLE(){
	        Connection conn = null;
	        Statement st = null;
	        
	        String sql=			"use AutoMeter  "
				        		+ "drop table data ";
				        		
			try {
	            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433", "sa", "123456");
	            st = conn.createStatement();
	            st.execute(sql);
	        } catch(ClassNotFoundException e){
	            e.printStackTrace();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally{
	            try {
	                if(conn!=null)conn.close();
	                if(st!=null)st.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    public static void dropDB(){
	        Connection conn = null;
	        Statement st = null;
	        
	        String sql=			"use master "
				        		+ "drop database AutoMeter";	

			try {
	            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433", "sa", "123456");
	            st = conn.createStatement();
	            st.execute(sql);
	        } catch(ClassNotFoundException e){
	            e.printStackTrace();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally{
	            try {
	                if(conn!=null)conn.close();
	                if(st!=null)st.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }    
	    
	    public static void initializeDBandTABLE()
	    {
	    	initializeDB();
	    	initializeTABLE();
	    }
	    
	    public static void dropTABLEandDB()
	    {
	    	dropTABLE();
	    	dropDB();
	    }
	public static int DBManager(String sql) throws SQLException
	/*传递sql语句*/
	{
		
		Statement stmt = DBConnection.conn.createStatement();
        int rs=-1;
        rs= stmt.executeUpdate(sql);
        //System.out.println(rs);
		if(rs<0)
		{
			return 1;
		}
		else
		{
			return 0;
		}
				
	}
	public static int DBManager_ifIn(String sql) throws SQLException
	/*传递sql语句*/
	{
		if ( DBConnection.conn == null )
			DBConnection.initConn();
		Statement stmt = DBConnection.conn.createStatement();
		ResultSet rs = null;  
        rs= stmt.executeQuery(sql);
         if(rs.next())   return 1;
           
       
         else return 0;
				
	}
	
	
	public static ArrayList<String> Query(String sql) throws SQLException{  
		initializeDBandTABLE() ;
		//String sql="select * from data order by appData desc";
		// @SuppressWarnings("rawtypes")
		ArrayList Data=new ArrayList();
	        //PreparedStatement pst = null;  
		if ( DBConnection.conn == null )
			DBConnection.initConn();
		Statement stmt = DBConnection.conn.createStatement();
	        ResultSet rs = null;  
	         
	           // pst = con.prepareStatement(sql);  
	            //rs = pst.executeQuery();  
	        rs= stmt.executeQuery(sql);
	        while(rs.next()){//如果对象中有数据，就会循环打印出来
	                //System.out.println(rs.getInt("appNo")+","+rs.getString("appName")+","+rs.getInt("appData"));
	                //Data.add(rs.getInt("appNo"));
	                Data.add(rs.getString("appName"));
	                
	                Data.add(rs.getString("appData"));
	                //System.out.print(rs.getString("appData"));
	               // System.out.println(rs.getString("appName"));
	            }
	            return Data;
	        
	    }
	public static void InsertString(ArrayList<String> List) throws SQLException
	 {
		 String sql = null;  boolean TOrF;
		 String sql1=null;
		 String chushi="insert into data values(";
		 String chushi1;
		 chushi1="select * from data where appName='";
		 for(int i=0;i<List.size();i+=2)
		 {
/*			 String appNo=  String.valueOf(i);
			 String appName=(String) List.get(i);
			 String appData = (String)List.get(i+1);
			 String currentTime=(String)List.get(i+2);
			 String endTime=(String)List.get(i+3);
			 String appLocation=(String)List.get(i+4);
*/
			 String appName = List.get ( i );
			 String appData = List.get ( i + 1 ) ;
			 String appNo = null ;
			 String currentTime = null ;
			 String endTime = null ;
			 String appLocation = null ;
			sql=chushi+appNo+","+ "'"+appName+"'"+","+appData+","+currentTime+","+endTime+","+"'"+appLocation+"');"; 
			//TOrF=DBUpdate(sql);
			sql1=chushi1+appName+"'"; 
			//System.out.println(sql1);
			if(DBManager_ifIn(sql1)==1)  
				{
				endTime=null;appLocation=null;
				sql=null;appNo=null;appName=null;appData=null;currentTime=null;
				continue;
				}
			 DBManager(sql);
			 endTime=null;appLocation=null;
				sql=null;appNo=null;appName=null;appData=null;currentTime=null;
		 }
		
		 
		 
		 
	 }
	
	public static void UpdateString(ArrayList<String> List) throws SQLException
	 {
		 String sql = null;  boolean TOrF;
		 String chushi="update data set appData=appData+";
		 for(int i=0;i<List.size();i+=2)
		 {
/*			 String appNo=  String.valueOf(i);
			 String appName=(String) List.get(i);
			 String appData = (String)List.get(i+1);
			 String currentTime=(String)List.get(i+2);
			 String endTime=(String)List.get(i+3);
			 String appLocation=(String)List.get(i+4);
*/
			 String appName = List.get ( i ) ;
			 String appData = List.get( i + 1 ) ;
			sql=chushi+appData+"where appName ='"+ appName+"'"; 
			
			//TOrF=DBUpdate(sql);
			//System.out.println(sql);
			DBManager(sql);
//			endTime=null;appLocation=null;
			sql=null;
//			appNo=null;
			appName=null;
			appData=null;
//			currentTime=null;
		 }
		 	 
	 }
	public static void InToDB(ArrayList <String> List) throws SQLException 
	{
		initializeDBandTABLE() ;
		String sql=null;
		int rs=-1;
        String chushi =null;
        chushi="select * from data where appName='";
        for(int i=0;i<List.size();i += 2)
		 {
/*			 String appNo=  String.valueOf(i);
			 String appName=(String) List.get(i);
			 String appData = (String)List.get(i+1);
			 String currentTime=(String)List.get(i+2);
			 String endTime=(String)List.get(i+3);
			 String appLocation=(String)List.get(i+4);
*/
        	String appName = List.get ( i ) ;
        	String appData = List.get ( i + 1 ) ;
			sql=chushi+appName+"'"; 
//        	sql =chushi+ appName ;
			//System.out.println(sql);
			rs=DBManager_ifIn(sql);
			//System.out.println(rs);
			if(rs==1)
			{
				UpdateString(List);

			}
			else
			{
				InsertString(List);
			}
//			endTime=null;appLocation=null;
			sql=null;
//			appNo=null;
			appName=null;
			appData=null;
//			currentTime=null;
		 }
	}
	
	public static void truncateTable() throws SQLException
	{
		initializeDBandTABLE() ;
		String sql=null;
		int rs=-1;
        String chushi =null;
        chushi="truncate table data";
        DBManager(chushi);	
	}
/*	
	public static boolean SearchDB() throws SQLException
	{
		int rs=-1;
        String chushi ="select name From master.dbo.sysdatabases where name='AutoMeter'";
        rs=DBManager_ifIn(chushi);        
        if(rs==1) 	return true;        
        else		return false; 
	}
*/
    public static void main(String[] args) throws Exception{
/*  	
    	boolean test ;
    	test = SearchDB () ;
    	System.out.println(test);
 	
    
    	ArrayList list=new ArrayList();
    	ArrayList Data=new ArrayList();
    	
    	
    	
    	
    	list.add("ori1");
    	list.add("256");
    	list.add("256");
    	list.add("256");
    	list.add("ksajdfjds");
    	
    	list.add("ori12");
    	list.add("12");
    	list.add("18");
    	list.add("256");
    	list.add("kdfsdfs");
    	
    	list.add("ori14");
    	list.add("120");
    	list.add("18");
    	list.add("256");
    	list.add("kdfsdfs");
    	
    	list.add("ori14497");
    	list.add("120");
    	list.add("18");
    	list.add("256");
    	list.add("kdfsdfs");
    	
    	list.add("oriasf");
    	list.add("1");
    	list.add("18");
    	list.add("256");
    	list.add("kdf");
    	
    	
    	
    	
    	
    	
    	
    	
    	initializeDBandTABLE();  
    	InToDB(list);
    	
                        //  create database && insert
    	
    	
    	
    	
    	
    	//  Query
    	Data=Query("select * from data order by appData desc");    System.out.println(Data);
       
    	
    	
    	
    	//drop database
    	
    	
    	//truncateTable();
    	
    	
    	
    	//dropTABLEandDB();
*/
    	initializeDB () ;
    }  

	
}






