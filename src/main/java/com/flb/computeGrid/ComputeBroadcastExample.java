package com.flb.computeGrid;

import org.apache.ignite.IgniteException;
import java.util.Collection;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;

public class ComputeBroadcastExample {
    /**
     * Executes example.
     */
    public static void main(String[] args) throws IgniteException {
        try (Ignite ignite = Ignition.start("examples/config/example-ignite.xml")) {
            System.out.println();
            System.out.println(">>> Compute broadcast example started.");

            // Print hello message on all nodes.
            hello(ignite);

            // Gather system info from all nodes.
            gatherSystemInfo(ignite);
        }
    }

    /**
     * Print 'Hello' message on all nodes.
     */
    private static void hello(Ignite ignite) throws IgniteException {
        // Print out hello message on all nodes.
        ignite.compute().broadcast(() -> {
            System.out.println();
            System.out.println(">>> Hello Node! :)");
        });

        System.out.println();
        System.out.println(">>> Check all nodes for hello message output.");
    }

    /**
     * Gather system info from all nodes and print it out.
     *
     * @param ignite Ignite instance.
     * @throws IgniteException if failed.
     */
    private static void gatherSystemInfo(Ignite ignite) throws IgniteException {
        // Gather system info from all nodes.
        Collection<String> res = ignite.compute().broadcast(() -> {
            System.out.println();
            System.out.println("Executing task on node: " + ignite.cluster().localNode().id());

            return "Node ID: " + ignite.cluster().localNode().id() + "\n" +
                    "OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version") + " " +
                    System.getProperty("os.arch") + "\n" +
                    "User: " + System.getProperty("user.name") + "\n" +
                    "JRE: " + System.getProperty("java.runtime.name") + " " +
                    System.getProperty("java.runtime.version");
        });

        // Print result.
        System.out.println();
        System.out.println("Nodes system information:");
        System.out.println();

        res.forEach(r -> {
            System.out.println(r);
            System.out.println();
        });
    }
}