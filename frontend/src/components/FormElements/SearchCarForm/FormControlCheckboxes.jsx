import React from "react";
import FormControl from "@material-ui/core/FormControl";
import FormLabel from "@material-ui/core/FormLabel";
import RenderCheckboxes from "./Checkboxes/RenderCheckboxes";
import {formsStaticContent, getLocalizedOptions} from "../formsStaticContent";
import {locales} from "../../../locales";

const FormControlCheckboxes = (props) => {

    const name = props.fields.name;

    return (
        <FormControl>
            <FormLabel>{locales.ru[name]}</FormLabel>
            <RenderCheckboxes checkboxData={getLocalizedOptions(formsStaticContent[name])} {...props}/>
        </FormControl>
    )
}

export default FormControlCheckboxes;