package xdevs.core.examples.efp;

import java.util.Collection;

import xdevs.core.modeling.Atomic;
import xdevs.core.modeling.Input;
import xdevs.core.modeling.Port;

public class DataCenter extends Atomic{

    //protected Port<Input> iInDataCenter = new Port<>("iInDataCenter");
 // ANSIBLE PORTINIT

    protected Port<Input> oOutDataCenter = new Port<>("oOutDataCenter");
    protected double processingTime;
    protected Input currentInput = null;

    public DataCenter(String name, double processingTime) {
        super(name);
// ANSIBLE CONSTRUCTOR

        //super.addInPort(iInDataCenter);
        super.addOutPort(oOutDataCenter);
        this.processingTime = processingTime;
        
    }

	@Override
	public void deltint() {
        super.passivate();		
	}

	@Override
	public void deltext(double e) {
		if (super.phaseIs("passive")) {
        	
			//Collection<Input> collection = iInDataCenter.getValues();
		    //System.out.println("DATACENTER COLLECTION: " + collection);
// ANSIBLE DELTEXT
		    
        	//currentInput = iInDataCenter.getSingleValue();
        	if(currentInput != null) {
            	System.out.println("DataCenter: " + currentInput.toString());
        	}
            super.holdIn("active", processingTime);
        }
		
	}

	@Override
	public void lambda() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize() {
        //super.holdIn("active", processingTime);
        super.passivate();

	}

	@Override
	public void exit() {		
	}
    
}
