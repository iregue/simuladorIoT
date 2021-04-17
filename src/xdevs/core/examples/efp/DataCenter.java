package xdevs.core.examples.efp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

import xdevs.core.modeling.Atomic;
import xdevs.core.modeling.Input;
import xdevs.core.modeling.Port;

public class DataCenter extends Atomic{
    private static final Logger LOGGER = Logger.getLogger(DataCenter.class.getName());

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
// ANSIBLE DELTEXT
		    
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
    
	public void storeCollection(Collection<Input> collection) throws IOException{
		LOGGER.info(this.name + "- Storing data in file");
	    BufferedWriter writer = new BufferedWriter(new FileWriter("collections_output.txt", true));
	    writer.newLine();
	    Iterator<Input> it = collection.iterator();
	    while(it.hasNext()) {
		    writer.append(it.next().toCSV());
		    writer.newLine();
	    }
	    writer.close();
	}
	
}
