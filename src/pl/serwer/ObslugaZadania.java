package pl.serwer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

class ObslugaZadania extends Thread {
    Socket sock;
    InputStream is;
    OutputStream os;
    BufferedReader inp;
    DataOutputStream outp;
    PrintWriter out;
    ObslugaZadania(Socket klientSocket) throws IOException{
        this.sock = klientSocket;
        this.is = sock.getInputStream();
        this.os = sock.getOutputStream();
        this.inp = new BufferedReader(new InputStreamReader(is));
        this.outp = new DataOutputStream(os);
        this.out = new PrintWriter(sock.getOutputStream(), true);}
    public void run() {
        String request = null;
        try {
            request = inp.readLine();
            System.out.println("Request: " + request);
        } catch (IOException e) {System.out.println(e.getMessage());
            this.interrupt();}
        SerwerHTTP.log.addLog(request, sock.getInetAddress().getHostAddress());
        String[] splited = request.split("\\s+");
        String file = splited[1].substring(1);
        System.out.println(file);
        File f = new File(file);
        FileInputStream fis = null;
        String fileExtension = "";
        boolean fileExist;
        int contentLength = 0;
        String contentType = "Content-Type: \r\n";

        if (f.exists() && !f.isDirectory()){
            fileExist = true;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {    e.printStackTrace();}
            int i = file.lastIndexOf('.');
            int p = Math.max(file.lastIndexOf('/'), file.lastIndexOf('\\'));
            if (i > p) fileExtension = file.substring(i+1);
            try {
                contentLength = fis.available();
            } catch (IOException e) {e.printStackTrace();}
            if (fileExtension.toLowerCase().equals("html") || fileExtension.toLowerCase().equals("html")){
                contentType = "Content-Type: text/html\r\n";}} 
        else{fileExist = false;}
        System.out.println("content type: " + contentType);
        System.out.println("content length: " + contentLength);
        if(request.startsWith("GET")) {
            if (file.equals("")){
                //response header
                try {
                    outp.writeBytes("HTTP/1.0 200 OK\r\n");
                    outp.writeBytes("" + contentType);
                    outp.writeBytes("Content-Length: " + contentLength + " \r\n");
                    outp.writeBytes("\r\n");

                    outp.writeBytes("<html>\r\n");
                    outp.writeBytes("<H1>Strona testowa</H1>\r\n");
                    outp.writeBytes("</html>\r\n");
                } catch (IOException e){}
            } else {
                if (fileExist){
                    try {
                        outp.writeBytes("HTTP/1.0 200 OK\r\n");
                        outp.writeBytes(contentType);
                        outp.writeBytes("Content-Length: " + contentLength + " \r\n");
                        outp.writeBytes("\r\n");
                        byte[] bufor;
                        bufor = new byte[2048];
                        int n = 0;
                        while ((n = fis.read(bufor)) != -1) {
                            outp.write(bufor, 0, n);
                        }
                    } catch (IOException e){}} 
               }
        }else {
            try {
                outp.writeBytes("HTTP/1.1 501 Not supported.\r\n");
            } catch (IOException e){}}
        f = null;
        try {
            inp.close();
            outp.close();
        } catch (IOException e){}}}