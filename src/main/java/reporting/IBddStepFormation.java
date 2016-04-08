/**
 * 
 */
package reporting;

/**
 * @author Interface to BDDStepCreation controller.
 *
 */
public interface IBddStepFormation {

    void createFile(String filename);

    void CreateHeader();

    void CreateContentRow(String TestCaseID, String TestStep, String BDDStep, String Status, String Remarks, String StepExecutionTime, boolean WriteFlag);

    void SaveFile();

    void autoFitCoulmn();
}
