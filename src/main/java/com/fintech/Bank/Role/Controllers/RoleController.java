package com.fintech.Bank.Role.Controllers;

import com.fintech.Bank.Role.Services.RoleService;
import com.fintech.Bank.Role.entity.Role;
import com.fintech.Bank.res.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('Admin')")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Response<Role>> createRole(@RequestBody Role roleRequest) {
        return ResponseEntity.ok(roleService.createRole(roleRequest));
    }

    @PutMapping
    public ResponseEntity<Response<Role>> updateRole(@RequestBody Role roleRequest) {
        return ResponseEntity.ok(roleService.updateRole(roleRequest));
    }

    @GetMapping
    public ResponseEntity<Response<List<Role>>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteRole(@PathVariable long id) {
        return ResponseEntity.ok(roleService.deleteRole(id));
    }
}
