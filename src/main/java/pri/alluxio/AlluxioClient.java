package pri.alluxio;

import alluxio.AlluxioURI;
import alluxio.Configuration;
import alluxio.PropertyKey;
import alluxio.client.file.FileInStream;
import alluxio.client.file.FileOutStream;
import alluxio.client.file.FileSystem;
import alluxio.client.file.options.DeleteOptions;
import alluxio.exception.AlluxioException;
import org.apache.log4j.Logger;

import java.io.IOException;

public class AlluxioClient {

    private static Logger logger = Logger.getLogger(AlluxioClient.class);
    private static final String MASTER_HOSTNAME = "172.17.171.104";
    private static final String MASTER_RPC_PORT = "19998";
    private static final String SECURITY_LOGIN_USERNAME = "root";

    AlluxioClient() {
        Configuration.set(PropertyKey.MASTER_HOSTNAME, MASTER_HOSTNAME);
        Configuration.set(PropertyKey.MASTER_RPC_PORT, MASTER_RPC_PORT);
        Configuration.set(PropertyKey.SECURITY_LOGIN_USERNAME, SECURITY_LOGIN_USERNAME);
    }

    public boolean deleteFile(String path) throws AlluxioException {
        FileSystem fs = FileSystem.Factory.get();
        AlluxioURI uri = new AlluxioURI(path);
        try {
            fs.delete(uri, DeleteOptions.defaults());
        } catch (IOException | AlluxioException e) {
            logger.error("Error while deleting file " + path + " " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean upLoadFile(String srcPath, String destPath) {

//        FileSystem fs = FileSystem.Factory.get();
//        AlluxioURI uri = new AlluxioURI(destPath);
        return true;
    }

    public String readFile(String filePath) {
        FileSystem fs = FileSystem.Factory.get();
        AlluxioURI uri = new AlluxioURI(filePath);
        StringBuilder builder = new StringBuilder();
        try {
            FileInStream in = fs.openFile(uri);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = in.read(bytes, 0, 1024)) > 0) {
                builder.append(new String(bytes));
            }
            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (AlluxioException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean createFile(String uri) {
        FileSystem fs = FileSystem.Factory.get();
        AlluxioURI alluxioURI = new AlluxioURI(uri);
        try {
            if (fs.exists(alluxioURI)) {
                logger.error("File " + uri + " is already exist.");
                return false;
            } else {
                FileOutStream out = fs.createFile(alluxioURI);
                out.close();
            }
        } catch (IOException | AlluxioException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean createDir(String uri) {
        FileSystem fs = FileSystem.Factory.get();
        AlluxioURI alluxioURI = new AlluxioURI(uri);
        try {
            if (fs.exists(alluxioURI)) {
                logger.error("Directory " + uri + " is already exist.");
                return false;
            } else {
                fs.createDirectory(alluxioURI);
            }
        } catch (IOException | AlluxioException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) {
        AlluxioClient client = new AlluxioClient();
//        String bytes = client.readFile("/lty/core-site.xml");
//        System.out.println(bytes);
//        logger.info(bytes);
        client.createDir("/lty/test");

//        client.createFile("/lty/text.txt");
    }

}
