/**
 * 
 */
package Common;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author hemasundar
 *
 */
public class TCStatusMap {
    private static Map<String, ArrayList<String>> tCStatusMap = new TreeMap<String, ArrayList<String>>();

    /**
    * 
    */
    public TCStatusMap() {

    }

    public static synchronized Map<String, ArrayList<String>> gettCStatusMap() {
	return tCStatusMap;
    }

    public static synchronized void settCStatusMap(String SuiteName, ArrayList<String> suiteExecutionDetails) {

	tCStatusMap.put(SuiteName, suiteExecutionDetails);
    }

    public static synchronized ArrayList<String> gettCStatusMapValue(String key) {
	return tCStatusMap.get(key);
    }
}
