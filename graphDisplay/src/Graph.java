import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

class Vertex
{
    private String name;
    private LinkedList<Edge> edge_list;

    public Vertex(String name)
    {
        this.name = name;
        edge_list = new LinkedList<>();
    }

    public String get_name()
    {
        return name;
    }

    public LinkedList<Edge> get_edges()
    {
        return edge_list;
    }
}

class Edge
{
    private int weight;
    private Vertex dest_vertex;

    public Edge(Vertex dest, int w)
    {
        this.dest_vertex = dest;
        this.weight = w;
    }

    public Edge(Vertex dest)
    {
        this.dest_vertex = dest;
        this.weight = 1;
    }

    public int get_weight()
    {
        return weight;
    }

    public Vertex get_dest_vertex()
    {
        return dest_vertex;
    }
}

class Graph
{
    private HashSet<Vertex> Vertexs;
    
    public Graph()
    {
        Vertexs = new HashSet<>();
    }

    public Vertex create_vertex(String id)
    {
        Vertex v = new Vertex(id);

        add_vertex(v);
        return v;
    }
    
    public void add_edge(Vertex v1, Vertex v2, int weight1, int weight2)
    {
        //since it's a directed graph add weights both ways
        v2.get_edges().add(new Edge(v1, weight1));
        v1.get_edges().add(new Edge(v2, weight2));
    }
    
    public void add_edge(Vertex v1, Vertex v2, int weight1)
    {
        //since it's a directed graph add weights both ways
        v2.get_edges().add(new Edge(v1, weight1));
        v1.get_edges().add(new Edge(v2, weight1));
    }

    public void add_edge(Vertex v1, Vertex v2)
    {
        add_edge(v1, v2, 1, 1);
    }

    public void insert(String name1, String name2)
    {
        Vertex vertex1 = get_vertex(name1);
        Vertex vertex2 = get_vertex(name2);
        if (vertex1 == null)
            vertex1 = create_vertex(name1);
        if( vertex2 == null)
            vertex2 = create_vertex(name2);

        add_edge(vertex1, vertex2);
    }

    public void insert(String name1, String name2, int weight)
    {
        Vertex vertex1 = get_vertex(name1);
        Vertex vertex2 = get_vertex(name2);
        if (vertex1 == null || vertex2 == null) {
            if (vertex1 == null)
                vertex1 = create_vertex(name1);
            if (vertex2 == null)
                vertex2 = create_vertex(name2);
        }
        add_edge(get_vertex(name1), get_vertex(name2), weight);
    }

    public void insert(String name1, String name2, int weight1, int weight2)
    {
        Vertex vertex1 = get_vertex(name1);
        Vertex vertex2 = get_vertex(name2);
        if (vertex1 == null || vertex2 == null) {
            if (vertex1 == null)
                vertex1 = create_vertex(name1);
            if (vertex2 == null)
                vertex2 = create_vertex(name2);
        }
        add_edge(get_vertex(name1), get_vertex(name2), weight1, weight2);
    }

    public boolean add_vertex(Vertex v)
    {
        return Vertexs.add(v);
    }

    public HashSet<Vertex> get_vertex_set()
    {
        return Vertexs;
    }

    public Vertex get_vertex(String id)
    {
        for (Vertex v : Vertexs) {
            if (v.get_name().equals(id))
                return v;
        }

        return null;
    }

    public int get_size()
    {
        return Vertexs.size();
    }
    

    public void print_graph()
    {
        for (Vertex v : Vertexs) {
            System.out.print("Source Vertex: "+ v.get_name() + ": ->");

            for (Edge e : v.get_edges()) {
                System.out.print("Dest Vertex: " + e.get_dest_vertex().get_name() + " weight: " + e.get_weight() + " || ");
            }
            System.out.print("\n");
        }
    }

    public void fill_graph(String file_name)
    {
        try
        {
            String line = "";

            FileReader reader = new FileReader(file_name); 
            BufferedReader buff = new BufferedReader(reader);    
            while ((line = buff.readLine()) != null) 
            {
                if (line.isEmpty()) continue;
                else {
                    String[] node_arr = line.split(" ");
                    for (int i = 0; i < node_arr.length; i++) {
                        if (i == 0) continue;
                        insert(node_arr[0], node_arr[i]);
                    }
                }
            }
            buff.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("[!] File not found.");
            return;
        }  
        catch (IOException e) {
            System.out.println("Error.");
            return;
        } 
    }
}