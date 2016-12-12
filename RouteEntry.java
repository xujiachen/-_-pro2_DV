/*
 * RouteEnter class
 * Used to save a route
 */

class RouteEntry {
    private IP DestIP_ = null;
    private IP NextHopIP_ = null;
    private int Cost_ = 1;

    public RouteEntry(IP DestIP, IP NextHopIP, int Cost) {
        DestIP_ = DestIP;
        NextHopIP_ = NextHopIP;
        Cost_ = Cost;
    }

    public IP getDestIP() {
        return DestIP_;
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
        return "DestIP: " + DestIP_.toString() + "  NextHopIP:" + NextHopIP_.toString() + "  Cost: " + Cost_;
    }

    public String show() {
        return DestIP_.toString() + "|" + NextHopIP_.toString() + "|" + Cost_;
    }
}
