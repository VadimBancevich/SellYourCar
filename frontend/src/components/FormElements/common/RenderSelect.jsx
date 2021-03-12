import React from "react";
import Select from "@material-ui/core/Select";
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";

const RenderSelect = (props) => {
    let options;
    if (props.options.length === 0) {
        props.input.onChange('all');
        props.setter('all');
    } else {
        options = props.options.map(option => <option key={option.id} value={option.id}>
            {option[props.textContentName]}</option>)
    }
    return (
        <FormControl variant="outlined" style={{margin: '0 10px 10px 10px'}}>
            <InputLabel>{props.selectLabel}</InputLabel>
            <Select style={{width: '200px'}} label={props.selectLabel} {...props.input}
                    onChange={event => {
                        props.input.onChange(event.target.value)
                        props.setter(event.target.value)
                    }} native={true} variant={"outlined"}>
                <option key={'all'} value={'all'}>Все</option>
                {options}
            </Select>
        </FormControl>
    )
}

export default RenderSelect;