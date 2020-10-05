package com.flb.cache;

import com.google.common.base.Stopwatch;
import jdk.jfr.Threshold;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.ClientException;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class PutGet {
    /** Entry point. */
    public static  final String CACHE_NAME = "put-get-falabella-example";
    public static final int THRESHOLD = 10000;
    public static void main(String[] args) {
        ClientConfiguration cfg = new ClientConfiguration().setAddresses("127.0.0.1:10800");
 //       IgniteConfiguration cfg = new IgniteConfiguration();

//        // The node will be started as a client node.
//        cfg.setClientMode(true);
//
//        // Classes of custom Java logic will be transferred over the wire from this app.
//        cfg.setPeerClassLoadingEnabled(false);
//        // Setting up an IP Finder to ensure the client can locate the servers.
//        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
//        ipFinder.setAddresses(Collections.singletonList("127.0.0.1:47500..47509"));
//        cfg.setDiscoverySpi(new TcpDiscoverySpi().setIpFinder(ipFinder));

        try (IgniteClient igniteClient = Ignition.startClient(cfg)) {
            System.out.println();
            System.out.println(">>> Thin client put-get example started.");



            ClientCache<Integer, Address> cache = igniteClient.getOrCreateCache(CACHE_NAME);

            System.out.format(">>> Created cache [%s].\n", CACHE_NAME);
            Stopwatch stopwatch = Stopwatch.createUnstarted();
            stopwatch.start();
            for (int i = 0; i< THRESHOLD; i++) {
                Address val = new Address(RandomStringUtils.randomAlphabetic(1023), ThreadLocalRandom.current().nextInt(1,634736473+1));

                cache.put(i, val);
                //System.out.format(">>> Saved [%s] in the cache.\n", val);
            }
            stopwatch.stop();
            printElapsedTime("Populating",stopwatch);
            System.out.println("Getting 10 rand0m Values ");
            stopwatch.reset();
            stopwatch.start();
            for (int i=0;i<1;i++) {
                int key = ThreadLocalRandom.current().nextInt(1,THRESHOLD+1);
                Address cachedVal = cache.get(1);
              //  System.out.format(">>> Loaded [%s] from the cache.\n", cachedVal.toString());
            }
            stopwatch.stop();
            printElapsedTime("Retrival ",stopwatch);
        }
        catch (ClientException e) {
            System.err.println(e.getMessage());
        }
        catch (Exception e) {
            System.err.format("Unexpected failure: %s\n", e);
        }
    }

    public static void printElapsedTime(final String pass, final Stopwatch stopwatch)
    {
        if (stopwatch.isRunning())
        {
            System.out.println("WARNING! Your stopwatch is still running!");
        }
        else // stopwatch not running
        {
            System.out.println(pass + " Time details: ");
            System.out.println("\t" + stopwatch.elapsed() + " elapsed milliseconds.");
            System.out.println("\t" + stopwatch.elapsed(TimeUnit.MINUTES) + " minutes");
            System.out.println("\t" + stopwatch.elapsed(TimeUnit.SECONDS) + " seconds");
            System.out.println("\t" + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " milliseconds");
            System.out.println("\t" + stopwatch.elapsed(TimeUnit.NANOSECONDS) + " nanoseconds");
        }
    }

}
