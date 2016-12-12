class IP {
    private int ip0;
    private int ip1;
    private int ip2;
    private int ip3;

    public IP(int a, int b, int c, int d) {
        ip0 = a;
        ip1 = b;
        ip2 = c;
        ip3 = d;
    }

    // IP need to be as a.b.c.d
    public IP(String ipStr) {
        String[] strArray = ipStr.split("\\.");
        ip0 = Integer.valueOf(strArray[0]);
        ip1 = Integer.valueOf(strArray[1]);
        ip2 = Integer.valueOf(strArray[2]);
        ip3 = Integer.valueOf(strArray[3]);
    }

    public String toString() {
        return Integer.toString(ip0) + ":"
                + Integer.toString(ip1) + ":"
                + Integer.toString(ip2) + ":"
                + Integer.toString(ip3);
    }

    public String show() {
        int count = 3;

        count++;
        if (ip0 >= 10)
            count++;
        if (ip0 >= 100)
            count++;

        count++;
        if (ip1 >= 10)
            count++;
        if (ip1 >= 100)
            count++;

        count++;
        if (ip2 >= 10)
            count++;
        if (ip2 >= 100)
            count++;

        count++;
        if (ip3 >= 10)
            count++;
        if (ip3 >= 100)
            count++;

        String ipStr = "";
        for (int i = 0; i < 15 - count; i++)
            ipStr += " ";

        return Integer.toString(ip0) + ":"
                + Integer.toString(ip1) + ":"
                + Integer.toString(ip2) + ":"
                + Integer.toString(ip3) + ipStr;
    }
}
