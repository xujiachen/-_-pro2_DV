/*
 * RouteTable Class
 * Used to save the route table
 *
 */

import java.util.List;
import java.util.LinkedList;

class RouteTable {
    private List<RouteEntry> RouteList;

    public RouteTable() {
        RouteList = new LinkedList<>();
        RouteList.add(new RouteEntry(
         new IP("22.22.33.44"), new IP("33.22.33.44"), 4));
    }

    public void AddRoute(RouteEntry entry) {
        // TODO add a new Route according the DV
    }

    // print in the window
    public void show() {
        System.out.println("Destination    |Next Hop       |Cost");
        System.out.println("---------------|---------------|----");
        for (int i = 0; i < RouteList.size(); i++) {
            System.out.println(RouteList.get(i).show());
        }
    }
}
