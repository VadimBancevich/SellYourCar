import React from "react";
import s from "./Mileage.module.css"
import {locales} from "../../../../locales";
import FormControl from "@material-ui/core/FormControl";
import FormLabel from "@material-ui/core/FormLabel";
import TextField from "@material-ui/core/TextField";

const minMileage = 0;
const maxMileage = 1000000;

const Mileage = (props) => {

    const validateMileage = (value) => {
        if (value >= minMileage && value <= maxMileage) {
            return value;
        }
    }

    const handleChange = (event, setter) => {
        let value = event.target.value;
        if (!value) {
            setter('');
        } else if (validateMileage(Number(value))) {
            setter(value)
        }
    }

    const handleBlurFrom = (event) => {
        let value = event.target.value;
        if (Number(value) > Number(props.mileageTo.input.value)) {
            props.mileageTo.input.onChange('')
        }
    }

    const handleBlurTo = (event) => {
        let value = event.target.value;
        if (Number(value) < Number(props.mileageFrom.input.value)) {
            props.mileageFrom.input.onChange('')
        }
    }

    return (
        <FormControl>
            <FormLabel>{locales.ru.mileageKm}</FormLabel>
            <div className={s.mileageBlock}>
                <TextField {...props.mileageFrom.input} size={"small"} type={"number"} onBlur={handleBlurFrom} variant={"outlined"}
                           placeholder={locales.ru.from} label={locales.ru.from}
                           onChange={event => handleChange(event, props.mileageFrom.input.onChange)}/>
                <TextField {...props.mileageTo.input} type={"number"} onBlur={handleBlurTo} variant={"outlined"}
                           placeholder={locales.ru.to} label={locales.ru.to} size={"small"}
                           onChange={event => handleChange(event, props.mileageTo.input.onChange)}/>
            </div>
        </FormControl>
    )
}

export default Mileage