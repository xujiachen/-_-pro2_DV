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

    // add route(s) from neighbors

    public void addRoutesFromNeighbor(RouteTable table, IP NeighborIP) {
        Router.isTableRefresh = true;
        for (int i = 0; i < table.size(); i++) {
            RouteEntry entry = table.get(i);
            // TODO add route from neighbor with DV
        }
        Router.isTableRefresh = false;
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
