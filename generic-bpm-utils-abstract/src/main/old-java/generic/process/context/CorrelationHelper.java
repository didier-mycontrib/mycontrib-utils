package generic.process.context;

import generic.process.context.instance.ProcessContextInstance;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CorrelationHelper<T extends Serializable,PCI extends ProcessContextInstance> {

	public Map<T,PCI>  mapDataPCInstance = new HashMap<T,PCI>();

	public PCI getInstanceFromCorrData(T corrData){
		return mapDataPCInstance.get(corrData);
	}

	public void storeProcessInstanceCorrData(PCI pci,T corrData){
		mapDataPCInstance.put(corrData, pci);
	}
	
	
	public void removeProcessInstanceWithCorrData(PCI pci){
		T corrDataToDelete=null;
		for(T corrData : mapDataPCInstance.keySet()){
			if(mapDataPCInstance.get(corrData) == pci)
				corrDataToDelete=corrData;
		}
		if(corrDataToDelete!=null)
			mapDataPCInstance.remove(corrDataToDelete);
	}
	
	
	public void removeCorrDataWithProcessInstance(T corrData){
			mapDataPCInstance.remove(corrData);
	}
	
}
