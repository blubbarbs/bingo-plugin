package com.gmail.blubberalls.bingo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Map.Entry;

public class Leaderboard<K, V extends Comparable<? super V>> {
    private HashMap<K, Node> nodes = new HashMap<K, Node>();
    private Node root = null;

    private class Node {
        K key;
        V value;
        Node parent = null;
        Node left = null;
        Node right = null;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        Node findMinimum() {
            if (left != null)
                return left.findMinimum();
            if (right != null)
                return right.findMinimum();

            return this;
        }

        Node find(V value) {
            int direction = value.compareTo(this.value);
            
            if (direction < 0) {
                return left == null || left.value == value ? left : left.find(value);
            }
            else {
                return right == null || right.value == value ? right : right.find(value);
            }
        }

        void removeChild(Node node) {
            if (left == node) {
                left = null;
            }
            else if (right == node) {
                right = null;
            }
        }

        void add(Node node) {
            int direction = node.value.compareTo(value);

            if (direction < 0) {
                if (left == null) {
                    left = node;
                    node.parent = this;
                } 
                else {
                    left.add(node);
                }
            } 
            else {
                if (right == null) {
                    right = node;
                    node.parent = this;
                } 
                else {
                    right.add(node);
                }
            }
        }

        void inOrder(Set<Node> set) {
            if (left != null){
                left.inOrder(set);
                set.add(left);
            }
            
            set.add(this);
            
            if (right != null) {
                right.inOrder(set);
                set.add(right);
            }
        }
    }

    private Set<Node> getNodesInOrder() {
        LinkedHashSet<Node> nodesInOrder = new LinkedHashSet<Node>();

        root.inOrder(nodesInOrder);

        return nodesInOrder;
    }

    public Set<Entry<K, V>> getEntries() {
        LinkedHashSet<Entry<K, V>> entries = new LinkedHashSet<Entry<K, V>>();

        for (final Node n : getNodesInOrder()) {
            entries.add(new Entry<K,V>() {

                @Override
                public K getKey() {
                    return n.key;
                }

                @Override
                public V getValue() {
                    return nodes.get(n.key).value;
                }

                @Override
                public V setValue(V value) {
                    put(n.key, value);
                    
                    return value;
                }
            });
        }

        return entries;
    }

    public Collection<K> getKeysWithValue(V value) {
        ArrayList<K> keys = new ArrayList<K>();
        Node nodeWithValue = root.find(value);

        if (nodeWithValue == null) return Collections.emptyList();

        while (nodeWithValue != null && nodeWithValue.value == value) {
            keys.add(nodeWithValue.key);
            
            nodeWithValue = nodeWithValue.right;
        }

        return keys;
    }

    public Collection<V> getValues() {
        ArrayList<V> values = new ArrayList<V>();
        
        for (Node n : getNodesInOrder()) {
            values.add(n.value);
        }

        return values;
    }

    public Set<K> getKeys() {
        LinkedHashSet<K> keys = new LinkedHashSet<K>();

        for (Node n : getNodesInOrder()) {
            keys.add(n.key);
        }

        return keys;
    }

    public V get(K key) {
        return nodes.containsKey(key) ? nodes.get(key).value : null;
    }

    public void remove(K key) {
        if (!nodes.containsKey(key)) return;

        Node node = nodes.get(key);
        Node successor = node.findMinimum();

        if (node == root) {
            root = successor;
        }
        else {
            if (node.parent.left == node) {
                node.parent.left = successor;
            }
            else {
                node.parent.right = successor;
            }
        }

        if (successor.parent != null) {
            successor.parent.removeChild(successor);
        }

        if (node.left != null) {
            node.left.parent = successor;
        }

        if (node.right != null) {
            node.right.parent = successor;
        }

        successor.parent = node.parent;
        successor.left = node.left;
        successor.right = node.right;
    }

    public void put(K key, V value) {
        if (root == null) {
            root = new Node(key, value);
            nodes.put(key, root);
            return;
        }

        if (nodes.containsKey(key)) {
            remove(key);
        } 

        nodes.put(key, new Node(key, value));
        root.add(nodes.get(key));
    }
}