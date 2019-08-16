/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.net.*;
import java.util.*;
import db.Services;


/**
 *
 * @author camil
 */
public class MainServer {
    
    private static Services DBServices;
    
    private final static int PORT = 5000;
    
    public static void main(String[] args){
        
        ServerSocket serverSocket = null;

        try{
            //Socket de servidor para esperar peticiones de la red
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor> Servidor Iniciado");
            System.out.println("Servidor> En espera de cliente...");
            //Socket de cliente
            Socket clientsocket = null;
            while(true){
                //Esperar la conexion, si existe la acepta
                clientsocket = serverSocket.accept();
                BufferedReader input = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
                PrintStream output = new PrintStream(clientsocket.getOutputStream());
                String request = input.readLine();
                System.out.println("Cliente> Peticion ["+request+"]");
                String strOutput = process(request);
                System.out.println("Servidor> Resultado de peticion");
                System.out.println("Servidor> \""+strOutput+"\"");
                output.flush();
                output.println(strOutput);
                clientsocket.close();
                
            }
        }catch(IOException ex){
            
            System.err.println(ex.getMessage());
        }
        
    }   
    
    public static String process(String request){
      DBServices = new Services();
      String result="";
      if(request!=null){
        StringTokenizer st = new StringTokenizer(request,",");
        int option = Integer.parseInt(st.nextToken());
        String account_number = st.nextToken();
        Long value = (st.hasMoreElements())?Long.parseLong(st.nextToken()):null;
        
        //Operacion de insertar o retirar
            switch(option){
                case 1:
                    DBServices.consingMoney(account_number, value);
                    result+="insertar -> en numero de cuenta: "+account_number + " -> $"+ value;
                    break;
                case 2:
                    DBServices.withDrawals(account_number, value);
                    result+="retirar -> en numero de cuenta: "+account_number + " -> $"+value;
                    break;
                case 3:
                    result+="Informacion -> en numero de cuenta: "+account_number + " -> $"+DBServices.getInfo(account_number);
                    break;
            }
                  
      }
        return result;
}
}

