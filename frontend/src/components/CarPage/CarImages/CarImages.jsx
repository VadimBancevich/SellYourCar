import React, {useEffect} from "react";
import SelectedCarImage from "./SelectedCarImage/SelectedCarImage";
import PreviewCarImages from "./PreviewCarImages/PreviewCarImages";
import s from './CarImage.module.css';
import {resetImages} from "../../../redux/reducers/imagesReducer";
import {useDispatch} from "react-redux";


const CarImages = (props) => {

    const {deleteImage} = props
    const dispatch = useDispatch();

    useEffect(() => () => dispatch(resetImages()), [])

    return (
        <div className={s.content}>
            <SelectedCarImage deleteImage={deleteImage} fields={props.fields}/>
            <PreviewCarImages/>
        </div>
    )
}

export default CarImages;