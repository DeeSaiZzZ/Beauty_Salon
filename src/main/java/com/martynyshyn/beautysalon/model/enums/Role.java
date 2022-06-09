package com.martynyshyn.beautysalon.model.enums;

import com.martynyshyn.beautysalon.model.User;

/**
 * Role entity.
 *
 * @author N.Martynyshyn
 *
 */

public enum Role {
    ADMIN,
    MASTER,
    DEFAULT;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId - 1];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
