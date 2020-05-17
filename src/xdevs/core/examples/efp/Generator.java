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

import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Collections;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import xdevs.core.modeling.Atomic;
import xdevs.core.modeling.Input;
import xdevs.core.modeling.Port;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author José Luis Risco Martín TODO: I must also modify this class, according
 * to the source code implemented by Saurabh, a iStart input port must be added.
 */
public class Generator extends Atomic {
    protected Port<Input> iStart = new Port<>("iStart");
    protected Port<Input> iStop = new Port<>("iStop");
    protected Port<Input> oOut = new Port<>("oOut");
    protected int jobCounter;
    protected double period;
    protected String path;
    protected ArrayList<Input> listaEntrada = new ArrayList<Input>();
    int contador = 0;

    public Generator(String name, double period, String path) {
        super(name);
        super.addInPort(iStop);
        super.addInPort(iStart);
        super.addOutPort(oOut);
        this.period = period;
        this.path = path;
        
        BufferedReader reader;
		try {
			//TODO: Recorrer ficheros de un directorio
			String userDirectory = Paths.get("")
			        .toAbsolutePath()
			        .toString();
			
			final File folder = new File(path);
			ArrayList<String> files = new ArrayList<String>();
			int i = 0;
			for (final File fileEntry : folder.listFiles()) {
				files.add(fileEntry.getPath());
			}
			Collections.sort(files);
			System.out.println("Sorted: " + files.toString());
			//for (final File fileEntry : folder.listFiles()) {
			for (int k = 0; k < files.size(); k++) {

		        if (/*fileEntry.isDirectory() && */false) {
		            continue;
		        }
		        else {
		        	System.out.println(files.get(k));
		            //reader = new BufferedReader(new FileReader(fileEntry.getPath()));
		            reader = new BufferedReader(new FileReader(files.get(k)));
					String line = reader.readLine();
					if(line != null) {
						line = reader.readLine(); //Salta la primera linea
					}
					while (line != null) {
						//System.out.println(line);
						String[] arrOfStr = line.split(",");
						try {
							Input datosEntrada = new Input(arrOfStr[0],Double.parseDouble(arrOfStr[1]),name);
							listaEntrada.add(datosEntrada);
					} catch (Exception e) {
						e.printStackTrace();
					}
						// read next line
						line = reader.readLine();
					}
					reader.close();
		        }
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public Generator(Element xmlAtomic) {
        super(xmlAtomic);
        iStart = (Port<Input>) super.getInPort(iStart.getName());
        iStop = (Port<Input>) super.getInPort(iStop.getName());
        oOut = (Port<Input>) super.getOutPort(oOut.getName());  
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
    	Input input = null;
    	if(contador < listaEntrada.size()) {
    		input = (Input) listaEntrada.get(contador);
    		//System.out.println(input.toString());
    		contador++;
    	}
    	else {
    		input = null;
    	}
        oOut.addValue(input);
    }

	@Override
	public String toString() {
		return "Generator [iStart=" + iStart + ", iStop=" + iStop + ", oOut=" + oOut + ", jobCounter=" + jobCounter
				+ ", period=" + period + ", listaEntrada=" + listaEntrada + "]";
	}
    
    
}
