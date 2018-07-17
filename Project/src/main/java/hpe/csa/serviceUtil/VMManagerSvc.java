package hpe.csa.serviceUtil;

import java.util.List;

import org.apache.log4j.Logger;

import hpe.csa.helperUtils.Helper;
import hpe.csa.model.VM;
import hpe.csa.model.Dao.VMDaoImpl;

public class VMManagerSvc {

	private VMDaoImpl vmDaoImpl=new VMDaoImpl();
	private Logger logger=Logger.getLogger(VMManagerSvc.class.getName());
	private Helper helper=new Helper();
	
	public List<VM> getAll() {
		List<VM> allVMs=vmDaoImpl.getAll();
		logger.info("All VMs are:"+helper.allVMsToString(allVMs));
		return allVMs;
	}

	public List<VM> getAvailableVms() {
		List<VM> allVMs=vmDaoImpl.getAvailableVms();
		logger.info("All avalilable VMs are:"+helper.allVMsToString(allVMs));
		return allVMs;
	}

	public VM addVM(VM vm) {
		vmDaoImpl.add(vm);
		return vm;
	}

	public List<VM> getByVersion(String version) {
		List<VM> allVMs=vmDaoImpl.getByVersion(version);
		logger.info("All VMs of version "+version+" are:"+helper.allVMsToString(allVMs));
		return allVMs;
	}
	
	

}
