import React from "react"
import {useSelector} from "react-redux";
import {locales} from "../locales";

const AccessDeniedPage = props => {

    let userRoles = useSelector(state => state.auth.roles);

    return (
        <div>
            {userRoles.length === 0 ?
                <h3>{locales.ru.notActiveAccountNotification}</h3> :
                <h3>{locales.ru.accessDeniedNotification}</h3>
            }
        </div>
    )
}

export default AccessDeniedPage;