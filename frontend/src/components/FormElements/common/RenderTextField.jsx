import {TextField} from "@material-ui/core";
import React from "react";

const RenderTextField = ({label, input, type}) => {
    return (
        <TextField type={type} label={label} placeholder={label} {...input} variant={"outlined"}/>
    )
}

export default RenderTextField;