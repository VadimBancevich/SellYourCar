import React from 'react';
import {Button,} from "@material-ui/core"
import AuthenticatedMenu from "./AuthenticatedMenu/AuthenticatedMenu";
import Popover from "@material-ui/core/Popover";
import LoginMenuContainer from "./LoginMenu/LoginMenuContainer";
import s from "./HeaderMenu.module.css"
import {locales} from "../../../locales";

const HeaderMenu = (props) => {

    const {isAuth, handleSaleCar, handleCloseMenu, anchorRef, handleOpenMenu, openMenu, errorText} = props;

    return (
        <div className={s.headerMenu}>
            <Button style={{marginRight: "3vw", whiteSpace: "nowrap"}} onClick={handleSaleCar}>
                {locales.ru.saleCar}
            </Button>
            <Button ref={anchorRef} variant="contained" color="secondary" onClick={handleOpenMenu}>
                {isAuth ? locales.ru.profile : locales.ru.login}
            </Button>
            <Popover anchorOrigin={{vertical: 'bottom', horizontal: 'right'}}
                     transformOrigin={{vertical: 'top', horizontal: 'right'}}
                     open={openMenu} getContentAnchorEl={null} anchorEl={anchorRef.current} onClose={handleCloseMenu}>
                {isAuth ? <AuthenticatedMenu handleClose={handleCloseMenu}/> :
                    <LoginMenuContainer errorText={errorText} handleClose={handleCloseMenu}/>}
            </Popover>
        </div>
    )
}

export default HeaderMenu;