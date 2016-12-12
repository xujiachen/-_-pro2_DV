/*
 * RouteEnter class
 * Used to save a route
 */

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

class RouteEntry {
    private IP DestinationIP_ = null;
    private IP NextHopIP_ = null;
    private int Cost_ = 1;

    public RouteEntry(IP DestinationIP, IP NextHopIP, int Cost) {
        DestinationIP_ = DestinationIP;
        NextHopIP_ = NextHopIP;
        Cost_ = Cost;
    }

    public RouteEntry(JSONObject jsonObject) {
        DestinationIP_ = new IP((String) jsonObject.get("DestinationIP"));
        NextHopIP_ = new IP((String) jsonObject.get("NextHopIP"));
        Cost_ = Integer.valueOf((String) jsonObject.get("Cost"));
    }

    public IP getDestIP() {
        return DestinationIP_;
    }

    public IP getNextHopIP() {
        return NextHopIP_;
    }

    public void setNextHopIP(IP NextHopIP) {
        NextHopIP_ = NextHopIP;
    }

    public int getCost() {
        return Cost_;
    }

    public void setCost(int Cost) {
        Cost_ = Cost;
    }

    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("DestinationIP", DestinationIP_.toString());
        obj.put("NextHopIP", NextHopIP_.toString());
        obj.put("Cost", Cost_);
        return obj.toString();
    }

    public String show() {
        return DestinationIP_.show() + "|" + NextHopIP_.show() + "|" + Cost_;
    }
}
