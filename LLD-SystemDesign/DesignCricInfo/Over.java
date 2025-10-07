package DesignCricInfo;

import java.util.ArrayList;
import java.util.List;

import DesignCricInfo.enums.BallType;
import DesignCricInfo.enums.DeliveryType;

@SuppressWarnings("unused")
public class Over {
    private final List<Ball> balls = new ArrayList<>();

    public void addBall(Ball ball) {
        balls.add(ball);
    }

    public List<Ball> getBalls() { return balls; }

    public boolean isComplete() {
        return balls.stream().filter(b -> b.getType() == BallType.LEGAL).count() >= 6;
    }
}
