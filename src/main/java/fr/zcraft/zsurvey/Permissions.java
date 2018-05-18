package fr.zcraft.zsurvey;

import org.bukkit.permissions.Permissible;

public enum Permissions
{
    USER("zsurvey.user"),
    ADMIN("zsurvey.admin");

    private String permission;

    Permissions(String permission)
    {
        this.permission = permission;
    }

    public String getPermission()
    {
        return permission;
    }

    public boolean grantedTo(Permissible permissible)
    {
        return permissible.hasPermission(permission);
    }
}