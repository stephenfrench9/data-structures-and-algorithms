package misc.graphs;

import datastructures.concrete.*;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import misc.Searcher;
import misc.exceptions.NoPathExistsException;
import misc.exceptions.NotYetImplementedException;

/**
 * Represents an undirected, weighted graph, possibly containing self-loops, parallel edges,
 * and unconnected components.
 *
 * Note: This class is not meant to be a full-featured way of representing a graph.
 * We stick with supporting just a few, core set of operations needed for the
 * remainder of the project.
 */
public class Graph<V, E extends Edge<V> & Comparable<E>> {
    // NOTE 1:
    //
    // Feel free to add as many fields, private helper methods, and private
    // inner classes as you want.
    //
    // And of course, as always, you may also use any of the data structures
    // and algorithms we've implemented so far.
    //
    // Note: If you plan on adding a new class, please be sure to make it a private
    // static inner class contained within this file. Our testing infrastructure
    // works by copying specific files from your project to ours, and if you
    // add new files, they won't be copied and your code will not compile.
    //
    //
    // NOTE 2:
    //
    // You may notice that the generic types of Graph are a little bit more
    // complicated then usual.
    //
    // This class uses two generic parameters: V and E.
    //
    // - 'V' is the type of the vertices in the graph. The vertices can be
    //   any type the client wants -- there are no restrictions.
    //
    // - 'E' is the type of the edges in the graph. We've constrained Graph
    //   so that E *must* always be an instance of Edge<V> AND Comparable<E>.
    //
    //   What this means is that if you have an object of type E, you can use
    //   any of the methods from both the Edge interface and from the Comparable
    //   interface
    //
    // If you have any additional questions about generics, or run into issues while
    // working with them, please ask ASAP either on Piazza or during office hours.
    //
    // Working with generics is really not the focus of this class, so if you
    // get stuck, let us know we'll try and help you get unstuck as best as we can.
    private ChainedHashDictionary<V, DoubleLinkedList<E>> mDic;
    private IList<E> mEdges;
    private int mNumEdges;
    /**
     * Constructs a new graph based on the given vertices and edges.
     *
     * @throws IllegalArgumentException  if any of the edges have a negative weight
     * @throws IllegalArgumentException  if one of the edges connects to a vertex not
     *                                   present in the 'vertices' list
     */
    public Graph(IList<V> vertices, IList<E> edges) {

        //All my edges are going to be of type E in Graph.java
        //I will trust that what is handed in will implement Edge and Comparable
        //I am surprised that this class does not guarantee
        ChainedHashDictionary<V, DoubleLinkedList<E>> al =
                new ChainedHashDictionary<V, DoubleLinkedList<E>>();
        mNumEdges = edges.size();
        for(V v : vertices) {
            DoubleLinkedList<E> vEdges = new DoubleLinkedList<E>();
            for(E e:edges) {
                V vert1 = e.getVertex1();
                V vert2 = e.getVertex2();
                if(v.equals(vert1) || v.equals(vert2)) {
                    vEdges.add(e);
                }
            }
            al.put(v, vEdges);
        }
        mDic = al;
        mEdges = edges;
    }

    /**
     * Sometimes, we store vertices and edges as sets instead of lists, so we
     * provide this extra constructor to make converting between the two more
     * convenient.
     */
    public Graph(ISet<V> vertices, ISet<E> edges) {
        // You do not need to modify this method.
        this(setToList(vertices), setToList(edges));
    }

    // You shouldn't need to call this helper method -- it only needs to be used
    // in the constructor above.
    private static <T> IList<T> setToList(ISet<T> set) {
        IList<T> output = new DoubleLinkedList<>();
        for (T item : set) {
            output.add(item);
        }
        return output;
    }

    /**
     * Returns the number of vertices contained within this graph.
     */
    public int numVertices() {
        return mDic.size();
    }

    /**
     * Returns the number of edges contained within this graph.
     */
    public int numEdges() {
        mDic.iterator();
        int acc = 0;
        for(KVPair<V, DoubleLinkedList<E>> v : mDic) {
             acc += v.getValue().size();
        }
        return mNumEdges;
    }

    /**
     * Returns the set of all edges that make up the minimum spanning tree of
     * this graph.
     *
     * If there exists multiple valid MSTs, return any one of them.
     *
     * Precondition: the graph does not contain any unconnected components.
     */
    public ISet<E> findMinimumSpanningTree() {
        ISet<E> mstEdges = new ChainedHashSet<>();
        ArrayDisjointSet<V> verticeSets = new ArrayDisjointSet<V>();
        for(KVPair<V, DoubleLinkedList<E>> v : mDic) {
             verticeSets.makeSet(v.getKey());
        }
        IList<E> sortedEdges = Searcher.topKSort(mEdges.size() + 1, mEdges);

        for(E e : sortedEdges) {
            V vert1 = e.getVertex1();
            V vert2 = e.getVertex2();
            int set1 = verticeSets.findSet(vert1);
            int set2 = verticeSets.findSet(vert2);
            if(set1!=set2) {
                mstEdges.add(e);
                verticeSets.union(vert1, vert2);
            }
        }
        return mstEdges;
    }

    /**
     * Returns the edges that make up the shortest path from the start
     * to the end.
     *
     * The first edge in the output list should be the edge leading out
     * of the starting node; the last edge in the output list should be
     * the edge connecting to the end node.
     *
     * Return an empty list if the start and end vertices are the same.
     *
     * @throws NoPathExistsException  if there does not exist a path from the start to the end
     */
    public IList<E> findShortestPathBetween(V start, V end) {
        //initialize
        ArrayHeap<Path> minHeap = new ArrayHeap<Path>();
        IList<E> steps;
        Path i = new Path(start);
        Path select = i;
        minHeap.insert(i);

        //the heap is ready to go
        while(!minHeap.isEmpty()) {
            Path current = minHeap.removeMin();
            if(current.mCurrentV.equals(end)) {
                select = current;
                break;
            }

            IList<V> pathVertices = current.mVertices;
            steps = mDic.get(current.mCurrentV);

            current.printVertices();

            for (E e : steps) {
                V next = e.getOtherVertex(current.mCurrentV);
                if(!pathVertices.contains(next)) {
                    Path p = current.fork(next, e, e.getWeight());
                    minHeap.insert(p);
                }
            }
        }
        System.out.println("THE ANSWER IS");
        select.printVertices();

        return select.mEdges;
    }

    private V getDeathVertice(Path current) {
        V v = current.mVertices.get(0);
        if(current.mVertices.size()==1) {
            v = current.mVertices.get(0);
        } else {
            int stl = current.mVertices.size()-2;
            v = current.mVertices.get(stl);
        }
        return v;
    }

    private class Path implements Comparable<Path> {
        public V mStartV;
        public V mCurrentV;
        public IList<E> mEdges;
        public IList<V> mVertices;
        public double mCost;

        public Path(V v) {
            mStartV = v;
            mCurrentV = v;
            mEdges = new DoubleLinkedList<E>();
            mVertices = new DoubleLinkedList<V>();
            mVertices.add(v);
            mCost = 0;
        }

        public Path(V v, E e, double cost, IList<E> edges, IList<V> verts) {
            mCurrentV = v;
            mEdges = new DoubleLinkedList<E>();
            mVertices = new DoubleLinkedList<V>();
            for(V old : verts) {
                mVertices.add(old);
            }
            mVertices.add(v);
            for(E old : edges) {
                mEdges.add(old);
            }
            mEdges.add(e);
            mCost = cost;
        }

        public Path fork(V v, E e, double cost) {
            Path p = new Path(v, e, mCost+cost, mEdges, mVertices);
            return p;
        }

        public void printVertices() {
            for(V v : mVertices) {
                System.out.print(v + ", ");
            }
            System.out.println(":-> "+mCost);
        }

        @Override
        public int compareTo(Path o) {
            int greater = 0;

            if(mCost > o.mCost) {
                greater = 1;
            } else if(mCost==o.mCost) {
                greater = 0;
            } else if(mCost < o.mCost) {
                greater = -1;
            }
            return greater;
        }
    }
}
