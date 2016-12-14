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
    private List<RouteEntry> RouteList;

    public RouteTable() {
        RouteList = new LinkedList<>();
    }

    public RouteTable(JSONObject jsonObj) {
        try {
            JSONArray jsonArray = jsonObj.getJSONArray("RouteList");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                RouteList.add(new RouteEntry(jsonObject));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public RouteTable(String jsonString) throws JSONException {
        new RouteTable(new JSONObject(jsonString));
    }

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

    public JSONObject toJSONObject() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("RouteList", RouteList);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // to a string as the format of json
    @Override
    public String toString() {
        return toJSONObject().toString();
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
