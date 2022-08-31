package com.gmail.blubberalls.bingo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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

    public Collection<K> getKeysWithValue(V value) {
        if (root == null) return Collections.emptyList();

        ArrayList<K> keys = new ArrayList<K>();
        Node nodeWithValue = root.find(value);

        if (nodeWithValue == null) return Collections.emptyList();

        while (nodeWithValue != null) {
            keys.add(nodeWithValue.key);            
            nodeWithValue = nodeWithValue.find(value);
        }

        return keys;
    }

    public Set<V> getUniqueValues() {
        if (root == null) return Collections.emptySet();

        HashSet<V> uniqueValues = new HashSet<V>();

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

    public V remove(Object key) {
        if (!nodes.containsKey(key)) return null;
        Node node = nodes.get(key);

        node.delete();
        nodes.remove(key);

        return node.value;
    }

    public V put(K key, V value) {
        if (root == null) {
            root = new Node(key, value);
            nodes.put(key, root);
            
            return value;
        }

        if (nodes.containsKey(key)) {
            Node node = nodes.get(key);

            node.delete();
            node.value = value;

            if (root != null) {
                root.addNode(node);
            }
            else {
                root = node;
            }

            return null;
        }
        else {
            Node node = new Node(key, value);

            nodes.put(key, node);
            root.addNode(node);

            return value;
        }
    }

    public void clear() {
        if (root != null) root.delete();
        
        nodes.clear();
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        map.forEach((k, v) -> put(k, v));
    }

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

        void delete() {
            Node successor = left != null || right != null ? findMinimum() : null;
            
            if (parent != null) {
                if (parent.left == this) {
                    parent.left = successor;
                }
                else {
                    parent.right = successor;
                }
            }
            else {
                root = successor;
            }
            
            if (successor != null) {
                successor.delete();
                
                if (left != null) {
                    left.parent = successor;
                }

                if (right != null) {
                    right.parent = successor;
                }

                successor.parent = parent;
                successor.left = left;
                successor.right = right;
                parent = null;
                left = null;
                right = null;
            }
        }

        void addNode(Node node) {                        
            int direction = node.value.compareTo(value);

            if (direction < 0) {
                if (left == null) {
                    left = node;
                    node.parent = this;
                } 
                else {
                    left.addNode(node);
                }
            } 
            else {
                if (right == null) {
                    right = node;
                    node.parent = this;
                } 
                else {
                    right.addNode(node);
                }
            }
        }

        void forEach(Consumer<Node> function) {
            if (right != null) {
                right.forEach(function);
            }

            function.accept(this);
            
            if (left != null) {
                left.forEach(function);
            }
        }
    }
}