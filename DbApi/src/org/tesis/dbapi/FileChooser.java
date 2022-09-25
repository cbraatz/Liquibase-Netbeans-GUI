package org.tesis.dbapi;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.tesis.util.Utils;
/**
 * Clase que permite la implementación de un file chooser para la seleccion de de una archivo o carpeta. 
 * @author Denis Maernitz
 */
public class FileChooser extends JPanel{
   private JButton button;
   private JFileChooser chooser;
   private String choosertitle;
   private String currentDirectory=null;
   private String selectedFile=null;
   private boolean isDirectorySelection=false;
   public FileChooser() {
        isDirectorySelection=true;
        button = new JButton("...");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                chooserAction();
            }
        });
        this.add(button);
   }
   public FileChooser(final FileNameExtensionFilter filtro) {//extensions puede ser algo como "JPG, PNG & GIF","jpg","png","gif" o "xml files (*.xml)", "xml"
        button = new JButton("...");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                chooserAction(filtro);
            }
        });
        this.add(button);
   }
  public JButton writeButton(){
      return button;
  }
  public void chooserAction() {
    currentDirectory=null;
    selectedFile=null;  
    chooser = new JFileChooser(); 
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle(choosertitle);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    //
    // disable the "All files" option.
    //
    chooser.setAcceptAllFileFilterUsed(false);
    //    
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
      currentDirectory=chooser.getCurrentDirectory().toString();
      //System.out.println("getCurrentDirectory(): " +  currentDirectory);
      selectedFile=chooser.getSelectedFile().toString();
      //System.out.println("getSelectedFile() : " +  selectedFile);
    }else {
      //System.out.println("No Selection ");
    }
 }
  public void chooserAction(FileNameExtensionFilter filtro) {
    currentDirectory=null;
    selectedFile=null; 
    chooser = new JFileChooser();
    chooser.setFileFilter(filtro);
    chooser.setAcceptAllFileFilterUsed(false);
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
      currentDirectory=chooser.getCurrentDirectory().toString();
      //System.out.println("getCurrentDirectory(): " +  currentDirectory);
      selectedFile=chooser.getSelectedFile().toString();
      //System.out.println("getSelectedFile() : " +  selectedFile);
    }else {
      //System.out.println("No Selection ");
    }
 }  
  @Override
  public Dimension getPreferredSize(){
    return new Dimension(200, 200);
    }

    public String getChoosertitle() {
        return choosertitle;
    }

    public String getCurrentDirectory() {
        return currentDirectory;
    }

    public String getSelectedFile() {
        if(null!=selectedFile){
            if(this.isDirectorySelection){
                return selectedFile+(!this.hasSlashAtTheEnd(selectedFile) ? Utils.getPathSeparator() : "");
            }else{
                return selectedFile;
            }
        }else{
            return null;
        }
    }
    private boolean hasSlashAtTheEnd(String path){
        if(path.charAt(path.length()-1)=='/' || path.charAt(path.length()-1)=='\\'){
            return true;
        }else{
            return false;
        }
       
    }
  public static void main(String s[]) {
    JFrame frame = new JFrame("");
    FileChooser panel = new FileChooser();
    frame.addWindowListener(
      new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          System.exit(0);
          }
        }
      );
    frame.getContentPane().add(panel,"Center");
    frame.setSize(panel.getPreferredSize());
    frame.setVisible(true);
    panel.writeButton();
    }
}