/*
 * Copyright (C) 2014-2015 José Luis Risco Martín <jlrisco@ucm.es> and 
 * Saurabh Mittal <smittal@duniptech.com>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, see
 * http://www.gnu.org/licenses/
 *
 * Contributors:
 *  - José Luis Risco Martín
 */
package xdevs.core.examples.efp;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;  // Import the File class
import java.io.FileOutputStream;
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.FileWriter;   // Import the FileWriter class

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import xdevs.core.modeling.Atomic;
import xdevs.core.modeling.Input;
import xdevs.core.modeling.Port;

/**
 *
 * @author jlrisco
 */
public class NodoVirtual extends Atomic {

    protected Port<Input> iInFichero = new Port<>("iInFichero");
    protected Port<Input> iInFisico = new Port<>("iInFisico");
    protected Port<Input> iInDB = new Port<>("iInDB");
    
    protected Port<Input> oOut = new Port<>("oOut");
    protected Input currentInput = null;
    protected double processingTime;

    public NodoVirtual(String name, double processingTime) {
        super(name);
        super.addInPort(iInFichero);
        super.addInPort(iInFisico);
        super.addInPort(iInDB);
        super.addOutPort(oOut);
        
        this.processingTime = processingTime;
        
    }
/*
    public SimuladorIoT(Element xmlAtomic) {
        super(xmlAtomic);
        iIn = (Port<Input>) super.getInPort(iIn.getName());
        oOut = (Port<Input>) super.getOutPort(oOut.getName());
        NodeList xmlParameters = xmlAtomic.getElementsByTagName("parameter");
        Element xmlParameter = (Element)xmlParameters.item(0);
        processingTime = Double.valueOf(xmlParameter.getAttribute("value"));
    }
*/
    @Override
    public void initialize() {
        super.passivate();
    }

    @Override
    public void exit() {
    }

    @Override
    public void deltint() {
        super.passivate();
    }

    @Override
    public void deltext(double e) {
        if (super.phaseIs("passive")) {
        	
        	currentInput = iInFichero.getSingleValue();
        	if(currentInput != null) {
            	System.out.println("NodoVirtual: " + currentInput.toString());

        	}
        	//currentInput.setSource("fichero");
            super.holdIn("active", processingTime);
        }
    }
    
    @Override
    public void lambda() {
        oOut.addValue(currentInput);
        
    }
    
}
