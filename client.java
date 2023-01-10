import java.net.*;
import java.io.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;


public class client extends JFrame {
   
Socket socket;
private PrintStream out;
BufferedReader br;
//graphical user interface bna rhe h  
//delcare components
private JLabel heading=new JLabel("Client Area");
private JTextArea messageArea= new JTextArea();
private JTextField messageInput = new JTextField();
private Font font = new Font("Roboto",Font.BOLD,20);
//constructor
public client()
{
    try{
        System.out.println("request to server");
        socket =new Socket("127.0.0.1",7777);
        System.out.println("connection done.");


      BufferedReader br = new BufferedReader(new InputStreamReader(socket.
        getInputStream()));
   PrintWriter out = new PrintWriter(socket.getOutputStream());
     createGUI();
     handleEvents();
        
     
     startReading();
     // startWriting();
    }catch(Exception e){
        //todo: handle exception
    }
}
        

/**
 * 
 */
private void handleEvents() {
messageInput.addKeyListener(new KeyListener(){

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        System.out.println("key released"+e.getKeyCode());
   if(e.getKeyCode()==10){
    //enter message
    String contentToSend=messageInput.getText();
   messageArea.append("Me :"+contentToSend+"\n");
    out.println(contentToSend);
    out.flush();
    messageInput.setText("");
    messageInput.requestFocus();
   }
   
    }
    
});

}

// CREATE GUI

private void createGUI(){
    // gui code
    
    this.setTitle("Client Message [end]");
    this.setSize(500,500);
    this.setLocationRelativeTo(null);
this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//conding for componenets
heading.setFont(font);
messageArea.setFont(font);
messageInput.setFont(font);

heading.setIcon(new ImageIcon("icon.png "));

heading.setHorizontalAlignment(SwingConstants.CENTER);
heading.setHorizontalTextPosition(SwingConstants.CENTER);
heading.setVerticalTextPosition(SwingConstants.BOTTOM);
heading.setHorizontalAlignment(SwingConstants.CENTER);
messageInput.setHorizontalAlignment(SwingConstants.CENTER);


heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
messageArea.setEditable(false);
//frame ka layout set krenge
this.setLayout( new BorderLayout());
//adding the componets to frame
this.add(heading,BorderLayout.NORTH);
JScrollPane jScrollPane=new JScrollPane(messageArea);
this.add(messageArea,BorderLayout.CENTER);
this.add(messageInput,BorderLayout.SOUTH);

this.setVisible(true);



}
    
    /**
     * 
     */
   


    public void startReading()
     {
         //thread read krega
         //runnable variable h 
         //lamda expression use krnge idhr
         Runnable r1=()->{
             System.out.println("reader start");
             while(true){
                 try{
                 
            
                String msg= br.readLine();
                 if(msg.equals("exit")){
             System.out.println("Server terminated the chat");
     JOptionPane.showMessageDialog(this,"Server Terminated the chat");
    messageInput.setEnabled(false);
    socket.close();
     break;
     }
    // System.out.println("Server : " + msg);
     messageArea.append("Server: "+msg+"\n");
             }catch (Exception e){
                 e.printStackTrace();
             }
         };
        };
     
         new Theard(r1).start();
     }

     public void startWriting(){
     
         Runnable r2=()->{
             System.out.println("writer started");
     while(true){
     try{
      BufferedReader br1= new BufferedReader(new
      InputStreamReader(System.in));
      String content = br1.readLine();
      
    
   
    out.println(content);
      out.flush();
     
     }catch(Exception e){
         e.printStackTrace();
     }
     }
         };
     new Theard(r2).start();
     }
          
   
   
    

    
    
    public static void main(String [] args){
System.out.println("this is client...");
new client();
    }
}
