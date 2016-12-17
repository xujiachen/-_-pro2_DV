/*
 * RouteTable Class
 * Used to save the route table
 *
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.LinkedList;

class RouteTable {
    private List<RouteEntry> RouteList = null;

    // constructors

    public RouteTable() {
        RouteList = new LinkedList<>();
    }

    public RouteTable(JSONObject jsonObj) {
        if (RouteList == null)
            RouteList = new LinkedList<>();

        try {
            JSONArray array = jsonObj.getJSONArray("RouteList");
            for (int i = 0; i < array.length(); i++) {
                RouteList.add(new RouteEntry(new JSONObject(array.getString(i))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public RouteTable(String jsonString) throws JSONException {
        this(new JSONObject(jsonString));
    }

    // gets

    public int size() {
        return RouteList.size();
    }

    public RouteEntry get(int index) {
        return RouteList.get(index);
    }

    public IP getNextHop(IP destination) {
        for (RouteEntry entry : RouteList) {
            if (destination.equals(entry.getDestinationIP()))
                return entry.getNextHopIP();
        }
        return null;
    }

    // delete entry

    public void remove(RouteEntry entry) {
        for (RouteEntry e : RouteList) {
            if (e.equals(entry)) {
                RouteList.remove(e);
            }
        }
    }

    // add route(s) from neighbors

    public void addRoutesFromNeighbor(RouteTable table, IP NeighborIP) {
        Router.isTableRefresh = true;

        // get the Cost to neighbor
        int CostToNeighbor = 0;
        IP nextHopToNeighbor = null;
        for (RouteEntry e : RouteList) {
            if (e.getDestinationIP().equals(NeighborIP)) {
                CostToNeighbor = e.getCost();
                nextHopToNeighbor = e.getNextHopIP();
            }
        }

        // can not reach this neighbor
        if (CostToNeighbor == 0)
            return;

        for (int i = 0; i < table.size(); i++) {
            RouteEntry entry = table.get(i);

            // ignore the entries whose destination or next hop is this router
            if (!(entry.getNextHopIP().equals(Router.getLocalIP()) || entry.getDestinationIP().equals(Router.getLocalIP()))) {

                // find the local entry which has same destination
                RouteEntry entrySameDestination = findSameDestination(entry);

                // if can not find, add this route
                if (entrySameDestination == null) {
                    RouteList.add(new RouteEntry(entry.getDestinationIP(), nextHopToNeighbor, entry.getCost() + CostToNeighbor));
                }

                // else, compare their cost and choose the less one
                else if (entry.getCost() + CostToNeighbor < entrySameDestination.getCost()) {
                    remove(entrySameDestination);
                    RouteList.add(new RouteEntry(entry.getDestinationIP(), nextHopToNeighbor, entry.getCost() + CostToNeighbor));
                }
            }
        }

        Router.isTableRefresh = false;
    }

    // find the entry whose destination is same as the given entry's
    // only use in the function addRoutesFromNeighbor
    private RouteEntry findSameDestination(RouteEntry entry) {
        for (RouteEntry e : RouteList) {
            if (entry.getDestinationIP().equals(e.getDestinationIP()))
                return e;
        }
        return null;
    }

    public void addRoute(RouteEntry entry) {
        RouteList.add(entry);
    }

    // format as String or JsonObject

    @Override
    public String toString() {
        return toJSONObject().toString();
    }

    public JSONObject toJSONObject() {
        try {
            JSONObject obj = new JSONObject();
            obj.put("RouteList", RouteList);
            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // print in the window

    public void show() {
        System.out.println("\nDestination    |Next Hop       |Cost");
        System.out.println("------------------------------------");
        for (RouteEntry entry : RouteList) {
            System.out.println(entry.show());
        }
    }
}
