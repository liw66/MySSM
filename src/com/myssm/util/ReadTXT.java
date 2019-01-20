package com.myssm.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
  
/**  
 * 读取TXT数据    
 */ 
public class ReadTXT {   
    @SuppressWarnings("resource")
	public static void main(String arg[]) throws SQLException { 
    	
    	Connection con = null;
        try 
        {   
        	long startTime=System.currentTimeMillis();
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	Date date = new Date(startTime);
        	System.out.println(formatter.format(date));

        	String connectionUrl="jdbc:microsoft:sqlserver://localhost:1433;DatebaseName=gd";//连接字符串
            String strUser = "sa";
            String strPwd ="123456";
            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");//驱动程序包
            con = DriverManager.getConnection(connectionUrl,strUser,strPwd); 
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            String delsql="TRUNCATE table gd.dbo.bank";
            stmt.addBatch(delsql);
            delsql="TRUNCATE table gd.dbo.log";
            stmt.addBatch(delsql);
        	stmt.executeBatch();
        	con.commit();
        	stmt.clearBatch();
            //读入文件
            File file = new File("D:\\LW\\CODE\\cd_pu_cust_cross_20160331_000.dat");   
            if (file.isFile() && file.exists()) 
            {   
               String lineTXT = null; 
               BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file))); 
               int sum = 0; 
               int tsum = 0;
               int fsum = 0;
               LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));  
               lineNumberReader.skip(Long.MAX_VALUE); 
               int lineNumber = lineNumberReader.getLineNumber();  
               System.out.println("总计："+lineNumber);
               while ((lineTXT = br.readLine()) != null) 
               { 
					sum++;  
					String[] a =lineTXT.split("\\|\\+\\|"); 
					if (a.length==61)
					{						
						String insql="insert into gd.dbo.bank values(";//数据库中的格式必须相对应                    			
					
						for(int i=0;i<a.length;i++)
						{
							insql+="'"+a[i]+"',";
						}
						if (insql.length() > 0)
						{
							insql=insql.substring(0, insql.length()-1);
						}
						insql += ")";                    
				        stmt.addBatch(insql); 
				        if (sum % 50000 == 0 || sum == lineNumber)
				        {				        	
				        	tsum+=stmt.executeBatch().length;
				        	con.commit();
				        	stmt.clearBatch();  
				        }
					}	                   
					else  	
					{			                   	
						fsum++;
						String inlog="insert into gd.dbo.log values(";//数据库中的格式必须相对应    
						inlog += "'" + formatter.format(date) + "','" + lineTXT + "')";
				       	stmt.addBatch(inlog); 
					}	                 
               }                 
               br.close(); 
               stmt.close();  
               System.out.println("处理："+ sum);  
               System.out.println("成功："+(tsum-fsum));
               System.out.println("失败："+fsum); 
               long endTime=System.currentTimeMillis();
               System.out.println("用时："+(endTime-startTime)/1000+"s");
	           date = new Date(endTime);
	           System.out.println(formatter.format(date));
            }
            else
            {   
                System.out.println("找不到指定的文件！");   
            }   
        } 
        catch (Exception e) 
        {   
            System.out.println("读取文件内容操作出错");   
            e.printStackTrace();  
            con.rollback();           
        }
        finally 
        {    
        	con.close();
        }  
    }  
}  