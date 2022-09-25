/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Claus
 */
public class KeyNamePanel extends JPanel{
    JTextField textFieldKeyName;
    JLabel labelKeyName;
    public KeyNamePanel(){
        textFieldKeyName = new javax.swing.JTextField();
        labelKeyName = new javax.swing.JLabel();
        textFieldKeyName.setText("");
        labelKeyName.setText("Key Name");
        this.setBackground(UIConstants.PANELS_PRIMARY_COLOR);
        
        javax.swing.GroupLayout keyPanelLayout = new javax.swing.GroupLayout(this);
        this.setLayout(keyPanelLayout);
        keyPanelLayout.setHorizontalGroup(
            keyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(keyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(keyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelKeyName)
                    .addGroup(keyPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(textFieldKeyName, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        keyPanelLayout.setVerticalGroup(
            keyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(keyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelKeyName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textFieldKeyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        /*javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(keyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 135, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(keyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 238, Short.MAX_VALUE))
        );*/
    }

    public JTextField getTextFieldKeyName() {
        return textFieldKeyName;
    }

    public void setTextFieldKeyName(JTextField textFieldKeyName) {
        this.textFieldKeyName = textFieldKeyName;
    }

    public JLabel getLabelKeyName() {
        return labelKeyName;
    }

    public void setLabelKeyName(JLabel labelKeyName) {
        this.labelKeyName = labelKeyName;
    }
    public void setKeyName(String keyName){
        this.textFieldKeyName.setText(keyName);
    }
    public void setKeyNameLabel(String keyLabel){
        this.labelKeyName.setText(keyLabel);
    }
}
