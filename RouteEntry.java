/*
 * RouteEnter class
 * Used to save a route
 */

class RouteEntry {
    private IP DestinationIP_ = null;
    private IP NextHopIP_ = null;
    private int Cost_ = 1;

    public RouteEntry(IP DestinationIP, IP NextHopIP, int Cost) {
        DestinationIP_ = DestinationIP;
        NextHopIP_ = NextHopIP;
        Cost_ = Cost;
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
        return "DestinationIP: " + DestinationIP_.toString() + "\nNextHopIP:" + NextHopIP_.toString() + "\nCost: " + Cost_;
    }

    public String show() {
        return DestinationIP_.show() + "|" + NextHopIP_.show() + "|" + Cost_;
    }
}
