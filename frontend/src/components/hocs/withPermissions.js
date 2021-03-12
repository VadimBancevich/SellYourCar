import React from "react";
import {useSelector} from "react-redux";
import {Redirect} from "react-router-dom";
import AccessDeniedPage from "../AccessDeniedPage";

const hasUserRequiredRole = (requiredRoles, userRoles) => {
    if (typeof requiredRoles === 'string') {
        return userRoles.includes(requiredRoles);
    } else if (Array.isArray(requiredRoles)) {
        requiredRoles.forEach(requiredRole => {
            if (userRoles.includes(requiredRole)) {
                return true;
            }
        })
    } else {
        throw new Error("RequiredRoles must be either string or array of strings")
    }
    return false;
}

export const withPermissions = (requiredRoles) => {
    return Component => props => {
        const {isAuth, roles} = useSelector(state => state.auth)

        if (!isAuth) {
            return <Redirect to={'/login'}/>
        }

        if (requiredRoles && !hasUserRequiredRole(requiredRoles, roles)) {
            return <AccessDeniedPage/>
        }

        return <Component {...props}/>
    }
}

export const withRoleUser = Component => props => {
    return withPermissions("ROLE_USER")(Component)(props)
}

export const withAuth = Component => props => {
    return withPermissions()(Component)(props)
}
