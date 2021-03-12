import React, {useState} from "react";
import Popper from "@material-ui/core/Popper";
import Paper from "@material-ui/core/Paper";
import Slider from "@material-ui/core/Slider";
import Button from "@material-ui/core/Button";
import ClickAwayListener from "@material-ui/core/ClickAwayListener";
import {locales} from "../../../../locales";

const EngineVolumeSlider = (props) => {
    const {value, step, max, min, onChange, onChangeCommitted, buttonPrefix, btnValue} = props;

    const [anchor, setAnchor] = useState(null);

    return (
        <ClickAwayListener onClickAway={() => setAnchor(null)}>
                    <span>
                        <Popper open={Boolean(anchor)} anchorEl={anchor} placement={"top"}>
                            <Paper>
                                <Slider value={value} step={step} max={max} style={{width: "20em"}}
                                        min={min} onChange={onChange}
                                        valueLabelFormat={value => value + ' ' + locales.ru.l + '.'}
                                        valueLabelDisplay={"on"}
                                        onChangeCommitted={onChangeCommitted}/>
                            </Paper>
                        </Popper>
                        <Button variant={"outlined"} size={"small"} onClick={event => setAnchor(anchor ? null : event.target)}>
                            {value === btnValue ? buttonPrefix : buttonPrefix + ' ' + value + locales.ru.l + '.'}
                        </Button>
                    </span>
        </ClickAwayListener>
    )
}

export default EngineVolumeSlider;