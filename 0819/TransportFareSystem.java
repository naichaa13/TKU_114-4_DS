abstract class Transport {
    private String routeName;

    Transport(String routeName) {
        this.routeName = routeName;
    }

    String getRouteName() {
        return routeName;
    }

    abstract int calculateFare(int distance);
}

class Bus extends Transport {
    private int baseFare;

    Bus(String routeName, int baseFare) {
        super(routeName);
        this.baseFare = Math.max(0, baseFare);
    }

    @Override
    int calculateFare(int distance) {
        int safeDistance = Math.max(0, distance);
        return baseFare + safeDistance * 2;
    }
}

class Taxi extends Transport {
    private int flagfall;

    Taxi(String routeName, int flagfall) {
        super(routeName);
        this.flagfall = Math.max(0, flagfall);
    }

    @Override
    int calculateFare(int distance) {
        int safeDistance = Math.max(0, distance);
        return flagfall + safeDistance * 10;
    }
}

public class TransportFareSystem {
    public static void main(String[] args) {
        Transport[] transports = {
                new Bus("Red 1", 15),
                new Bus("Blue 2", 15),
                new Taxi("City Taxi", 85),
                new Taxi("Night Taxi", 100)
        };

        int distance = 12;

        for (Transport transport : transports) {
            System.out.println(transport.getRouteName() + " 票價：" +
                    transport.calculateFare(distance));
        }
    }
}