package com.example.facilitymanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.Set;

@Entity
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(nullable = false, unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<UserModel> roleUsers;

    public Long getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public Set<UserModel> getRoleUsers() {
        return roleUsers;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setUsers(Set<UserModel> roleUsers) {
        this.roleUsers = roleUsers;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setRoleUsers(Set<UserModel> roleUsers) {
        this.roleUsers = roleUsers;
    }
}
