package mazes.generators.maze;

import datastructures.concrete.ChainedHashSet;
import datastructures.interfaces.ISet;
import mazes.entities.Maze;
import mazes.entities.Room;
import mazes.entities.Wall;
import misc.exceptions.NotYetImplementedException;
import misc.graphs.Graph;

import java.awt.*;
import java.util.Iterator;

/**
 * Carves out a maze based on Kruskal's algorithm.
 *
 * See the spec for more details.
 */
public class KruskalMazeCarver implements MazeCarver {
    @Override
    public ISet<Wall> returnWallsToRemove(Maze maze) {
        // Note: make sure that the input maze remains unmodified after this method is over.
        //
        // In particular, if you call 'wall.setDistance()' at any point, make sure to
        // call 'wall.resetDistanceToOriginal()' on the same wall before returning.
        ISet<Wall> remove = new ChainedHashSet<>();
        ISet<Wall> edges = maze.getWalls();
        ISet<Room> verts = maze.getRooms();
        Graph<Room, Wall> g = new Graph<Room, Wall>(verts, edges);

        remove = g.findMinimumSpanningTree();

        return remove;
    }
}
