/*
 * RouteEnter class
 * Used to save a route
 */

class RouteEnter {
    private String SourceIP_ = null;
    private String DestIP_ = null;
    private String NextHopIP_ = null;
    private int Cost_ = 1;

    public RouteEnter(String SourceIP, String DestIP, String NextHopIP, int Cost) {
        SourceIP_ = SourceIP;
        DestIP_ = DestIP;
        NextHopIP_ = NextHopIP;
        Cost_ = Cost;
    }

    public String getSourceIP() {
        return SourceIP_;
    }

    public String getDestIP() {
        return DestIP_;
    }

    public String getNextHopIP() {
        return NextHopIP_;
    }

    public void setNextHopIP(String NextHopIP) {
        NextHopIP_ = NextHopIP;
    }

    public int getCost() {
        return Cost_;
    }

    public void setCost(int Cost) {
        Cost_ = Cost;
    }

    public String toString() {
        return "SourceIP: "+ SourceIP_ + "  DestIP: " + DestIP_ + "  NextHopIP:" + NextHopIP_ + "  Cost: " + Cost_;
    }
}
