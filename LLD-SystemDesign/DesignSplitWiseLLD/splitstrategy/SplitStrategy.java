package DesignSplitWiseLLD.splitstrategy;

import java.util.List;
import java.util.Map;

import DesignSplitWiseLLD.Split;
import DesignSplitWiseLLD.User;

public interface SplitStrategy {
    List<Split> calculateSplits(Map<User, Double> splitData, double totalAmount);
}
