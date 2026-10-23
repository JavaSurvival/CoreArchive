package net.corearchive.api;

import net.corearchive.model.item.ItemTransactionActions;

/**
 * Container transaction actions used by typed container lookup filters.
 */
public enum ContainerAction {
    REMOVE(ItemTransactionActions.REMOVE),
    ADD(ItemTransactionActions.ADD);

    private final int id;

    ContainerAction(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }
}
