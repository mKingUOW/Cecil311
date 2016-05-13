package com.scrumSystem.role;

/**
 * Creates appropriate role for the user that has logged in
 * Created by Matt on 10/05/2016.
 * @author Matt King
 */
public class RoleFactory
{
    /**
     * Private constructor prevents instance creation
     */
    private RoleFactory() {}

    /**
     * Creates a new role for the logged in user based on the type passed in
     * @param roleType The role type to be created
     * @return The role created
     */
    public static Role createRole(String roleType)
    {
        Role role;

        switch(roleType)
        {
            case "PO":
                role = new ProductOwner();
                break;
            case "SM":
                role = new ScrumMaster();
                break;
            case "SA":
                //may need to create and add references within the system admin role to other role
                //types to give admin their functionality
                role = new SystemAdmin();
                break;
            case "TM":
                role = new TeamMember();
                break;
            default:
                role = null;
                break;
        }

        return role;
    }
}
