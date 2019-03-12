package autoMeter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CMDLineTest 
{
    public static void exeCmd(String commandStr) {  
        BufferedReader br = null;  
        try {  
            Process p = Runtime.getRuntime().exec(commandStr);  
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));  
            String line = null;  
            StringBuilder sb = new StringBuilder();  
            while ((line = br.readLine()) != null) {  
                if ( line.indexOf("cmd.exe") != -1 )
                {
                	line = line.replace ("cmd.exe", " " ) ;
                	line = line.trim () ;
                	System.out.println ( line.substring(0, line.indexOf(" ") ) ) ;
                	break ;
                }
            }  
           
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
        finally  
        {  
            if (br != null)  
            {  
                try {  
                    br.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
  
    public static void main(String[] args) {  
       //String commandStr = "ping www.taobao.com";  
        //String commandStr = "ipconfig"; 
    	String commandStr = "tasklist" ;
        CMDLineTest.exeCmd(commandStr);  
    }  
}
