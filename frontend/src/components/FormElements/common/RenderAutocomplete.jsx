import React, {useState} from "react";
import Autocomplete from "@material-ui/lab/Autocomplete";
import TextField from "@material-ui/core/TextField";

const RenderAutocomplete = props => {

    const {
        options, input, placeholder, optionLabelName = "title", valueName = "value",
        onChanged, meta, isRequired = false
    } = props
    const [inputValue, setInputValue] = useState('');

    const handleChange = (e, v) => {
        onChanged && onChanged(e, v)
        input.onChange(v === null ? '' : v[valueName]);
    }

    const getOptionLabel = option => {
        return option[optionLabelName] || options.find(o => o[valueName] === option)[optionLabelName];
    }

    return (
        <Autocomplete options={options} placeholder={placeholder} disabled={options.length === 0}
                      getOptionLabel={getOptionLabel} size={"small"}
                      value={input.value ? input.value : null} inputValue={inputValue}
                      onChange={handleChange}
                      onInputChange={(event, value) => setInputValue(value)}
                      getOptionSelected={(option, value) => option[valueName] === value[valueName] || option[valueName] === value}
                      renderInput={params => <TextField error={meta && meta.touched && meta.invalid}
                                                        helperText={meta && meta.touched && meta.error}
                                                        variant={"outlined"} {...params}
                                                        label={placeholder + (isRequired ? '*' : '')}/>}/>
    )
}

export default RenderAutocomplete;