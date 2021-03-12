import React, {useRef, useState} from "react";
import {useHistory} from "react-router-dom";
import {connect} from "react-redux";
import HeaderMenu from "./HeaderMenu";
import {locales} from "../../../locales";

const HeaderMenuContainer = (props) => {
    const [openMenu, setOpenMenu] = useState(false);
    const [errorText, setErrorText] = useState(null);
    const anchorRef = useRef();
    const history = useHistory();
    const handleOpenMenu = () => {
        setOpenMenu(true);
    }
    const handleCloseMenu = () => {
        setOpenMenu(false);
        setErrorText(null);
    }
    const handleSaleCar = () => {
        if (props.isAuth) {
            history.push('/cars/sale')
        } else {
            handleOpenMenu();
            setErrorText(locales.ru.loginForSaleCarError)
        }
    }

    return <HeaderMenu {...props} openMenu={openMenu} errorText={errorText} anchorRef={anchorRef}
                       handleOpenMenu={handleOpenMenu} handleCloseMenu={handleCloseMenu}
                       handleSaleCar={handleSaleCar}/>
}

const mapStateToProps = (state) => ({
    isAuth: state.auth.isAuth
})

export default connect(mapStateToProps)(HeaderMenuContainer)