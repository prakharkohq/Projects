package com.flb.cluster.basic;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class ClusterGroupExample {
    public static void main(String[] args) {
        try(Ignite ignite = Ignition.start("example-ignite.xml")) {

        }
    }
}
