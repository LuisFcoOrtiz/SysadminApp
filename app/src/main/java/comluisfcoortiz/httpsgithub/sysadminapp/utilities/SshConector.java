package comluisfcoortiz.httpsgithub.sysadminapp.utilities;

import android.content.pm.PackageInstaller;
import android.util.Log;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.net.NoRouteToHostException;
import java.util.Properties;

public class SshConector {

    //variables
    protected String host, user, password, consoleMessage;  //variables to connect
    protected Session session;
    protected int port=22;  //ssh port
    //ssh connection
    private JSch jschChannel;
    private int intTimeOut = 60000; //timeout for connection


    public SshConector(String hostE, String userE, String passwordE) {
        host = hostE;
        user = userE;
        password = passwordE;
        intTimeOut = 60000;         //timeout by default
        jschChannel  = new JSch();
    }//end constructor

    public SshConector() {
    }

    public boolean connect() {
        try {
            session = jschChannel.getSession(user, host, port);
            session.setPassword(password);
            // Avoid asking for key confirmation
            Properties prop = new Properties();
            prop.put("StrictHostKeyChecking", "no");
            session.setConfig(prop);
            //start
            session.connect(intTimeOut);
            return true;    //connection correctly
        }catch (JSchException e) {
            e.printStackTrace();
            Log.i("INFO_SESSION","Error: "+ e.getMessage());
            return false;   //error
        }/* catch (NetworkOnMainThreadException ex) {
            return false;
        }*/
    }//connect with the server

    public void setTimeOutConnection(int timeOut) {
        intTimeOut = timeOut;
    }//end

    public long getTimeOut() {
        return intTimeOut;
    }//get the timeout to connect

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConsoleMessage() {
        return consoleMessage;
    }

    public void setIntTimeOut(int intTimeOut) {
        this.intTimeOut = intTimeOut;
    }

}//end of class
