package org.uulib.util;

import java.util.Objects;
import java.util.function.Supplier;

public final class Lazy<T> implements Supplier<T> {
	
	private final Supplier<? extends T> supplier;
	private final Object lock = new Object();
	private volatile T val;
	
	public static <T> Lazy<T> lazy(Supplier<? extends T> supplier) {
		return new Lazy<>(supplier);
	}
	
	private Lazy(Supplier<? extends T> supplier) {
		this.supplier = Objects.requireNonNull(supplier);
	}

	@Override
	public T get() {
		T rc = val;
		if(rc==null) {
			synchronized(lock) {
				rc = val;
				if(rc==null) {
					rc = supplier.get();
					val = rc;
				}
			}
		}
		return rc;
	}

}
