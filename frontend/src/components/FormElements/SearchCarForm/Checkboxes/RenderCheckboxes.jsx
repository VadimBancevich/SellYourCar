import React from "react";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";

const RenderCheckboxes = (props) => {
    const {fields} = props;

    const handleChange = (event, checked) => {
        const targetValue = event.target.value;
        if (checked) {
            fields.push(targetValue);
        } else {
            fields.forEach((name, index, fields) => {
                if (fields.get(index) === targetValue) {
                    fields.remove(index);
                }
            });
        }
    }

    const isChecked = (value) => {
        const values = fields.getAll();
        return values && values.find(c => c === value);
    }

    return (
        <>
            {props.checkboxData.map(checkbox => {
                return <FormControlLabel key={checkbox.value} label={checkbox.title}
                                         control={<Checkbox key={checkbox.value} value={checkbox.value}
                                                            checked={Boolean(isChecked(checkbox.value))}
                                                            onChange={handleChange}/>}/>
            })}
        </>
    )
}


export default RenderCheckboxes;
