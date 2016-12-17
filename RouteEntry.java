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

    // constructors

    RouteEntry(IP DestinationIP, IP NextHopIP, int Cost) {
        DestinationIP_ = DestinationIP;
        NextHopIP_ = NextHopIP;
        Cost_ = Cost;
    }

    RouteEntry(JSONObject jsonObject) throws JSONException {
        DestinationIP_ = new IP((String) jsonObject.get("DestinationIP"));
        NextHopIP_ = new IP((String) jsonObject.get("NextHopIP"));
        Cost_ = jsonObject.getInt("Cost");
    }

    // gets and sets

    IP getDestinationIP() {
        return DestinationIP_;
    }

    IP getNextHopIP() {
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

    // format as json

    @Override
    public String toString() {
        return toJSONObject().toString();
    }

    public JSONObject toJSONObject() {

        try {
            JSONObject obj = new JSONObject();
            obj.put("DestinationIP", DestinationIP_.toString());
            obj.put("NextHopIP", NextHopIP_.toString());
            obj.put("Cost", Cost_);
            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // print on the window

    String show() {
        return DestinationIP_.show() + "|" + NextHopIP_.show() + "|" + Cost_;
    }

    // judge whether two entry are same

    public boolean equals(RouteEntry entry) {
        if (entry.getCost() == Cost_
                && entry.getNextHopIP().equals(NextHopIP_)
                && entry.getDestinationIP().equals(DestinationIP_)) {
            return true;
        } else return false;
    }
}
