package org.tesis.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.openide.util.Exceptions;
import org.tesis.db.Column;
import org.tesis.db.Data;
import org.tesis.db.DataColumn;
import org.tesis.db.DataRow;
import org.tesis.db.PK;
import org.tesis.db.Table;
import org.tesis.dbapi.Message;
import org.tesis.exception.InvalidParameterException;

public class DataTableEditor extends JPanel  implements TableModelListener, ListSelectionListener{
    private JTable jtable;
    private JScrollPane jscrollPane;
    //private Dbms dbms;
    private DefaultTableModel model;
    //List<TableChange> changeList=new ArrayList<>();
    private Data addDataList=new Data();
    private Data editDataList=new Data();
    private Data removeDataList=new Data();
    private String[] columnNames;
    private Boolean[] columnQuoted;//if columns are quoted
    private String[][] data;
    private String oldData[][];
    //private List <String> initialRowIIDs;
    private List <String> rowIDs;
    private List<RowID> initialRowIDs;//era Long initialRowIDs[];
    private int numberOfNewRows=0;//numero de nuevas filas agregadas
    private int numberOfBlankRows;
    private final int BLANK_ROWS=1;//numero de filas en blanco que se agregan cada vez
    private Table dbTable;
    private JButton removeButton = new JButton("Borrar");
    private int[] selectedRows;
    public DataTableEditor(){ 
        
    }
    public DataTableEditor(Table dbTable){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.dbTable=dbTable;
        columnNames= new String[dbTable.getColumns().size()];
        columnQuoted= new Boolean[dbTable.getColumns().size()];
        int c=0;
        for(Column col:dbTable.getColumns()){
            columnNames[c]=col.getName();
            columnQuoted[c]=col.getType().isInsertRequestWithQuotes();
            c++;
        }
        int numberOfInitiallRows=dbTable.getRows().size();
        data=new String[numberOfInitiallRows+BLANK_ROWS][dbTable.getColumns().size()];
        numberOfBlankRows=BLANK_ROWS;
        oldData=new String[numberOfInitiallRows][dbTable.getColumns().size()];
        rowIDs=new ArrayList<>();
        //initialRowIIDs=new ArrayList<>();
        initialRowIDs=new ArrayList<>();
       
        
        
        boolean exists;
        int r=0;
        //List<String> pkNames=this.dbTable.getPKColumnList().getColumns();
        for(DataRow row:dbTable.getRows()){
            c=0;
            for(String columnName:columnNames){
                exists=false;
                for(DataColumn col:row.getDataColumns()){
                    if (col.getColumnName().equals(columnName)){
                        data[r][c]=col.getValue();
                        oldData[r][c]=col.getValue();
                        exists=true;
                        /*for(String pkName:pkNames){
                            if(col.getColumnName().equals(pkName)){
                                iidList.add(new PK(columnName, col.getValue(), col.isQuoted()));
                            }
                        }*/
                        break;
                    }
                }
                if(!exists){
                    data[r][c]=null;
                }
                c++;
            }
            
            
            this.rowIDs.add(row.getInternalId());
            this.initialRowIDs.add(new RowID(row.getInternalId()));
            //this.initialRowIIDs.add(row.getInternalId());
            r++;
        }
        model=new DefaultTableModel(data, columnNames);
        jtable=new JTable(model);
        this.model.addTableModelListener(this);
        jscrollPane = new JScrollPane(jtable);
        jtable.setFillsViewportHeight(true);
        jtable.getSelectionModel().addListSelectionListener(this);
        
        this.jtable.setDefaultRenderer(Object.class, new MyCellRender(this.dbTable));
        
        this.add(jscrollPane);
        removeButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            removeRows();
         }
        });
        removeButton.setEnabled(false);
        selectedRows=null;
        JPanel buttons=new JPanel();
        this.add(buttons);
        buttons.add(removeButton);
        buttons.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        validate();
    }
    private void removeRows(){
        boolean resp=Message.showYesNoQuestionMessage((selectedRows.length==1?"¿Está seguro que desea borrar este elemento?":"¿Está seguro que desea borrar estos "+selectedRows.length+" elementos?"));
        if(resp){
            boolean exists;
            for(int i=selectedRows.length-1; i>=0; i--){
                int row=selectedRows[i];
                exists=false;
                for(RowID iri:initialRowIDs){
                    if(iri.isEnabled()){
                        if(this.rowIDs.get(row).equals(iri.getId())){
                            exists=true;
                            iri.setEnabled(false);//marca como desabilitado en initialRowIDs
                            break;
                        }
                    }
                }
                if(exists){//si se esta borrando un dato ya existente en la base de datos
                    this.addRowToRemoveList(row);
                    //this.addDataList.removeRowFromListIfExists(this.rowIDs.get(row));//no es muy necesario xq no debería ser agregado a esta lista si es un dato precargado
                    this.editDataList.removeRowFromListIfExists(this.rowIDs.get(row));
                    //this.initialRowIIDs.remove(row);//borra solo si es un dato cargado a la bd y no un dato nuevo
                }else{
                    this.addDataList.removeRowFromListIfExists(this.rowIDs.get(row));
                    //this.editDataList.removeRowFromListIfExists(this.rowIDs.get(row));//no es muy necesario xq no debería ser agregado a esta lista si es un dato es nuevo
                    this.numberOfNewRows--;
                }
                this.model.removeRow(row);
                this.rowIDs.remove(row);
            }
        }
    }
    public JTable getJTable() {
        return jtable;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int col = e.getColumn();
        //TableModel model = (TableModel)e.getSource();
        if(row<model.getRowCount() && row>=0 && col<model.getColumnCount() && col>=0){
            String columnName = this.model.getColumnName(col);
            
            String newDataValue = this.model.getValueAt(row, col).toString();
            //System.out.println("Type="+e.getType()+"  FirstRow="+row+"  Col="+col+"  ColName="+columnName+"  Data="+newDataValue);
            if(e.getType() == TableModelEvent.UPDATE){
                if(row < rowIDs.size()){//cuando se agrega nueva fila el id aun no esta agregado en rowIDs
                    if(this.isInternalIdInInitialRowsList(rowIDs.get(row))){//si se está editando una fila nueva que no estuvo en la tabla original
                        String oldDataValue = oldData[row][col];
                        if(oldDataValue==null || !newDataValue.toString().equals(oldDataValue.toString())){
                            this.addRowToEditList(row, col, columnName, newDataValue);
                        }
                    }else{
                        this.addRowToAddList(row, col, columnName, newDataValue);
                    }
                }else{
                    this.addRowToAddList(row, col, columnName, newDataValue);
                }
            }            
        }
    }
    /*private void addToChangeList(TableChange tableChange){
        this.changeList.add(tableChange);
    }*/
    private boolean isInternalIdInInitialRowsList(String internalId){
        if(null != internalId){
            for (RowID initialRowID : this.initialRowIDs) {
                if(initialRowID.getId().equals(internalId)){
                    return true;
                }
            }
        }
        return false;
    }
    
    private void addRowToAddList(int row, int column, String columnName, String newDataValue){
        /*if(newDataValue.isEmpty()){
            newDataValue=null;
        }*/
        if(row>=rowIDs.size() || null==this.addDataList.getByInternalId(rowIDs.get(row))){//si es nuevo
            try {
                List<DataColumn> dataColumns=new ArrayList<>();
                dataColumns.add(new DataColumn(columnName,newDataValue,columnQuoted[column]));
                DataRow dr=new DataRow(this.dbTable.getName(),dataColumns);
                rowIDs.add(dr.getInternalId());
                addDataList.addDataRow(dr);
                //addNewRows
                this.numberOfNewRows++;
                this.numberOfBlankRows--;
                if(numberOfBlankRows==0){
                    for(int i=0;i<BLANK_ROWS;i++){
                        this.model.addRow(new String[this.columnNames.length]);
                    }
                    //this.numberOfNewRows=this.numberOfNewRows+BLANK_ROWS;
                    this.numberOfBlankRows=this.numberOfBlankRows+BLANK_ROWS;
                }
                validate();
            } catch (InvalidParameterException ex) {
                Exceptions.printStackTrace(ex);
                Message.showErrorMessage("Falló la creación del DataColumn porque un parámetro no es correcto (#1). Mensaje="+ex.getMessage());
                //ex.printStackTrace();
            }
        }else{
            try {
                if(!addDataList.replaceValueOrAddDataColumnByInternalId(rowIDs.get(row), new DataColumn(columnName,newDataValue,this.columnQuoted[column]))){
                    Message.showErrorMessage("Falló el remplazo del valor '"+newDataValue+"' en la columna '"+columnName+"'.");
                }
            } catch (InvalidParameterException ex) {
                Exceptions.printStackTrace(ex);
                Message.showErrorMessage("Falló la creación del DataColumn porque un parámetro no es correcto (#2). Mensaje="+ex.getMessage());
            }
        }
    }
    
    private void addRowToEditList(int row, int column, String columnName, String newDataValue){
        /*if(newDataValue.isEmpty()){
            newDataValue=null;
        }*/
        if(null==this.editDataList.getByInternalId(rowIDs.get(row))){//si es nuevo
            try {
                List<DataColumn> dataColumns=new ArrayList<>();
                dataColumns.add(new DataColumn(columnName,newDataValue,columnQuoted[column]));
                DataRow dr=new DataRow(rowIDs.get(row).toString(), this.dbTable.getName(),dataColumns);
                //rowIDs[row]=dr.getInternalId();
                editDataList.addDataRow(dr);
            } catch (InvalidParameterException ex) {
                Exceptions.printStackTrace(ex);
                Message.showErrorMessage("Falló la creación del DataColumn porque un parámetro no es correcto (#1). Mensaje="+ex.getMessage());
                //ex.printStackTrace();
            }
        }else{
            try {
                if(!editDataList.replaceValueOrAddDataColumnByInternalId(rowIDs.get(row), new DataColumn(columnName,newDataValue,this.columnQuoted[column]))){
                    Message.showErrorMessage("Falló el remplazo del valor '"+newDataValue+"' en la columna '"+columnName+"'.");
                }
            } catch (InvalidParameterException ex) {
                Exceptions.printStackTrace(ex);
                Message.showErrorMessage("Falló la creación del DataColumn porque un parámetro no es correcto (#2). Mensaje="+ex.getMessage());
            }
        }
    }
    public int validateAllValues(){
        int errs=0;
        for(int c=0;c<this.model.getColumnCount();c++){
            Column column=dbTable.getColumnByName(this.model.getColumnName(c));
            for(int r=0;r<(this.model.getRowCount()-1);r++){
                Object val=this.model.getValueAt(r, c);
                if(!column.getType().validateValue((val==null?null:val.toString()), column.isAllowNull())){
                    errs++;
                }
            }
        }
        return errs;
    }
    private void addRowToRemoveList(int row){
        DataRow dr=new DataRow(rowIDs.get(row).toString(),this.dbTable.getName());
        removeDataList.addDataRow(dr);
    }

    public Data getAddDataList() {
        return addDataList;
    }

    public Data getEditDataList() {
        return editDataList;
    }

    public Data getRemoveDataList() {
        return removeDataList;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(null==selectedRows){
            removeButton.setEnabled(true);
        }
        selectedRows=jtable.getSelectedRows();
    }

    public void printResults(){
        //for(Data d:addDataList){
       /* System.out.println("ADD "+addDataList.getDataRowCount());
        System.out.println("EDIT "+editDataList.getDataRowCount());
        System.out.println("REM "+removeDataList.getDataRowCount());
        System.out.println("NewRows "+this.numberOfNewRows);*/
        //}
    }
    private class RowID{
        private final String id;
        private boolean enabled=true;;
        public RowID(String id){
            this.id=id;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getId() {
            return id;
        }

        public boolean isEnabled() {
            return enabled;
        }
        
    }
}
