package fr.zcraft.zsurvey;

import org.bukkit.permissions.Permissible;

public enum Permissions
{
    USER("zsurvey.user"),	//Donne le droit de voter et creer des sondages
    ADMIN("zsurvey.admin");	//Donne le droit tous les droits

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