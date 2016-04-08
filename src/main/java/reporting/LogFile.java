package reporting;

import java.io.File;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import Common.Utility;

public class LogFile {

    private Logger log;
    public Utility utility;

    /**
    * 
    */
    public LogFile() {
	log = Logger.getLogger("LOG");
    }

    /*********************************************************************************************
     * Step action name: prepareLogger Description: Prepare a logger with a new
     * file name in the Execution_Log folder
     *********************************************************************************************/

    // Prepare a logger with a new file name.
    public void prepareLogger(String FilePath) {
	try {
	    log.setLevel(Level.INFO);
	    File myLogFile = new File(FilePath);
	    String path = myLogFile.getAbsolutePath();
	    if (!myLogFile.exists()) {
		path.getBytes();
		myLogFile.getParentFile().mkdirs();
		boolean isCreated = myLogFile.createNewFile();
		log.info(isCreated);
	    }

	    Layout layout = new PatternLayout("...");
	    FileAppender appender = new FileAppender(new PatternLayout(), myLogFile.getAbsolutePath(), true);
	    log.removeAllAppenders();
	    log.addAppender(appender);
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
    }

    /*********************************************************************************************
     * Step action name: prepareHeader Description: Create header for logging
     *********************************************************************************************/

    public void prepareHeader() {
	log.info("Timestamp" + "\t\t\t\t\t\t" + "TestCaseID" + "\t\t\t\t" + "TestStep" + "\t\t" + "BDD_Step" + "\t\t\t\t\t\t\t\t\t\t" + "Status" + "\t\t"
	        + "Remarks" + "\n");
    }

    /*********************************************************************************************
     * Step action name: writeStepLog Description: Log Teststep info and result
     *********************************************************************************************/

    public void writeStepLogBeforeStep(String TestCaseID, String TestStep, String Status, String Remarks, String BDDStep, boolean flag) {
	if (flag) {
	    log.info("Timestamp: " + new Utility(null).getCurrentTimeStamp() + "\n TestCaseID: " + TestCaseID + "\n TestStep: " + TestStep + "\n BDD_Step: "
		    + BDDStep );
	}

    }
    
    public void writeStepLogAfterStep(String TestCaseID, String TestStep, String Status, String Remarks, String BDDStep, boolean flag) {
	if (flag) {
	    log.info("Status: " + Status + "\n Remarks: " + Remarks + "\n");
	}

    }

    /*********************************************************************************************
     * Step action name: writeInfo Description: Log info
     *********************************************************************************************/

    public void writeInfo(String info) {
	log.info(info);
    }

    /*********************************************************************************************
     * Step action name: writeError Description: Log error
     *********************************************************************************************/

    public void writeError(String error) {
	log.error(error);
    }

    /*********************************************************************************************
     * Step action name: writeDebug Description: Log debug
     *********************************************************************************************/

    public void writeDebug(String debug) {
	log.debug(debug);
    }

}
