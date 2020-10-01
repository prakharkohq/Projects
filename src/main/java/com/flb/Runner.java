package com.flb;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class Runner {
    public static void main(String[] args) {
        final Ignite ignite = Ignition.start("apacheignite-cassandra.xml");
        ignite.cache("CatalogCategory").loadCache(null);
        ignite.cache("CatalogGood").loadCache(null);
    }
}
