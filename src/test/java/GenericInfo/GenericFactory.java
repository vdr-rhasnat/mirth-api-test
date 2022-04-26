package GenericInfo;

import Context.GlobalContext;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class GenericFactory {
    public boolean checkFileIsExistOrNot(String fileName, String extension) {
        File temp;
        boolean isExists = false;
        try
        {
            temp = File.createTempFile(fileName,extension);
            isExists = temp.exists();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return isExists;
    }

    public boolean isMessageSentToServer(String ipAddress, Integer port) {
        try{
            String msgState = GlobalContext.hl7Message;
            Socket socket = new Socket(ipAddress, port);
            PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            outToServer.write(msgState);
            outToServer.flush();
            System.out.print("msg sent to server");
            socket.close();
            Thread.sleep(500);
            return true;
        }
        catch(Exception ex){
            ex.getMessage();
            return false;
        }
    }

    public String getFilePatientContent() {
        String fileData = "";
        StringBuffer sb;
        try {
            Path filePath = Path.of(GlobalContext.fileUrl);
            String content = Files.readString(filePath);
            File f= new File(GlobalContext.fileUrl);           //file to be delete
            f.delete();
            fileData = content;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  fileData;
    }

    public String getFileName(String fileUrl) {
        File folder = new File(fileUrl);
        String fileName = "";
        String contents[] = folder.list();
        fileName = contents[0];
        GlobalContext.fileUrl = fileUrl+"/"+fileName;
        fileUrl =  GlobalContext.fileUrl;
        fileName = fileUrl.substring(0, fileUrl.lastIndexOf("."));
        return  fileName;
    }
}
