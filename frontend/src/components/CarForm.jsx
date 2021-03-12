import React from "react";
import s from "./CreateAddPage/SaleCarPage.module.css";
import {Field, FieldArray, Fields} from "redux-form";
import AddImage from "./CreateAddPage/AddImage/AddImage";
import {capitalizeFirstLetter, formsStaticContent, getLocalizedOptions} from "./FormElements/formsStaticContent";
import {locales} from "../locales";
import Textarea from "./FormElements/Textarea/Textarea";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import BrandModelGenerationTest from "./FormElements/BrandModelGenerationTest";
import InputAdornment from "@material-ui/core/InputAdornment";
import RenderAutocomplete from "./FormElements/common/RenderAutocomplete";

const RenderTextField = (props) => {
    const {placeholder, isRequired, startAdornmentValue, adornmentPosition = "start"} = props;

    return (
        <TextField  {...props} {...props.input} error={props.meta.touched && props.meta.invalid}
                    helperText={props.meta.touched && props.meta.error} size={"small"}
                    variant={"outlined"} placeholder={placeholder} label={placeholder + (isRequired ? '*' : '')}
                    InputProps={{
                        startAdornment: startAdornmentValue ?
                            <InputAdornment position={adornmentPosition}>{startAdornmentValue}</InputAdornment> : null
                    }}/>
    )
}

const getVolumes = () => {
    let result = [];
    for (let i = 0.1; i <= 10.0; i += 0.1) {
        result.push({
            value: Math.round(i * 1000), title: (i.toFixed(1) + ' ' + locales.ru.l)
        })
    }
    return result;
}
const getYears = () => {
    const years = [];
    for (let i = new Date().getFullYear(); i >= 1930; i--) {
        years.push({value: i, title: String(i)})
    }
    return years;
}

const requireValidate = (value, allValues, props, name) => {
    return allValues[name] ? null : locales.ru.requireError
}

const CarForm = props => {

    const {submitHandler, reset, showReset = false} = props;

    return (
        <form className={s.mainBlock} onSubmit={props.handleSubmit((formData) => submitHandler(formData))}>
            <FieldArray name={"images"} component={AddImage}/>
            <div className={s.paramsBlock}>

                <Fields names={["brandId", "modelId", "generationId"]} validate={requireValidate} isRequired={true}
                        component={BrandModelGenerationTest}/>

                <Field name={'year'} options={getYears()} placeholder={locales.ru.year} isRequired={true}
                       component={RenderAutocomplete} validate={requireValidate}/>

                <Field name={'bodyType'} options={getLocalizedOptions(formsStaticContent.bodyType)}
                       placeholder={locales.ru.bodyType} component={RenderAutocomplete} isRequired={true}
                       validate={requireValidate}/>

                <Field name={'engine'} options={getLocalizedOptions(formsStaticContent.engine)}
                       validate={requireValidate} isRequired={true}
                       placeholder={locales.ru.engine} component={RenderAutocomplete}/>

                <Field name={'engineVolume'} options={getVolumes()} validate={requireValidate} isRequired={true}
                       placeholder={locales.ru.engineVolume} component={RenderAutocomplete}/>

                <Field name={'gas'} required={false} options={getLocalizedOptions(formsStaticContent.gas)}
                       placeholder={locales.ru.gas} component={RenderAutocomplete}/>

                <Field name={'transmission'} options={getLocalizedOptions(formsStaticContent.transmission)}
                       validate={requireValidate} isRequired={true}
                       placeholder={locales.ru.transmission} component={RenderAutocomplete}/>

                <Field name={'drivetrain'} options={getLocalizedOptions(formsStaticContent.drivetrain)}
                       validate={requireValidate} isRequired={true}
                       placeholder={locales.ru.drivetrain} component={RenderAutocomplete}/>

                <Field name={'mileage'} type={"number"} placeholder={locales.ru.mileage} validate={requireValidate}
                       startAdornmentValue={capitalizeFirstLetter(locales.ru.km)} component={RenderTextField}/>

                <Field name={'condition'} options={getLocalizedOptions(formsStaticContent.condition)}
                       validate={requireValidate} isRequired={true}
                       placeholder={locales.ru.condition} component={RenderAutocomplete}/>

                <Field name={'vin'} placeholder={locales.ru.vin} component={RenderTextField}/>

                <Field name={'phone'} placeholder={locales.ru.phoneNumber} component={RenderTextField}/>

                <Field name={'sellerName'} placeholder={locales.ru.name} component={RenderTextField}/>

                <Field name={'price'} startAdornmentValue={"$"} validate={requireValidate} type={'number'}
                       placeholder={locales.ru.price}
                       component={RenderTextField} isRequired={true}/>

                <Field name={'description'} className={s.textarea} placeholder={locales.ru.description}
                       component={Textarea}/>

                <div>
                    <Button type={"submit"} variant={"contained"} color={"primary"}>Submit</Button>
                    {showReset && <Button style={{marginLeft: "5px"}} type={"reset"} variant={"outlined"}
                                          onClick={() => reset()} color={"secondary"}>Reset</Button>}
                </div>
            </div>
        </form>
    )
}

export default CarForm