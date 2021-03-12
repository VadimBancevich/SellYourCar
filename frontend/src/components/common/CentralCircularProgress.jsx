import React from "react";
import s from "./common.module.css"
import CircularProgress from "@material-ui/core/CircularProgress";

const CentralCircularProgress = props => {

    return (
        <div className={s.absCenter}><CircularProgress size={150}/></div>
    )

}

export default CentralCircularProgress;