package miran.blog.zk;

import org.apache.curator.test.InstanceSpec;
import org.apache.curator.test.TestingCluster;
import org.apache.curator.test.TestingZooKeeperServer;

import java.io.File;

public class TestingClusterDemo {
    // server1 configuration
    String server1DataPath = "./data/1";
    int server1Port = 2081;
    int server1ElectionPort = 12081;
    int server1QuorumPort = 22081;

    // server2 configuration
    String server2DataPath = "./data/2";
    int server2Port = 2082;
    int server2ElectionPort = 12082;
    int server2QuorumPort = 22082;

    // server3 configuration
    String server3DataPath = "./data/3";
    int server3Port = 2083;
    int server3ElectionPort = 12083;
    int server3QuorumPort = 22083;

    TestingZooKeeperServer leader = null;
    TestingCluster cluster = null;
    String connectString = null;

    public String getConnectString() {
        if (cluster == null) {
            throw new RuntimeException("Zookeeper cluster is not started yet");
        }
        connectString = cluster.getConnectString();
        return connectString;
    }

    public void startCluster() throws Exception {
        InstanceSpec instanceSpec1 = new InstanceSpec(new File(server1DataPath), server1Port, server1ElectionPort, server1QuorumPort, false, 1);
        InstanceSpec instanceSpec2 = new InstanceSpec(new File(server2DataPath), server2Port, server2ElectionPort, server2QuorumPort, false, 2);
        InstanceSpec instanceSpec3 = new InstanceSpec(new File(server3DataPath), server3Port, server3ElectionPort, server3QuorumPort, false, 3);

        TestingCluster cluster = new TestingCluster(instanceSpec1, instanceSpec2, instanceSpec3);
        cluster.start();
        Thread.sleep(2000l);

        System.out.println("-----------------------------------------------------------");
        for (TestingZooKeeperServer server : cluster.getServers()) {
            System.out.print(server.getInstanceSpec().getServerId() + "-");
            System.out.print(server.getQuorumPeer().getServerState() + "-");
            System.out.print(server.getInstanceSpec().getDataDirectory().getAbsolutePath());

            //System.out.println("connectString: " + cluster.getConnectString());

            if ("leading".equals(server.getQuorumPeer().getServerState())) {
                leader = server;
            }
        }
        System.out.println("-----------------------------------------------------------");
    }

    public void stopCluster() throws Exception {
        if (cluster != null) {
            cluster.stop();
        }
        System.out.println("Zookeeper cluster stopped");
    }

    public void testElection() throws Exception {
        leader.kill();
        Thread.sleep(2000l);

        System.out.println("--after leader kill--");
        for (TestingZooKeeperServer server : cluster.getServers()) {
            System.out.print(server.getInstanceSpec().getServerId() + "-");
            System.out.print(server.getQuorumPeer().getServerState() + "-");
            System.out.print(server.getInstanceSpec().getDataDirectory().getAbsolutePath());

            System.out.println();
        }

        cluster.stop();
    }


}
