import React from "react";
import s from "./Logo.module.css"
import {Link} from "react-router-dom";

const Logo = (props) => {
    return <Link to={''} className={s.logo}>SellYourCar</Link>
}

export default Logo;