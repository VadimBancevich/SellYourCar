import React, {useEffect, useState} from "react";
import MenuItem from "@material-ui/core/MenuItem";
import FormControl from "@material-ui/core/FormControl";
import FormLabel from "@material-ui/core/FormLabel";
import TextField from "@material-ui/core/TextField";
import {locales} from "../../../locales";

const Sort = (props) => {

    const {sortBy, sortDir} = props;

    const [value, setValue] = useState(1);

    useEffect(() => !sortBy.input.value && !sortDir.input.value && setValue(1),
        [sortBy.input.value, sortDir.input.value])

    const setSortBy = sortBy.input.onChange;
    const setSortDir = sortDir.input.onChange;

    const handleRelevant = () => {
        setSortBy('');
        setSortDir('');
    }
    const handleChip = () => {
        setSortBy('price')
        setSortDir('');
    }
    const handleExpensive = () => {
        setSortBy('price');
        setSortDir('desc')
    }
    const handleNewest = () => {
        setSortBy('year');
        setSortDir('desc')
    }
    const handleOldest = () => {
        setSortBy('year');
        setSortDir('')
    }

    const handleChange = (event) => {
        setValue(event.target.value);
    }

    return (
        <>
            <FormControl fullWidth={true}>
                <FormLabel style={{marginBottom: '8px'}}>{locales.ru.sort}</FormLabel>
                <TextField select={true} value={value} size={"small"} onChange={handleChange} variant={"outlined"}>
                    <MenuItem onClick={handleRelevant} value={1}>{locales.ru.relevant}</MenuItem>
                    <MenuItem onClick={handleChip} value={2}>{locales.ru.chip}</MenuItem>
                    <MenuItem onClick={handleExpensive} value={3}>{locales.ru.expensive}</MenuItem>
                    <MenuItem onClick={handleNewest} value={4}>{locales.ru.newest}</MenuItem>
                    <MenuItem onClick={handleOldest} value={5}>{locales.ru.oldest}</MenuItem>
                </TextField>
            </FormControl>
        </>
    )
}
export default Sort;