package org.tesis.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import org.openide.util.Exceptions;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.property.PropertyValueValidator;
import org.tesis.changelog.tag.ColumnTag;
import org.tesis.changelog.type.Type;
import org.tesis.changelog.type.TypeFactory;
import org.tesis.db.Column;
import org.tesis.db.Constants;
import org.tesis.db.dbms.Dbms;
import org.tesis.dbapi.Message;
import org.tesis.dbapi.MyLogger;
import org.tesis.exception.InvalidTypeException;
import org.tesis.mybase.ColumnList;

/**
 * Clase que permite manejar  todos los elementos que aparecen en el  panel editor. 
 * <br> esta clase maneja todos los componentes que permiten hacer alguna operación sobre las tablas de la base de datos,
 * <br> la misma permite graficar la interface que es utilizada para realizar dichas operaciones.
 * @author Claus
 */

public class TableEditor extends MyEditor implements ComponentListener{
    /*
     *  Se crean e inicializan todos aquellos elementos a ser utilizados en el panel
     */
    private javax.swing.JButton buttonAdd;
    private javax.swing.JComboBox comboBoxType;
    private javax.swing.JLabel labelTitle1;
    private javax.swing.JLabel labelTitle2;
    private javax.swing.JLabel labelTitle3;
    private javax.swing.JLabel labelTitle4;
    private javax.swing.JLabel labelTitle5;
    private javax.swing.JPanel panelList;
    private javax.swing.JSpinner spinnerLenght;
    private javax.swing.JTextField textFiendName;
    private javax.swing.GroupLayout panelListLayout;
    private javax.swing.JCheckBox checkboxPK;
    private javax.swing.JCheckBox checkboxAllowNull;
    private List<UIColumn> columns;
    private List<Column> originalColumns=new ArrayList<>();
    private Type selectedType=null;
    private final int SPINNER_DEFAULT_VALUE=1;
    private Dbms dbms;
    private long selectedColInternalId=0;
    
    /**
     * Constructor del editor de la tablas,  este constructor inicializa y crea el TableEditor, panel que  visualiza los campos  y las por propiedades de los mismos, además de permitir 
     * <br> realizar operaciones sobres los campos tales como, editar, crear y modificar.
     * 
     * @param dbms Objeto del tipo Dbms el cual contiene todos los datos necesarios y referentes al motor de base de datos utilizado 
     */

    public TableEditor(Dbms dbms) {
        this.dbms=dbms;
        keyNamePanel=new KeyNamePanel();
        keyNamePanel.setVisible(false);
        columns=new ArrayList<>();
        labelTitle1 = new javax.swing.JLabel();
        textFiendName = new javax.swing.JTextField();
        labelTitle2 = new javax.swing.JLabel();
        comboBoxType = new javax.swing.JComboBox();
        labelTitle3 = new javax.swing.JLabel();
        labelTitle4 = new javax.swing.JLabel();
        labelTitle5 = new javax.swing.JLabel();
        spinnerLenght = new javax.swing.JSpinner();
        buttonAdd = new javax.swing.JButton();
        panelList = new javax.swing.JPanel();
        panelListLayout = new javax.swing.GroupLayout(panelList);
        checkboxPK= new javax.swing.JCheckBox();
        checkboxAllowNull= new javax.swing.JCheckBox();
        
        this.setBackground(UIConstants.PANELS_PRIMARY_COLOR);
        this.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Columnas"));
        labelTitle1.setFont(new java.awt.Font("Tahoma", 1, 12));
        labelTitle1.setText("Nombre:");     
        labelTitle2.setFont(new java.awt.Font("Tahoma", 1, 12));
        labelTitle2.setText("Tipo:");
        labelTitle3.setFont(new java.awt.Font("Tahoma", 1, 12));
        labelTitle3.setText("Longitud:");
        labelTitle4.setFont(new java.awt.Font("Tahoma", 1, 12));
        labelTitle4.setText("PK:");
        labelTitle5.setFont(new java.awt.Font("Tahoma", 1, 12));
        labelTitle5.setText("Null:");
        comboBoxType.setModel(new javax.swing.DefaultComboBoxModel(dbms.getDbmsInstance().getAllTypeNames().toArray()));
        comboBoxType.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSelectedType();
            }
        });
        updateSelectedType();
        this.checkboxPK.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                checkBoxPKActionPerformed();
            }
        });
        checkboxPK.setText("");
        checkboxAllowNull.setText("");
        spinnerLenght.setValue(SPINNER_DEFAULT_VALUE);
        buttonAdd.setText("+");
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {@Override public void actionPerformed(java.awt.event.ActionEvent evt) {buttonAddActionPerformed(evt);}});

        javax.swing.GroupLayout MyLayout = new javax.swing.GroupLayout(this);
        this.setLayout(MyLayout);
        
        //ordenado por columnas, una al lado de la otra
        ParallelGroup pg3=MyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
           pg3.addComponent(labelTitle1);
           pg3.addComponent(textFiendName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE);
        ParallelGroup pg4=MyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
           pg4.addComponent(comboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE);
           pg4.addComponent(labelTitle2);
        ParallelGroup pg5=MyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false);
           pg5.addComponent(labelTitle3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
           pg5.addComponent(spinnerLenght);
        ParallelGroup pg6=MyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false);
           pg6.addComponent(labelTitle4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
           pg6.addComponent(checkboxPK);
        ParallelGroup pg7=MyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false);
           pg7.addComponent(labelTitle5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
           pg7.addComponent(checkboxAllowNull);   
        SequentialGroup sg2=MyLayout.createSequentialGroup();    
            sg2.addGroup(pg3);
            sg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,20,20);
            sg2.addGroup(pg4);
            sg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,20,20);
            sg2.addGroup(pg5);
            sg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,20,20);
            sg2.addGroup(pg6);
            sg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,20,20);
            sg2.addGroup(pg7);
            sg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,20,20);
            sg2.addComponent(buttonAdd);
        ParallelGroup pg2=MyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
            pg2.addComponent(panelList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);             
            pg2.addGroup(sg2);
            pg2.addComponent(keyNamePanel);
        SequentialGroup sg1=MyLayout.createSequentialGroup();
            sg1.addContainerGap();
            sg1.addGroup(pg2);
            sg1.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        ParallelGroup pg1=MyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
            pg1.addGroup(sg1);
            
        MyLayout.setHorizontalGroup(pg1);
            
        
        //ordenado por filas de elementos, tiras largas una sobre la otra
        ParallelGroup xpg0=MyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
            xpg0.addComponent(keyNamePanel);
           
        ParallelGroup xpg2=MyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
            xpg2.addComponent(labelTitle1);
            xpg2.addComponent(labelTitle2);
            xpg2.addComponent(labelTitle3);  
            xpg2.addComponent(labelTitle4); 
            xpg2.addComponent(labelTitle5); 
        ParallelGroup xpg3=MyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
            xpg3.addComponent(textFiendName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
            xpg3.addComponent(comboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
            xpg3.addComponent(spinnerLenght, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
            xpg3.addComponent(checkboxPK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
            xpg3.addComponent(checkboxAllowNull, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
            xpg3.addComponent(buttonAdd);
        SequentialGroup xsg1=MyLayout.createSequentialGroup();
            xsg1.addContainerGap();
            
            xsg1.addGroup(xpg2);
            xsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            xsg1.addGroup(xpg3);
            xsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            xsg1.addComponent(panelList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
            xsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            xsg1.addGroup(xpg0);
            xsg1.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        ParallelGroup xpg1=MyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
            xpg1.addGroup(xsg1);
            
        MyLayout.setVerticalGroup(xpg1);
        
    }

    
    /**
     * Metodo generico utilizado para setear objetos
     * @param obj Objeto a setear
     */
    @Override
    public void setValues(Object obj) {
        List<Column> cols=(List<Column>) obj;
        this.columns=new ArrayList<>();
        for(Column c:cols){
            UIColumn col=new UIColumn(c);
            col.addComponentListener(this);
            columns.add(col);
            originalColumns=cols;
        }
        updateColumns();
    }
   /**
     * Método que permite habilitar y deshabilitar el Spiner de longitud de los campos,  el método captura el tipo de datos seleccionado en el comboBox y verifica con los tipos de datos
     * <br> especificados para el motor, si los mismos  poseen o no la propiedad  para especificar la longitud que puede aceptar el campo.
     * <br> <br>En caso de que se pueda especificar una longitud para el tipo de datos el Spiner se activa en caso contrario se desactiva
     */

    private void updateSelectedType(){
        try {
            this.selectedType=TypeFactory.getTypeInstance(this.comboBoxType.getSelectedItem().toString(),dbms);
            if(this.selectedType.hasLenght()){
                spinnerLenght.setEnabled(true);
            }else{
                spinnerLenght.setEnabled(false);
            }
        } catch (InvalidTypeException ex) {
            spinnerLenght.setEnabled(false);
            Logger.getLogger(TableEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      /**
     * Método que permite setear el tipo de  motor de base de datos  a ser utilizado 
     * 
     * @param dbms Recibe como parámetro un objeto del tipo Dbms que contiene todos las propiedades del motor de base de datos. 
     */

    public void setDbms(Dbms dbms) {
        this.dbms = dbms;
    }

     /**
     * Método que permite  refrescar el TableEditor, es utilizado para refrescar la lista de columnas que  son visualizadas en el  panel TableEditor
     * <br> El método es  utilizado luego de eliminar, crear o editar algún campo, el mismo hace que esos cambios se reflejen en la interface grafica
     */

    private void updateColumns(){
        panelList.setLayout(panelListLayout);
        ParallelGroup pg1 = panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        SequentialGroup sg=panelListLayout.createSequentialGroup();
        for(UIColumn c:columns){
            if(c.isVisible()){
                c.addMouseListener(new MyMouseEvent());
                pg1.addComponent(c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
                sg.addComponent(c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
            }
        }
        panelListLayout.setHorizontalGroup(pg1);
        ParallelGroup pg2 = panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        pg2.addGroup(sg);
        panelListLayout.setVerticalGroup(pg2);
    }
   /**
     * Método que permite eliminar un campo de la lista de campos correspondiente a una tabla.
     * <br>El método recibe como parámetro el nombre del campo a eliminar, busca el mismo dentro de la lista y lo remueva
     * @param name  Nombre del campo a ser eliminado
     */

    public void removeColumnByName(String name){
        for(UIColumn c: columns){
            if(c.getColumnName().equals(name)){
                columns.remove(c);
                break;
            }
        }
    }
    /**
     * Método que permite resetear  el panel TableEditor, el mismo  vuelve a todos los componentes del panel a su estado original y limpia además la lista de columnas 
     * <b>visualizadas en el mismo
     */

    @Override
    public void reset(){
        for(UIColumn u:columns){
            u.setVisible(false);//no se xq tengo que hacer esto pero si no hago queda
        }
        this.columns=new ArrayList<>();
        for(Column co:originalColumns){
            UIColumn uic=new UIColumn(co);
            uic.addMouseListener(new MyMouseEvent());
            columns.add(uic);
        }
        this.keyNamePanel.setKeyName("");
        updateColumns();
        this.textFiendName.setText("");
        this.spinnerLenght.setValue(SPINNER_DEFAULT_VALUE);
        this.checkboxPK.setSelected(false);
        this.checkboxAllowNull.setSelected(false);
        this.comboBoxType.setSelectedIndex(0);
        this.selectedColInternalId=0;
        this.keyNamePanel.setVisible(false);
        validate();
    }

    @Override
    public void componentResized(ComponentEvent e) {
       //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentShown(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        UIColumn col=(UIColumn)e.getComponent();
        if(col.isDeleted()){
            this.removeColumnByName(col.getColumnName());
            this.updateColumns();
            this.clearFields();
        }
    }
    
   /**
     * Método que permite buscar una columna de una tabla utilizando el Id interno de la misma.
     * @param internalID Id interno del campo  que se desea buscar
     * @return  Objeto del tipo UIColumn que contiene  todas la propiedades de la columna en caso de que se encuentre una columna con el Id especificado, en caso contrario retorna NULL
     */
    private UIColumn getUIColumnByInternalId(long internalID){
        for(UIColumn c:this.columns){
            if(c.isVisible()){
                if(c.getInternalId()==internalID){
                    return c;
                }
            }
        }
        return null;
    }
    /**
     * Método que permite buscar una columna de una tabla utilizando el nombre de la misma.
     * @param name  Nombre del campo  que se desea buscar
     * @return  Objeto del tipo UIColumn que contiene  todas la propiedades de la columna en caso de que se encuentre una columna con el Nombre especificado, en caso contrario retorna NULL
     */
 
    private UIColumn getUIColumnByName(String name){
        for(UIColumn c:this.columns){
            if(c.isVisible()){
                if(c.getColumnName().equals(name)){
                    return c;
                }
            }
        }
        return null;
    }
    private Column getOriginalColumnsByName(String name) {
        for(Column col:originalColumns){
            if(col.getName().equals(name)){
               return col; 
            }
        }
        return null;
    }
     /**
     * Método ejecutado al presionar el botón utilizado para agregar  un nuevo campo a la lista de campos (botón con el símbolo +), dentro del método se realizan controles como:
     * <br>    SI nombre del campo no es vacío    
     * <br>    Si el nombre del campo ingresado es o no valido.    
     * <br>    Si el nombre ingresado no igual a algún otro campo que pertenece a la tabla sobre la cual se está trabajando.
     * <br>Una vez verificado que los datos ingresados sean válidos, el nuevo campo es agregado a  la lista de columnas de la tabla y se actualiza el panes TableEditor para que la nueva
     * <br>sea agregada a la lista de columnas de la tabla sobre la cual se esté trabajando
     * @param evt 
     */

    private void buttonAddActionPerformed(java.awt.event.ActionEvent evt){
        if(!this.textFiendName.getText().isEmpty()){
            if(PropertyValueValidator.validateName(this.textFiendName.getText())){
                if(null == this.getUIColumnByName(this.textFiendName.getText()) || selectedColInternalId > 0){
                    Column c=this.getOriginalColumnsByName(this.textFiendName.getText());
                    boolean valid = true;
                    if(null != c){
                        if(c.getInternalId() != selectedColInternalId){
                            boolean x=Message.showYesNoQuestionMessage("La columna "+c.getName()+" ha sido borrada o editada recientemente. ¿Desea cargar la original?");
                            if(x){
                                this.selectColumn(new UIColumn(c));
                                this.selectedColInternalId=c.getInternalId();
                            }else{
                                valid=false;
                            }
                        }
                    }
                    if(valid){
                        Type t=null;
                        try {
                            t = TypeFactory.getTypeInstance(this.comboBoxType.getSelectedItem().toString(), dbms);
                            if(this.spinnerLenght.isEnabled()){
                               t.setLenght(this.spinnerLenght.getValue().toString());
                            }
                        } catch (InvalidTypeException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                        UIColumn col= this.getUIColumnByInternalId(selectedColInternalId);
                        if(null==col){
                            if(selectedColInternalId==0){//si se agrega nueva columna
                                col=new UIColumn(new Column(this.textFiendName.getText(), t, this.checkboxPK.isSelected(), this.checkboxAllowNull.isSelected()));
                            }else{//si la columna fue borrada y agregada de nuevo hay que mostrar
                                col=new UIColumn(new Column(this.textFiendName.getText(), t, this.checkboxPK.isSelected(), this.checkboxAllowNull.isSelected(),selectedColInternalId));
                            }
                            col.addComponentListener(this);
                            this.columns.add(col);
                        }else{
                            col.setColName(this.textFiendName.getText());
                            col.setColType(t.getPublicName());
                            if(t.getLenght()<0){
                                col.setColLenght("");
                            }else{
                                col.setColLenght(t.getLenght().toString());
                            }
                            col.setColNullable(this.checkboxAllowNull.isSelected());
                            col.setColPK(this.checkboxPK.isSelected());
                        }
                        if(col.isColumnPK() && !this.isEditMode()){//si es pk y si no se está editando la tabla
                            keyNamePanel.setVisible(true);
                        }
                    }else{
                        Message.showWarningMessage("Esta columna no puede ser agregada. Favor reemplazarla por la original.");
                        this.textFiendName.setText("");
                    }
                }else{
                    Message.showWarningMessage("Este nombre de columna ya existe. Favor ingresar otro");
                    this.textFiendName.setText("");
                }
                this.updateColumns();
                this.clearFields();
            }else{
                Message.showWarningMessage("El nombre de la columna no es válido.");
            }
        }else{
            Message.showWarningMessage("Debe proporcionar un nombre de Columna antes de continuar.");
        }
    }
    private void clearFields(){
        selectedColInternalId=0;
        this.textFiendName.setText("");
        this.checkboxAllowNull.setSelected(false);
        this.checkboxPK.setSelected(false);
    }
    /**
     * Método utilizado para resetear los valores del checkBox utilizado para especificar si  el campo  es o no clave primaria, en caso de que el checkBox este  tildado  el método lo
     * <br>destilada y lo habilita, en caso contrario lo habilita nada más.
     */

    private void checkBoxPKActionPerformed(){
        if(this.checkboxPK.isSelected()){
            this.checkboxAllowNull.setSelected(false);
            this.checkboxAllowNull.setEnabled(false);
        }else{
            this.checkboxAllowNull.setEnabled(true);
        }
    }
    /**
     * Método utilizado  para exportar  una lista de columnas a una lista de objetos del tipo ColumnList,  además de la lista de los campos asociados a la tabla, también es agregado
     * <br>el objeto Dbms el cual contiene todas la propiedades del motor  de base de datos utilizado, estos datos son necesarios para validación de  los campos.
     * @return Lista de objetos ColumnList, la cual contiene todos los campos de la base de datos además  del objeto Dbms, el cual contiene las propiedades del motor.
     * @throws Exception  
     */

    @Override
    public ColumnList exportEditorResults() throws Exception{
        try{
            ColumnList columnTags=new ColumnList();
            for(UIColumn uic:columns){
                if(uic.isVisible()){
                    PropertyList props =new PropertyList();
                    props.addProperty(Constants.PROPERTY_ID, Long.toString(uic.getInternalId()));
                    props.addProperty(Constants.PROPERTY_NAME, uic.getColumnName());
                    props.addProperty(Constants.PROPERTY_TYPE, uic.getColumnType());
                    props.addProperty(Constants.PROPERTY_PRIMARY_KEY, Boolean.toString(uic.isColumnPK()));
                    props.addProperty(Constants.PROPERTY_NULLABLE, Boolean.toString(uic.isColumnNullable()));
                    if(TypeFactory.getTypeInstance(uic.getColumnType(),dbms).hasLenght()){
                        props.addProperty(Constants.PROPERTY_LENGHT, uic.getColumnLenght());
                    }
                    columnTags.addColumn(new ColumnTag(props, dbms));
                }
            }
            return columnTags;
        }catch(Exception e){
            MyLogger.LogErrorMessage(e);
            throw new Exception("Ha ocurrido un error en el método exportColumnListObject()",e);
        }
    }
    
    /**
     * Método que permite exportar la lista de columnas  visualizadas en el panel TableEditor a una lista de objetos del tipo Column.
     * @return La lista de objetos del tipo Column
     * @throws Exception 
     */
    public List<Column> exportColumns() throws Exception{
            List<Column> cols=new ArrayList<>();
            for(UIColumn uic:columns){
                if(uic.isVisible()){
                    Type type=TypeFactory.getTypeInstance(uic.getColumnType(),dbms);
                    if(type.hasLenght()){
                        type.setLenght(uic.getColumnLenght());
                    }
                    Column c=new Column(uic.getColumnName(), type, uic.isColumnPK(), uic.isColumnNullable(),uic.getInternalId());
                    cols.add(c);
                }
            }
            return cols;
    }
    /**
     * Método que permite setear los componentes  que representan los atributos de un campo, este método realiza el seteo con los atributos de la columna seleccionada dentro 
     * <br>de los componentes que  representan dichos atributos, la columna seleccionada es recibida por parámetro por el método.
     * 
     * @param col Columna seleccionada en la lista de campos que posee la tabla
     */

    public void selectColumn(UIColumn col){
        selectedColInternalId=col.getInternalId();
        this.textFiendName.setText(col.getColumnName());
        this.comboBoxType.setSelectedItem(col.getColumnType());
        if(checkboxPK.isEnabled()){
            checkboxPK.setSelected(col.isColumnPK());
        }
        if(checkboxAllowNull.isEnabled()){
            checkboxAllowNull.setSelected(col.isColumnNullable());
        }
        if(spinnerLenght.isEnabled()){
            spinnerLenght.setValue(Integer.parseInt(col.getColumnLenght()));
        }
    }
    /**
     * Clase en la cual se implementan  de manera personalizada  el comportamiento que debe tener el mouse  en las diferentes situaciones
     */
    private class MyMouseEvent implements MouseListener{
        
       /**
         * Método personalizado, el cual  obtiene  la columna seleccionada de  la lista de campos que se visualiza en el TableEditor y realiza la llamada del método selectColumn()
         * <br>pasando por parámetro al método el campo seleccionado
         * @param e 
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            UIColumn c=(UIColumn) e.getSource();
            selectColumn(c);
        }
        /**
         * 
         * @param e 
         */
        @Override
        public void mousePressed(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        /**
         * 
         * @param e 
         */
        @Override
        public void mouseReleased(MouseEvent e) {
           // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        /**
         * Método  ejecutado cuando el mouse  esta sobre algún campo visualizado en la lista de campos del panel TableEditor, el método hace que cambie el color del fondo de la fila sobre la 
         * <br> cual está el mouse.
         * @param e 
         */

        @Override
        public void mouseEntered(MouseEvent e) {//mouse over
            UIColumn c=(UIColumn) e.getSource();
            c.setBackground(UIConstants.LIST_ELEMENTS_ON_MOUSE_OVER_COLOR);
        }
        /**
         * Método  ejecutado cuando el mouse  deja de estar sobre algún campo visualizado en la lista de campos del panel TableEditor, el método hace que cambie el color del fondo de la fila  
         * <br>  cuando el mouse deja de estar sobre ella.
         * @param e 
         */
        @Override
        public void mouseExited(MouseEvent e) {
           UIColumn c=(UIColumn) e.getSource();
            c.setBackground(UIConstants.LIST_ELEMENTS_ORIGINAL_COLOR);
        }
        
    }
}