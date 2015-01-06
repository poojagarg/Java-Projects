package conceptual_modelling;
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
public class conceptual_modeling_driver extends JFrame{
    
    javax.swing.JButton jButton_Collection_file_choose;
    javax.swing.JButton jButton_submit;
    javax.swing.JLabel jLabel_Collection_file_choose;
    javax.swing.JLabel jLabel_parameter;
    javax.swing.JPanel jPanel1;
    javax.swing.JTextField jTextField_Collection_file_choose;
    javax.swing.JTextField jTextField_parameter;
    File file;
    static Collection collection_of_documents;
    
    public conceptual_modeling_driver(String s){
        //panel1.setLayout(new BorderLayout());
        
        setVisible(true);
        setSize(600, 400);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        jPanel1 = new javax.swing.JPanel();
        jLabel_Collection_file_choose = new javax.swing.JLabel("Choose File");
        jTextField_Collection_file_choose = new javax.swing.JTextField(40);
        jLabel_parameter = new javax.swing.JLabel("Parameter");
        jTextField_parameter = new javax.swing.JTextField(10);
        jButton_Collection_file_choose = new javax.swing.JButton("Choose File");
        jButton_submit = new javax.swing.JButton("submit");

        

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        
        jPanel1.setLayout(new BoxLayout(jPanel1,BoxLayout.Y_AXIS));
                   
        jLabel_Collection_file_choose.setText("Collection");

        jLabel_parameter.setText("Parameter(0-1)");

        jButton_Collection_file_choose.setText("Choose file");
        jButton_Collection_file_choose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Collection_file_chooseActionPerformed(evt);
            }
        });
        jButton_submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_submitActionPerformed(evt);
            }
        });
        
        JPanel panelChooseFIle=new JPanel();
        JPanel panelParameter=new JPanel();
        panelChooseFIle.add(jLabel_Collection_file_choose); 
        panelChooseFIle.add(jTextField_Collection_file_choose);
        panelChooseFIle.add(jButton_Collection_file_choose);
        panelParameter.add(jLabel_parameter);
        panelParameter.add(jTextField_parameter);
        
        jPanel1.add(panelChooseFIle);
        jPanel1.add(panelParameter);
        jPanel1.add(jButton_submit);
        
        this.add(jPanel1);
        pack();
    }
     private void jButton_Collection_file_chooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Collection_file_chooseActionPerformed
        // TODO add your handling code here:
        JFileChooser fc;
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
         int returnVal = fc.showOpenDialog(conceptual_modeling_driver.this);
         
        file = fc.getSelectedFile();
            if (returnVal == JFileChooser.APPROVE_OPTION){                 
                jTextField_Collection_file_choose.setText(file.getName());
                
            }
            else jTextField_Collection_file_choose.setText("Error");
    }//GEN-LAST:event_jButton_Collection_file_chooseActionPerformed

    public static void main(String[] args) {
    conceptual_modeling_driver frame = new conceptual_modeling_driver("conceptual_modeling_driver");
    
}
    
private void jButton_submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_submitActionPerformed
        // TODO add your handling code here:
        
                String p=jTextField_parameter.getText();
                collection_of_documents=new Collection(file, p);
                
                System.out.println("done");
                
    }//GEN-LAST:event_jButton_submitActionPerformed

}
