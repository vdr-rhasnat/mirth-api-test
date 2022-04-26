package GenericInfo;

import Context.GlobalContext;
import org.junit.Assert;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

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
            String msgState = GlobalContext.Message;
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

    public boolean generateFlatFile(){
        try {
            LocalDate myObj = LocalDate.now();
            FileWriter myWriter = new FileWriter("C:\\FlatFile\\filename-"+ myObj+".txt");
            myWriter.write(GlobalContext.Message);
            myWriter.close();
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
    }

    public String getFlatFileContent(Integer port) {
        String flatFileContent = "";
        try {
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String greeting = null;
                do{
                    greeting = in.readLine();
                    if(greeting != null){
                        flatFileContent += greeting;
                    }
                } while (greeting != null);
            }
            catch (IOException ex){
            }
        return flatFileContent;
    }
}
