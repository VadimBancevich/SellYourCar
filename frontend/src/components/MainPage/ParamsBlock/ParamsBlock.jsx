import React, {useEffect} from "react";
import {Field, FieldArray, Fields} from "redux-form";
import Accordion from "@material-ui/core/Accordion";
import AccordionSummary from "@material-ui/core/AccordionSummary";
import Typography from "@material-ui/core/Typography";
import AccordionDetails from "@material-ui/core/AccordionDetails";
import EngineVolumeSliderContainer
    from "../../FormElements/SearchCarForm/EngineVolumeSlider/EngineVolumeSliderContainer";
import Mileage from "../../FormElements/SearchCarForm/Mileage/Mileage";
import Years from "../../FormElements/SearchCarForm/Years";
import FormControlCheckboxes from "../../FormElements/SearchCarForm/FormControlCheckboxes";
import s from "./ParamsBlock.module.css";
import Sort from "../../FormElements/SearchCarForm/Sort";
import Button from "@material-ui/core/Button";
import {locales} from "../../../locales";
import BrandModelGenerationTest from "../../FormElements/BrandModelGenerationTest";
import {formsStaticContent, getLocalizedOptions} from "../../FormElements/formsStaticContent";
import RenderAutocomplete from "../../FormElements/common/RenderAutocomplete";

const ParamsBlock = (props) => {
    const {handleSubmit, handleFormSubmit, page, setCarPage} = props;

    const submitForm = () => {
        props.dispatch(props.submit('findCars'));
    }

    useEffect(() => submitForm(), [page])

    const handleSubmitClick = () => {
        if (page !== 1) {
            props.change('page', 1);
            setCarPage(1);
        } else {
            submitForm();
        }
    }

    return (
        <div>
            <form onSubmit={handleSubmit(handleFormSubmit)}>
                <div className={s.mainParamsBlock}>
                    <div className={s.brandModelGeneration}>
                        <Fields names={["brandId", "modelId", "generationId"]} component={BrandModelGenerationTest}/>
                    </div>
                    <div className={s.years}>
                        <Fields names={["yearFrom", "yearTo"]} component={Years}/>
                    </div>
                    <div className={s.submitReset}>
                        <Button type={"button"} size={"small"} variant={"contained"}
                                onClick={handleSubmitClick}
                                color={"primary"}>{locales.ru.find}</Button>
                        <Button type={"reset"} size={"small"} variant={"outlined"} color={"primary"}
                                onClick={props.reset}>{locales.ru.reset}</Button>
                    </div>
                </div>
                <Accordion className={s.accordionWidth} style={{margin: "0 auto"}}>
                    <AccordionSummary>
                        <Typography>Больше параметров</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <div className={s.additionalParamsBlock}>
                            <div className={s.engine}>
                                <FieldArray name={'engine'} component={FormControlCheckboxes}/>
                            </div>
                            <div className={s.engineVolume}>
                                <Fields names={["engineVolumeFrom", "engineVolumeTo"]}
                                        component={EngineVolumeSliderContainer}/>
                            </div>
                            <div className={s.gas}>
                                <FieldArray name={'gas'} component={FormControlCheckboxes}/>
                            </div>
                            <div className={s.transmission}>
                                <FieldArray name={'transmission'} component={FormControlCheckboxes}/>
                            </div>

                            <div className={s.drivetrain}>
                                <FieldArray name={'drivetrain'} component={FormControlCheckboxes}/>
                            </div>

                            <div className={s.condition}>
                                <FieldArray name={'condition'} component={FormControlCheckboxes}/>
                            </div>

                            <div className={s.mileage}>
                                <Fields names={["mileageFrom", "mileageTo"]} component={Mileage}/>
                            </div>
                            <div className={s.bodyType}>
                                <Field name={"bodyType"} options={getLocalizedOptions(formsStaticContent.bodyType)}
                                       placeholder={locales.ru.bodyType} component={RenderAutocomplete}/>
                            </div>
                            <div className={s.sort}>
                                <Fields names={["sortBy", "sortDir"]} component={Sort}/>
                            </div>
                        </div>
                    </AccordionDetails>
                </Accordion>
            </form>
        </div>
    )
}

export default ParamsBlock;

