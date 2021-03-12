import React, {useState} from "react";
import s from "./SelectedCarImage.module.css";
import {ArrowBack, ArrowForward, DeleteForever} from "@material-ui/icons";
import IconButton from "@material-ui/core/IconButton";
import {connect} from "react-redux";
import {defImg, nextImage, prevImage} from "../../../../redux/reducers/imagesReducer";

const SelectedCarImage = (props) => {
    const {imagesCount, prevImage, nextImage, deleteImage, image, defaultImage, carId, selectedImageIndex} = props;
    const [showArrows, setShowArrows] = useState(false);
    const handleDeleteImage = () => {
        props.fields.remove(selectedImageIndex)
        deleteImage(image, carId)
    }
    return (
        <div className={s.content} onMouseEnter={() => setShowArrows(true)}
             onMouseLeave={() => setShowArrows(false)}>
            {deleteImage && imagesCount > 0 && image !== defaultImage &&
            <div className={s.deleteButton}>
                <IconButton onClick={handleDeleteImage}>
                    <DeleteForever fontSize={"small"}/>
                </IconButton>
            </div>}
            <div style={{left: "0", display: showArrows && imagesCount > 1 ? '' : "none"}}
                 className={s.arrowContainerNew}>
                <IconButton style={{color: "darkgray"}} hidden={showArrows}
                            onClick={prevImage}><ArrowBack/></IconButton>
            </div>
            <div style={{right: "0", display: showArrows && imagesCount > 1 ? '' : "none"}}
                 className={s.arrowContainerNew}>
                <IconButton style={{color: "darkgray"}} hidden={showArrows}
                            onClick={nextImage}><ArrowForward/></IconButton>
            </div>
            <img onError={event => event.target.src = defImg} className={s.selectedImage} src={image}/>


        </div>
    )
}

const mapDispatchToProps = dispatch => ({
    nextImage: () => dispatch(nextImage()),
    prevImage: () => dispatch(prevImage())
})

const mapStateToProps = state => ({
    image: state.images.images[state.images.selectedImageIndex],
    defaultImage: state.images.defaultImage,
    imagesCount: state.images.images.length,
    selectedImageIndex: state.images.selectedImageIndex,
    carId: state.updateCar.car ? state.updateCar.car.id : null
})

export default connect(mapStateToProps, mapDispatchToProps)(SelectedCarImage);
