package utilities;

import java.util.ArrayDeque;

import mapcomp.IMapComponent;

public class ArrayPrior<E> extends ArrayDeque<IMapComponent> {
	private static final long serialVersionUID = -3904413270374859397L;

	public ArrayPrior() {
		super();
	}
	
//	@Override
//	public boolean add(IMapComponent e) {
//		try {
//			if(e.prior(this.getLast())) {
//				this.addLast(e);
//			} else {
//				this.addFirst(e);
//			}
//		} catch (Exception ex) {
//			this.addFirst(e);
//		}
//		return true;
//		}
	
//	@Override
//	public boolean remove(Object o) {
//		super.remove(o);
//		if(this.getFirst().prior(this.getLast())) {
//			IMapComponent e = super.pollFirst();
//			this.addLast(e);
//		} return true;
//	}

}
