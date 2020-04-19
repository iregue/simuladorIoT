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

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import xdevs.core.modeling.Atomic;
import xdevs.core.modeling.Input;
import xdevs.core.modeling.Port;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author José Luis Risco Martín TODO: I must also modify this class, according
 * to the source code implemented by Saurabh, a iStart input port must be added.
 */
public class Generator extends Atomic {
    protected Port<Job> iStart = new Port<>("iStart");
    protected Port<Job> iStop = new Port<>("iStop");
    protected Port<Job> oOut = new Port<>("oOut");
    protected int jobCounter;
    protected double period;
    protected ArrayList<Input> listaEntrada = new ArrayList<Input>();
    int contador = 0;

    public Generator(String name, double period) {
        super(name);
        super.addInPort(iStop);
        super.addInPort(iStart);
        super.addOutPort(oOut);
        this.period = period;
        
        BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
					"/home/iregueiro/Documentos/universidad/DATA.csv"));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				String[] arrOfStr = line.split(",", 3);
				Input datosEntrada = new Input(Double.parseDouble(arrOfStr[0]),Double.parseDouble(arrOfStr[1]),Double.parseDouble(arrOfStr[2]));
				listaEntrada.add(datosEntrada);
				
				// read next line
				line = reader.readLine();
			}
			reader.close();
		      for (Input num : listaEntrada) { 		      
		           //System.out.println(num.toString()); 		
		      }		
		      } catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public Generator(Element xmlAtomic) {
        super(xmlAtomic);
        iStart = (Port<Job>) super.getInPort(iStart.getName());
        iStop = (Port<Job>) super.getInPort(iStop.getName());
        oOut = (Port<Job>) super.getOutPort(oOut.getName());  
        NodeList xmlParameters = xmlAtomic.getElementsByTagName("parameter");
        Element xmlParameter = (Element)xmlParameters.item(0);
        period = Double.valueOf(xmlParameter.getAttribute("value"));
    }

    @Override
    public void initialize() {
        jobCounter = 1;
        this.holdIn("active", period);
    }

    @Override
    public void exit() {
    }

    @Override
    public void deltint() {
        jobCounter++;
        this.holdIn("active", period);
    }

    @Override
    public void deltext(double e) {
        super.passivate();
    }

    @Override
    public void lambda() {
    	Job job;
    	if(contador < listaEntrada.size()) {
    		Input input = (Input) listaEntrada.get(contador);
    		//System.out.println(input.toString());
    		contador++;
            job = new Job("" + jobCounter + "", input);
    	}
    	else {
            job = new Job("" + jobCounter + "");

    	}
        oOut.addValue(job);
    }

	@Override
	public String toString() {
		return "Generator [iStart=" + iStart + ", iStop=" + iStop + ", oOut=" + oOut + ", jobCounter=" + jobCounter
				+ ", period=" + period + ", listaEntrada=" + listaEntrada + "]";
	}
    
    
}
