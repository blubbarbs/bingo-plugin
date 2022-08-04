package com.gmail.blubberalls.bingo.goal_factories;

import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goal.Goal;

public class NumerableGoalFactory<T extends Goal> extends GoalFactory<T> {
    private int max = 1;
    private int min = 1;

    public NumerableGoalFactory(String name, Class<T> productClass) {
        super(name, productClass);
    }
    
    public NumerableGoalFactory<T> withMinimum(int min) {
        this.min = min;

        return this;
    }

    public NumerableGoalFactory<T> withMaximum(int max) {
        this.max = max;

        return this;
    }

    @Override
    public T createGoal(Game game) {
        T goal = super.createGoal(game);
        int randomNumber = game.getRandom().nextInt(min, max);

        goal.setGoalNumber(randomNumber);

        return goal;
    }

}
