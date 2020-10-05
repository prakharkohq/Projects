package com.flb.cache;

import org.apache.ignite.binary.BinaryObjectException;
import org.apache.ignite.binary.BinaryReader;
import org.apache.ignite.binary.BinaryWriter;
import org.apache.ignite.binary.Binarylizable;

/**
 * Employee address.
 * <p>
 * This class implements  only for example purposes,
 * in order to show how to customize serialization and deserialization of
 * binary objects.
 */
public class Address implements Binarylizable {
    /** Street. */
    private String street;

    /** ZIP code. */
    private int zip;

    /**
     * Required for binary deserialization.
     */
    public Address() {
        // No-op.
    }


    public Address(String street, int zip) {
        this.street = street;
        this.zip = zip;
    }

    @Override public void writeBinary(BinaryWriter writer) throws BinaryObjectException {
        writer.writeString("street", street);
        writer.writeInt("zip", zip);
    }

    @Override public void readBinary(BinaryReader reader) throws BinaryObjectException {
        street = reader.readString("street");
        zip = reader.readInt("zip");
        System.out.println("Read Binary from Cache is Street"+street+"  ZIP is"+zip);
    }

    @Override public String toString() {
        return "Address [street=" + street +
                ", zip=" + zip + ']';
    }
}