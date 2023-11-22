import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.Graphics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
import java.util.Random;
import java.lang.Math;

class vector2
{
    public int x;
    public int y;

    vector2()
    {
        x = y = 0;
    }

    vector2(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public double distance(vector2 input)
    {
        return Math.sqrt((Math.pow(input.x-this.x, 2)+Math.pow(input.y-this.y, 2))); 
    }
}

public class dispGraph extends JPanel
{
    static int diameter = 35;
    static int radius = diameter/2;
    static int canvas_size = 700;
    private static ArrayList<Graph> graphs = new ArrayList<Graph>();
    Random rand = new Random(); 

    public void paint(Graphics g)
    {
        HashMap<Vertex, vector2> vertex_coords = new HashMap<Vertex, vector2>();
        HashSet<Integer> coords = new HashSet<Integer>();
        vector2 vec2 = new vector2();
        Color colour = Color.RED;
        int x = 0;
        int y = 0;

        for (Graph graph_to_paint : graphs) {
            g.setColor(colour);

            for (Vertex node : graph_to_paint.get_vertex_set()) { 
                while (!Utility.check_bounds(x, y, radius, coords)) {
                    x = rand.nextInt(canvas_size - (diameter*2));
                    y = rand.nextInt(canvas_size - (diameter*2));
                }
                vec2 = new vector2(x, y);
                vertex_coords.put(node, vec2);
                
                coords.add(x);
                coords.add(y);
                g.fillOval(x, y, radius, radius);
            }

            g.setColor(Color.BLACK);
            for (Vertex v : graph_to_paint.get_vertex_set()) {
                g.drawString(v.get_name(), vertex_coords.get(v).x, vertex_coords.get(v).y);    
                for (Edge e : v.get_edges()) {

                    vector2 vec2_src = vertex_coords.get(v);
                    vector2 vec2_dest = vertex_coords.get(e.get_dest_vertex());

                    g.drawLine(vec2_src.x+(radius/2), vec2_src.y+(radius/2), vec2_dest.x+(radius/2), vec2_dest.y+(radius/2));
                }
            }
        }
    }
    
    public static void main(String[] args)
    {
        Graph test_graph = new Graph();
        test_graph.fill_graph("/home/bigadmin/myProgs/Java/graphdisp/graph.txt");
        graphs.add(test_graph);
        // my_graph.insert("Node1", "Node2");
        // my_graph.insert("Node2", "Node3");
        // graphs.add(my_graph);


        // Graph my_graph2 = new Graph();
        // my_graph2.insert("Node21", "Node22");
        // my_graph2.insert("Node22", "Node23");
        // graphs.add(my_graph2);
 
        JFrame f = new JFrame();
        JMenu menu_2 = new JMenu("Graph");
        JMenu menu = new JMenu("Options");
        JMenuBar menu_bar = new JMenuBar();
    


        //TODO: Allow for graphs to be editable
        JMenuItem graph_add = new JMenuItem(new AbstractAction("Add a graph") {
            public void actionPerformed(ActionEvent e) {
                graphs.add(new Graph());
                graphs.get(0).insert("Node1", "node2");
            }
        });

        JMenuItem item = new JMenuItem(new AbstractAction("Exit") {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu_2.add(graph_add);
        menu.add(item);
        menu_bar.add(menu_2);
        menu_bar.add(menu);

        f.setJMenuBar(menu_bar);
        f.add(new dispGraph());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(canvas_size, canvas_size);
        f.setVisible(true);
    }
}