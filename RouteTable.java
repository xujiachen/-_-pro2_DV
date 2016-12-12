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
    }

    public void AddRoute(RouteEntry entry) {
        // TODO add a new Route according the DV
        RouteList.add(entry);
    }

    // print in the window
    public void show() {
        System.out.println("Destination    |Next Hop       |Cost");
        System.out.println("------------------------------------");
        for (RouteEntry entry : RouteList) {
            System.out.println(entry.show());
        }
    }
}
