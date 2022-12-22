import javax.swing.*;
import javax.xml.namespace.QName;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Random;

import java.lang.Math.*;



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
}

public class dispGraph extends JPanel
{
    static int diameter = 35;
    static int radius = diameter/2;
    static int canvas_size = 500;
    private static ArrayList<Graph> graphs = new ArrayList<Graph>();
    private static int node_width = diameter;
    private static int node_height = diameter;

    public void paint(Graphics g)
    {
        int x, y;
        HashMap<Vertex, vector2> vertex_coords = new HashMap<Vertex, vector2>();
        HashSet<Integer> rand_nums = new HashSet<Integer>();
        vector2 vec2 = new vector2();
        Random rand = new Random();
        Color colour = Color.RED;
        x = y = 0;

        for (Graph graph_to_paint : graphs) {
            g.setColor(colour);
            for (Vertex v : graph_to_paint.get_vertex_set()) {
                x = canvas_size / 4;
                y = canvas_size / 2;
                
                while (rand_nums.contains(x) || rand_nums.contains(y)) {
                    if (rand_nums.contains(x)) {
                        x += 60;
                    } else if (rand_nums.contains(y)) {
                        y += 40;
                    }
                }
                //If node is at top id will not be displayed so node must be moved down
                if (y == 0)
                    y += 20;

                vec2 = new vector2(x, y);
                vertex_coords.put(v, vec2);
                
                g.fillOval(x, y, node_width, node_height); 
                rand_nums.add(x);
                rand_nums.add(y);
            }

            g.setColor(Color.BLACK);
            for (Vertex v : graph_to_paint.get_vertex_set()) {
                g.drawString(v.get_name(), vertex_coords.get(v).x, vertex_coords.get(v).y);    
                for (Edge e : v.get_edges()) {

                    vector2 vec2_src = vertex_coords.get(v);
                    vector2 vec2_dest = vertex_coords.get(e.get_dest_vertex());

                    //adding to go down and right on the canvas
                    //divide diameter by 2 to get middle of circle
                    g.drawLine(vec2_src.x+radius, vec2_src.y+radius, vec2_dest.x+radius, vec2_dest.y+radius);
                }
            }
        }
    }
    
    
    public static void main(String[] args)
    {
        Graph my_graph = new Graph();
        my_graph.insert("Node1", "Node2");
        my_graph.insert("Node2", "Node3");
        graphs.add(my_graph);


        Graph my_graph2 = new Graph();
        my_graph2.insert("Node21", "Node22");
        my_graph2.insert("Node22", "Node23");
        graphs.add(my_graph2);
 
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