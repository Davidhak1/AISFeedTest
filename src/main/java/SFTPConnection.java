import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import resources.Utils;

import java.util.Properties;

/**
 *
 * @author javagists.com
 *
 */
public class SFTPConnection {
         public Properties prop;

    public void SFTPCon() throws Exception {

        prop = Utils.initProp("src/main/java/resources/data.properties")   ;


        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession(prop.getProperty("ftpUser"), prop.getProperty("ftpUrl"));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(prop.getProperty("ftpPassword"));
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.get("/incentivesfeeds/acura/20190125000000.547-9f0af2633f314d9badc5619b83ee37e1/process/vandergriffacuraadw-76017.xml",
                    "src/main/java/resources/test.xml");
            sftpChannel.exit();
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception{
          SFTPConnection test = new SFTPConnection();
          test.SFTPCon();
    }

    public void XMLDataValidation(){
        
    }

}