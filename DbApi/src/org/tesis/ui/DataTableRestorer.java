package org.tesis.ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.tesis.db.Column;
import org.tesis.exception.InvalidValueException;
import org.tesis.db.DataColumn;
import org.tesis.db.DataRow;
import org.tesis.db.Table;
import org.tesis.dbapi.Message;

public class DataTableRestorer extends JPanel  implements /*TableModelListener,*/ ListSelectionListener{
    private JTable jtable;
    private JScrollPane jscrollPane;
    private DefaultTableModel model;
    //private Data addDataList=new Data();
    //private Data editDataList=new Data();
    //private Data removeDataList=new Data();
    private String[] columnNames;
    private Boolean[] columnQuoted;//if columns are quoted
    private String[][] data;
    private String oldData[][];
    private List <String> rowIDs;
    private List<String> initialRowIDs;//era Long initialRowIDs[];
    //private int numberOfNewRows=0;//numero de nuevas filas agregadas
    //private int numberOfBlankRows;
    private final int BLANK_ROWS=0;//numero de filas en blanco que se agregan cada vez
    private Table dbTable;
    //private JButton removeButton = new JButton("Borrar");
    private int[] selectedRows;
    public DataTableRestorer(){ 
        
    }
    private List<DataRow> getAllValidRemovedRows(){//esto no es util por el momento
        /*List<DataRow>rows=new ArrayList<>();
        boolean exists;
        int x=0;
        for(DataRow row:dbTable.getRemovedRows()){
            exists=false;
            for(DataRow r:dbTable.getRows()){//verifica que la fila borrada no existe entre los datos cargados
                if(r.getInternalId().equals(row.getInternalId())){
                    exists=true;
                    break;
                }
            }
            if(exists==false){
                for(int count=0;count<x;count++){//verifica que la fila borrada no existe entre los datos cargados
                    DataRow r=dbTable.getRemovedRows().get(count);
                    if(r.getInternalId().equals(row.getInternalId())){
                        exists=true;
                    }
                    count++;
                }
            }
            if(exists==false){//si no existe en la tabla de datos y si no existe en la tabla de datos borrados
                rows.add(row);
            }else{
                System.err.println("La fila con id = "+row.getInternalId()+" y datos "+row.toString()+" no ha sido agregada a la tabla de datos borrados.");
            }
            x++;
        }
        return rows;*/
        return dbTable.getRemovedRows();
    }
    public DataTableRestorer(Table dbTable, DataFilter dataFilter) throws Exception{
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.dbTable=dbTable;
        //this.setSize(1000, 800);
        columnNames= new String[dbTable.getColumns().size()];
        columnQuoted= new Boolean[dbTable.getColumns().size()];
        int c=0;
        for(Column col:dbTable.getColumns()){
            columnNames[c]=col.getName();
            columnQuoted[c]=col.getType().isInsertRequestWithQuotes();
            c++;
        }
        
        List<DataRow> rows;
        if(dataFilter==null){
            rows=this.getAllValidRemovedRows();
        }else{
            rows=new ArrayList<>();
            for(DataRow row:this.getAllValidRemovedRows()){
                try{
                    DataColumn dcol=row.getDataColumnByName(dataFilter.getColumnName());
                    if(dataFilter.passFilter(dcol.getValue())){
                        rows.add(row);
                    }
                }catch(Exception e){
                    System.out.println("La columna "+dataFilter.getColumnName()+" tiene datos nulos.");
                }
            }
        }
        int numberOfInitiallRows=rows.size();
        data=new String[numberOfInitiallRows+BLANK_ROWS][dbTable.getColumns().size()];
        //numberOfBlankRows=BLANK_ROWS;
        oldData=new String[numberOfInitiallRows][dbTable.getColumns().size()];
        rowIDs=new ArrayList<>();
        initialRowIDs=new ArrayList<>();
       
        boolean exists;
        int r=0;
        for(DataRow row:rows){
            c=0;
            for(String columnName:columnNames){
                exists=false;
                for(DataColumn col:row.getDataColumns()){
                    if (col.getColumnName().equals(columnName)){
                        data[r][c]=col.getValue();
                        oldData[r][c]=col.getValue();
                        exists=true;
                        break;
                    }
                }
                if(!exists){
                    data[r][c]=null;
                }
                c++;
            }
            
            this.rowIDs.add(row.getInternalId());
            this.initialRowIDs.add(row.getInternalId());
            r++;
        }
        model=new DefaultTableModel(data, columnNames);
        jtable=new JTable(model);
        //this.model.addTableModelListener(this);
        
        jtable.setFillsViewportHeight(true);
        jtable.getSelectionModel().addListSelectionListener(this);
        
        this.jtable.setDefaultRenderer(Object.class, new MyCellRender(this.dbTable));
        
        
        /*jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for(int i=0;i<columnNames.length;i++){
            jtable.getColumnModel().getColumn(i).setMinWidth(300);
        }*/
        jscrollPane = new JScrollPane(jtable);
        this.add(jscrollPane);
        
        
    }
    public List<DataRow> getRemovedRows()throws InvalidValueException{
        boolean resp=Message.showYesNoQuestionMessage((selectedRows.length==1?"¿Está seguro que desea restaurar este registro?":"¿Está seguro que desea restaurar estos "+selectedRows.length+" registros?"));
        List<DataRow> rows=new ArrayList<>();
        if(resp){
            boolean exists;
            for(int i=selectedRows.length-1; i>=0; i--){
                String row=this.rowIDs.get(selectedRows[i]);
                exists=false;
                for(String iri:initialRowIDs){
                    if(row.equals(iri)){
                        exists=true;
                        break;
                    }
                }
                if(exists){
                    DataRow dr=dbTable.getRemovedDataRowByInternalId(row);
                    if(dr!=null){
                        //dr.setInternalId(dr.getInternalId()+"-2");
                        rows.add(dr);
                    }else{
                        throw new InvalidValueException("Ocurrió un error (#1) al extraer el registro de la tabla. No se encontró el registro Nro. "+i);
                    }
                }else{
                    throw new InvalidValueException("Ocurrió un error (#2) al extraer el registro de la tabla. No se encontró el registro Nro. "+i);
                }
            }
        }
        return rows;
    }
    public JTable getJTable() {
        return jtable;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        /*if(null==selectedRows){
            removeButton.setEnabled(true);
        }*/
        selectedRows=jtable.getSelectedRows();
    }

    public void printResults(){
        //System.out.println("Registros a restaurar = "+selectedRows.length);
    }
}