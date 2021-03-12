import React from "react";
import Button from "@material-ui/core/Button";
import {Link, useHistory} from "react-router-dom";
import {resetUserData} from "../../../../redux/reducers/authReducer";
import {connect} from "react-redux";
import {authAPI} from "../../../../api/api";
import {locales} from "../../../../locales";

const AuthenticatedMenu = (props) => {

    const history = useHistory();
    const {handleClose} = props;

    const handleLogout = () => {
        authAPI.logout().then(() => {
                props.resetUserData();
                history.push('/');
                handleClose();
            }
        )
    }

    return (
        <div>
            <div>
                <Link to={'/cars/my'}>
                    <Button fullWidth={true} onClick={handleClose}>{locales.ru.myCars}</Button>
                </Link>
            </div>
            <div>
                <Link to={'/messages'}>
                    <Button fullWidth={true} onClick={handleClose}>{locales.ru.messages}</Button>
                </Link>
            </div>
            <div>
                <Button fullWidth={true} onClick={handleLogout}>{locales.ru.logout}</Button>
            </div>
        </div>
    )
}

export default connect(null, {resetUserData})(AuthenticatedMenu);