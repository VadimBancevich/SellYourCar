import React from "react";
import LoginMenuContainer from "./Header/HeaderMenu/LoginMenu/LoginMenuContainer";

const LoginPage = props => {

    const style = {
        width: "max-content",
        margin: "auto",
        height: "max-content",
        position: "absolute",
        top: "0",
        bottom: "0",
        left: "0",
        right: "0",
        border: "2px solid gray",
        borderRadius: "5px"
    }

    return (
        <div style={style}>
            <LoginMenuContainer {...props}/>
        </div>
    )
}

export default LoginPage;