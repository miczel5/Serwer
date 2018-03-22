package pl.serwer;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.net.ssl.*;

public class SerwerHTTP {
    static int port = 27370;
    public static logi log = new logi();
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        ServerSocket serv;
        while (true) {
            try {
                System.out.println("Wprowadź port serwera:");
                int port = Integer.parseInt(s.next());
                serv = new ServerSocket(port);
                if (serv.isBound()){
                    System.out.println("Serwer wystartował!");
                    break;
                }} 
            catch (BindException e){System.out.println("Port zajęty! Proszę wybrać inny!");}}
        while(true) {
            System.out.println("Oczekiwanie na polaczenie...");
            Socket sock = serv.accept();
            //tworzenie watku obslugi tego polaczenia
            try {
                new ObslugaZadania(sock).start();
            } catch (IOException e){
                System.out.println(e.toString());
            }}}}