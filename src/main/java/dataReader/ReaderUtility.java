package dataReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import Common.Property;
import reporting.LogFile;

/**
 * All Utility functions that assist in communicating with external files like
 * TestCase sheet, DataData sheet and Object Repository sheet etc are listed
 * here.
 * 
 * @author
 *
 */
public class ReaderUtility {

    public LogFile objLog;

    private String TestCaseFileLocation;

    private String TestDataFileLocation;

    private String ObjectRepositoryFileLocation;

    private String CurrentTestSuiteID;
    public Property property;

    /**
     * Constructor method that assigns current TestSuite name located in the
     * uiautomation.properties file. This is the name of the file available
     * under TestCase dir.
     */
    public ReaderUtility(Property property) {
	objLog = new LogFile();
	this.property = property;
	CurrentTestSuiteID = property.TestSuite;
    }

    /**
     * Set the location of all the external files (TestCase, TestData &
     * ObjectRepository) needed for the framework.
     * 
     * @param reusableFlag
     *            a boolean value used to locate the file when reusable
     *            scenarios are in execution. Property.FileSeperator and
     *            Property.ObjectRepositoryFileLocation values are used from
     *            Property.java file
     */
    public void setFileLocation(boolean reusableFlag) {

	TestCaseFileLocation = property.TESTCASE_LOC + CurrentTestSuiteID + property.FILE_EXTENSION;

	// Check whether reusableFlag is true. If yes, use
	// 'GlobalActionFile.xls' file available under TestCase dir.
	// In this case 'TestCaseFileLocation' will NOT point to a particular
	// test suite.
	// if(reusableFlag){
	// TestCaseFileLocation = Property.TESTCASE_LOC +
	// Property.REUSABLE_FILE_NAME + Property.FILE_EXTENSION;
	// }

	/*
	 * TestData and TestCase info are in same sheet so their location is
	 * same.
	 */
	TestDataFileLocation = TestCaseFileLocation;

	// 'ObjectRepositoryFileLocation' pointing to
	// src/main/resources/ObjectRepository/ObjectRepository.xls
	ObjectRepositoryFileLocation = property.ObjectRepositoryFileLocation + property.FILE_EXTENSION;
    }

    /**
     * Helper function that takes a file (excel) and name of sheet in that file
     * and Returns a table representing data from given sheet in tabular format.
     * 
     */

    public List<String[]> getRequiredRows(String FilePath, String Sheet) throws Exception {

	/*
	 * ITable objSheet = null; // ITable: An interface that represents a
	 * collection of tabular data. try { File inFile = new File(FilePath);
	 * XlsDataSet ds = new XlsDataSet(inFile);
	 * 
	 * 
	 * XlsDataSet : This dataset implementation can read and write MS Excel
	 * documents. Each sheet represents a table. The first row of a sheet
	 * defines the columns names and remaining rows contains the data.
	 * 
	 * objSheet = ds.getTable(Sheet);
	 * 
	 * } catch (DataSetException de) { throw new DataSetException(
	 * "No such sheet,please check the file again."); } catch (Exception e)
	 * { throw e; } return objSheet;
	 */

	File inFile = new File(FilePath);
	List<String[]> completeData = new ArrayList<String[]>();
	BufferedReader reader = null;
	try {
	    String indRow = "";
	    reader = new BufferedReader(new FileReader(inFile));
	    // Skip reading the header
	    // reader.readLine();
	    while ((indRow = reader.readLine()) != null) {
		String[] indRowAsArray = indRow.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		for (int i = 0; i < indRowAsArray.length; i++) {
		    indRowAsArray[i] = indRowAsArray[i].trim();
		    if (indRowAsArray[i].startsWith("\"") && indRowAsArray[i].endsWith("\"")) {
			indRowAsArray[i] = indRowAsArray[i].substring(1, indRowAsArray[i].length() - 1);
			indRowAsArray[i] = indRowAsArray[i].trim();
		    }
		    if (indRowAsArray[i].contains("\"\"")) {
			indRowAsArray[i] = indRowAsArray[i].replaceAll("\"\"", "\"");

		    }
		}
		completeData.add(indRowAsArray);
	    }
	} catch (Exception e) {
	    // TODO: handle exception
	}
	return completeData;
    }

    /**
     * Get the data from Object Repository, implicitly calls 'getRequiredRows()'
     * 
     * @return <b>ResultSet object of Data.</b>
     * @throws Exception
     */
    public List<String[]> getORData() throws Exception {
	try {
	    List<String[]> objTb = getRequiredRows(ObjectRepositoryFileLocation, property.ObjectRepositorySheet);

	    return objTb;
	} catch (Exception e) {
	    throw e;
	}

    }

    /**
     * Get the data from Test case file.
     * 
     * @return <b>ResultSet object of the data </b>
     * @throws Exception
     */
    public List<String[]> getTestCaseData() throws Exception {
	try {
	    List<String[]> rs = getRequiredRows(TestCaseFileLocation, property.TestCaseSheet);

	    return rs;

	} catch (Exception e) {
	    throw e;
	}
    }

    /**
     * Get the data from Test data file.
     * 
     * @return <b>ResultSet object of data.</b>
     * @throws Exception
     */
    public List<String[]> getTestData() throws Exception {
	try {
	    List<String[]> rs = getRequiredRows(TestDataFileLocation, property.TestDataSheet);

	    return rs;

	} catch (Exception e) {
	    throw e;
	}
    }

    public String getCellValue(List<String[]> sheet, int row, String ColHeader) {
	int colNum = 0;

	String[] header = sheet.get(0);
	try {
	    for (int i = 0; i < header.length; i++) {
		if (header[i].equalsIgnoreCase(ColHeader)) {
		    colNum = i;
		    break;
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	String[] specificRow = sheet.get(row);
	return specificRow[colNum];

    }
}
