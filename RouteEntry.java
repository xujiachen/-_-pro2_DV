/*
 * RouteEnter class
 * Used to save a route
 */

class RouteEntry {
    private IP SourceIP_ = null;
    private IP DestIP_ = null;
    private IP NextHopIP_ = null;
    private int Cost_ = 1;

    public RouteEntry(IP SourceIP, IP DestIP, IP NextHopIP, int Cost) {
        SourceIP_ = SourceIP;
        DestIP_ = DestIP;
        NextHopIP_ = NextHopIP;
        Cost_ = Cost;
    }

    public IP getSourceIP() {
        return SourceIP_;
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
        return "SourceIP: "+ SourceIP_.toString() + "  DestIP: " + DestIP_.toString() + "  NextHopIP:" + NextHopIP_.toString() + "  Cost: " + Cost_;
    }

    public String show() {
        if (Cost_ < 10)
            return SourceIP_.toString() + "|" + DestIP_.toString() + "|" + NextHopIP_.toString() + "|   " + Cost_;
        else
            return SourceIP_.toString() + "|" + DestIP_.toString() + "|" + NextHopIP_.toString() + "|  " + Cost_;
    }
}
