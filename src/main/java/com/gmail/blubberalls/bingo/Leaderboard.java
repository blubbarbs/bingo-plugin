package com.gmail.blubberalls.bingo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class Leaderboard<K, V extends Comparable<? super V>> implements Map<K, V> {
    private HashMap<K, Node> nodes = new HashMap<K, Node>();
    private Node root = null;

    public int size() {
        return nodes.size();
    }

    public boolean containsKey(Object key) {
        return nodes.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return nodes.values().stream().anyMatch(node -> node.value.equals(value));
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    public Set<Entry<K, V>> entrySet() {
        if (root == null) return Collections.emptySet();

        LinkedHashSet<Entry<K, V>> entries = new LinkedHashSet<Entry<K, V>>();

        root.forEach(n -> {
            entries.add(new Entry<K,V>() {

                @Override
                public K getKey() {
                    return n.key;
                }

                @Override
                public V getValue() {
                    return n.value;
                }

                @Override
                public V setValue(V value) {
                    put(n.key, value);
                    
                    return value;
                }
            });
        });

        return entries;
    }

    public K getKeyAt(int index) {
        Node node = root;

        for (int i = 0; i < index && node != null; i++) {
            node = node.next;
        }

        return node != null ? node.key : null;
    }

    public Collection<K> getKeysWithValue(V value) {
        if (root == null) return Collections.emptyList();

        ArrayList<K> keys = new ArrayList<K>();
        Node nodeWithValue = root.findNode(value);

        if (nodeWithValue == null) return Collections.emptyList();

        while (nodeWithValue != null && nodeWithValue.value.equals(value)) {
            keys.add(nodeWithValue.key);            
            nodeWithValue = nodeWithValue.next;
        }

        return keys;
    }

    public Set<V> getUniqueValues() {
        if (root == null) return Collections.emptySet();

        LinkedHashSet<V> uniqueValues = new LinkedHashSet<V>();

        root.forEach(n -> uniqueValues.add(n.value));

        return uniqueValues;
    }

    public List<V> values() {
        if (root == null) return Collections.emptyList();

        ArrayList<V> values = new ArrayList<V>();

        root.forEach(node -> values.add(node.value));

        return values;
    }

    public Set<K> keySet() {
        if (root == null) return Collections.emptySet();

        LinkedHashSet<K> keys = new LinkedHashSet<K>();

        root.forEach(node -> keys.add(node.key));

        return keys;
    }

    public V get(Object key) {
        return nodes.containsKey(key) ? nodes.get(key).value : null;
    }

    public V getOrDefault(Object key, V defaultValue) {
        return containsKey(key) ? get(key) : defaultValue;
    }

    public V remove(Object key) {
        if (!nodes.containsKey(key)) return null;
        
        Node node = nodes.get(key);
        
        root.deleteNode(node);
        nodes.remove(key);

        return node.value;
    }

    public V put(K key, V value) {
        if (root == null) {
            root = new Node(key, value);
            nodes.put(key, root);
            
            return value;
        }
        else if (nodes.containsKey(key)) {
            Node node = nodes.get(key);

            root.deleteNode(node);
            node.value = value;
            
            if (root == null) {
                root = node;
            }
            else {
                root.addNode(node);
            }

            return value;
        }
        else {
            Node node = new Node(key, value);
            nodes.put(key, node);

            root.addNode(node);

            return value;
        }
    }

    public void clear() {
        root.forEach(node -> node.next = null);
        root = null;
        nodes.clear();
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        map.forEach((k, v) -> put(k, v));
    }

    private class Node {
        K key;
        V value;
        Node next = null;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        Node findNode(V value) {
            if (this.value.equals(value)) {
                return this;
            }
            else if (next != null) {
                return next.findNode(value);
            }
            else {
                return null;
            }
        }

        void deleteNode(Node node) {
            if (node == root) {
                root = root.next;
            }
            else if (next == node) {
                next = node.next;
            }
            else if (next != null) {
                next.deleteNode(node);
            }
        }

        void addNode(Node node) {                        
            if (node.value.compareTo(root.value) > 0) {
                node.next = root;
                root = node;
            }
            else if (next == null || node.value.compareTo(next.value) > 0) {
                node.next = next;
                next = node;
            }
            else {
                next.addNode(node);
            }
        }

        void forEach(Consumer<Node> consumer) {
            Node next = this.next;

            consumer.accept(this);
            
            if (next != null) {
                next.forEach(consumer);
            }
        }
    }
}