import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Firewall {
    private String pathToRulesCSV;
    private Set<String> firewallRules = new HashSet<>(); // Optimizes searching rule for a given IP.
    private static final String cvsSplitBy = ",";

    /**
     * Constructor - Firewall
     *
     * @param pathToRulesCSV
     */
    public Firewall(final String pathToRulesCSV) {
        this.pathToRulesCSV = pathToRulesCSV;
        addRulesToSetFromCSV(); //add rules from csv to hashset.
    }

    /**
     * Method - accept_packet : Check if rule exists for deciding the fate of packet
     *
     * @param direction
     * @param protocol
     * @param port
     * @param ip
     * @return boolean indication about whether the package got accepted or not
     */
    public boolean accept_packet(final String direction, final String protocol, final int port, final String ip) {
        boolean result = false;
        String checkPacket = new String();
        checkPacket = direction + protocol + port + ip;

        result = firewallRules.contains(checkPacket) ? true : false;
        return result;
    }

    /**
     * Method - addRulesToSetFromCSV : Adds the rules from csv to set.
     */
    private void addRulesToSetFromCSV() {
        String csvLine = "";

        try {

            BufferedReader br = new BufferedReader(new FileReader(pathToRulesCSV));

            while ((csvLine = br.readLine()) != null) {

                String[] rule = csvLine.split(cvsSplitBy);

                //handle port range
                List<Integer> portList = new LinkedList<>();
                String port = rule[2];
                if (port.contains("-")) {
                    getListofPort(port, portList);
                } else {
                    portList.add(Integer.valueOf(port));
                }

                // handle ip range
                List<String> ipList = new LinkedList<>();
                String ip = rule[3];
                if (ip.contains("-")) {
                    getListofIP(ip, ipList);
                } else {
                    ipList.add(ip);
                }

                // Add every possible ip and port combination in a range of ip and port specification
                portList.forEach(portInsert -> {

                    ipList.forEach(ipInsert -> {

                        String ruleToInsert = new String();
                        ruleToInsert = rule[0] + rule[1] + portInsert + ipInsert;

                        firewallRules.add(ruleToInsert);
                    });
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method - getListofPort : adds a range of ports into portList
     *
     * @param portRange
     * @param portList
     */
    private void getListofPort(final String portRange, List<Integer> portList) {
        String[] ports = portRange.split("-"); // 10000-10050
        IntStream.range(Integer.valueOf(ports[0]), Integer.valueOf(ports[1])).forEach(i -> {
            portList.add(i);
        });
    }

    /**
     * Method - getListofIP : adds a range of IP's into ipList
     *
     * @param ipRange
     * @param ipList
     */
    private void getListofIP(final String ipRange, List<String> ipList) {
        String[] ips = ipRange.split("-");

        LongStream.range(ipToLong(ips[0]), ipToLong(ips[1])).forEach(i -> {
            ipList.add(longToIp(i));
        });

    }

    /**
     * Method - ipToLong: Convert a given IP address to a long representation (192.168.1.2 -> 3232235778)
     *
     * @param ipAddress
     * @return ip as a long
     */
    public long ipToLong(String ipAddress) {

        String[] ipAddressInArray = ipAddress.split("\\.");

        long result = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {

            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            result += ip * Math.pow(256, power);
        }
        return result;
    }

    /**
     * Method - longToIp: Convert long representation of a IP back to normal String representation (3232235778 -> 192.168.1.2)
     *
     * @param ip
     * @return ip as a string
     */
    public String longToIp(long ip) {
        StringBuilder result = new StringBuilder(15);

        for (int i = 0; i < 4; i++) {

            result.insert(0, Long.toString(ip & 0xff));

            if (i < 3) {
                result.insert(0, '.');
            }

            ip = ip >> 8;
        }
        return result.toString();
    }

}
