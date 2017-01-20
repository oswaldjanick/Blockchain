package de.neozo.domain;


import java.net.InetAddress;

public class Node {

    private InetAddress address;

    public InetAddress getAddress() {
        return address;
    }

    public Node setAddress(InetAddress address) {
        this.address = address;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return address != null ? address.equals(node.address) : node.address == null;
    }

    @Override
    public int hashCode() {
        return address != null ? address.hashCode() : 0;
    }
}
