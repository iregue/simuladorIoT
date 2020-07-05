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
import java.util.logging.Level;

import xdevs.core.modeling.Coupled;
import xdevs.core.simulation.Coordinator;
import xdevs.core.util.DevsLogger;

/**
 *
 * @author jlrisco
 */
public class Gpt extends Coupled {

    public Gpt(String name, double period, double observationTime) {
    	super(name);
        
		String userDirectory = Paths.get("")
		        .toAbsolutePath()
		        .toString();
		
        Ficheros ap1 = new Ficheros("ap1", period, userDirectory + "/data/pruebas/");
        super.addComponent(ap1);
        Ficheros ap5 = new Ficheros("ap5", period, userDirectory + "/data/pruebas/");
        super.addComponent(ap5);
        /*Ficheros ap6 = new Ficheros("ap6", period, userDirectory + "/data/ap6/");
        super.addComponent(ap6);
        Ficheros ap7 = new Ficheros("ap7", period, userDirectory + "/data/ap7/");
        super.addComponent(ap7);
        Ficheros dh1 = new Ficheros("dh1", period, userDirectory + "/data/dh1/");
        super.addComponent(dh1);
        Ficheros dh2 = new Ficheros("dh2", period, userDirectory + "/data/dh2/");
        super.addComponent(dh2);
        Ficheros dh3 = new Ficheros("dh3", period, userDirectory + "/data/dh3/");
        super.addComponent(dh3);
        Ficheros dh4 = new Ficheros("dh4", period, userDirectory + "/data/dh4/");
        super.addComponent(dh4);
        Ficheros dh5 = new Ficheros("dh5", period, userDirectory + "/data/dh5/");
        super.addComponent(dh5);
        Ficheros dh6 = new Ficheros("dh6", period, userDirectory + "/data/dh6/");
        super.addComponent(dh6);
        Ficheros dh7 = new Ficheros("dh7", period, userDirectory + "/data/dh7/");
        super.addComponent(dh7);
        Ficheros dh8 = new Ficheros("dh8", period, userDirectory + "/data/dh8/");
        super.addComponent(dh8);
        Ficheros dh9 = new Ficheros("dh9", period, userDirectory + "/data/dh9/");
        super.addComponent(dh9);
        Ficheros dh10 = new Ficheros("dh10", period, userDirectory + "/data/dh10/");
        super.addComponent(dh10);*/
        
    	NodoVirtual nodoVirtual1 = new NodoVirtual("nodoVirtual1", period);
        super.addComponent(nodoVirtual1);
    	NodoVirtual nodoVirtual2 = new NodoVirtual("nodoVirtual2", period);
        super.addComponent(nodoVirtual2);    	
        NodoVirtual nodoVirtual3 = new NodoVirtual("nodoVirtual3", period);
        super.addComponent(nodoVirtual3);
        NodoVirtual nodoVirtual4 = new NodoVirtual("nodoVirtual4", period);
        super.addComponent(nodoVirtual4);
        NodoVirtual nodoVirtual5 = new NodoVirtual("nodoVirtual5", period);
        super.addComponent(nodoVirtual5);
        NodoVirtual nodoVirtual6 = new NodoVirtual("nodoVirtual6", period);
        super.addComponent(nodoVirtual6);
        NodoVirtual nodoVirtual7 = new NodoVirtual("nodoVirtual7", period);
        super.addComponent(nodoVirtual7);
        NodoVirtual nodoVirtual8 = new NodoVirtual("nodoVirtual8", period);
        super.addComponent(nodoVirtual8);
        NodoVirtual nodoVirtual9 = new NodoVirtual("nodoVirtual9", period);
        super.addComponent(nodoVirtual9);
        NodoVirtual nodoVirtual10 = new NodoVirtual("nodoVirtual10", period);
        super.addComponent(nodoVirtual10);
        NodoVirtual nodoVirtual11 = new NodoVirtual("nodoVirtual11", period);
        super.addComponent(nodoVirtual11);
        NodoVirtual nodoVirtual12 = new NodoVirtual("nodoVirtual12", period);
        super.addComponent(nodoVirtual12);
        NodoVirtual nodoVirtual13 = new NodoVirtual("nodoVirtual13", period);
        super.addComponent(nodoVirtual13);
        NodoVirtual nodoVirtual14 = new NodoVirtual("nodoVirtual14", period);
        super.addComponent(nodoVirtual14);
        NodoVirtual nodoVirtual15 = new NodoVirtual("nodoVirtual15", period);
        super.addComponent(nodoVirtual15);
        FogServer fogserver1 = new FogServer("fogserver1", period);
        super.addComponent(fogserver1);
        DataCenter dataCenter = new DataCenter("dataCenter", observationTime);
        
        super.addCoupling(ap1.oOut, nodoVirtual1.iInFichero);
        super.addCoupling(ap5.oOut, nodoVirtual2.iInFichero);
        /*super.addCoupling(ap6.oOut, nodoVirtual3.iInFichero);
        super.addCoupling(ap7.oOut, nodoVirtual4.iInFichero);
        super.addCoupling(dh1.oOut, nodoVirtual5.iInFichero);
        
        super.addCoupling(dh2.oOut, nodoVirtual6.iInFichero);
        super.addCoupling(dh3.oOut, nodoVirtual7.iInFichero);
        super.addCoupling(dh4.oOut, nodoVirtual8.iInFichero);
        super.addCoupling(dh5.oOut, nodoVirtual9.iInFichero);
        super.addCoupling(dh6.oOut, nodoVirtual10.iInFichero);
        
        super.addCoupling(dh7.oOut, nodoVirtual11.iInFichero);
        super.addCoupling(dh8.oOut, nodoVirtual12.iInFichero);
        super.addCoupling(dh9.oOut, nodoVirtual13.iInFichero);
        super.addCoupling(dh10.oOut, nodoVirtual14.iInFichero);*/

        
        super.addCoupling(nodoVirtual1.oOut, fogserver1.iInNodoVirtual1);
        super.addCoupling(nodoVirtual2.oOut, fogserver1.iInNodoVirtual2);
        super.addCoupling(nodoVirtual3.oOut, fogserver1.iInNodoVirtual3);
        super.addCoupling(nodoVirtual4.oOut, fogserver1.iInNodoVirtual4);
        super.addCoupling(nodoVirtual5.oOut, fogserver1.iInNodoVirtual5);
        super.addCoupling(nodoVirtual6.oOut, fogserver1.iInNodoVirtual6);
        super.addCoupling(nodoVirtual7.oOut, fogserver1.iInNodoVirtual7);
        super.addCoupling(nodoVirtual8.oOut, fogserver1.iInNodoVirtual8);
        super.addCoupling(nodoVirtual9.oOut, fogserver1.iInNodoVirtual9);
        super.addCoupling(nodoVirtual10.oOut, fogserver1.iInNodoVirtual10);
        super.addCoupling(nodoVirtual11.oOut, fogserver1.iInNodoVirtual11);
        super.addCoupling(nodoVirtual12.oOut, fogserver1.iInNodoVirtual12);
        super.addCoupling(nodoVirtual13.oOut, fogserver1.iInNodoVirtual13);
        super.addCoupling(nodoVirtual14.oOut, fogserver1.iInNodoVirtual14);
        super.addCoupling(nodoVirtual15.oOut, fogserver1.iInNodoVirtual15);

        super.addCoupling(fogserver1.oOut, dataCenter.iInDataCenter);
        

    }

    public static void main(String args[]) {
        DevsLogger.setup(Level.INFO);
        Gpt gpt = new Gpt("gpt", 1, 200);
        //CoordinatorParallel coordinator = new CoordinatorParallel(gpt);
        Coordinator coordinator = new Coordinator(gpt);
        //RTCentralCoordinator coordinator = new RTCentralCoordinator(gpt);
        coordinator.initialize();
        coordinator.simulate(Long.MAX_VALUE);

    }

}
