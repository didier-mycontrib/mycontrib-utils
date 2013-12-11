package generic.bpm;

import java.util.HashMap;
import java.util.Map;

public class ProcessMapUtil {
	public static Map<String,Object> oneEntryMap(String keyName,Object objectValue){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(keyName, objectValue);
		return map;
	}
}
