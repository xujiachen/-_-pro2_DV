public class LocalRouteTable {
    static private RouteTable table;

    static RouteTable getTable() {
        if (table == null)
            table = new RouteTable();

        return table;
    }

    static void show() {
        table.show();
    }
}
