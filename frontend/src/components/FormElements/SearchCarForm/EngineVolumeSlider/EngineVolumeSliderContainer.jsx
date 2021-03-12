import React, {useEffect, useState} from "react";
import EngineVolumeSlider from "./EngineVolumeSlider";
import {locales} from "../../../../locales";
import s from "./EngineVolumeSlider.module.css";
import FormControl from "@material-ui/core/FormControl";
import FormLabel from "@material-ui/core/FormLabel";


const minValue = 0;
const maxValue = 8;
const step = 0.1;

const EngineVolumeSliderContainer = (props) => {
    const [valueFrom, setValueFrom] = useState(minValue);
    const [valueTo, setValueTo] = useState(maxValue);

    const {engineVolumeFrom, engineVolumeTo} = props;

    useEffect(() => !engineVolumeFrom.input.value && setValueFrom(minValue), [engineVolumeFrom.input.value])
    useEffect(() => !engineVolumeTo.input.value && setValueTo(maxValue), [engineVolumeTo.input.value])

    const handleCommittedChanges = () => {
        props["engineVolumeFrom"].input.onChange(valueFrom === minValue ? '' : valueFrom * 1000);
        props["engineVolumeTo"].input.onChange(valueTo === maxValue ? '' : valueTo * 1000);
    }

    const handleChangeValueFrom = (event, value) => {
        setValueFrom(value)
        if (value >= valueTo) {
            setValueTo(value);
        }
    }
    const handleChangeValueTo = (event, value) => {
        setValueTo(value)
        if (value <= valueFrom) {
            setValueFrom(value);
        }
    }

    return (
        <div className={s.engineVolumeBlock}>
            <FormControl>
                <FormLabel>{locales.ru.engineVolume}</FormLabel>
                <div className={s.buttonsBlock}>
                    <EngineVolumeSlider value={valueFrom} step={step} max={maxValue} min={minValue} btnValue={minValue}
                                        onChange={handleChangeValueFrom} onChangeCommitted={handleCommittedChanges}
                                        buttonPrefix={locales.ru.from}/>
                    <EngineVolumeSlider value={valueTo} step={step} max={maxValue} min={minValue} btnValue={maxValue}
                                        onChange={handleChangeValueTo} onChangeCommitted={handleCommittedChanges}
                                        buttonPrefix={locales.ru.to}/>
                </div>
            </FormControl>
        </div>

    )
}

export default EngineVolumeSliderContainer;