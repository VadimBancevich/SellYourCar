import React, {useEffect, useState} from "react";
import Autocomplete from "@material-ui/lab/Autocomplete";
import TextField from "@material-ui/core/TextField";
import {locales} from "../../../locales";

const AC = (props) => {
    const {options, onChange, label, labelName, placeholder, meta} = props;
    const [value, setValue] = useState(null);

    useEffect(() => {
        if (!options || options.length <= 0) {
            setValue(null);
        }
    }, [options])

    const handleChange = (event, value) => {
        if (value) {
            onChange(value.id)
            setValue(value)
        } else {
            onChange('')
            setValue(null)
        }
    }
    return <Autocomplete {...props} options={options} disabled={options.length <= 0}
                         onChange={handleChange}
                         getOptionLabel={option => {
                             return option[labelName] ? option[labelName] : ""
                         }}
                         getOptionSelected={(option, value1) => option.id === value1.id}
                         value={value}
                         renderInput={params => <TextField variant={"outlined"} placeholder={placeholder} label={label}
                                                           {...params} error={meta.touched && meta.invalid}
                                                           helperText={meta.touched && meta.error}/>}/>
}

const BrandModelGeneration = (props) => {
    const {brands, models, generations, setSelectedBrandId, setSelectedModelId, setSelectedGenerationId} = props;
    return (
        <>
            <AC options={brands} onChange={setSelectedBrandId} meta={props.brandId.meta} name={props.brandId.input.name}
                label={locales.ru.brand} labelName={"brandName"}
                placeholder={locales.ru.brand}/>
            <AC options={models} onChange={setSelectedModelId} meta={props.modelId.meta}
                label={locales.ru.model} labelName={"modelName"}
                placeholder={locales.ru.model}/>
            <AC options={generations} onChange={setSelectedGenerationId} meta={props.generationId.meta}
                label={locales.ru.generation} labelName={"generationName"}
                placeholder={locales.ru.generation}/>
        </>
    )
}


export default BrandModelGeneration;