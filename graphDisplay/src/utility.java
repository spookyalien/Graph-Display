import java.util.HashSet;

class Utility 
{
    public static boolean check_bounds(int x, int y, int bound, HashSet<Integer> coords)
    {
        if ((x-bound) <= bound || (y-bound) <= bound) {
            return false;
        }
        
        boolean flag1 = true; 
        boolean flag2 = true;
        for (int i = (x-bound); i < (x+bound); i++) {
            if (coords.contains(i)) flag1 = false;
        }
        for (int i = y-bound; i < y+bound; i++) {
            if (coords.contains(i)) flag2 = false;
        }

        return (flag1 || flag2);
    }
}
