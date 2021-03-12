import React from "react";
import s from "../CarImage.module.css";
import {connect} from "react-redux";
import {defImg, selectImageByIndex} from "../../../../redux/reducers/imagesReducer";

const PreviewCarImages = (props) => {
    const {images, selectedImageIndex, selectImageByIndex} = props;

    return (
        <div className={s.previewImages}>
            {images.length > 0 ? images.map((image, index) => {
                let classes = s.previewImage + ' ' + (Number(selectedImageIndex) === index ? s.selectedPreviewImage : '');
                return (
                    <img onError={event => event.target.src = defImg} key={index} onClick={() => selectImageByIndex(index)}
                         className={classes} src={image}/>
                )
            }) : ''}
        </div>
    )
}

const mapStateToProps = state => ({
    images: state.images.images,
    selectedImageIndex: state.images.selectedImageIndex
})
export default connect(mapStateToProps, {selectImageByIndex})(PreviewCarImages);