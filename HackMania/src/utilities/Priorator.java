package utilities;

import java.util.Comparator;

import mapcomp.IMapComponent;


public class Priorator implements Comparator<IMapComponent> {

	public Priorator() {}

	@Override
	public int compare(IMapComponent lo, IMapComponent ro) {
		return(lo.prior(ro));
		
	}

}
