package tpbitcoin;

import org.bitcoinj.core.Sha256Hash;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class HashRateEstimator {
    private final int duration; //duration of each experience, in milliseconds
    private final int numberOfTries; // number of experience

    /** Create a new object for estimating the number of SHA256 hashs the host can perform per second
    ** @param duration: (milli-seconds) duration of each experiment
     * @param numberOfTries : number of experiments to run
     */
    public HashRateEstimator(int duration, int numberOfTries) {
        this.duration = duration;
        this.numberOfTries = numberOfTries;
    }

    /**
     * @return : return the hashrate (hash/second)
     */
    public double estimate(){
        byte[] bytes = new byte[256];
        MessageDigest md = Sha256Hash.newDigest();
        int c ;
        ArrayList<Integer> allC = new ArrayList<>();
        Random random =new Random();
        long currentTime;
        for (int i = 0; i < numberOfTries; i++) {
            currentTime = System.currentTimeMillis();
            c = 0;
            while (System.currentTimeMillis() - currentTime <= duration){
                random.nextBytes(bytes);
                c++;
                md.digest(bytes);
            }
        allC.add(c);

        }
        double avg = 0;
        for (int i : allC) {
            avg+=i;
        }
        return (avg / allC.size())/(duration/1000);
    }

}
