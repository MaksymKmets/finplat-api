package com.flax.finplat.permission;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OperationPermission {
    public static final String CREATE_OPERATION = "CREATE_OPERATION";
    public static final String UPDATE_OPERATION = "UPDATE_OPERATION";
    public static final String DELETE_OPERATION = "DELETE_OPERATION";
    public static final String VIEW_OPERATIONS = "VIEW_OPERATIONS";
}
