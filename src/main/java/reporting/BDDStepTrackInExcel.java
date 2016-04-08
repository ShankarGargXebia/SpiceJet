/**
 * 
 */
package reporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Common.Property;

/**
 * Create BDD Step in Excel Format.
 *
 */
public class BDDStepTrackInExcel implements IBddStepFormation {

    public LogFile objLog;
    private final boolean True;
    private XSSFWorkbook wb;
    private XSSFSheet sheet;
    private String Workbook;
    private int rowNumber;
    // private FileOutputStream out = null;
    private FileInputStream in;
    private File file;
    Property property;

    /**
    * 
    */
    public BDDStepTrackInExcel() {
	objLog = new LogFile();
	True = false;
	Workbook = "";
    }

    /*********************************************************************************************
     * Class Name: createFile Description: Create File for BDD log sheet
     * Property.BDDFile_Location is used from Property.java file
     *********************************************************************************************/

    @Override
    public void createFile(String Filename) {

	Workbook = property.BDDFile_Location + Filename;
	CreateWorkBook(Workbook);
	// CreateSheet();
    }

    /*********************************************************************************************
     * Class Name: CreateWorkBook Description: Create Workbook In BDD log file
     *********************************************************************************************/
    private void CreateWorkBook(String WorkbookName) {
	try {

	    // OPCPackage fileSystems =
	    // OPCPackage.open(file.getAbsolutePath(),PackageAccess.READ);

	    file = new File(WorkbookName);

	    if (!file.exists()) {
		wb = new XSSFWorkbook();
		sheet = wb.createSheet("Steps");

		CreateHeader();
	    } else {
		in = new FileInputStream(WorkbookName);
		wb = new XSSFWorkbook(in);
		sheet = wb.getSheet("steps");
	    }
	} catch (Exception e) {
	    objLog.writeInfo(e.toString());
	}
    }

    /*********************************************************************************************
     * Class Name: CreateHeader Description: Create Header in BDD log sheet
     *********************************************************************************************/

    @Override
    public void CreateHeader() {
	// Create a row and put some cells in it. Rows are 0 based.
	XSSFRow row = sheet.createRow(rowNumber);
	rowNumber++;
	// Create a cell and put a value in it.
	XSSFCell cellTestCaseID = row.createCell(0);
	XSSFCell cellTestCaseStep = row.createCell(1);
	XSSFCell cellBDDStep = row.createCell(2);
	XSSFCell cellStatus = row.createCell(3);
	XSSFCell cellRemarks = row.createCell(5);
	XSSFCell cellExecutionTime = row.createCell(4);
	XSSFCellStyle style = wb.createCellStyle();

	RichTextString TestCaseId = BoldContent("TestCaseName");
	RichTextString TestStep = BoldContent("TestStep");
	RichTextString BDDStepName = BoldContent("BDDStepName");
	RichTextString Status = BoldContent("Status");
	RichTextString ExecutionTime = BoldContent("ExecutionTime");
	RichTextString Remarks = BoldContent("Remarks");

	cellTestCaseID.setCellValue(TestCaseId);

	cellTestCaseStep.setCellValue(TestStep);
	cellBDDStep.setCellValue(BDDStepName);
	cellStatus.setCellValue(Status);
	cellExecutionTime.setCellValue(ExecutionTime);
	cellRemarks.setCellValue(Remarks);

	// SaveWorkBook();
    }

    /*********************************************************************************************
     * Class Name: CreateContentRow Description: Create a cell in Excel and put
     * a value in it
     *********************************************************************************************/

    @Override
    public void CreateContentRow(String TestCaseID, String TestStep, String BDDStep, String Status, String Remarks, String ExecutionTime, boolean ISWrite) {
	if (ISWrite) {
	    XSSFRow row = sheet.createRow(rowNumber);
	    rowNumber++;

	    RichTextString status = null;
	    if (Status.toLowerCase().equals(property.PASS)) {
		status = setGreen(Status);
	    } else if (Status.toLowerCase().equals(property.FAIL)) {
		status = setRed(Status);
	    }

	    // Create a cell and put a value in it.
	    XSSFCell cellTestCaseID = row.createCell(0);
	    XSSFCell cellTestCaseStep = row.createCell(1);
	    XSSFCell cellBDDStep = row.createCell(2);
	    XSSFCell cellStatus = row.createCell(3);
	    XSSFCell cellRemarks = row.createCell(5);
	    XSSFCell cellExecutionTime = row.createCell(4);
	    cellTestCaseID.setCellValue(TestCaseID);
	    cellTestCaseStep.setCellValue(TestStep);
	    cellBDDStep.setCellValue(BDDStep);
	    cellStatus.setCellValue(status);
	    cellRemarks.setCellValue(Remarks);
	    cellExecutionTime.setCellValue(ExecutionTime);
	}

    }

    // Function to Auto size the column widths
    @Override
    public void autoFitCoulmn() {

	for (int columnPosition = 0; columnPosition < 6; columnPosition++) {
	    sheet.autoSizeColumn((columnPosition));
	}
    }

    /*********************************************************************************************
     * Class Name: SaveFile Description: Save BDD log file
     *********************************************************************************************/

    @Override
    public void SaveFile() {
	try {
	    FileOutputStream out = new FileOutputStream(file, True);
	    wb.write(out);

	    // wb.close();
	    out.close();
	} catch (Exception e) {
	    objLog.writeInfo(e.toString());
	}
    }

    public void openExcelSheet(String Filename) {
	try {
	    FileInputStream fileInputStream = new FileInputStream(new File(Filename));
	    wb = new XSSFWorkbook(fileInputStream);
	    sheet = wb.getSheet("steps");

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /*********************************************************************************************
     * Class Name: GetdatafromBDDExcelReport Description:
     *********************************************************************************************/

    public String getRowBDDStepReport(String TestCaseID) {

	String check = TestCaseID;
	String filteredRows = "";

	Iterator<Row> rowIterable = sheet.iterator();
	while (rowIterable.hasNext()) {
	    Row row = rowIterable.next();
	    Iterator<Cell> cellIterable = row.iterator();

	    while (cellIterable.hasNext()) {
		Cell cell = cellIterable.next();

		if (cell.getColumnIndex() == 0 && !cell.getStringCellValue().trim().equals("")) {
		    check = cell.getStringCellValue();
		}

		if (check.equalsIgnoreCase(TestCaseID)) {
		    if (cell.getStringCellValue().trim().equals("")) {
			filteredRows = filteredRows + "/t";
		    }
		    filteredRows = filteredRows + cell.getStringCellValue() + "@";
		}
	    }
	    filteredRows = filteredRows + "/n";
	}
	return filteredRows;
    }

    /*********************************************************************************************
     * Class Name: BoldContent Description: Set font and color for headers
     *********************************************************************************************/

    private RichTextString BoldContent(String content) {
	XSSFFont font = wb.createFont();
	font.setBoldweight(font.BOLDWEIGHT_BOLD);

	RichTextString string = new XSSFRichTextString(content);
	string.applyFont(font);
	return string;
    }

    /*********************************************************************************************
     * Class Name: setGreen Description: Set font and color for a failed step
     *********************************************************************************************/
    private RichTextString setGreen(String content) {
	XSSFFont font = wb.createFont();

	font.setColor(HSSFColor.DARK_GREEN.index);
	RichTextString string = new XSSFRichTextString(content);
	string.applyFont(font);
	return string;

    }

    /*********************************************************************************************
     * Class Name: setRed Description: Set font and color for a failed step
     *********************************************************************************************/
    private RichTextString setRed(String content) {
	XSSFFont font = wb.createFont();

	font.setColor(HSSFColor.DARK_RED.index);
	RichTextString string = new XSSFRichTextString(content);
	string.applyFont(font);
	return string;

    }

}
