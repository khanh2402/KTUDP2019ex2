/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninhhoangcuong.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ninhh
 */
public class Server {

    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
    private static final String ASCIICODE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    private static final boolean SPECIAL = true;
    private static final boolean NOMAL = false;
    private static Random ran;

    //generaten string not include special charecters
    public static String RandomString() {
        ran = new Random();
        //size of stringBuilder
        int n = ran.nextInt(50);
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            /* generate a random number between 
            0 to AlphaNumericString variable length */
            int index = (int) (ALPHABET.length() * Math.random());
            // add Character one by one in end of sb 
            sb.append(ALPHABET.charAt(index));
        }
        return sb.toString();
    }

    //generaten string include special charecters
    public static String RandomString(boolean flag) {
        ran = new Random();
        //size of stringBuilder
        int n = ran.nextInt(50);
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            /* generate a random number between 
            0 to AlphaNumericString variable length */
            int index = (int) (ASCIICODE.length() * Math.random());
            // add Character one by one in end of sb 
            sb.append(ASCIICODE.charAt(index));
        }
        return sb.toString();
    }

    //check result
    public static boolean Check(String requestID, String str1, String str2, String requestIDCL, String str1CL, String str2CL) {
        if (requestID.equals(requestIDCL) == true && str1.equals(str1CL) == true && str2.equals(str2CL) == true) {
            return true;
        }
        return false;
    }

    //split special charecter
    public static String SplitString(String sendString, boolean flag) {
       String specialString = sendString.replaceAll("[^!\"#$%&'()*+,-./:;<=>?@\\\\^_`{|}~\\[\\]]","");
       String nomalString = sendString.replaceAll("[^A-Za-z0-9]","");
       if(flag==true){
           return specialString;
       }else{
           return nomalString;
       }
    }

    public static void main(String[] args) {
        try {
            DatagramSocket socketServer = null;
            DatagramPacket receivePacket, sendPacket;
            int port = 1108;
            byte[] receive, send;
            String data = null;
            String requestId = null;
            //open server
            socketServer = new DatagramSocket(port);
            while (true) {
                //nhan du lieu tu client
                receive = new byte[1024];
                receivePacket = new DatagramPacket(receive, receive.length);
                socketServer.receive(receivePacket);
                String arg1 = new String(receivePacket.getData()).trim();
                //split
                String[] result = arg1.split(";");
                for (String i : result) {
                    System.out.println(i);
                }
                //gui du lieu cho client
                send = new byte[1024];
                requestId = RandomString();
                data = RandomString(true);
                String sendString = requestId + ";" + data;
                send = sendString.getBytes();
                System.out.println("Send to client : "+ sendString);
                //dinh nghia packet gui di
                sendPacket = new DatagramPacket(send, send.length, receivePacket.getSocketAddress());
                //gui di cho client
                socketServer.send(sendPacket);
                //nhan lai du lieu va so sanh ket qua
                receive = new byte[1024];
                receivePacket = new DatagramPacket(receive, receive.length);
                socketServer.receive(receivePacket);
                String recieveString = new String(receivePacket.getData()).trim();
                //split
                String []result2 = recieveString.split(";",2);
                String dataCL = result2[1];
                String [] dataSPL = dataCL.split(",",2);
                for(String i : dataSPL){
                    System.out.println(i);
                }
                System.out.println( SplitString(sendString, NOMAL));
                System.out.println(SplitString(sendString, SPECIAL));
                System.out.println("Result : "
                        + Check(requestId, SplitString(data, NOMAL), SplitString(data, SPECIAL), result2[0], dataSPL[0], dataSPL[1]));
            }
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
