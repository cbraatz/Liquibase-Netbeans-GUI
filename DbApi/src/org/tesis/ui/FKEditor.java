package org.tesis.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import org.tesis.db.Column;
import org.tesis.db.ForeignKey;
import org.tesis.db.Table;
import org.tesis.dbapi.Message;
import org.tesis.dbapi.MyLogger;
import org.tesis.exception.InvalidParameterException;


/**
 * Componente tipo JPanel que es insertado para agregar y editar Foreign Keys
 */
public class FKEditor extends MyEditor{
    private MyJComboBox combo_1;
    private MyJComboBox combo_2;
    private MyJComboBox combo_3;
    private JPanel fkList;
    private JScrollPane jScrollPane1;
    private JLabel label_1;
    private JLabel label_2;
    private JLabel label_3;
    private JPanel panel_left;
    private JPanel panel_right;
    private JButton addButton;
    private JButton removeButton;
    private List<UIForeignKey> newUIForeignKeys;
    //private Dbms dbms; creo que no será necesario
    private Table table;
    private long selectedFKInternalID=0;
    //private List <Table> tables;
    /**
     * Constructor del FKEditor
     * @param dbms dbms
     * @param tables tablas a las que se puede hacer referencia en el FK
     * @param table tabla a la que se desea agregar el FK
     * @param tableForeignKeys la lista de foreign keys de table
     */
    public FKEditor(List<Table> tables, Table table) throws InvalidParameterException {//table no tiene foreign keys
        this.table=table;
        keyNamePanel=new KeyNamePanel();
        panel_left = new JPanel();
        label_1 = new JLabel();
        combo_1 = new MyJComboBox();
        label_2 = new JLabel();
        combo_2 = new MyJComboBox();
        label_3 = new JLabel();
        combo_3 = new MyJComboBox();
        panel_right = new JPanel();
        jScrollPane1 = new JScrollPane();
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setMaximumSize(new Dimension(253,350));
        jScrollPane1.setPreferredSize(new Dimension(253,350));
        addButton=new JButton();
        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                addAction();
            }
        });
        removeButton=new JButton();
        removeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAction();
            }
        });
        this.setBackground(UIConstants.PANELS_PRIMARY_COLOR);
        this.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Agregar/Editar Clave Foránea"));
        combo_1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBox1OnItemSelected();
            }
        });
        combo_2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBox2OnItemSelected();
            }
        });

        //seteando el panel izquierdo
        label_1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_1.setText("Columna:");
        label_2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_2.setText("Tabla Foránea:");
        label_3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_3.setText("Columna Foránea:");
        addButton.setText("Agregar");
        removeButton.setText("Borrar");
        this.loadCombo1ColumnValues(table);//carga el combo1
        this.loadCombo2ForeignTablesValues(tables);//carga el combo2
        //el combo 3 actualiza automaticamente luego de actualizar el 1 y el 2
        
        panel_left.setBackground(UIConstants.PANELS_SECONDARY_COLOR);
        panel_left.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Selectores"));
        javax.swing.GroupLayout panel_leftLayout = new javax.swing.GroupLayout(panel_left);
        panel_left.setLayout(panel_leftLayout);
        ParallelGroup pg3=panel_leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
            pg3.addComponent(combo_1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE);
            pg3.addComponent(combo_2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE);
            pg3.addComponent(combo_3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE);
            pg3.addComponent(addButton);
        SequentialGroup sg3=panel_leftLayout.createSequentialGroup();
            sg3.addGap(10, 10, 10);
            sg3.addGroup(pg3);
        ParallelGroup pg2=panel_leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
            pg2.addComponent(label_1);
            pg2.addComponent(label_2);
            pg2.addComponent(label_3);
            pg2.addGroup(sg3);
        SequentialGroup sg1=panel_leftLayout.createSequentialGroup();
            sg1.addContainerGap();
            sg1.addGroup(pg2);
            sg1.addContainerGap(112, Short.MAX_VALUE); 
        ParallelGroup pg1=panel_leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
            pg1.addGroup(sg1);
        panel_leftLayout.setHorizontalGroup(pg1);
        
        SequentialGroup sg4=panel_leftLayout.createSequentialGroup();
            sg4.addGap(93, 93, 93);
            sg4.addComponent(label_1);
            sg4.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            sg4.addComponent(combo_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
            sg4.addGap(18, 18, 18);
            sg4.addComponent(label_2);
            sg4.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            sg4.addComponent(combo_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
            sg4.addGap(18, 18, 18);
            sg4.addComponent(label_3);
            sg4.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            sg4.addComponent(combo_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
            sg4.addContainerGap(121, Short.MAX_VALUE);
            sg4.addComponent(addButton);
        ParallelGroup pg4=panel_leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
            pg4.addGroup(sg4);
        
        panel_leftLayout.setVerticalGroup(pg4);

        //seteando la lista del panel derecho
        this.loadFKListInitialConfiguration();
        
        panel_right.setBackground(UIConstants.PANELS_TERCIARY_COLOR);
        panel_right.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Lista de claves de la tabla"));
        GroupLayout panel_rightLayout = new GroupLayout(panel_right);
        panel_right.setLayout(panel_rightLayout);
        panel_right.setPreferredSize(panel_left.getPreferredSize());
        SequentialGroup sg5=panel_rightLayout.createSequentialGroup();
            sg5.addContainerGap();
            //sg5.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE);
            sg5.addGroup(panel_rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_rightLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(removeButton)));
            sg5.addContainerGap();
        ParallelGroup pg5=panel_rightLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
            pg5.addGroup(sg5);
        panel_rightLayout.setHorizontalGroup(pg5);
        SequentialGroup sg6=panel_rightLayout.createSequentialGroup();
            /*sg6.addContainerGap();
            sg6.addComponent(jScrollPane1);
            sg6.addContainerGap();*/
            
            sg6.addContainerGap();
            sg6.addComponent(jScrollPane1);
            sg6.addComponent(removeButton);
            sg6.addContainerGap();
            sg6.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            sg6.addComponent(removeButton);
        ParallelGroup pg6=panel_rightLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
            pg6.addGroup(sg6);
            panel_rightLayout.setVerticalGroup(pg6);
                    
        //juntando ambos paneles
        javax.swing.GroupLayout myLayout = new javax.swing.GroupLayout(this);
        this.setLayout(myLayout);
        SequentialGroup sg7=myLayout.createSequentialGroup();
            sg7.addContainerGap();
            sg7.addComponent(panel_left, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
            sg7.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            sg7.addComponent(panel_right, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
            sg7.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        /*ParallelGroup pg7=myLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
            pg7.addGroup(sg7);
        myLayout.setHorizontalGroup(pg7);*/
        
        
        
        /*ParallelGroup pg9=myLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false);
            pg9.addComponent(panel_right, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
            pg9.addComponent(panel_left, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);*/
        /*SequentialGroup sg8=myLayout.createSequentialGroup();
            sg8.addContainerGap();
            sg8.addGroup(pg9);
            sg8.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);*/
        /*ParallelGroup pg8=myLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
            pg8.addGroup(sg8);
        myLayout.setVerticalGroup(pg8);*/
        
        
             //////////////////////////////////////////////////////
            /*SequentialGroup sgg=layout.createSequentialGroup();
                sgg.addComponent(left, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
                sgg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
                sgg.addComponent(right, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);*/
            ParallelGroup pgg=myLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false);
                pgg.addComponent(keyNamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
                pgg.addGroup(sg7);
            SequentialGroup sg=myLayout.createSequentialGroup();
                sg.addGroup(pgg);
            ParallelGroup pg=myLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
                pg.addGroup(sg);
                pg.addGap(0, 11, Short.MAX_VALUE);
            myLayout.setHorizontalGroup(pg);
                
            
            ParallelGroup bpgg=myLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
                    bpgg.addComponent(panel_left, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
                    bpgg.addComponent(panel_right, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
            SequentialGroup bsg=myLayout.createSequentialGroup();
                bsg.addComponent(keyNamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
                bsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
                bsg.addGroup(bpgg);
                bsg.addGap(0, 11, Short.MAX_VALUE);
            ParallelGroup bpg=myLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
            
            bpg.addGroup(bsg);
        myLayout.setVerticalGroup(bpg);
        /////////////////////////////////////////////////////
        
        
    }
    private void initList(){
        fkList = new JPanel();
        fkList.setLayout(new BoxLayout(fkList, BoxLayout.PAGE_AXIS));
        fkList.setBackground(Color.LIGHT_GRAY); 
        
    }
    
    /**
     * Carga la lista de FK con los valores iniciales
     */
    private void loadListValues(){
        //fkList = new JPanel();
        initList();
        for(int i=0;i<newUIForeignKeys.size();i++){
            UIForeignKey uifk=(UIForeignKey) newUIForeignKeys.get(i);
            uifk.addMouseListener(new MyMouseEvent());
            fkList.add(uifk);
            //System.out.println("Agregado el listener para "+uifk.getColumnName());
        }
        jScrollPane1.setViewportView(fkList);
        jScrollPane1.updateUI();
        jScrollPane1.revalidate();
        this.keyNamePanel.setKeyName("");
    }

    @Override
    public Object exportEditorResults() throws Exception{
        List<UIForeignKey> fks=new ArrayList<>();
        for(UIForeignKey f:newUIForeignKeys){
            if(f.isVisible()){
               fks.add(f);
            }
        }
        return fks;
    }
    
    private void loadCombo1ColumnValues(Table tab){//tabla actual
        DefaultComboBoxModel model= new DefaultComboBoxModel();
        if(null!=tab){
            for (Column c: tab.getColumns()){
                model.addElement(c);
            }
            combo_1.setModel(model);
            this.loadCombo3ForeignTableColumnValues();
        }else{
            combo_1.setModel(model);
        }
    }
    
    private void loadCombo2ForeignTablesValues(List<Table> tables){//tabla foranea
        DefaultComboBoxModel model= new DefaultComboBoxModel();
        if(null!=tables){
            for (Table t: tables){
                if(!t.getName().equals(table.getName())){//no agrega a la lista de foreign tables la tabla de origen
                    model.addElement(t);
                }
            }
            combo_2.setModel(model);
            this.loadCombo3ForeignTableColumnValues();
        }else{
            combo_2.setModel(model);
        }
    }
    
    private void loadCombo3ForeignTableColumnValues(){//columna foranea
        DefaultComboBoxModel model= new DefaultComboBoxModel();
        Object o=combo_1.getSelectedItem();
        Object o2=combo_2.getSelectedItem();
        if(null!=o && null!=o2){
            Column col=(Column) o;
            Table tab=(Table) o2;
            for (Column c: tab.getColumns()){
                if(c.getType().toString().equals(col.getType().toString())){//solo agrega las columnas con tipo de dato igual al item seleccionado en el combo1(Columna)
                   if(c.isPk()){//solo agrega los pk
                       model.addElement(c);
                   }
                }
            }
        }
        combo_3.setModel(model);
    }
    
    @Override
    public void reset() throws Exception{
        setInternalId(0);
        this.loadFKListInitialConfiguration();
        this.keyNamePanel.setKeyName("");
     /* this.columns=new ArrayList<>();
        updateColumns();
        this.textFiendName.setText("");
        this.spinnerLenght.setValue(SPINNER_DEFAULT_VALUE);
        this.checkboxPK.setSelected(false);
        this.checkboxAllowNull.setSelected(false);
        this.comboBoxType.setSelectedIndex(0);
        this.selectedColInternalId=0;*/
        //panelContainer.revalidate();
        validate();
    }

    private void comboBox2OnItemSelected(){
        //actualizar el combo 3
        this.loadCombo3ForeignTableColumnValues();
    }
    private void comboBox1OnItemSelected(){
        //actualizar el combo 3
        this.loadCombo3ForeignTableColumnValues();
        this.setInternalId(0);
    }

    
    
    private void addAction() {
        try {
            String key=this.keyNamePanel.getTextFieldKeyName().getText();
            if(!key.isEmpty()){
                Column col=(Column)combo_1.getSelectedItem();
                Table ftab=(Table)combo_2.getSelectedItem();
                Column fcol=(Column)combo_3.getSelectedItem();
                if(null!=col && null!=ftab && null!=fcol){
                    boolean valid=true;
                    for(UIForeignKey fk:newUIForeignKeys){
                        if(fk.isVisible()){
                            if(fk.getColumnName().equals(col.getName())){
                                valid=false;
                            }
                        }
                    }
                 //  if(valid){
                        if(selectedFKInternalID==0){
                            if(valid){
                                this.newUIForeignKeys.add(new UIForeignKey(new ForeignKey(null, key, this.table, col, ftab, fcol)));
                            }else{
                                Message.showWarningMessage("La columna "+col.getName()+" ya está en la lista de ForeignKeys. Si decea editar el FK, favor seleccionelo de la lista");
                            }
                        }else{
                            UIForeignKey uiFK=null;
                            for(UIForeignKey fk:newUIForeignKeys){
                                if(fk.getInternalID() == selectedFKInternalID){
                                    uiFK=fk;
                                    break;
                                }
                            }
                            uiFK.setColumnName(col.getName());
                            uiFK.setForeignTableName(ftab.getName());
                            uiFK.setForeignColumnName(fcol.getName()); 
                            setInternalId(0);
                        } 
                        this.loadListValues();

                    /**}else{
                        Message.showWarningMessage("La columna "+col.getName()+" ya está en la lista de ForeignKeys.");
                    }*/
                }else{
                    Message.showWarningMessage("Favor seleccionar un valor para Columna, Tabla foránea y Columna foránea; alguna de ellas no es válida.");
                }
                this.keyNamePanel.setKeyName("");
            }else{
                Message.showWarningMessage("Favor especifigar un valor para el nombre de la clave foranea.");
            }
        }catch (InvalidParameterException ex) {
            Message.showErrorMessage(ex.getMessage());
            MyLogger.LogErrorMessage(ex);
        }
    }
    private void removeAction() {
        if(selectedFKInternalID==0){
            Message.showWarningMessage("No hay Foreign Key seleccionado para borrar.");
        }else{
            UIForeignKey uiFK=null;
            for(UIForeignKey fk:newUIForeignKeys){
                if(fk.isVisible()){
                    if(fk.getInternalID() == selectedFKInternalID){
                        uiFK=fk;
                        break;
                    }
                }
            }
            newUIForeignKeys.remove(uiFK);
            setInternalId(0);
        } 
        this.loadListValues();
    }
    int aux=0;
    private void selectForeignKey(UIForeignKey uiFk){
        this.combo_1.setSelectedItem(uiFk.getColumnName());
        this.combo_2.setSelectedItem(uiFk.getForeignTableName());
        this.combo_3.setSelectedItem(uiFk.getForeignColumnName());
        this.keyNamePanel.setKeyName(uiFk.getFKName());
        setInternalId(uiFk.getInternalID());
    }
    private void setInternalId(long id){
        this.selectedFKInternalID=id;
        for(UIForeignKey u:newUIForeignKeys){
            if(u.isVisible()){
                if(u.getInternalID()==id){
                    u.setBackground(UIConstants.LIST_ELEMENTS_ON_MOUSE_OVER_COLOR);
                }else{
                    u.setBackground(UIConstants.LIST_ELEMENTS_ORIGINAL_COLOR);
                }
            }
        }
    }

    private void loadFKListInitialConfiguration() throws InvalidParameterException {
        newUIForeignKeys=new ArrayList<>();
        for(ForeignKey fk:table.getForeignKeys().getForeignKeys()){
            if(!fk.getTable().getName().equals(table.getName())){
                throw new InvalidParameterException("Parámetro tableForeignKeys del constructor PKEditor no debe contener ForeignKeys pertenecientes a otra tabla; solamente de la tabla "+table.getName());
            }
            newUIForeignKeys.add(new UIForeignKey(fk));//va cargando la lista de vieja de UIForeignKeys
        }
        this.loadListValues();
    }
    private class MyMouseEvent implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            UIForeignKey f=(UIForeignKey) e.getSource();
            if(selectedFKInternalID != f.getInternalID()){//para que no ejecute varias veces al hacer click, sino no se xq repite 5 veces
                selectForeignKey(f);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
           // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            UIForeignKey f=(UIForeignKey) e.getSource();
            f.setBackground(UIConstants.LIST_ELEMENTS_ON_MOUSE_OVER_COLOR);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            UIForeignKey f=(UIForeignKey) e.getSource();
            if(f.getInternalID()!=selectedFKInternalID){
                f.setBackground(UIConstants.LIST_ELEMENTS_ORIGINAL_COLOR);
            }
        }
    }
}