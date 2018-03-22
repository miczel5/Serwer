package pl.serwer;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class logi {
    private Date date;
    private DateFormat dateFormat;
    private DateFormat dateFormatFile;
    private File fileLog;
    private String fileName;
    private File path = new File("log");
    logi(){
        date = new Date();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        dateFormatFile = new SimpleDateFormat("yyyy_MM_dd");
        if (!path.exists()) {
            if (path.mkdir()) {
                System.out.println("Directory is created!");}
            else {System.out.println("Failed to create directory!");}}
        if (!createFile()){
            System.out.println("Failed to create file!");}}
    private boolean createFile(){
        fileName = "log/log_" + dateFormatFile.format(date) + ".txt";
        fileLog = new File(fileName);
        if(!fileLog.exists()) {
            try {
                fileLog.createNewFile();
            } catch (IOException e) {
                System.out.println("Failed to create file: " + fileName + "!");
                return false;} }
        return true;}
    void addLog(String message, String IP){
        System.out.println(dateFormat.format(date));
        if(fileLog.exists() && !fileLog.isDirectory()) {
            if (!fileLog.getName().equals(fileName)) {
                if (!createFile()){
                    System.out.println("Failed to create file!");}}}
        try(FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)){
            out.println(dateFormat.format(date) + " | " + IP + ": " + message);
        } catch (IOException e) {}}}