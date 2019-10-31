/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninhhoangcuong.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ninhh
 */
public class Client {
    private static final boolean SPECIAL = true;
    private static final boolean NOMAL = false;
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
            DatagramSocket socketClient = null;
            DatagramPacket recievePacket, sendPacket;
            int port = 1108;
            String hostName = "localhost";
            byte[] send, receive;
            int qCode = 101;
            String studentCode = "B16DCCN046";
            String requestId = null;
            String data = null;
            //open 
            socketClient = new DatagramSocket();
            System.out.println("Server is runing ...");
            //send to server
            InetAddress ia = InetAddress.getByName(hostName);
            send = new byte[1024];
            receive = new byte[1024];
            String sendString = ";"+studentCode+";"+qCode;
            send = sendString.getBytes();
            sendPacket = new DatagramPacket(send, send.length, ia, port);
            //gui di
            socketClient.send(sendPacket);
            System.out.println("Send sucsessfully !");
            //nhan data tu server
            //dinh nghia packet nhan
            recievePacket = new DatagramPacket(receive, receive.length);
            //nhan du lieu
            socketClient.receive(recievePacket);
            String recieveString = new String(recievePacket.getData()).trim();
            System.out.println("Recive from server : "+ recieveString);
            String[] result = recieveString.split(";",2);
            for (String i : result) {
                System.out.println(i);
            }
            requestId = result[0];
            data = result[1];
            String str1 = SplitString(data, NOMAL);
            String str2 = SplitString(data, SPECIAL);
            sendString = requestId+";"+str1+","+str2;
            send = new byte[1024];
            send = sendString.getBytes();
            System.out.println("Send to server :"+sendString);
            //dinh nghia packet gui
            sendPacket = new DatagramPacket(send, send.length, recievePacket.getSocketAddress());
            socketClient.send(sendPacket);
            System.out.println("Send sucsessfully !");
            socketClient.close();
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
