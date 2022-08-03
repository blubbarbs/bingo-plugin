package com.gmail.blubberalls.bingo.goal_factories;

import org.bukkit.entity.EntityType;

import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goal.EntityGoal;

public class EntityGoalFactory<T extends EntityGoal> extends NumerableGoalFactory<T> {
    private EntityType type;
    
    public EntityGoalFactory(String name, Class<T> productClass) {
        super(name, productClass);
    }
    
    public EntityType getEntityType() {
        return type;
    }

    public EntityGoalFactory<T> setEntityType(EntityType type) {
        this.type = type;

        return this;
    }

    @Override
    public T createGoal(Game game) {
        T goal = super.createGoal(game);

        goal.setEntityTargetKey(type);

        return goal;
    }

}