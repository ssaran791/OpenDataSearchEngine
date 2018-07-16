package com.studentsdealz.AI.Indexer;

import com.studentsdealz.AI.Indexer.DAL.DB_MYSQL.IndexQueueDAL_AI;
import com.studentsdealz.AI.Indexer.MODEL.DB_MYSQL.IndexQueueMODEL_AI;
import com.studentsdealz.AppConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by saran on 8/9/17.
 */
public class Indexer {
    static DecimalFormat format = new DecimalFormat("0.###"); //used to remove the unwaned extra zero after the decimal points
    public static void main(String... args) throws Exception
    {
        //in our case AI index we are converting all the xls file in to the sqlite files.
        //index("2957461");

        List<IndexQueueMODEL_AI> to_index_Datas= IndexQueueDAL_AI.getAllToIndexID();
//        for(IndexQueueMODEL_AI to_index:to_index_Datas)
//        {
//            index(to_index.getDownload_id()+"");
//        }
        index("88687");



        System.out.println("Completed Index===");

//        Sqlite_Helper sqlite=new Sqlite_Helper();
//        sqlite.setSqlite_path("/home/saran/rajasthan_hacking_data/Ai_Index/test.db");
//        sqlite.exec_init();
    }

    public static void index(String file_name_id) throws Exception {
        //col_info
        List<String> avaible_cols=new ArrayList<>();


        //detecting the file type
        String excelFilePath=AppConstants.CrawlerConstants_AI.STORAGE_FOLDER+file_name_id+".xls";

        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

        Workbook workbook = new HSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        Sqlite_Helper sqlite=new Sqlite_Helper();
        sqlite.setSqlite_path(AppConstants.IndexerConstant_AI.STORAGE_OF_CONVERTED_FILES+file_name_id+".db");
        boolean is_first=true;

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            avaible_cols=new ArrayList<>();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:

                        avaible_cols.add(cell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:

                        avaible_cols.add(cell.getBooleanCellValue()+"");
                        break;
                    case Cell.CELL_TYPE_NUMERIC:

                        avaible_cols.add(format.format(cell.getNumericCellValue())+"");
                        break;
                }
            }
           if(is_first)
           {
               sqlite.setColumns(avaible_cols);
               sqlite.exec_init();
               is_first=false;
           }else{
                sqlite.insert(avaible_cols);
           }

            //break;
        }

        workbook.close();
        inputStream.close();


        //saving all the datas

    }
}
