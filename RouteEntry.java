/*
 * RouteEnter class
 * Used to save a route
 */

import org.json.JSONException;
import org.json.JSONObject;

class RouteEntry {
    private IP DestinationIP_ = null;
    private IP NextHopIP_ = null;
    private int Cost_ = 1;

    public RouteEntry(IP DestinationIP, IP NextHopIP, int Cost) {
        DestinationIP_ = DestinationIP;
        NextHopIP_ = NextHopIP;
        Cost_ = Cost;
    }

    public RouteEntry(JSONObject jsonObject) throws JSONException {
        DestinationIP_ = new IP((String) jsonObject.get("DestinationIP"));
        NextHopIP_ = new IP((String) jsonObject.get("NextHopIP"));
        Cost_ = Integer.valueOf((String) jsonObject.get("Cost"));
    }

    public IP getDestinationIP() {
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

    // to string as the format of json
    @Override
    public String toString() {
        try {
            JSONObject obj = new JSONObject();
            obj.put("DestinationIP", DestinationIP_.toString());
            obj.put("NextHopIP", NextHopIP_.toString());
            obj.put("Cost", Cost_);
            return obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String show() {
        return DestinationIP_.show() + "|" + NextHopIP_.show() + "|" + Cost_;
    }
}
