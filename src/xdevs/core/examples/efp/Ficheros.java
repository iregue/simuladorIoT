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

//import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.text.SimpleDateFormat;  

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
public class Ficheros extends Atomic {
    protected Port<Input> iStart = new Port<>("iStart");
    protected Port<Input> iStop = new Port<>("iStop");
    protected Port<Input> oOut = new Port<>("oOut");
    protected double period;
    protected String path;
    protected ArrayList<Input> listaEntrada = new ArrayList<Input>();
    int contador = 0;
    private String line;
    private BufferedReader reader;
    ArrayList<String> files = new ArrayList<String>();
    private int contadorFicheros = 0;
    long initialDate = 0;
    long startDate = 0;
    long endDate = 0;
    long actualDate = 0;
    Input inputToSend = null;
    
    public Ficheros(String name, double period, String path, String startDate, String endDate) {
        super(name);
        super.addInPort(iStop);
        super.addInPort(iStart);
        super.addOutPort(oOut);
        this.period = period;
        this.path = path;
        if(startDate != null && endDate != null) {
	        try {
				this.startDate = parseDate(startDate).getTime();
				this.endDate = parseDate(endDate).getTime();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        }
        
		try {
			//Recorrer ficheros de un directorio
			/*
			String userDirectory = Paths.get("")
			        .toAbsolutePath()
			        .toString();
			*/
			final File folder = new File(path);
			//ArrayList<String> files = new ArrayList<String>();
			for (final File fileEntry : folder.listFiles()) {
				files.add(fileEntry.getPath());
			}
			Collections.sort(files);
			System.out.println("Sorted: " + files.toString());
			/*
			for (int k = 0; k < files.size(); k++) {
		        
		        System.out.println(files.get(k));
		        reader = new BufferedReader(new FileReader(files.get(k)));
				line = reader.readLine();
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
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public void initialize(){
    	Input datosEntrada = null;
    	try {
		    reader = new BufferedReader(new FileReader(files.get(contadorFicheros)));
		    line = reader.readLine(); //Se salta la primera linea
		    line = reader.readLine();
		    if(line != null) {
		    	String[] arrOfStr = line.split(",");
				try {
					datosEntrada = new Input(arrOfStr[0],Double.parseDouble(arrOfStr[1]),name);
				} 
				catch (Exception e) {
						e.printStackTrace();
				}
		    }
    	}
    	catch (IOException e) {
			e.printStackTrace();
		}
    	if(datosEntrada.getDate() !=null) {
	    	try {
				initialDate = parseDate(datosEntrada.getDate()).getTime();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	if((startDate <= initialDate && initialDate <= endDate) || startDate == 0) {
	    		inputToSend = datosEntrada;
	    	}
    	}
    	
    	//Leer primer linea con datos del fichero
        //Fecha=2010-03-20 17:27:07 
    	//radiacion=258.692
    	this.holdIn("active", 0);
        
    }

    @Override
    public void exit() {
    	try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void deltint() {
    	boolean existOtherFile = true;
    	Input datosEntrada = null;
    	try {
    		//if(contadorFicheros < files.size()) {
		    	//reader = new BufferedReader(new FileReader(files.get(contadorFicheros)));
		    	line = reader.readLine();
		    	if(line == null) {
		    		existOtherFile = nextFile();
		    	}
		    	if(!existOtherFile) {
		    		this.passivate();
		    	}
		    	if(line != null && existOtherFile) {
			    	String[] arrOfStr = line.split(",");
					try {
						datosEntrada = new Input(arrOfStr[0],Double.parseDouble(arrOfStr[1]),name);
						
						if(datosEntrada.getDate() !=null) {
					    	try {
								actualDate = parseDate(datosEntrada.getDate()).getTime();
							} catch (Exception e) {
								e.printStackTrace();
							}
					    	if((startDate <= initialDate && initialDate <= endDate) || startDate == 0) {
					    		inputToSend = datosEntrada;
					    	}
				    	}
				    	double period = (actualDate - initialDate)/1000;
				        this.holdIn("active", period);
				        initialDate = actualDate;
					}
					catch (Exception e) {
							e.printStackTrace();
					}
		    	}
    		//}
    		
    	}
    	catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	//Leer siguiente linea del fichero
    	//Nueva fecha=Fecha=2010-03-20 17:27:08
    	//Diferencia = fecha nueva - fecha = 1
    	//radiación=259.692
    	//this.holdIn("active", Diferencia);
    	//Si termina el fichero this.passivate();
    	
    	
    }

    @Override
    public void deltext(double e) {
        super.passivate();
    }

    @Override
    public void lambda() {
    	/*
    	Input input = null;
    	if(contador < listaEntrada.size()) {
    		input = (Input) listaEntrada.get(contador);
    		//System.out.println(input.toString());
    		contador++;
    	}
    	else {
    		input = null;
    	}
    	*/
        oOut.addValue(inputToSend);
    }

	@Override
	public String toString() {
		return "Generator [iStart=" + iStart + ", iStop=" + iStop + ", oOut=" + oOut 
				+ ", period=" + period;
	}
    
    public Date parseDate(String sDate) throws Exception{
    	String[] splitDate = line.split("-");
    	//2010-03-20 17:29:59-10:00
    	String stringDate= splitDate[0] + "-" + splitDate[1] + "-" + splitDate[2];
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date date=formatter.parse(stringDate);
        return date;
    }
    
    public boolean nextFile() {
    	contadorFicheros++;
    	if(contadorFicheros < files.size()) {
    		try {
				reader = new BufferedReader(new FileReader(files.get(contadorFicheros)));
				line = reader.readLine(); //Se salta la primera linea del fichero
	    		line = reader.readLine();
			} 
    		catch (Exception e) {
				e.printStackTrace();
			}
    		return true;
    	}
    	else {
    		return false;
    	}
    }
}
