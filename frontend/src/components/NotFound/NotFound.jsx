import React from "react";
import s from "./NotFound.module.css"
import {Link} from "react-router-dom";
import Button from "@material-ui/core/Button";

const NotFound = props => {

    return (
        <div className={s.container}>
            <div className={s.code}>404</div>
            <div className={s.message}>Page not found</div>
            <Link to={"/"}>Home</Link>
            <Button size={"small"} variant={"text"} onClick={() => props.history.goBack()}>Back</Button>
        </div>
    )
}

export default NotFound;