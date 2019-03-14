package resources;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import static resources.Utils.getTheRightFolderName;

public class SFTPConnection {
    public static Properties prop;
    private static JSch jsch;
    private static Session session;
    private static Channel channel;
    private static ChannelSftp sftpChannel;

    public SFTPConnection(){}

    public static void SFTPCon() throws Exception {

        prop = Utils.initProp("src/main/java/resources/data.properties");

        jsch = new JSch();
        session = null;

        try {
            session = jsch.getSession(prop.getProperty("ftpUser"), prop.getProperty("ftpUrl"));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(prop.getProperty("ftpPassword"));
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();
            sftpChannel = (ChannelSftp) channel;

        } catch (JSchException e) {
            e.printStackTrace();
        }

    }

    public static void downloadTheFilesFromAISFeed(String feedRunId) {
        try {

            SFTPCon();
            String path = prop.getProperty("ais-base-path");
            String savePath = prop.getProperty("aisSaveDir");

            List<String> fileNames = ls(path);
            System.out.println(fileNames);
            String latestFeedFolder = getTheRightFolderName(fileNames, feedRunId);
            System.out.println(latestFeedFolder);

            List<String> aisFiles = ls(path+latestFeedFolder+"/download");
            System.out.println(aisFiles);

            FileUtils.cleanDirectory(new File("src/main/java/resources/aisFiles"));

            for(String file : aisFiles){
                sftpChannel.get(path + latestFeedFolder + "/download/"+file,
                        savePath+file);
            }

            sftpChannel.exit();
            session.disconnect();
        }
        catch (JSchException e) {
            e.printStackTrace();
        }
        catch (SftpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        SFTPConnection test = new SFTPConnection();
        test.downloadTheFilesFromAISFeed("1685c0d0a14b433bb22ea049aa73cb39");
    }

    public static List<String> ls(String path) throws Exception {
        try {
            List<String> list = new ArrayList<String>();
            Vector<ChannelSftp.LsEntry> vector = sftpChannel.ls(path);
            for (ChannelSftp.LsEntry entry : vector) {
                if(entry.getFilename().length()>2)          //skipping the files that have . or .. as a name
                    list.add(entry.getFilename());
            }
//            channel.disconnect();
            return list;
        } catch (SftpException e) {
            throw new Exception("Cannot execute ls command on sftp connection", e);
        }
    }


}

