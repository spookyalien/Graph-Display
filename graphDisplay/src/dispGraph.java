import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.Graphics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Random;



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

class math_thread extends Thread 
{
    public boolean flag = false;
    int x, y, radius, bound;
    HashSet<Integer> coords;
    Random rand;


    public math_thread(int radius, int bound, HashSet<Integer> coords)
    {
        this.radius = radius;
        this.coords = coords;
        this.bound = bound;
        rand = new Random();
        x = rand.nextInt(bound/2);
        y = rand.nextInt(bound/2);
    }

    public void run() {
        while (!flag) {
            // Ensure entire node is shown within canvas
            this.x = rand.nextInt(bound/2);
            this.y = rand.nextInt(bound/2);
            flag = Utility.check_bounds(this.x, this.y, (this.radius*2), this.coords);
        }
    }

    public int get_x()
    {
        return this.x;
    }

    public int get_y()
    {
        return this.y;
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
        math_thread t1;
        int x = 0;
        int y = 0;

        for (Graph graph_to_paint : graphs) {
            g.setColor(colour);
            for (Vertex v : graph_to_paint.get_vertex_set()) { 
                y = rand.nextInt(canvas_size/2);
                x = rand.nextInt(canvas_size/2);
                vec2 = new vector2(x, y);
                vertex_coords.put(v, vec2);
                
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