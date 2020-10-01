package com.flb.ignite.datastrucuture;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicLong;
import org.apache.ignite.Ignition;
import org.apache.ignite.lang.IgniteCallable;

import java.util.UUID;

public final class IgniteAtomicLongExample {
    /** Number of retries */
    private static final int RETRIES = 30;
    public static void main(String[] args) throws Exception {
        try (Ignite ignite = Ignition.start("example-ignite.xml")) {
            System.out.println();

            // Make name for atomic long (by which it will be known in the cluster).
            String atomicName = UUID.randomUUID().toString();

            // Initialize atomic long.
            final IgniteAtomicLong atomicLong = ignite.atomicLong(atomicName, 0, true);

            System.out.println();
            System.out.println("Atomic long initial value : " + atomicLong.get() + '.');

            // Try increment atomic long from all nodes.
            // Note that this node is also part of the ignite cluster.
            ignite.compute().broadcast(new IgniteCallable<Object>() {
                @Override public Object call() {
                    for (int i = 0; i < RETRIES; i++)
                        System.out.println("AtomicLong value has been incremented: " + atomicLong.incrementAndGet());

                    return null;
                }
            });

            System.out.println();
            System.out.println("Atomic long value after successful CAS: " + atomicLong.get());
        }
    }
}
