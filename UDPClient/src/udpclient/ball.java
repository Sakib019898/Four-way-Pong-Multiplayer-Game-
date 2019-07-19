/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpclient;

import java.awt.Color;
import java.awt.Graphics;
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
 * @author User
 */

public class ball {
    double x,y ,yValue,xValue;
    int player=-1;
    int start=0;
    private static DatagramSocket clientSocket =null;
    private static InetAddress IPAddress = null;
    public ball(){
    x=350;
    y=250;
    xValue=-0.5;
    yValue=0.5;
    try {
            ///UDP connection
            clientSocket= new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(pong.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            IPAddress = InetAddress.getByName("localhost");
        } catch (UnknownHostException ex) {
            Logger.getLogger(pong.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    }
    
    public void draw(Graphics g){
       g.setColor(Color.white);
       g.fillOval((int)x-10, (int)y-10, 20, 20);
       
    }
    
    public void checkBoardCollision(leaderboard p1,leaderboard p2,leaderboard2 p3,leaderboard2 p4){
     
        if(x<=50){
            
        if(y==p1.getY()+80 || y==p1.getY()+81 || y==p1.getY()-1){
          yValue=-yValue;
        }    
        else if(y>=p1.getY() && y<p1.getY()+80){ 
        xValue=-xValue;
        p1.scorePlus();
        }
     }
     else if(x>=650){
        
         
         
        if(y==p2.getY()+80 || y==p2.getY()+81 || y==p2.getY()-1){
          yValue=-yValue;
        }    
        else if(y>=p2.getY() && y<p2.getY()+80){ 
        xValue=-xValue;
        
        p2.scorePlus();
        }
      }
     else if(y<=50){
         if(x==p3.getX()+80 || y==p3.getX()+81 || y==p3.getX()-1){
          xValue=-xValue;
        }    
        else if(x>=p3.getX() && x<=p3.getX()+80){ 
        yValue=-yValue;
        p3.scorePlus(); 
        }
     }
     else if(y>=530){
        if(x==p4.getX()+80 || y==p4.getX()+81 || y==p4.getX()-1){
          xValue=-xValue;
        }    
        else if(x>=p4.getX() && x<=p4.getX()+80){ 
        yValue=-yValue;
        p4.scorePlus();
      }   
     }
        
    }
    public void start_game(){
       start=1;
    }
    public void move(leaderboard p1,leaderboard p2,leaderboard2 p3,leaderboard2 p4,int noOfplayer,double doubleX,double doubleY,int mainBall){
     if(start==1){
         if(noOfplayer==mainBall){
            x+=xValue;
            y+=yValue;
         }
         else if(noOfplayer!=mainBall){
           x=doubleX;   
           y=doubleY;   
         }
     if(p1.playerDown==1 && x<30){
         xValue=-xValue;
         
     }
     else if(p2.playerDown==1 && x>660){
         xValue=-xValue;
         
     }      
     else if(p3.playerDown==1 && y<30){
         yValue=-yValue;
         
     }
     else if(p4.playerDown==1 && y>540){
         yValue=-yValue;
         
     }
     else if(x<10 && noOfplayer==0){
             x=350;
             y=250;     
             System.out.println("down1");
             sendToServer("die:"+noOfplayer+":no");
             p1.setXY();
            }
    else if(x>690 && noOfplayer==1){
             x=350;
             y=250; 
             System.out.println("down2");
             sendToServer("die:"+noOfplayer+":no");
             p2.setXY();
           }
    else if(y>570 && noOfplayer==3){
             x=350;
             y=250; 
             System.out.println("down4");
             sendToServer("die:"+noOfplayer+":no");
             p4.setXY();
           }
    else if(y<10 && noOfplayer==2){
            x=350;
            y=250;
            System.out.println("down3");
            sendToServer("die:"+noOfplayer+":no");
            p3.setXY();
          }
     }
     
    }
    public int getX(){
      return (int)x;
    }
    public int getY(){
      return (int)y;
    }
    public double getDoubleX(){
      return x+1;
    }
    public double getDoubleY(){
      return y+1;
    }
    public void setDoubleX(double doubleX){
      x=doubleX;
    }
    public void setDoubleY(double doubleY){
      y=doubleY;
    }
    private void sendToServer(String s){
        String sentence = s;
        byte[] sendData = new byte[500];
        sendData = sentence.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        try {
            clientSocket.send(sendPacket);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    
    }
}
