import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import sun.awt.image.ToolkitImage;
public class ArmyDeployment extends JFrame{
    private JPanel toolbarPanel = new JPanel();
    private JPanel elementsPanel = new JPanel();
    private JPanel mapPanel = new JPanel(); 
    private JPanel optionsPanel = new JPanel(); 
    JButton Arty=new JButton("Arty");
    JButton snt=new JButton("S&T");
    JButton Armd=new JButton("Armd");
    JButton EME=new JButton("EME");
    JButton Avn=new JButton("Avn");
    JButton Inf=new JButton("Inf");
    JButton Pioneer=new JButton("Pioneer");
    JButton Engrs=new JButton("Engrs");
    JButton chooseFile=new JButton("Choose File");
    JButton Save=new JButton("Save");
    JButton Clear=new JButton("Clear");
    private JLabel map=new JLabel(); 
    private JLabel chooseMap = new JLabel("Map");
    private JTextField mapText = new JTextField(20);
    private int mouseX = 200;
    private int mouseY = 100;
    private mousemotion listenerMouse=new mousemotion();
    
    public ArmyDeployment(String s){
        //panel1.setLayout(new BorderLayout());
        
        setVisible(true);
        setSize(600, 400);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        elementsPanel.add(Arty);
        elementsPanel.add(Armd);
        elementsPanel.add(Inf);
        elementsPanel.add(Pioneer);
        elementsPanel.add(Engrs);
        elementsPanel.add(Avn);
        elementsPanel.add(EME);      
        elementsPanel.add(snt);
        elementsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        //panel1.add
        optionsPanel.add(chooseMap);
        optionsPanel.add(mapText);
        optionsPanel.add(chooseFile); 
        optionsPanel.add(Clear);
        optionsPanel.add(Save);
        optionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        //panel1.setBounds(0, 0, 600, 40);
        //panel2.setBounds(0, 40, 600, 360);
        this.add(toolbarPanel,BorderLayout.NORTH);
        
        toolbarPanel.setLayout(new BoxLayout(toolbarPanel,BoxLayout.Y_AXIS));
        toolbarPanel.add(elementsPanel);
        toolbarPanel.add(optionsPanel);
        
        mapPanel.add(map);
        
        map.setOpaque(true);
                   
        mapPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        this.add(mapPanel,BorderLayout.CENTER);
        mapPanel.setBackground(Color.white);
        Clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                map.removeAll(); 
                mapPanel.repaint();
                map.setIcon(new ImageIcon(new ImageIcon("Images/White.jpg").getImage().getScaledInstance(mapPanel.size().width,mapPanel.size().height,  java.awt.Image.SCALE_SMOOTH)));
            }
        });
        Save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
         
            BufferedImage image = new BufferedImage(map.size().width,map.size().height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = image.createGraphics();
            mapPanel.paint(graphics2D);
            JFileChooser fc=new JFileChooser();
            int retrieval = fc.showSaveDialog(null);
            if (retrieval == JFileChooser.APPROVE_OPTION) {
        try {
            
            ImageIO.write(image,"jpeg", new File(fc.getSelectedFile().getAbsoluteFile().getAbsolutePath()+".jpeg"));
        }
        catch(Exception exception)
        {
            //code
        }
            }}});
            
        /*Save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                ImageIcon i=(ImageIcon)map.getIcon();   
                BufferedImage buffered = ((ToolkitImage)i.getImage()).getBufferedImage();
                BufferedImage bi2 =new BufferedImage(mapPanel.size().width,mapPanel.size().height, BufferedImage.TYPE_INT_RGB);
                Graphics big = bi2.getGraphics();
                mapPanel.getGraphics()
                big.drawImage(, 0, 0, bi2);
        
            try {
                // save to file
                File outputfile = new File("saved.png");
                ImageIO.write(buffered, "png", outputfile);
                File outputfile2 = new File("saved2.png");
                ImageIO.write(bi2, "png", outputfile);
                System.out.println("done");
            } catch (IOException e) {
                e.printStackTrace();
            }}});*/
        chooseFile.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                JFileChooser fc;
                fc = new JFileChooser(); 
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnVal = fc.showOpenDialog(ArmyDeployment.this);
                
                try{
                 Image img = (new ImageIcon(fc.getSelectedFile().getAbsolutePath())).getImage();
                 
                    if (returnVal == JFileChooser.APPROVE_OPTION){  
                        
                        mapText.setText(fc.getSelectedFile().getName());                
                    }
                    else mapText.setText("Error");

                    img = img.getScaledInstance(mapPanel.size().width,mapPanel.size().height,  java.awt.Image.SCALE_SMOOTH);
                    map.setIcon(new ImageIcon(img));
                    }
                catch(Exception e){System.out.println("not an image!!!"+e);}
                
            }
        }); 
        
        class elementListener implements ActionListener{            
        public void actionPerformed(ActionEvent ae){
                String imageName="Images/"+ae.getActionCommand()+".jpg";
                Image img=(new ImageIcon(imageName)).getImage(); 
                img = img.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
                JLabel j=new JLabel();
                //j.setName(ae.getActionCommand());
                map.add(j);
                
                j.setIcon(new ImageIcon(img));
                j.setBounds(200,200, 25, 25);
                j.setAutoscrolls(true);
                j.setOpaque(true);                         
                j.addMouseMotionListener(listenerMouse);                
            }
        }
        elementListener eList=new elementListener() ;
        Arty.addActionListener(eList);
        snt.addActionListener(eList);
        Armd.addActionListener(eList);
        EME.addActionListener(eList);
        Avn.addActionListener(eList);
        Inf.addActionListener(eList);
        Pioneer.addActionListener(eList);
        Engrs.addActionListener(eList);
    }
    public static void main(String[] args) {
    ArmyDeployment frame = new ArmyDeployment("Army Deployment");
    
}
    
}
class mousemotion implements MouseMotionListener {
private boolean drag = true;

public void mousePressed(MouseEvent e) {
   
        drag = true;
    
}


public void mouseReleased(MouseEvent e) {
    drag = false;
}

@Override
public void mouseDragged(MouseEvent e) {
    if (drag == true) {
        JComponent jc = (JComponent)e.getSource();
        jc.setLocation(jc.getX()+e.getX(), jc.getY()+e.getY());
    }
}

public void mouseMoved(MouseEvent e) {}
}

