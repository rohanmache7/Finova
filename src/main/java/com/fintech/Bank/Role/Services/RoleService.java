package com.fintech.Bank.Role.Services;

import com.fintech.Bank.Role.entity.Role;
import com.fintech.Bank.res.Response;

import java.util.List;

public interface RoleService {
    Response<Role>createRole(Role roleRequest);
    Response<Role>updateRole(Role roleRequest);
    Response<List<Role>>getAllRoles();
    Response<?>deleteRole(Long id);
}
