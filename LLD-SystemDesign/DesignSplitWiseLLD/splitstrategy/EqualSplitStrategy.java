package DesignSplitWiseLLD.splitstrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import DesignSplitWiseLLD.Split;
import DesignSplitWiseLLD.User;

public class EqualSplitStrategy implements SplitStrategy{

    @Override
    public List<Split> calculateSplits(Map<User, Double> splitData, double totalAmount) {
        int numberOfUsers = splitData.size();
        double equalAmount = totalAmount/numberOfUsers;

        List<Split> splits = new ArrayList<>();
        for(User user : splitData.keySet()){
            splits.add(new Split(user, equalAmount)) ;
        }

        return splits;
    }
    
}
