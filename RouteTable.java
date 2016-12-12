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

    public RouteTable(List<RouteEntry> routeEntries) {
        RouteList = routeEntries;
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

    public int size() {
        return RouteList.size();
    }

    public RouteEntry get(int index) {
        return RouteList.get(index);
    }

    public void addRoutes(RouteTable table) {
        for (int i = 0; i < table.size(); i++) {
            addRoute(table.get(i));
        }
    }

    public void addRoute(RouteEntry entry) {
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

    @Override
    public String toString() {
        return RouteList.toString();
    }
}
