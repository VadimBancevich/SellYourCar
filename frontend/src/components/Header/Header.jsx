import React from 'react';
import {AppBar} from "@material-ui/core"
import Toolbar from "@material-ui/core/Toolbar";
import Logo from "./Logo/Logo";
import HeaderMenuContainer from "./HeaderMenu/HeaderMenuContainer";
import s from "./Header.module.css"

const Header = (props) => {

    return (
        <AppBar position="static" className={s.zIndex}>
            <Toolbar>
                <Logo/>
                <HeaderMenuContainer/>
            </Toolbar>
        </AppBar>
    )
}
export default Header;
